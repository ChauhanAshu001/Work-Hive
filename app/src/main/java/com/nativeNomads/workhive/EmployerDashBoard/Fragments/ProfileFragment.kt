package com.nativeNomads.workhive.EmployerDashBoard.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.EmployerDashBoard.CompleteProfileActivity
import com.nativeNomads.workhive.EmployerDashBoard.PostJobActivity
import com.nativeNomads.workhive.R

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileCompany: TextView
    private lateinit var completeProfileButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var postJobButton: Button

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        profileImage = view.findViewById(R.id.profileImage)
        profileName = view.findViewById(R.id.profileName)
        profileCompany = view.findViewById(R.id.profileCompany)
        completeProfileButton = view.findViewById(R.id.completeProfileButton)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        postJobButton = view.findViewById(R.id.postJobButton)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Load user profile information
        loadUserProfile()

        // Set up button click listeners
        completeProfileButton.setOnClickListener {
            // Navigate to CompleteProfileActivity
            val intent = Intent(requireContext(), CompleteProfileActivity::class.java)
            startActivity(intent)
        }


        postJobButton.setOnClickListener {
            // Navigate to PostJobActivity
            val intent = Intent(requireContext(), PostJobActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child("users").child(userId).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").value.toString()
                    val company = dataSnapshot.child("company").value.toString()
                    profileName.text = name
                    profileCompany.text = company
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        }
    }
}