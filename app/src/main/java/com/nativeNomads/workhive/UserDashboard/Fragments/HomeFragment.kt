package com.nativeNomads.workhive.UserDashboard.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.UserDashboard.Adapter.JobListingAdapter

class HomeFragment : Fragment() {
    private lateinit var adapter: JobListingAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchViewJobs: SearchView
    private val jobs = mutableListOf<Job>()
    private val filteredJobs = mutableListOf<Job>()

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.reference.child("jobs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewJobs = view.findViewById(R.id.searchViewJobs)

        val recyclerViewRecommendedJobs = view.findViewById<RecyclerView>(R.id.recyclerViewRecommendedJobs)
        recyclerViewRecommendedJobs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewRecommendedJobs.adapter = JobListingAdapter(jobs, requireActivity())

        recyclerView = view.findViewById(R.id.recyclerViewBookmarkedJobs)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = JobListingAdapter(filteredJobs, requireActivity())
        recyclerView.adapter = adapter

        loadJobsFromFirebase()
        setupSearchView()
    }

    private fun loadJobsFromFirebase() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobs.clear()
                filteredJobs.clear()

                for (companySnapshot in snapshot.children) {
                    for (jobSnapshot in companySnapshot.children) {
                        val job = jobSnapshot.getValue(Job::class.java)
                        job?.let {
                            jobs.add(it)
                            filteredJobs.add(it)
                        }
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load jobs: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSearchView() {
        searchViewJobs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterJobs(newText ?: "")
                return true
            }
        })
    }

    private fun filterJobs(query: String) {
        val lowerCaseQuery = query.lowercase()
        filteredJobs.clear()

        if (lowerCaseQuery.isEmpty()) {
            filteredJobs.addAll(jobs)
        } else {
            for (job in jobs) {
                val title = job.title?.lowercase() ?: ""
                val companyName = job.companyName.lowercase() ?: ""
                val salary = job.salary.lowercase() ?: ""
                if (title.contains(lowerCaseQuery) || companyName.contains(lowerCaseQuery) || salary.contains(lowerCaseQuery)) {
                    filteredJobs.add(job)
                }
            }
        }

        adapter.notifyDataSetChanged()
    }
}
