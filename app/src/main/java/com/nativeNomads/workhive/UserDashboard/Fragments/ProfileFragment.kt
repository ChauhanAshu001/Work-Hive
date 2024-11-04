package com.nativeNomads.workhive.UserDashboard.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.nativeNomads.workhive.R


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    lateinit var nameTextView :TextView
    lateinit var experienceTextView:TextView
    lateinit var qualificationTextView:TextView
    lateinit var skillsTextView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editProfile: Button =view.findViewById(R.id.editProfile)
        nameTextView= view.findViewById(R.id.textViewName)
        experienceTextView=view.findViewById(R.id.textViewExperience)
        qualificationTextView=view.findViewById(R.id.textViewQualifications)
        skillsTextView=view.findViewById(R.id.textViewSkills)

        editProfile.setOnClickListener{
            val fragmentManager:FragmentManager=requireActivity().supportFragmentManager
            val editProfileDialog=EditProfileDialog()
            editProfileDialog.show(fragmentManager,"EditProfileDialog")

        }
    }

    fun setData(name:String,experience:String,qualifications:String,skills:String){
        nameTextView.text=name
        experienceTextView.text=experience
        qualificationTextView.text=qualifications
        skillsTextView.text=skills

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */

    }
}