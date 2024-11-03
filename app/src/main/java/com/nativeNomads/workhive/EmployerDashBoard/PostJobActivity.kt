package com.nativeNomads.workhive.EmployerDashBoard

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R

class PostJobActivity : AppCompatActivity() {

    private lateinit var jobTitleEditText: TextInputEditText
    private lateinit var jobRoleEditText: TextInputEditText
    private lateinit var jobLocationEditText: TextInputEditText
    private lateinit var jobStatusEditText: TextInputEditText
    private lateinit var jobSalaryEditText: TextInputEditText
    private lateinit var jobExperienceEditText: TextInputEditText
    private lateinit var jobDescriptionEditText: TextInputEditText
    private lateinit var jobTypeEditText: TextInputEditText
    private lateinit var jobQualificationsEditText: TextInputEditText
    private lateinit var jobResponsibilitiesEditText: TextInputEditText
    private lateinit var jobBenefitsEditText: TextInputEditText
    private lateinit var jobApplicationDeadlineEditText: TextInputEditText
    private lateinit var postJobButton: Button

    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_job)

        // Initialize UI elements
        jobTitleEditText = findViewById(R.id.jobTitleEditText)
        jobRoleEditText = findViewById(R.id.jobRoleEditText)
        jobLocationEditText = findViewById(R.id.jobLocationEditText)
        jobStatusEditText = findViewById(R.id.jobStatusEditText)
        jobSalaryEditText = findViewById(R.id.jobSalaryEditText)
        jobExperienceEditText = findViewById(R.id.jobExperienceEditText)
        jobDescriptionEditText = findViewById(R.id.jobDescriptionEditText)
        jobTypeEditText = findViewById(R.id.jobTypeEditText)
        jobQualificationsEditText = findViewById(R.id.jobQualificationsEditText)
        jobResponsibilitiesEditText = findViewById(R.id.jobResponsibilitiesEditText)
        jobBenefitsEditText = findViewById(R.id.jobBenefitsEditText)
        jobApplicationDeadlineEditText = findViewById(R.id.jobApplicationDeadlineEditText)
        postJobButton = findViewById(R.id.postJobButton)

        // Set up the post job button click listener
        postJobButton.setOnClickListener {
            postJob()
        }
    }

    private fun postJob() {
        val jobTitle = jobTitleEditText.text.toString().trim()
        val jobRole = jobRoleEditText.text.toString().trim()
        val jobLocation = jobLocationEditText.text.toString().trim()
        val jobStatus = jobStatusEditText.text.toString().trim()
        val jobSalary = jobSalaryEditText.text.toString().trim()
        val jobExperience = jobExperienceEditText.text.toString().trim()
        val jobDescription = jobDescriptionEditText.text.toString().trim()
        val jobType = jobTypeEditText.text.toString().trim()
        val jobQualifications = jobQualificationsEditText.text.toString().trim()
        val jobResponsibilities = jobResponsibilitiesEditText.text.toString().trim()
        val jobBenefits = jobBenefitsEditText.text.toString().trim()
        val jobApplicationDeadline = jobApplicationDeadlineEditText.text.toString().trim()

        if (!validateFields(
                jobTitle, jobRole, jobLocation, jobStatus, jobSalary,
                jobExperience, jobDescription, jobType, jobQualifications,
                jobResponsibilities, jobBenefits, jobApplicationDeadline
            )
        ) return

        val userId ="3"  // Replace with Firebase UID or default
        if (userId.isEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        database.child("companies").child(userId).child("companyName").get().addOnSuccessListener { snapshot ->
            val companyName = snapshot.value?.toString() ?: "Unknown Company"

            val jobId = database.child("jobs").push().key ?: return@addOnSuccessListener
            val job = Job(
                jobId,
                jobTitle,
                jobRole,
                jobLocation,
                companyName,
                jobStatus,
                userId,
                jobSalary,
                jobExperience,
                jobDescription,
                jobType,
                jobQualifications,
                jobResponsibilities,
                jobBenefits,
                jobApplicationDeadline
            )

            // Save job to Realtime Database
            database.child("jobs").child(userId).child(jobId).setValue(job)
                .addOnSuccessListener {
                    Toast.makeText(this, "Job posted successfully", Toast.LENGTH_SHORT).show()
                    finish()
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
        jobRole: String,
        jobLocation: String,
        jobStatus: String,
        jobSalary: String,
        jobExperience: String,
        jobDescription: String,
        jobType: String,
        jobQualifications: String,
        jobResponsibilities: String,
        jobBenefits: String,
        jobApplicationDeadline: String
    ): Boolean {
        return when {
            jobTitle.isEmpty() -> {
                Toast.makeText(this, "Job title cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobRole.isEmpty() -> {
                Toast.makeText(this, "Job role cannot be empty", Toast.LENGTH_SHORT).show()
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
            jobDescription.isEmpty() -> {
                Toast.makeText(this, "Job description cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobType.isEmpty() -> {
                Toast.makeText(this, "Job type cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobQualifications.isEmpty() -> {
                Toast.makeText(this, "Qualifications cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobResponsibilities.isEmpty() -> {
                Toast.makeText(this, "Responsibilities cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobBenefits.isEmpty() -> {
                Toast.makeText(this, "Benefits cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            jobApplicationDeadline.isEmpty() -> {
                Toast.makeText(this, "Application deadline cannot be empty", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
