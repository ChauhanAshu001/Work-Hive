package com.nativeNomads.workhive.EmployerDashBoard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.EmployerDashBoard.Adapters.ApplicationAdapter
import com.nativeNomads.workhive.EmployerDashBoard.Data.Application
import com.nativeNomads.workhive.R

class ApplicationsFragment : Fragment() {

    private lateinit var applicationRecyclerView: RecyclerView
    private lateinit var applicationAdapter: ApplicationAdapter
    private val applicationList = mutableListOf<Application>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_applications, container, false)

        // Initialize RecyclerView
        applicationRecyclerView = view.findViewById(R.id.applicationsRecyclerView)
        applicationRecyclerView.layoutManager = LinearLayoutManager(context)

        // Sample data for testing
        applicationList.add(Application("1", "Alice Johnson", "Software Engineer", "Under Review", "user123"))
        applicationList.add(Application("2", "Bob Smith", "Data Scientist", "Interview Scheduled", "user124"))

        // Set up adapter
        applicationAdapter = ApplicationAdapter(applicationList)
        applicationRecyclerView.adapter = applicationAdapter

        return view
    }
}
