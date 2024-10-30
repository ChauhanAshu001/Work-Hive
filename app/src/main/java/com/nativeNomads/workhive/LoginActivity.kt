package com.nativeNomads.workhive

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
import com.google.firebase.auth.FirebaseUser
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
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userType = getUserType() // Retrieve stored user type
            navigateToNextActivity(currentUser)
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
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
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
                    user?.let { navigateToNextActivity(it) }
                } else {
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToNextActivity(user: FirebaseUser) {
        // Check user type and navigate accordingly
        val intent = if (userType == "jobSeeker") {
            Intent(this, EmployerActivity::class.java) // Use the correct Activity for job seekers
        } else {
            Intent(this, MainActivity::class.java) // Use the correct Activity for employers
        }
        startActivity(intent)
        finish()
    }

    private fun saveUserType(userType: String) {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_type", userType)
            apply()
        }
    }

    private fun getUserType(): String {
        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPref.getString("user_type", "jobSeeker") ?: "jobSeeker" // Default to "jobSeeker"
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
