package com.nativeNomads.workhive.UserDashboard.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.UserDashboard.Fragments.BookmarkedFragment

class JobListingAdapter(
    private var jobs: MutableList<Job>,
    private val context: Context,

) : RecyclerView.Adapter<JobListingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCompanyName: TextView = itemView.findViewById(R.id.companyName)
        val textViewJobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        val textViewJobLocation: TextView = itemView.findViewById(R.id.jobLocation)
        val textViewJobType: TextView = itemView.findViewById(R.id.jobType)
        val textViewJobSalary: TextView = itemView.findViewById(R.id.jobSalary)
        val moreInfo: Button = itemView.findViewById(R.id.more)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.job_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job:Job = jobs[position]


        holder.textViewCompanyName.text = job.companyName
        holder.textViewJobTitle.text = job.role
        holder.textViewJobLocation.text = job.location
        holder.textViewJobType.text = job.jobType
        holder.textViewJobSalary.text = job.salary


        holder.moreInfo.setOnClickListener {
            val intent = Intent(context, JobInfo::class.java)

            intent.putExtra("JOB_ID", job.jobId)
            intent.putExtra("JOB_ROLE", job.role)
            intent.putExtra("COMPANY_NAME", job.companyName)
            intent.putExtra("JOB_TYPE", job.jobType)
            intent.putExtra("JOB_LOCATION", job.location)
            intent.putExtra("JOB_SALARY", job.salary)
            intent.putExtra("JOB_DEADLINE", job.applicationDeadline)
            intent.putExtra("JOB_EXPERIENCE", job.experienceNeeded)
            intent.putExtra("JOB_QUALIFICATION", job.qualifications)
            intent.putExtra("JOB_RESPONSIBILITIES", job.responsibilities)
            intent.putExtra("COMPANY_DESCRIPTION", job.description)
            intent.putExtra("JOB_BENEFITS", job.benefits)
            intent.putExtra("JOB_TITLE",job.title)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
}
