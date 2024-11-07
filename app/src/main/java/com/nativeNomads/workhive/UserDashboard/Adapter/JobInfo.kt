package com.nativeNomads.workhive.UserDashboard.Adapter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nativeNomads.workhive.EmployerDashBoard.Data.Application
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.UserDashboard.Fragments.BookmarkedFragment

class JobInfo : AppCompatActivity() {

    private lateinit var applyNow: Button
    private lateinit var userId: String
    private lateinit var jobId: String
    lateinit var companyName: String
    lateinit var role: String
    lateinit var jobType: String
    lateinit var location: String
    lateinit var salary: String
    lateinit var deadline: String
    lateinit var experience: String
    lateinit var qualification: String
    lateinit var responsibilities: String
    lateinit var description: String
    lateinit var benefits: String
    lateinit var title: String
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference = database.reference.child("Applied jobs")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_info)

        jobId = intent.getStringExtra("JOB_ID").orEmpty()
        companyName = intent.getStringExtra("COMPANY_NAME").toString()
        role = intent.getStringExtra("JOB_ROLE").toString()
        jobType = intent.getStringExtra("JOB_TYPE").toString()
        location = intent.getStringExtra("JOB_LOCATION").toString()
        salary = intent.getStringExtra("JOB_SALARY").toString()
        deadline = intent.getStringExtra("JOB_DEADLINE").toString()
        experience = intent.getStringExtra("JOB_EXPERIENCE").toString()
        qualification = intent.getStringExtra("JOB_QUALIFICATION").toString()
        responsibilities = intent.getStringExtra("JOB_RESPONSIBILITIES").toString()
        description = intent.getStringExtra("COMPANY_DESCRIPTION").toString()
        benefits = intent.getStringExtra("JOB_BENEFITS").toString()
        title = intent.getStringExtra("JOB_TITLE").toString()
        val companyId = intent.getStringExtra("COMPANY_ID").toString()

        setJobData()

        userId = "user3"
        applyNow = findViewById(R.id.applyButton)

        applyNow.setOnClickListener {

            val userDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

            userDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: ""
                        val experience = snapshot.child("experience").getValue(String::class.java) ?: ""
                        val qualification = snapshot.child("qualifications").getValue(String::class.java) ?: ""
                        val skills = snapshot.child("skills").getValue(String::class.java) ?: ""

                        val application = Application(
                            applicationId = jobId,
                            name = name,
                            status = "Applied",
                            userId = userId,
                            qualification = qualification,
                            skills = skills,
                            experience = experience,
                            companyName = companyName
                        )

                        reference.child(companyId).child(userId).setValue(application)
                            .addOnSuccessListener {
                                Toast.makeText(this@JobInfo, "Applied Successfully", Toast.LENGTH_SHORT).show()
                                applyNow.isEnabled = false
                                applyNow.text = "Applied"
                                applyNow.setBackgroundColor(resources.getColor(R.color.green2, theme))
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@JobInfo, "Failed to Apply", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this@JobInfo, "User data not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@JobInfo, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        findViewById<Button>(R.id.bookmarkButton).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("COMPANY_NAME", companyName)
            bundle.putString("LOCATION", location)
            bundle.putString("SALARY", salary)
            bundle.putString("JOB_TYPE", jobType)
            bundle.putString("JOB_TITLE", title)
            val fragment = BookmarkedFragment()
            fragment.arguments = bundle

        }
    }

    private fun setJobData() {
        findViewById<TextView>(R.id.companyName).text = companyName
        findViewById<TextView>(R.id.jobRole).text = role
        findViewById<TextView>(R.id.jobType).text = jobType
        findViewById<TextView>(R.id.jobLocation).text = location
        findViewById<TextView>(R.id.salary).text = salary
        findViewById<TextView>(R.id.applicationDeadline).text = deadline
        findViewById<TextView>(R.id.experienceNeeded).text = experience
        findViewById<TextView>(R.id.qualificationsNeeded).text = qualification
        findViewById<TextView>(R.id.responsibilities).text = responsibilities
        findViewById<TextView>(R.id.companyDescription).text = description
        findViewById<TextView>(R.id.benefits).text = benefits
    }
}
