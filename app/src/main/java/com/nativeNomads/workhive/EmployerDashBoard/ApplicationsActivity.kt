package com.nativeNomads.workhive.EmployerDashBoard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.R
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Collections


class ApplicationsActivity : AppCompatActivity() {
    private lateinit var applicantName: TextView
    private lateinit var positionApplied: TextView
    private lateinit var experience: TextView
    private lateinit var skills: TextView
    private lateinit var status: TextView
    private lateinit var takeForInterviewButton: Button
    private lateinit var confirmApplicationButton: Button
    private lateinit var hireButton: Button

    private lateinit var userId: String
    private lateinit var companyId: String

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var appliedJobsReference: DatabaseReference
    private lateinit var hiredJobsReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applications)

        // Initialize TextViews and Buttons
        applicantName = findViewById(R.id.textName)
        positionApplied = findViewById(R.id.textAppliedFor)
        experience = findViewById(R.id.textExperience)
        skills = findViewById(R.id.textViewSkills)
        status = findViewById(R.id.textStatus)
        takeForInterviewButton = findViewById(R.id.buttonTakeForInterview)
        confirmApplicationButton = findViewById(R.id.buttonConfirmApplication)
        hireButton = findViewById(R.id.buttonHire)

        // Retrieve the individual properties from the Intent
        val applicantNameText = intent.getStringExtra("name") ?: "N/A"
        val positionAppliedText = intent.getStringExtra("companyName") ?: "N/A"
        val experienceText = intent.getStringExtra("experience") ?: "N/A"
        val skillsText = intent.getStringExtra("skills") ?: "N/A"
        val statusText = intent.getStringExtra("status") ?: "N/A"
        userId = intent.getStringExtra("userId") ?: "N/A"
        companyId = "3"

        // Set the data to the TextViews
        applicantName.text = applicantNameText
        positionApplied.text = "Position: $positionAppliedText"
        experience.text = "Experience: $experienceText"
        skills.text = "Skills: $skillsText"
        status.text = "Status: $statusText"

        // Initialize Firebase references
        appliedJobsReference = database.reference.child("Applied jobs").child(companyId).child(userId)
        hiredJobsReference = database.reference.child("Hired jobs").child(companyId).child(userId)

        // Set click listeners for the buttons
        takeForInterviewButton.setOnClickListener {
            updateApplicationStatus("Under Interview")
            takeForInterviewButton.visibility = View.GONE
            confirmApplicationButton.visibility = View.GONE
        }

        confirmApplicationButton.setOnClickListener {
            updateApplicationStatus("Confirmed Apllication")
            confirmApplicationButton.visibility = View.GONE
        }

        hireButton.setOnClickListener {
            updateApplicationStatus("Hired")
            hireButton.visibility = View.GONE
            takeForInterviewButton.visibility = View.GONE
            confirmApplicationButton.visibility = View.GONE

        }

        val config = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallService.init(application,AppConstants.appId,AppConstants.appSign,companyId,companyId,config)

        val videoCallButton = findViewById<ZegoSendCallInvitationButton>(R.id.zegoSendCallInvitationButton)

        videoCallButton.setIsVideoCall(true)
        videoCallButton.setResourceID("zego_uikit_call")

        videoCallButton.setInvitees(Collections.singletonList(ZegoUIKitUser(userId,userId)))



    }

    private fun updateApplicationStatus(newStatus: String) {

        appliedJobsReference.child("status").setValue(newStatus).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                status.text = "Status: $newStatus"

                when (newStatus) {
                    "Under Interview" -> {
                        status.setBackgroundColor(Color.YELLOW)
                    }
                    "Confirmed" -> {
                        status.setBackgroundColor(Color.GREEN)
                    }
                    "Hired" -> {
                        status.setBackgroundColor(Color.MAGENTA)
                    }
                    else -> {
                        status.setBackgroundColor(Color.WHITE)
                    }
                }

                if (newStatus == "Hired") {

                    appliedJobsReference.removeValue()
                    hiredJobsReference.setValue(true)
                }
            } else {

                status.text = "Status: Error updating status"
                status.setBackgroundColor(Color.RED)
            }
        }
    }
}
