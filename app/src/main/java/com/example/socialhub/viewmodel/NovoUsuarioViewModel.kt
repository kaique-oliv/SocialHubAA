package com.example.socialhub.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityNovoUsuarioBinding
import com.example.socialhub.view.NovaOngActivity
import com.example.socialhub.view.NovoUsuarioActivity

class NovoUsuarioViewModel: ViewModel() {

    lateinit var binding: ActivityNovoUsuarioBinding
    lateinit var rootActivity: NovoUsuarioActivity

    fun addBinding(viewBinding: ActivityNovoUsuarioBinding, activity: NovoUsuarioActivity){
        binding = viewBinding;
        rootActivity = activity;
        setListeners();
    }

    private fun setListeners() {
//        binding.btnOpcaoNovaOng.setOnClickListener {
//            val intent = Intent(rootActivity, NovaOngActivity::class.java)
//            rootActivity.startActivity(intent)
//        }
//        binding.btnOpcaoNovoUsuario.setOnClickListener {
//            val intent = Intent(rootActivity, NovoUsuarioActivity::class.java)
//            rootActivity.startActivity(intent)
//        }
    }

}