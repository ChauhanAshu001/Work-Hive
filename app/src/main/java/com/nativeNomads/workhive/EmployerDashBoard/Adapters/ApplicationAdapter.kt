package com.nativeNomads.workhive.EmployerDashBoard.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nativeNomads.workhive.R
import com.nativeNomads.workhive.EmployerDashBoard.Data.Application
import com.nativeNomads.workhive.EmployerDashBoard.ApplicationsActivity

class ApplicationAdapter(
    private val applicationList: MutableList<Application>,
    private val companyId: String,
    private val context: Context
) : RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val appliedJobsReference: DatabaseReference = database.reference.child("Applied jobs").child(companyId)
    private val hiredJobsReference: DatabaseReference = database.reference.child("Hired jobs").child(companyId)

    class ApplicationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val applicantName: TextView = view.findViewById(R.id.applicantName)
        val positionApplied: TextView = view.findViewById(R.id.positionApplied)
        val applicationStatus: TextView = view.findViewById(R.id.applicationStatus)
        val accept: ImageView = view.findViewById(R.id.imageAccept)
        val reject: ImageView = view.findViewById(R.id.imageReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_application, parent, false)
        return ApplicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val application = applicationList[position]
        holder.applicantName.text = application.name
        holder.positionApplied.text = "Position: ${application.companyName}"
        holder.applicationStatus.text = application.status

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ApplicationsActivity::class.java)
            intent.putExtra("name", application.name)
            intent.putExtra("companyName", application.companyName)
            intent.putExtra("status", application.status)
            intent.putExtra("experience", application.experience)
            intent.putExtra("skills", application.skills)
            intent.putExtra("userId", application.userId)
            context.startActivity(intent)
        }


        holder.reject.setOnClickListener {
            appliedJobsReference.child(application.userId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Application rejected!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to reject application", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun getItemCount() = applicationList.size
}
