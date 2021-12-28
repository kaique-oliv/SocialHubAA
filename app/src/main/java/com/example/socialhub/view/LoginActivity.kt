package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding

import com.example.socialhub.viewmodel.LoginViewModel
import java.lang.NullPointerException

class LoginActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)

        var viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.addBinding(binding, this)

    }


}