package com.nativeNomads.workhive.EmployerDashBoard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.nativeNomads.workhive.R

class CompleteProfileActivity : AppCompatActivity() {

    private lateinit var companyEditText: EditText
    private lateinit var employerNameEditText: EditText
    private lateinit var companyImageView: ImageView
    private lateinit var completeButton: Button
    private var imageUri: Uri? = null
    val userId = "3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_profile)

        companyEditText = findViewById(R.id.companyEditText)
        employerNameEditText = findViewById(R.id.employerNameEditText)
        companyImageView = findViewById(R.id.companyImageView)
        completeButton = findViewById(R.id.completeButton)

        // Fetch profile data from Firebase and populate fields
        loadProfileData()

        // Set up click listener to open image selector
        companyImageView.setOnClickListener {
            selectImage()
        }

        // Handle profile completion with image upload
        completeButton.setOnClickListener {
            val companyName = companyEditText.text.toString()
            val employerName = employerNameEditText.text.toString()

            if (companyName.isNotEmpty() && employerName.isNotEmpty()) {
                uploadImageToFirestore(companyName, employerName)
            } else {
                Toast.makeText(this, "Please complete all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadProfileData() {
        val database = FirebaseDatabase.getInstance().reference
        // Replace with actual user ID

        database.child("companies").child(userId).get().addOnSuccessListener { snapshot ->
            val companyName = snapshot.child("companyName").value?.toString() ?: "Unknown Company"
            val employerName = snapshot.child("employerName").value?.toString() ?: "Unknown Employer"
            val imageUrl = snapshot.child("imageUrl").value?.toString()

            // Populate EditText fields
            companyEditText.setText(companyName)
            employerNameEditText.setText(employerName)

            // Load image if available
            if (!imageUrl.isNullOrEmpty()) {
                loadImageFromUrl(imageUrl)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImageFromUrl(url: String) {
        // Use your preferred image loading library, such as Glide or Picasso
        // For example, with Glide:
        Glide.with(this).load(url).into(companyImageView)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data
            companyImageView.setImageURI(imageUri) // Show selected image
        }
    }

    private fun uploadImageToFirestore(companyName: String, employerName: String) {
        val storageReference = FirebaseStorage.getInstance().reference.child("company_images/${userId}.jpg")

        imageUri?.let { uri ->
            storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                    saveProfileToDatabase(companyName, employerName, imageUrl.toString())
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfileToDatabase(companyName: String, employerName: String, imageUrl: String) {
        val database = FirebaseDatabase.getInstance().reference
        val userId = "3" // Replace with actual user ID

        val userProfile = mapOf(
            "companyName" to companyName,
            "employerName" to employerName,
            "imageUrl" to imageUrl
        )

        database.child("companies").child(userId).setValue(userProfile).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Profile completed!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity and return to previous screen
            } else {
                Toast.makeText(this, "Error completing profile", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
