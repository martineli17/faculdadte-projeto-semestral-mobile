package com.example.trabalho_lancamentos.services.lancamento

import com.example.trabalho_lancamentos.models.lancamento.AddLancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.GetDashboardLancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.LancamentoModel
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ILancamentoService {
    @POST("lancamentos")
    fun enviarLancamento(@Body credentials: AddLancamentoModel?):Call<Void>;

    @GET("lancamentos")
    fun buscarLancamentos(@Query("User") userEmail: String)
        :Call<List<GetDashboardLancamentoModel>>;

    @GET("lancamentos")
    fun buscarLancamentosTodos(@Query("User") userEmail: String)
            :Call<List<LancamentoModel>>;

}