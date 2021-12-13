package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.databinding.ActivityNovoUsuarioOpcaoContaBinding
import com.example.socialhub.viewmodel.LoginViewModel
import com.example.socialhub.viewmodel.NovoUsuarioOpcaoContaViewModel

class NovoUsuarioOpcaoContaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovoUsuarioOpcaoContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoUsuarioOpcaoContaBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        var viewModel = ViewModelProvider(this).get(NovoUsuarioOpcaoContaViewModel::class.java)

        viewModel.addBinding(binding, this)
    }
}