package com.example.socialhub.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityStartBinding
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
    }
}