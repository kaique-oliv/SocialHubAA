package com.example.socialhub.viewmodel

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.provider.Settings.Global.getString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityLoginBinding
import com.example.socialhub.view.LoginActivity
import com.example.socialhub.view.MainActivity
import com.example.socialhub.view.NovoUsuarioOpcaoContaActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import java.util.*
import com.firebase.ui.auth.AuthUI

class LoginViewModel: ViewModel() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var rootActivity: LoginActivity
    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
        )
    private var REQUEST_CODE =1001
    private var passwordIsVisible: Boolean = false
    private var email: String = "";
    private var senha: String = "";
    private var lembrar: Boolean = false;

    fun addBinding(viewBinding: ActivityLoginBinding, activity: LoginActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };

        //Abrir arquivo para gravar dados, e arquivo não existe ele cria...
        val arquivo = rootActivity.getSharedPreferences("usuario", MODE_PRIVATE)

        verificaAutenticado();
        setListeners();
    }

    private fun setListeners() {

        binding.imgEyeShowPassword.setOnClickListener {
            if (!passwordIsVisible) {
                binding.imgEyeShowPassword.setImageResource(R.drawable.ic_eye_close_24)
                binding.loginEditTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordIsVisible = true
            }
            else {
                binding.imgEyeShowPassword.setImageResource(R.drawable.ic_eye_24)
                binding.loginEditTextPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                passwordIsVisible = false
            }
        }
        //login no google
        binding.btnSignInGoogle.setOnClickListener {
            startActivityForResult (rootActivity,
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
                REQUEST_CODE, null
            )
        }



        binding.loginTextViewCriarConta.setOnClickListener {
            val intent = Intent(rootActivity, NovoUsuarioOpcaoContaActivity::class.java)
            rootActivity.startActivity(intent)
        }

        binding.loginButtonEntrar.setOnClickListener {
            autenticar()
        }

    }

    private fun autenticar() {

        if (validar()) {
            email = binding.loginEditTextEmail.text.toString().lowercase(Locale.getDefault())
            senha = binding.loginEditTextPassword.text.toString().lowercase(Locale.getDefault())
            try {
                //FirebaseApp.initializeApp(rootActivity)
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener (OnCompleteListener{
                        if (it.isSuccessful){

                            lembrar = binding.loginCheckBoxLembrar.isChecked

                            //Abrir arquivo para gravar dados, ee arquivo não existe ele cria...
                            val dados = rootActivity.getSharedPreferences("usuario", MODE_PRIVATE)

                            //Para alerar conteudo do arquivo
                            val editor = dados.edit()
                            editor.putString("email", email)
                            editor.putString("senha", senha)
                            editor.putBoolean("lembrar", lembrar)
                            editor.apply()

                            entrar()
                        }
                    })
                    .addOnFailureListener (OnFailureListener {
                        try {
                            throw it
                        } catch (e: FirebaseNoSignedInUserException){
                            Toast.makeText(
                                rootActivity,
                                rootActivity.resources.getString(R.string.criar_usuario_email_incorreto),
                                Toast.LENGTH_SHORT).show()
                        } catch (e: Exception){
                            Toast.makeText(
                                rootActivity,
                                "${e.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    })
            }catch (e: Exception){
                Log.i("social-hub", "${e.message}")
            }
        }

    }

    private fun entrar() {

        val intent = Intent(rootActivity, MainActivity::class.java)
        rootActivity.startActivity(intent)
        rootActivity.finish();

    }

    private fun validar(): Boolean {

        if (binding.loginEditTextEmail.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.resources.getString(R.string.login_error_email_obrigatorio);
            return false
        }
        if (binding.loginEditTextPassword.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.resources.getString(R.string.login_error_password_obrigatorio);
            return false
        }
        return true
    }

    private fun verificaAutenticado(){
        if (lembrar) {

            val usuarioAutenticado = FirebaseAuth.getInstance().currentUser

            if (usuarioAutenticado != null) {
                entrar()
            }
        }
    }



//    fun criarUsuario(){
//
//        val uid = FirebaseAuth.getInstance().uid
//
//        var usuario = Usuario(
//            uid.toString(),
//            "monica",
//            23,
//            "werbrt@ter.com",
//            "base64ImageString")
//
//        FirebaseFirestore.getInstance().collection("usuarios")
//            .add(usuario)
//            .addOnSuccessListener (OnSuccessListener {
//                Log.i("social-hub", "${it.id}")
//            })
//            .addOnFailureListener(OnFailureListener {
//                Log.i("social-hub", "${it.message}")
//            })
//    }

//    fun updateUsuario(){
//        FirebaseFirestore.getInstance().collection("usuarios")
//            .up (usuario)
//            .addOnSuccessListener (OnSuccessListener {
//
//            })
//    }
}