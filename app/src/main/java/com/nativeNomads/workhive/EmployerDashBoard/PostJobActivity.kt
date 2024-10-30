package com.nativeNomads.workhive.EmployerDashBoard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R

class PostJobActivity : AppCompatActivity() {

    private lateinit var jobTitleEditText: EditText
    private lateinit var jobLocationEditText: EditText
    private lateinit var jobStatusEditText: EditText
    private lateinit var jobSalaryEditText: EditText
    private lateinit var jobExperienceEditText: EditText
    private lateinit var postJobButton: Button

    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)

        // Initialize UI elements
        jobTitleEditText = findViewById(R.id.jobTitleEditText)
        jobLocationEditText = findViewById(R.id.jobLocationEditText)
        jobStatusEditText = findViewById(R.id.jobStatusEditText)
        jobSalaryEditText = findViewById(R.id.jobSalaryEditText) // Initialize new field
        jobExperienceEditText = findViewById(R.id.jobExperienceEditText) // Initialize new field
        postJobButton = findViewById(R.id.postJobButton)

        // Set up the post job button click listener
        postJobButton.setOnClickListener {
            postJob()
        }
    }

    private fun postJob() {
        val jobTitle = jobTitleEditText.text.toString().trim()
        val jobLocation = jobLocationEditText.text.toString().trim()
        val jobStatus = jobStatusEditText.text.toString().trim()
        val jobSalary = jobSalaryEditText.text.toString().trim() // Get salary
        val jobExperience = jobExperienceEditText.text.toString().trim() // Get experience

        // Validate fields
        if (!validateFields(jobTitle, jobLocation, jobStatus, jobSalary, jobExperience)) return

        val userId = "2" // Get the logged-in user's ID
        if (userId.isNullOrEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        database.child("companies").child(userId).child("companyName").get().addOnSuccessListener { snapshot ->
            val companyName = snapshot.value?.toString() ?: "Unknown Company"

            val jobId = database.child("jobs").push().key ?: return@addOnSuccessListener // Generate a unique key
            val job = Job(jobId, jobTitle, jobLocation, companyName, jobStatus, userId, jobSalary, jobExperience)

            // Save job to Realtime Database
            database.child("jobs").child(jobId).setValue(job)
                .addOnSuccessListener {
                    Toast.makeText(this, "Job posted successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error posting job: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve company name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateFields(
        jobTitle: String,
        jobLocation: String,
        jobStatus: String,
        jobSalary: String,
        jobExperience: String
    ): Boolean {
        return when {
            jobTitle.isEmpty() -> {
                Toast.makeText(this, "Job title cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobLocation.isEmpty() -> {
                Toast.makeText(this, "Job location cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobStatus.isEmpty() -> {
                Toast.makeText(this, "Job status cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobSalary.isEmpty() -> {
                Toast.makeText(this, "Job salary cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobExperience.isEmpty() -> {
                Toast.makeText(this, "Job experience cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
