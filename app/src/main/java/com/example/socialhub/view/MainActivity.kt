package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.databinding.ActivityMainBinding
import com.example.socialhub.fragments.FeedFragment
import com.example.socialhub.fragments.ProfileFragment
import com.example.socialhub.viewmodel.LoginViewModel
import com.example.socialhub.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private val feedFragment = FeedFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        var viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.addBinding(binding, this)

        replaceFragment(feedFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_profile -> replaceFragment(profileFragment)
                R.id.navigation_feed -> replaceFragment(feedFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val fragmentManager = supportFragmentManager
            val fragmentTrasaction = fragmentManager.beginTransaction()
            fragmentTrasaction.replace(R.id.fragmentContainer, fragment)
            fragmentTrasaction.commit()
        }

    }
}