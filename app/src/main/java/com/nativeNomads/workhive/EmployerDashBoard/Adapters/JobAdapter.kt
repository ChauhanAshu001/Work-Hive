package com.nativeNomads.workhive.EmployerDashBoard.Adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nativeNomads.workhive.EmployerDashBoard.Data.Job
import com.nativeNomads.workhive.R
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.JobsFragment

class JobAdapter(
    private val jobList: List<Job>,
    private val fragment: JobsFragment
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        val jobLocation: TextView = itemView.findViewById(R.id.jobLocation)
        val jobStatus: TextView = itemView.findViewById(R.id.jobStatus)
        val jobRole: TextView = itemView.findViewById(R.id.jobRole)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteJobIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        holder.jobTitle.text = job.title
        holder.jobLocation.text = "Location: ${job.location}"
        holder.jobStatus.text = job.status
        holder.jobRole.text = "Role : ${job.role}"

        holder.deleteIcon.setOnClickListener {
            showDeleteConfirmationDialog(job.jobId)
        }
    }

    override fun getItemCount() = jobList.size

    private fun showDeleteConfirmationDialog(jobId: String) {
        val builder = AlertDialog.Builder(fragment.requireContext())
        builder.setTitle("Delete Job")
        builder.setMessage("Are you sure you want to remove this job?")
        builder.setPositiveButton("Yes") { dialog, which ->
            deleteJob(jobId)
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show() // Show the dialog
    }

    private fun deleteJob(jobId: String) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("jobs").child("2").child(jobId).removeValue()
            .addOnSuccessListener {
                // Optionally notify the user about successful deletion
                Toast.makeText(fragment.context, "Job deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(fragment.context, "Error deleting job: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
