package com.nativeNomads.workhive.EmployerDashBoard.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.R

class ApplicationAdapter(private val applicationList: MutableList<com.nativeNomads.workhive.EmployerDashBoard.Data.Application>) : RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {

    class ApplicationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val applicantName: TextView = view.findViewById(R.id.applicantName)
        val positionApplied: TextView = view.findViewById(R.id.positionApplied)
        val applicationStatus: TextView = view.findViewById(R.id.applicationStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_application, parent, false)
        return ApplicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val application = applicationList[position]
        holder.applicantName.text = application.name
        holder.positionApplied.text = "Position: ${application.position}"
        holder.applicationStatus.text = application.status
    }

    override fun getItemCount() = applicationList.size
}
