package com.example.socialhub.viewmodel

import android.content.ContextWrapper
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityNovoUsuarioOpcaoContaBinding
import com.example.socialhub.view.NovaOngActivity
import com.example.socialhub.view.NovoUsuarioActivity
import com.example.socialhub.view.NovoUsuarioOpcaoContaActivity

class NovoUsuarioOpcaoContaViewModel: ViewModel() {

    lateinit var binding: ActivityNovoUsuarioOpcaoContaBinding
    lateinit var rootActivity: NovoUsuarioOpcaoContaActivity

    fun addBinding(viewBinding: ActivityNovoUsuarioOpcaoContaBinding, activity: NovoUsuarioOpcaoContaActivity){
        binding = viewBinding;
        rootActivity = activity;
        setListeners();
    }

    private fun setListeners() {
        binding.btnOpcaoNovaOng.setOnClickListener {
            val intent = Intent(rootActivity, NovaOngActivity::class.java)
            rootActivity.startActivity(intent)
        }
        binding.btnOpcaoNovoUsuario.setOnClickListener {
            val intent = Intent(rootActivity, NovoUsuarioActivity::class.java)
            rootActivity.startActivity(intent)
        }
    }
}