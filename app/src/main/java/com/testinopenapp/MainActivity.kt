package com.testinopenapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.testinopenapp.databinding.ActivityMainBinding
import com.testinopenapp.ui.campaign.CampaignFragment
import com.testinopenapp.ui.courses.CourseFragment
import com.testinopenapp.ui.dashboard.DashboardFragment
import com.testinopenapp.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dashboardFragment = DashboardFragment()
        val courseFragment = CourseFragment()
        val campaignFragment = CampaignFragment()
        val profileFragment = ProfileFragment()


        setCurrentFragment(dashboardFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_main)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_dashboard -> setCurrentFragment(dashboardFragment)
                R.id.nav_courses -> setCurrentFragment(courseFragment)
                R.id.nav_campaign -> setCurrentFragment(campaignFragment)
                R.id.nav_profile -> setCurrentFragment(profileFragment)
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}