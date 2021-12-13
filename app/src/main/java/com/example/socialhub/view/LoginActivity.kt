package com.example.socialhub.view

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        var viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.addBinding(binding, this)

    }
}