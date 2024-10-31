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
    private lateinit var completeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        companyEditText = findViewById(R.id.companyEditText)
        completeButton = findViewById(R.id.completeButton)

        completeButton.setOnClickListener {
            val companyName = companyEditText.text.toString()

            if (companyName.isNotEmpty()) {
                // Assuming you are using Firebase Realtime Database to store the company name
                val userId = "2"
                val database = FirebaseDatabase.getInstance().reference

                // Store the company name under the userId
                database.child("companies").child(userId!!).child("companyName").setValue(companyName).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Profile completed!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity and return to previous screen
                    } else {
                        Toast.makeText(this, "Error completing profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a company name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
