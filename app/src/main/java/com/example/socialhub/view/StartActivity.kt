package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.databinding.ActivityStartBinding
import com.example.socialhub.viewmodel.StartViewModel

import java.lang.NullPointerException

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        binding = ActivityStartBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        //setando a view model
        var viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        viewModel.addBinding(binding, this)
    }
}