package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.databinding.ActivityNovoUsuarioBinding
import com.example.socialhub.viewmodel.NovoUsuarioViewModel
import java.lang.NullPointerException

class NovoUsuarioActivity : AppCompatActivity() , LifecycleOwner {

    private lateinit var binding: ActivityNovoUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        binding = ActivityNovoUsuarioBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this).get(NovoUsuarioViewModel::class.java)

        viewModel.addBinding(binding, this)
    }
}