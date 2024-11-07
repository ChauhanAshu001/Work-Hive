package com.nativeNomads.workhive.UserDashboard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nativeNomads.workhive.R

class ProfileFragment : Fragment(), EditProfileDialog.EditProfileListener {

    private lateinit var nameTextView: TextView
    private lateinit var experienceTextView: TextView
    private lateinit var qualificationTextView: TextView
    private lateinit var skillsTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.textViewName)
        experienceTextView = view.findViewById(R.id.textViewExperience)
        qualificationTextView = view.findViewById(R.id.textViewQualifications)
        skillsTextView = view.findViewById(R.id.textViewSkills)

        val userId = "user3"
        if (userId != null) {
            val databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val name = snapshot.child("name").getValue(String::class.java) ?: ""
                        val experience = snapshot.child("experience").getValue(String::class.java) ?: ""
                        val qualifications = snapshot.child("qualifications").getValue(String::class.java) ?: ""
                        val skills = snapshot.child("skills").getValue(String::class.java) ?: ""

                        nameTextView.text = name
                        experienceTextView.text = experience
                        qualificationTextView.text = qualifications
                        skillsTextView.text = skills
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        val editProfile: Button = view.findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            val editProfileDialog = EditProfileDialog()
            editProfileDialog.show(childFragmentManager, "EditProfileDialog")
        }
    }


    override fun onProfileUpdated(name: String, experience: String, qualifications: String, skills: String) {
        nameTextView.text = name
        experienceTextView.text = experience
        qualificationTextView.text = qualifications
        skillsTextView.text = skills
    }

}
