package com.nativeNomads.workhive.UserDashboard.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.UserDashboard.Adapter.BookmarkAdapter
import com.nativeNomads.workhive.UserDashboard.data.BookmarkJobsData


class BookmarkedFragment : Fragment() {
    var bookmarkedJobs=ArrayList<BookmarkJobsData>()
    lateinit var companyName:String
    lateinit var location:String
    lateinit var salary:String
    lateinit var title:String
    lateinit var jobType:String
    lateinit var recyclerViewBookmarkJobs:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        companyName= arguments?.getString("COMPANY_NAME").toString()
        location= arguments?.getString("LOCATION").toString()
        salary= arguments?.getString("SALARY").toString()
        title= arguments?.getString("JOB_TITLE").toString()
        jobType= arguments?.getString("JOB_TYPE").toString()

        val bookmarkJobData =BookmarkJobsData(companyName,location,salary,title,jobType)
        bookmarkedJobs.clear()
        recyclerViewBookmarkJobs=view.findViewById(R.id.recyclerViewBookmarkedJobs)
        bookmarkedJobs.add(bookmarkJobData)
        recyclerViewBookmarkJobs.layoutManager= LinearLayoutManager(requireContext())
        recyclerViewBookmarkJobs.adapter= BookmarkAdapter(bookmarkedJobs,requireContext())
    }

    companion object {

    }
}