package com.nativeNomads.workhive.EmployerDashBoard.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.R

class JobAdapter(private val jobList: MutableList<com.nativeNomads.workhive.EmployerDashBoard.Data.Job>) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jobTitle: TextView = view.findViewById(R.id.jobTitle)
        val jobLocation: TextView = view.findViewById(R.id.jobLocation)
        val jobStatus: TextView = view.findViewById(R.id.jobStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        holder.jobTitle.text = job.title
        holder.jobLocation.text = "Location: ${job.location}"
        holder.jobStatus.text = job.status
    }

    override fun getItemCount() = jobList.size
}
