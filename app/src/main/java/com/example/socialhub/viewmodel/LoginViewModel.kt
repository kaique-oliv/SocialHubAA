package com.example.socialhub.viewmodel

import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast
import com.example.socialhub.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.view.LoginActivity
import com.example.socialhub.view.MainActivity
import com.example.socialhub.view.NovoUsuarioOpcaoContaActivity
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {

    lateinit var binding: ActivityLoginBinding
    lateinit var rootActivity: LoginActivity

    fun addBinding(viewBinding: ActivityLoginBinding, activity: LoginActivity){
        binding = viewBinding;
        rootActivity = activity;
        setListeners();
    }

    private fun setListeners() {
        binding.loginTextViewCriarConta.setOnClickListener {
            val intent = Intent(rootActivity, NovoUsuarioOpcaoContaActivity::class.java)
            rootActivity.startActivity(intent)
            //rootActivity.finish();
        }

        binding.loginButtonEntrar.setOnClickListener {
            autenticar()
        }
    }

    private fun autenticar() {
        if (validar()) {
            //Abrir arquivo
            val dados = rootActivity.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)
            val editor = dados.edit()

            val email = dados.getString("email", "")
            val senha = dados.getString("senha", "")

            if (binding.loginCheckBoxLembrar.isChecked)
            {
                editor.putBoolean("lembrar", true)
            }else{
                editor.putBoolean("lembrar", false)
            }

            editor.apply()

            if (email == binding.loginEditTextEmail.text.toString()
                    .lowercase() && senha == binding.loginEditTextPassword.text.toString()
                    .lowercase()
            ) {
                val intent = Intent(rootActivity, MainActivity::class.java)
                //startActivity(intent)
                //finish();
            } else {
              Toast.makeText(
                  rootActivity,
                  rootActivity.getResources().getString(R.string.login_error_usuario_incorreto),
                  Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validar(): Boolean {
        if (binding.loginEditTextEmail.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.getResources().getString(R.string.login_error_email_obrigatorio);
            return false
        }
        if (binding.loginEditTextPassword.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.getResources().getString(R.string.login_error_password_obrigatorio);
            return false
        }
        return true
    }

    override fun onCleared() {
        super.onCleared()
    }
}