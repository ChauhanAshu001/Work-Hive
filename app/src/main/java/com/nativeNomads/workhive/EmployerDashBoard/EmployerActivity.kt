package com.nativeNomads.workhive.EmployerDashBoard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.AnalyticsFragment
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.ApplicationsFragment
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.JobsFragment
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.MessagesFragment
import com.nativeNomads.workhive.EmployerDashBoard.Fragments.ProfileFragment
import com.nativeNomads.workhive.R

class EmployerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer)

        val bottomNav = findViewById<BottomNavigationView>(R.id.employer_bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_jobs -> loadFragment(JobsFragment())
                R.id.nav_messages -> loadFragment(MessagesFragment())
                R.id.nav_applications -> loadFragment(ApplicationsFragment())
                R.id.nav_analytics -> loadFragment(AnalyticsFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_jobs // Default Fragment
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.employer_fragment_container, fragment)
            .commit()
    }
}