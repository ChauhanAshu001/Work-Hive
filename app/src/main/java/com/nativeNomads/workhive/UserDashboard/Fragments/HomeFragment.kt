package com.nativeNomads.workhive.UserDashboard.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.UserDashboard.Adapter.JobListingAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */



class HomeFragment : Fragment() {
    lateinit var adapter: JobListingAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var companyName:ArrayList<String>
    lateinit var jobTitle:ArrayList<String>

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference =database.reference.child("jobs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyName= arrayListOf()
        jobTitle= arrayListOf()

        recyclerView=view.findViewById(R.id.recyclerViewJobs)
        recyclerView.layoutManager= LinearLayoutManager(context)
        adapter=JobListingAdapter(companyName,jobTitle,requireActivity())
        recyclerView.adapter=adapter


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(companies in snapshot.children){
                    for(job in companies.children) {

                        val nameOfCompany = job.child("companyName").value as String
                        val jobRole = job.child("role").value as String
                        companyName.add(nameOfCompany)
                        jobTitle.add(jobRole)
                    }
                }


                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
//        adapter= JobListingAdapter(companyName,jobTitle,requireContext())
//        recyclerView.adapter=adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */

    }
}