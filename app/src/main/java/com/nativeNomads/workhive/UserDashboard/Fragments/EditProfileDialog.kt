package com.nativeNomads.workhive.UserDashboard.Fragments

import android.app.Dialog
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
import com.nativeNomads.workhive.R


class EditProfileDialog : DialogFragment() {
    lateinit var name:EditText
    lateinit var experience:EditText
    lateinit var qualifications:EditText
    lateinit var skills:EditText
    lateinit var cancel:Button
    lateinit var save:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        val view:View=inflater.inflate(R.layout.fragment_edit_profile_dialog, container, false)
        name=view.findViewById(R.id.editTextName)
        experience=view.findViewById(R.id.editTextExperience)
        qualifications=view.findViewById(R.id.editTextQualifications)
        skills=view.findViewById(R.id.editTextSkills)
        cancel=view.findViewById(R.id.buttonCancel)
        save=view.findViewById(R.id.buttonSave)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        save.setOnClickListener{
            val name=name.text.toString()
            val experience=experience.text.toString()
            val qualifications=qualifications.text.toString()
            val skills=skills.text.toString()

            val profileFragment = ProfileFragment()
            profileFragment.setData(name,experience,qualifications,skills)
            dialog?.dismiss()


        }
        cancel.setOnClickListener {
            dialog?.dismiss()
        }

        return view
    }

    companion object {

    }
}