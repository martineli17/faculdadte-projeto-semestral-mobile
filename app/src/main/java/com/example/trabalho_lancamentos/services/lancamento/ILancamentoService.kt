package com.example.trabalho_lancamentos.services.lancamento

import retrofit2.http.Body
import retrofit2.http.POST
import com.example.trabalho_lancamentos.models.lancamento.AddLancamentoModel
import retrofit2.Call

interface ILancamentoService {
    @POST("lancamentos")
    fun enviarLancamento(@Body credentials: AddLancamentoModel?):Call<Void>;
}