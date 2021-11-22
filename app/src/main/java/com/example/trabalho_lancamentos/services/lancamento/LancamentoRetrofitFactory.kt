package com.example.trabalho_lancamentos.services.lancamento

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LancamentoRetrofitFactory {
    private val _url = "https://618b135034b4f400177c49c3.mockapi.io/api/v1/";

    private val _retrofit = Retrofit
        .Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build();


    fun lancamentoService(): ILancamentoService {
        return _retrofit.create(ILancamentoService::class.java)
    }
}