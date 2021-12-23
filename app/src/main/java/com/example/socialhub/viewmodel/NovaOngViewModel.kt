package com.example.socialhub.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.socialhub.R
import com.example.socialhub.databinding.ActivityNovaOngBinding
import com.example.socialhub.utils.RetrofitFactory
import com.example.socialhub.helpers.checkCpf
import com.example.socialhub.helpers.unmask
import com.example.socialhub.interfaces.CEPService
import com.example.socialhub.model.CepModel
import com.example.socialhub.view.NovaOngActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.registerEditTextCnpj.addTextChangedListener(object: TextWatcher {
            var isUpdating: Boolean = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var s = 2
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                var mask = "##.###.###/####-##"

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
                binding.registerEditTextCnpj.setText(mascara)
                binding.registerEditTextCnpj.setSelection(mascara.length)

                if (str.length >= 14) {
                    if (checkCpf(str))
                        binding.registerEditTextCnpj.setTextColor(rootActivity.resources.getColor(R.color.green))
                    else
                        binding.registerEditTextCnpj.setTextColor(rootActivity.resources.getColor(R.color.red))
                }
                else {
                    binding.registerEditTextCnpj.setTextColor(rootActivity.resources.getColor(R.color.black))
                }
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
    }
}