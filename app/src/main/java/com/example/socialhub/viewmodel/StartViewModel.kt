package com.example.socialhub.viewmodel

import android.animation.ObjectAnimator
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityNovoUsuarioOpcaoContaBinding
import com.example.socialhub.databinding.ActivityStartBinding
import com.example.socialhub.view.*
import com.google.firebase.auth.FirebaseAuth


class StartViewModel: ViewModel() {

    private lateinit var binding: ActivityStartBinding
    private lateinit var rootActivity: StartActivity
    private var lembrar: Boolean = false;

    fun addBinding(viewBinding: ActivityStartBinding, activity: StartActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        //logar se houver dados
        //verificaAutenticado()

        //animar logo e titulo
        animar()

        //mandar para login depois de animar
        val intent = Intent(rootActivity, LoginActivity::class.java)
        rootActivity.startActivity(intent)
        rootActivity.finish();
    }

//    private fun verificaAutenticado(){
//        if (lembrar) {
//            val usuarioAutenticado = FirebaseAuth.getInstance().currentUser
//
//            if (usuarioAutenticado != null) {
//                entrar()
//            }
//        }
//    }
//    private fun entrar() {
//
//        val intent = Intent(rootActivity, MainActivity::class.java)
//        rootActivity.startActivity(intent)
//        rootActivity.finish();
//
//    }
    private fun animar(){
        var logo = binding.ivLogoStart.drawable as AnimatedVectorDrawable
        logo.start()
        }
    }
 
