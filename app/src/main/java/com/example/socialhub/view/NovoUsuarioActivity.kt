package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.databinding.ActivityNovoUsuarioBinding
import com.example.socialhub.viewmodel.NovoUsuarioViewModel

class NovoUsuarioActivity : AppCompatActivity() , LifecycleOwner {

    private lateinit var binding: ActivityNovoUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoUsuarioBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        var viewModel = ViewModelProvider(this).get(NovoUsuarioViewModel::class.java)

        viewModel.addBinding(binding, this)
    }
}