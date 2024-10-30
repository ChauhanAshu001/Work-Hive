package com.nativeNomads.workhive.EmployerDashBoard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.EmployerDashBoard.Adapters.JobAdapter
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R

class JobsFragment : Fragment() {

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private val jobList = mutableListOf<Job>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_jobs, container, false)

        // Initialize RecyclerView and set layout manager
        jobRecyclerView = view.findViewById(R.id.jobsRecyclerView)
        jobRecyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data for testing
        jobList.add(Job("1", "Software Engineer", "Shanghai", "Open for applications", "user123"))
        jobList.add(Job("2", "Data Scientist", "Beijing", "Closed", "user124"))

        // Set up adapter
        jobAdapter = JobAdapter(jobList)
        jobRecyclerView.adapter = jobAdapter

        return view
    }
}
