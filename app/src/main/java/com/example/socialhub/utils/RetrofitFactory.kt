package com.example.socialhub.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory() {

    companion object {

        private lateinit var retrofit: Retrofit
        private const val baseUrl ="https://viacep.com.br/ws/"

        fun getRetrofit() : Retrofit {

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }
    }
}