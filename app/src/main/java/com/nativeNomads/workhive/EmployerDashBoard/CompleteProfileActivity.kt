package com.nativeNomads.workhive.EmployerDashBoard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.R

class CompleteProfileActivity : AppCompatActivity() {

    private lateinit var companyEditText: EditText
    private lateinit var employerNameEditText: EditText
    private lateinit var completeButton: Button
    private val userId = "3" // Replace with actual user ID if needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        companyEditText = findViewById(R.id.companyEditText)
        employerNameEditText = findViewById(R.id.employerNameEditText)
        completeButton = findViewById(R.id.completeButton)


        completeButton.setOnClickListener {
            val companyName = companyEditText.text.toString()
            val employerName = employerNameEditText.text.toString()

            if (companyName.isNotEmpty() && employerName.isNotEmpty()) {
                saveProfileToDatabase(companyName, employerName)
            } else {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveProfileToDatabase(companyName: String, employerName: String) {
        val database = FirebaseDatabase.getInstance().reference
        val userId = "3"

        val userProfile = mapOf(
            "company" to companyName,
            "name" to employerName
        )

        database.child("companies").child(userId).setValue(userProfile).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile completed!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error completing profile", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
