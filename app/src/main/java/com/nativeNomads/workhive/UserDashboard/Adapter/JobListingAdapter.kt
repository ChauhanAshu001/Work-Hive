package com.nativeNomads.workhive.UserDashboard.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.R

class JobListingAdapter(
    var companyName: ArrayList<String>,
    var jobTitle: ArrayList<String>,
    var context: Context
) : RecyclerView.Adapter<JobListingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewCompanyName: TextView = itemView.findViewById(R.id.companyName)
        var textViewJobTitle: TextView = itemView.findViewById(R.id.jobDescribe)
        var cardView: CardView = itemView.findViewById(R.id.cardView)
        var bookmarkButton:Button =itemView.findViewById(R.id.bookmarkButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.job_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewCompanyName.text = companyName[position]
        holder.textViewJobTitle.text = jobTitle[position]

        // Set up the click listener for the "More Info" button
        holder.cardView.setOnClickListener {
            val intent = Intent(context, JobInfo::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return companyName.size
    }
}
