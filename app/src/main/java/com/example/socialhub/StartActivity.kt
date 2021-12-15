package com.example.socialhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.NullPointerException

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Esconder ActionBar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_start)
    }
}