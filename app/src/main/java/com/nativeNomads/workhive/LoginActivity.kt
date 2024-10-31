package com.nativeNomads.workhive

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.nativeNomads.workhive.EmployerDashBoard.EmployerActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var userType: String? = null // Class property to store userType
    lateinit var sharedPreferences: SharedPreferences


    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
        // Check if user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {

            navigateToNextActivity(userType)
        }

        // Set click listeners for the buttons
        findViewById<Button>(R.id.button3).setOnClickListener {
            userType = "jobSeeker" // Set userType
            signInUser()
        }
        findViewById<Button>(R.id.button).setOnClickListener {
            userType = "employer" // Set userType
            signInUser()
        }
    }

    private fun signInUser() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if(resultCode==Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
            else if(resultCode==Activity.RESULT_CANCELED){
                Toast.makeText(applicationContext,"unable to SignIn",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
            // Store user type for navigation after login
            userType?.let { saveUserType(it) } // Save userType only if it's not null
        } catch (e: ApiException) {
            Toast.makeText(this, "Sign in failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to next activity
                    val user = auth.currentUser
                    user?.let { navigateToNextActivity(userType) }
                } else {
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToNextActivity(userType: String?) {
        // Check user type and navigate accordingly
        val intent = if (userType == "jobSeeker") {
            Intent(this, EmployerActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun saveUserType(userType: String) {
        // Save user type in SharedPreferences or any other persistent storage
        // Implement logic to store userType
        sharedPreferences=this.getSharedPreferences("saveUserType", Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putString("key userType",userType)
        editor.apply()
        Toast.makeText(applicationContext,"You are saved as  $userType",Toast.LENGTH_SHORT).show()
    }

    private fun getUserType() {
        sharedPreferences=this.getSharedPreferences("saveUserType",Context.MODE_PRIVATE)
        userType=sharedPreferences.getString("key userType",null)

    }

    override fun onPause() {
        super.onPause()
        saveUserType(userType.toString())
    }

    override fun onResume() {
        super.onResume()
        getUserType()
    }

}