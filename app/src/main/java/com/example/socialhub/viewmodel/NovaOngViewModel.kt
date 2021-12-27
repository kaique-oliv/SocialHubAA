package com.example.socialhub.viewmodel

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityNovaOngBinding
import com.example.socialhub.helpers.AppConstants
import com.example.socialhub.utils.RetrofitFactory
import com.example.socialhub.helpers.checkCpf
import com.example.socialhub.helpers.unmask
import com.example.socialhub.interfaces.CEPService
import com.example.socialhub.model.CepModel
import com.example.socialhub.model.Ong
import com.example.socialhub.model.Usuario
import com.example.socialhub.view.MainActivity
import com.example.socialhub.view.NovaOngActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NovaOngViewModel: ViewModel() {

    private lateinit var binding: ActivityNovaOngBinding
    private lateinit var rootActivity: NovaOngActivity
    private var newPasswordIsVisible: Boolean = false
    private var confirPasswordIsVisible: Boolean = false

    fun addBinding(viewBinding: ActivityNovaOngBinding, activity: NovaOngActivity){
        viewBinding.also { binding = it };
        activity.also { rootActivity = it };
        setListeners();
    }

    private fun setListeners() {

        binding.registerEditTextCep.addTextChangedListener(object: TextWatcher {
            var isUpdating: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var s = 2
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                var mask = "#####-###"

                if (isUpdating) {
                    isUpdating = false
                    return
                }

                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && count > before) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                binding.registerEditTextCep.setText(mascara)
                binding.registerEditTextCep.setSelection(mascara.length)
            }

            override fun afterTextChanged(s: Editable) {
                val str = unmask(s.toString())
                if (!str.isEmpty()) {
                    if (str.length == 8) {

                        val retrofit = RetrofitFactory.getRetrofit().create(CEPService::class.java)

                        val call: Call<CepModel> = retrofit.getCep(str)

                        call.enqueue(object : Callback<CepModel> {
                            override fun onResponse(call: Call<CepModel>, response: Response<CepModel>) {

                                if (!response.message().equals("Bad Request")) {

                                    val cep = response.body()

                                    binding.registerEditTextPublicPlace.setText(cep!!.logradouro)
                                    binding.registerEditTextDistrict.setText(cep!!.bairro)
                                    binding.registerEditTextCity.setText(cep!!.localidade)
                                    binding.registerEditTextState.setText(cep!!.uf)
                                }
                            }

                            override fun onFailure(call: Call<CepModel>, t: Throwable) {
                                Log.i("social-hub", t.message.toString())
                            }
                        })
                    }
                }
            }
        })

        binding.registerEditTextCpf.addTextChangedListener(object: TextWatcher {
            var isUpdating: Boolean = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var s = 2
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                var mask = "###.###.###-##"

                if (isUpdating) {
                    isUpdating = false
                    return
                }

                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && count > before) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                binding.registerEditTextCpf.setText(mascara)
                binding.registerEditTextCpf.setSelection(mascara.length)

                if (str.length >= 14) {
                    if (checkCpf(str))
                        binding.registerEditTextCpf.setTextColor(rootActivity.resources.getColor(R.color.green))
                    else
                        binding.registerEditTextCpf.setTextColor(rootActivity.resources.getColor(R.color.red))
                }
                else {
                    binding.registerEditTextCpf.setTextColor(rootActivity.resources.getColor(R.color.black))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                var s = 2
            }
        })

        binding.registerEditTextPhoneNumber.addTextChangedListener(object: TextWatcher {
            var isUpdating: Boolean = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var s = 2
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                var mask = "(##)#.####.####"

                if (isUpdating) {
                    isUpdating = false
                    return
                }

                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && count > before) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                binding.registerEditTextPhoneNumber.setText(mascara)
                binding.registerEditTextPhoneNumber.setSelection(mascara.length)

            }

            override fun afterTextChanged(s: Editable?) {
                var s = 2
            }
        })

        binding.imgEyeShowNewPassword.setOnClickListener{
            if (!newPasswordIsVisible) {
                binding.imgEyeShowNewPassword.setImageResource(R.drawable.ic_eye_close_24)
                binding.loginEditTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                newPasswordIsVisible = true
            }
            else{
                binding.imgEyeShowNewPassword.setImageResource(R.drawable.ic_eye_24)
                binding.loginEditTextPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                newPasswordIsVisible = false
            }
        }
        binding.imgEyeShowConfirmPassword.setOnClickListener{
            if (!confirPasswordIsVisible) {
                binding.imgEyeShowConfirmPassword.setImageResource(R.drawable.ic_eye_close_24)
                binding.loginEditTextPasswordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
                confirPasswordIsVisible = true
            }
            else{
                binding.imgEyeShowConfirmPassword.setImageResource(R.drawable.ic_eye_24)
                binding.loginEditTextPasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance();
                confirPasswordIsVisible = false
            }
        }

        binding.criarContaButton.setOnClickListener {
            if (validar() == true){
                criarConta()
            }
        }
    }

    private fun criarConta()
    {
        val ong = Ong (
            logo = AppConstants.DEFALT_AVATAR
        )

        if (validar()) {
            ong.email = binding.registerEditTextEmail.text.toString().lowercase(Locale.getDefault())
            val senha = binding.loginEditTextPasswordConfirm.text.toString().lowercase(Locale.getDefault())
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(ong.email, senha)
                .addOnSuccessListener(OnSuccessListener{

                    ong.uid = it.user!!.uid

                    //Abrir arquivo para gravar dados, se arquivo não existe ele cria...
                    val dados = rootActivity.getSharedPreferences("Ong", Context.MODE_PRIVATE)

                    //Para alterar conteudo do arquivo
                    val editor = dados.edit()
                    editor.putString("email", ong.email)
                    editor.putString("senha", senha)
                    editor.putBoolean("lembrar", true)
                    editor.apply()

                    //Grava no FireBase
                    FirebaseFirestore
                        .getInstance()
                        .collection("Ong")
                        .add(ong)

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
                            rootActivity.resources.getString(R.string.criar_usuario_ja_existe),
                            Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthWeakPasswordException){
                        Toast.makeText(
                            rootActivity,
                            rootActivity.resources.getString(R.string.criar_usuario_senha_fraca),
                            Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthInvalidUserException){
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
        }
    }

    private fun validar(): Boolean {
        if (binding.registerEditTextOrganizationName.text.isNullOrEmpty())
        {
            binding.registerEditTextOrganizationName.error = rootActivity.resources.getString(R.string.ong_profile_falta_organization);
            return false
        }
        if (binding.registerEditTextPhoneNumber.text.isNullOrEmpty())
        {
            binding.registerEditTextPhoneNumber.error = rootActivity.resources.getString(R.string.ong_profile_falta_fone);
            return false
        }
        if (binding.registerEditTextCep.text.isNullOrEmpty())
        {
            binding.registerEditTextCep.error = rootActivity.resources.getString(R.string.ong_profile_falta_cep);
            return false
        }


        if (binding.registerEditTextName.text.isNullOrEmpty())
        {
            binding.registerEditTextName.error = rootActivity.resources.getString(R.string.ong_profile_falta_responsible_name);
            return false
        }
        if (binding.registerEditTextCpf.text.isNullOrEmpty())
        {
            binding.registerEditTextCpf.error = rootActivity.resources.getString(R.string.ong_profile_falta_responsible_cpf);
            return false
        }
        if (binding.registerEditTextEmailResponsible.text.isNullOrEmpty())
        {
            binding.registerEditTextEmailResponsible.error = rootActivity.resources.getString(R.string.ong_profile_falta_responsible_email);
            return false
        }


        if (binding.registerEditTextEmail.text.isNullOrEmpty()) {
            binding.registerEditTextEmail.error = rootActivity.resources.getString(R.string.login_error_email_obrigatorio);
            return false
        }
        if (binding.loginEditTextPassword.text.isNullOrEmpty()) {
            binding.loginEditTextPassword.error = rootActivity.resources.getString(R.string.obrigatorio_senha_nova);
            return false
        }
        if (binding.loginEditTextPasswordConfirm.text.isNullOrEmpty()) {
            binding.loginEditTextPasswordConfirm.error = rootActivity.resources.getString(R.string.obrigatorio_confirmar_senha_nova);
            return false
        }
        if (binding.loginEditTextPassword.text.toString() != binding.loginEditTextPasswordConfirm.text.toString()){
            Toast.makeText(
                rootActivity,
                rootActivity.resources.getString(R.string.senha_escolhida_divergente_confirmacao),
                Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}