package com.example.trabalho_lancamentos.services.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContaRetrofitInitializer {

    private val URL = "https://api.fluo.work/v1/"

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun ServiceConta() : IContaService{
        return retrofit.create(IContaService::class.java)
    }
}