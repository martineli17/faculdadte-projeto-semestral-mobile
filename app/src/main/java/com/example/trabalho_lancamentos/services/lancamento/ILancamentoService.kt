package com.example.trabalho_lancamentos.services.lancamento

import com.example.trabalho_lancamentos.models.lancamento.AddLancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.GetDashboardLancamentoModel
import retrofit2.Call
import retrofit2.http.*

interface ILancamentoService {
    @POST("lancamentos")
    fun enviarLancamento(@Body credentials: AddLancamentoModel?):Call<Void>;

    @GET("lancamentos")
    fun buscarLancamentos(@Query("User") userEmail: String,
                          @Query("Year") year: Int,
                          @Query("Month") month: Int)
        :Call<List<GetDashboardLancamentoModel>>;
}