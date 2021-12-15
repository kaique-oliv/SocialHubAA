package com.example.socialhub.viewmodel

import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.socialhub.databinding.ActivityNovoUsuarioBinding
import com.example.socialhub.view.MainActivity
import com.example.socialhub.view.NovoUsuarioActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.util.*


class NovoUsuarioViewModel: ViewModel() {

    private lateinit var binding: ActivityNovoUsuarioBinding
    private lateinit var rootActivity: NovoUsuarioActivity
    private var newPasswordIsVisible: Boolean = false
    private var confirPasswordIsVisible: Boolean = false

    fun addBinding(viewBinding: ActivityNovoUsuarioBinding, activity: NovoUsuarioActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        setListeners();
    }

    private fun setListeners() {
        binding.imgEyeShowNewPassword.setOnClickListener{
            if (!newPasswordIsVisible) {
                binding.imgEyeShowNewPassword.setImageResource(com.example.socialhub.R.drawable.ic_eye_close_24)
                binding.loginEditTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                newPasswordIsVisible = true
            }
            else{
                binding.imgEyeShowNewPassword.setImageResource(com.example.socialhub.R.drawable.ic_eye_24)
                binding.loginEditTextPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                newPasswordIsVisible = false
            }
        }
        binding.imgEyeShowConfirmPassword.setOnClickListener{
            if (!confirPasswordIsVisible) {
                binding.imgEyeShowConfirmPassword.setImageResource(com.example.socialhub.R.drawable.ic_eye_close_24)
                binding.loginEditTextPasswordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
                confirPasswordIsVisible = true
            }
            else{
                binding.imgEyeShowConfirmPassword.setImageResource(com.example.socialhub.R.drawable.ic_eye_24)
                binding.loginEditTextPasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance();
                confirPasswordIsVisible = false
            }
        }

        binding.criarContaButton.setOnClickListener {
            criarConta()
        }
    }

    private fun criarConta()
    {
        if (validar()) {
            val email = binding.loginEditTextEmail.text.toString().lowercase(Locale.getDefault())
            val senha = binding.loginEditTextPasswordConfirm.text.toString().lowercase(Locale.getDefault())
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(OnSuccessListener{
                    Log.i("social-hub", it.user!!.email.toString())

                    val lembrar = binding.loginCheckBoxLembrar.isChecked

                    //Abrir arquivo para gravar dados
                    //Se arquivo não existe ele criar...
                    val dados = rootActivity.getSharedPreferences("usuario", Context.MODE_PRIVATE)

                    //Para alerar conteudo do arquivo
                    val editor = dados.edit()
                    editor.putString("email", email)
                    editor.putString("senha", senha)
                    editor.putBoolean("lembrar", lembrar)
                    editor.apply()

                    val intent = Intent(rootActivity, MainActivity::class.java)
                    rootActivity.startActivity(intent)
                    rootActivity.finish();

                })
                .addOnFailureListener(OnFailureListener{
                    Log.i("social-hub", it.message.toString())
                    try {
                       throw it
                    } catch (e: FirebaseAuthUserCollisionException){
                        Toast.makeText(
                            rootActivity,
                            rootActivity.resources.getString(com.example.socialhub.R.string.criar_usuario_ja_existe),
                            Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthWeakPasswordException){
                        Toast.makeText(
                            rootActivity,
                            rootActivity.resources.getString(com.example.socialhub.R.string.criar_usuario_senha_fraca),
                            Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidUserException){
                        Toast.makeText(
                            rootActivity,
                            rootActivity.resources.getString(com.example.socialhub.R.string.criar_usuario_email_incorreto),
                            Toast.LENGTH_SHORT).show()
                    } catch (e: Exception){
                        Toast.makeText(
                            rootActivity,
                            "${e.message}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    private fun validar(): Boolean {
        if (binding.loginEditTextEmail.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.resources.getString(com.example.socialhub.R.string.login_error_email_obrigatorio);
            return false
        }
        if (binding.loginEditTextPassword.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.resources.getString(com.example.socialhub.R.string.obrigatorio_senha_nova);
            return false
        }
        if (binding.loginEditTextPasswordConfirm.text.isNullOrEmpty()) {
            binding.loginEditTextEmail.error = rootActivity.resources.getString(com.example.socialhub.R.string.obrigatorio_confirmar_senha_nova);
            return false
        }
        if (binding.loginEditTextPassword.text.toString() != binding.loginEditTextPasswordConfirm.text.toString()){
            Toast.makeText(
                rootActivity,
                rootActivity.resources.getString(com.example.socialhub.R.string.senha_escolhida_divergente_confirmacao),
                Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}