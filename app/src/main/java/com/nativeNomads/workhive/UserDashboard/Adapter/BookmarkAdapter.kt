package com.nativeNomads.workhive.UserDashboard.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.R

import com.nativeNomads.workhive.UserDashboard.data.BookmarkJobsData

class BookmarkAdapter (
    private val bookmarkedJobs:ArrayList<BookmarkJobsData>,
    private val context: Context
):RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val bookmarkedCompanyName:TextView=itemView.findViewById(R.id.bookmarkedCompanyName)
        val bookmarkedLocation:TextView=itemView.findViewById(R.id.bookmarkedJobLocation)
        val bookmarkedSalary:TextView=itemView.findViewById(R.id.bookmarkedJobSalary)
        val bookmarkedTitle:TextView=itemView.findViewById(R.id.bookmarkedJobTitle)
        val bookmarkedJobType:TextView=itemView.findViewById(R.id.bookmarkedJobType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.bookmark_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmarkedJob:BookmarkJobsData=bookmarkedJobs[position]
        holder.bookmarkedCompanyName.text=bookmarkedJob.companyName
        holder.bookmarkedLocation.text=bookmarkedJob.location
        holder.bookmarkedSalary.text=bookmarkedJob.salary
        holder.bookmarkedTitle.text=bookmarkedJob.title
        holder.bookmarkedJobType.text=bookmarkedJob.jobType
    }


    override fun getItemCount(): Int {
        return bookmarkedJobs.size
    }
}