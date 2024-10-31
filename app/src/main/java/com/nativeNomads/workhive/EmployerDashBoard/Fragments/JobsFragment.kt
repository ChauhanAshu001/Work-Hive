package com.nativeNomads.workhive.EmployerDashBoard.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nativeNomads.workhive.EmployerDashBoard.Adapters.JobAdapter
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R

class JobsFragment : Fragment() {

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private val jobList = mutableListOf<Job>()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val userId = "2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_jobs, container, false)

        jobRecyclerView = view.findViewById(R.id.jobsRecyclerView)
        jobRecyclerView.layoutManager = LinearLayoutManager(context)

        jobAdapter = JobAdapter(jobList, this)
        jobRecyclerView.adapter = jobAdapter

        fetchJobs()

        return view
    }

    private fun fetchJobs() {
        // Use the userId to fetch jobs for the specific user
        database.child("jobs").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear() // Clear existing job list
                if (snapshot.exists()) { // Check if snapshot has children
                    for (jobSnapshot in snapshot.children) {
                        val job = jobSnapshot.getValue(Job::class.java)
                        if (job != null) {
                            job.jobId = jobSnapshot.key.toString()
                            jobList.add(job)
                        }
                    }

                    jobRecyclerView.adapter=JobAdapter(jobList,this@JobsFragment)
                } else {
                    Toast.makeText(context, "No jobs found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load jobs: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
