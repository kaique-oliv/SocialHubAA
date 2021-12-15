package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.databinding.ActivityMainBinding
import com.example.socialhub.viewmodel.LoginViewModel
import com.example.socialhub.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), LifecycleOwner {

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
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_blog -> {
                val fragment = FeedFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_chapter -> {
//                val fragment = ChapterFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
//                    .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_store -> {
//                val fragment = StoreFragment()
//                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
//                    .commit()
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }
}