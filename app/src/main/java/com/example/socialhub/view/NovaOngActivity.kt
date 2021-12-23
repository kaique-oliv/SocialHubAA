package com.example.socialhub.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.socialhub.databinding.ActivityNovaOngBinding
import com.example.socialhub.viewmodel.NovaOngViewModel

class NovaOngActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityNovaOngBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        binding = ActivityNovaOngBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this).get(NovaOngViewModel::class.java)

        viewModel.addBinding(binding, this)
    }
}