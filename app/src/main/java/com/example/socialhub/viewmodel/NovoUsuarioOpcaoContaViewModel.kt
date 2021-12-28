package com.example.socialhub.viewmodel

import android.content.ContextWrapper
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityNovoUsuarioOpcaoContaBinding
import com.example.socialhub.view.NovaOngActivity
import com.example.socialhub.view.NovoUsuarioActivity
import com.example.socialhub.view.NovoUsuarioOpcaoContaActivity

class NovoUsuarioOpcaoContaViewModel: ViewModel() {

    private lateinit var binding: ActivityNovoUsuarioOpcaoContaBinding
    private lateinit var rootActivity: NovoUsuarioOpcaoContaActivity

    fun addBinding(viewBinding: ActivityNovoUsuarioOpcaoContaBinding, activity: NovoUsuarioOpcaoContaActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        setListeners();
    }

    private fun setListeners() {
        binding.btnOpcaoNovaOng.setOnClickListener {
            val intent = Intent(
                rootActivity,
                NovaOngActivity::class.java
            )
            intent.putExtra("TipoUsuario", "Org")
            with(rootActivity) {
                startActivity(intent)
            }
        }
        binding.btnOpcaoNovoUsuario.setOnClickListener {
            val intent = Intent(
                rootActivity,
                NovoUsuarioActivity::class.java
            )
            intent.putExtra("TipoUsuario", "Usuario")

            with(rootActivity) {
                startActivity(intent)
            }
        }
    }
}