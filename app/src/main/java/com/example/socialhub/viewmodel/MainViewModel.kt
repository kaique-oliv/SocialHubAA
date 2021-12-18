package com.example.socialhub.viewmodel

import android.content.Context
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityMainBinding
import com.example.socialhub.view.MainActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView




class MainViewModel: ViewModel() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rootActivity: MainActivity

    fun addBinding(viewBinding: ActivityMainBinding, activity: MainActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        //replaceFragment(FeedFragment())
        setListeners();
    }

    private fun setListeners() {

//        binding.ibMainOpenFeed.setOnClickListener{
//            replaceFragment(FeedFragment())
//        }
//        binding.ibMainOpenProfile.setOnClickListener{
//            replaceFragment(ProfileFragment())
//        }
    }


}