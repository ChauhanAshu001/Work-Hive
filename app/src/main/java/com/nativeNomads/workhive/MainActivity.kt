package com.nativeNomads.workhive

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nativeNomads.workhive.EmployerDashBoard.AppConstants
import com.nativeNomads.workhive.UserDashboard.Fragments.ConnectFragment
import com.nativeNomads.workhive.UserDashboard.Fragments.HomeFragment
import com.nativeNomads.workhive.UserDashboard.Fragments.ProfileFragment
import com.nativeNomads.workhive.databinding.ActivityMainBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController

    val userId = "user3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> openFragment(HomeFragment())
                R.id.connect -> openFragment(ConnectFragment())
                R.id.profileFragmentEmployee -> openFragment(ProfileFragment())
            }
            true
        }

        findViewById<BottomNavigationView>(R.id.bottomNavigation).selectedItemId=R.id.homeFragment

        // In dono line ko change mat karna video call ke liye hai

        val config = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallService.init(application,AppConstants.appId,AppConstants.appSign,userId,userId,config)

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
