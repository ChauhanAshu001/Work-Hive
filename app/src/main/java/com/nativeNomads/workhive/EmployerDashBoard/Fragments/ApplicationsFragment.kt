package com.nativeNomads.workhive.EmployerDashBoard.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nativeNomads.workhive.EmployerDashBoard.Adapters.ApplicationAdapter
import com.nativeNomads.workhive.EmployerDashBoard.Data.Application
import com.nativeNomads.workhive.R

class ApplicationsFragment : Fragment() {

    private lateinit var applicationRecyclerView: RecyclerView
    private lateinit var applicationAdapter: ApplicationAdapter
    private val applicationList = mutableListOf<Application>()
    private lateinit var databaseReference: DatabaseReference
    private val companyId = "3"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_applications, container, false)

        applicationRecyclerView = view.findViewById(R.id.acceptedInvitationsRecyclerView)
        applicationRecyclerView.layoutManager = LinearLayoutManager(context)

        applicationAdapter = ApplicationAdapter(applicationList,"3",requireContext())
        applicationRecyclerView.adapter = applicationAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Applied jobs")

        fetchApplications()

        return view
    }

    private fun fetchApplications() {
        Log.d("FirebaseDebug", "Starting to fetch applications...")

        databaseReference.child(companyId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("FirebaseDebug", "Data snapshot received for companyId: $companyId")

                    applicationList.clear()
                    Log.d("FirebaseDebug", "Cleared applicationList")

                    for (userSnapshot in snapshot.children) {
                        Log.d("FirebaseDebug", "Processing userSnapshot: ${userSnapshot.key}")

                        val application = userSnapshot.getValue(Application::class.java)
                        if (application != null) {
                            applicationList.add(application)
                            Log.d("FirebaseDebug", "Added application: $application")
                        }
                    }

                    applicationAdapter.notifyDataSetChanged()
                    Log.d("FirebaseDebug", "Adapter notified about new data")

                    if (applicationList.isEmpty()) {
                        Toast.makeText(context, "No applications found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseDebug", "Error fetching data: ${error.message}")
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
