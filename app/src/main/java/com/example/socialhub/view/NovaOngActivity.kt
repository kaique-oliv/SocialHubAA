package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.databinding.ActivityMainBinding
import com.example.socialhub.databinding.ActivityNovaOngBinding

class NovaOngActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNovaOngBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaOngBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
    }
}