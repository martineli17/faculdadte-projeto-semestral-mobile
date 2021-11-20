package com.example.trabalho_lancamentos.services.login

import com.example.trabalho_lancamentos.models.login.ContaModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface IContaService {

    @POST("account/auth")
    fun auth (@Body conta: ContaModel): Call<ContaModel>

    @POST("account")
    fun register(@Body conta: ContaModel): Call<ContaModel>

    @PUT("account")
    fun update(@Body conta: ContaModel): Call<ContaModel>
}