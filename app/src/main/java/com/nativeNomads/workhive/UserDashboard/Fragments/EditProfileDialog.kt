package com.nativeNomads.workhive.UserDashboard.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.R


class EditProfileDialog : DialogFragment() {

    interface EditProfileListener {
        fun onProfileUpdated(name: String, experience: String, qualifications: String, skills: String)
    }

    private var listener: EditProfileListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? EditProfileListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_edit_profile_dialog, container, false)
        val name: EditText = view.findViewById(R.id.editTextName)
        val experience: EditText = view.findViewById(R.id.editTextExperience)
        val qualifications: EditText = view.findViewById(R.id.editTextQualifications)
        val skills: EditText = view.findViewById(R.id.editTextSkills)
        val cancel: Button = view.findViewById(R.id.buttonCancel)
        val save: Button = view.findViewById(R.id.buttonSave)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        save.setOnClickListener {
            val userId = "user3"
            if (userId != null) {
                val databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

                // Create a map for the updated profile data
                val updatedData = mapOf(
                    "name" to name.text.toString(),
                    "experience" to experience.text.toString(),
                    "qualifications" to qualifications.text.toString(),
                    "skills" to skills.text.toString()
                )

                databaseRef.setValue(updatedData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        dialog?.dismiss()
                    } else {
                        // Handle any errors (you could show a Toast here)
                    }
                }
            }
        }


        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
