package com.example.socialhub.viewmodel

import android.content.Context
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityMainBinding
import com.example.socialhub.view.FeedFragment
import com.example.socialhub.view.MainActivity
import com.example.socialhub.view.ProfileFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView




class MainViewModel: ViewModel() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rootActivity: MainActivity

    fun addBinding(viewBinding: ActivityMainBinding, activity: MainActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        replaceFragment(FeedFragment())
        setListeners();
    }

    private fun setListeners() {

        binding.ibMainOpenFeed.setOnClickListener{
            replaceFragment(FeedFragment())
        }
        binding.ibMainOpenProfile.setOnClickListener{
            replaceFragment(ProfileFragment())
        }
//        binding.bottomNavigationView.setOnClickListener {
//            var itemId = binding.bottomNavigationView.selectedItemId
//            Log.i("social-hub", "$itemId")
//        }



//        val mNavigationView = binding.bottomNavigationView as NavigationView
//
//        mNavigationView.setNavigationItemSelectedListener { it: MenuItem ->
//
//            when (it.itemId) {
//                rootActivity.
//
//                Log.i("social-hub","${it.itemId}")
//                rootActivity.id.nav_item1 -> replaceFragment(FeedFragment())
//                R.id.nav_item2-> doThat()
//                else -> {
//                    true
//                }
//            }
//        }
    }

//    private fun getSelectedItem(bottomNavigationView: BottomNavigationView): Int {
//        val menu: Menu = bottomNavigationView.menu
//        for (i in 0 until bottomNavigationView.menu.size()) {
//            val menuItem: MenuItem = menu.getItem(i)
//            if (menuItem.isChecked) {
//                return menuItem.itemId
//            }
//        }
//        return 0
//    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = rootActivity.supportFragmentManager
        val fragmentTrasaction = fragmentManager.beginTransaction()
        fragmentTrasaction.replace(binding.fragmentContainer.id, fragment)
        fragmentTrasaction.commit()
    }
}