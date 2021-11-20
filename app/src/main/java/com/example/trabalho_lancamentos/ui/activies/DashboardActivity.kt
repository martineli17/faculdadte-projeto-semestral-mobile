package com.example.trabalho_lancamentos.ui.activies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.app.UserApp
import com.example.trabalho_lancamentos.models.lancamento.GetDashboardLancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.TipoLancamento
import com.example.trabalho_lancamentos.services.lancamento.LancamentoRetrofitFactory
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity() {
    var listaLancamentos: List<GetDashboardLancamentoModel> = ArrayList<GetDashboardLancamentoModel>();
    var dataAtual = SimpleDateFormat("MM/yyyy").format(Date());
    var mesAtual = dataAtual.split("/").get(0);
    var anoAtual = dataAtual.split("/").get(1);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        findViewById<TextView>(R.id.dashboard_lancamento_data).text = dataAtual;

        getLancamentos();
    }

    fun getLancamentos() {
        var responseCall =
            LancamentoRetrofitFactory().lancamentoService().buscarLancamentos(UserApp.email, anoAtual.toInt(), mesAtual.toInt());
        responseCall.enqueue(object : retrofit2.Callback<List<GetDashboardLancamentoModel>> {
            override fun onResponse(
                call: Call<List<GetDashboardLancamentoModel>>,
                response: Response<List<GetDashboardLancamentoModel>>
            ) {
                if (response.code() == 200) {
                    response.body()?.let { lancamentos ->
                        listaLancamentos = lancamentos;
                        setSaidas();
                        setEntradas();
                        setSaldo();
                    }
                }
            }

            override fun onFailure(call: Call<List<GetDashboardLancamentoModel>>, t: Throwable) {
                Toast.makeText(
                    this@DashboardActivity,
                    getString(R.string.error_api),
                    Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    fun setSaidas() {
        var saidas =
            listaLancamentos.filter { lancamento -> lancamento.Type == TipoLancamento.money_out };
        var valorTotalSaidas = saidas.sumOf { lancamento -> lancamento.Cash };
        findViewById<TextView>(R.id.dashboard_lancamento_saida_valor).text =
            getString(R.string.real_prefix_simbolo) + valorTotalSaidas.toString();
    }

    fun setEntradas() {
        var entradas =
            listaLancamentos.filter { lancamento -> lancamento.Type == TipoLancamento.money_in };
        var valorTotalEntradas = entradas.sumOf { lancamento -> lancamento.Cash };
        findViewById<TextView>(R.id.dashboard_lancamento_entrada_valor).text =
            getString(R.string.real_prefix_simbolo) + valorTotalEntradas.toString();
    }

    fun setSaldo() {
        var saidas =
            listaLancamentos.filter { lancamento -> lancamento.Type == TipoLancamento.money_out };
        var valorTotalSaidas = saidas.sumOf { lancamento -> lancamento.Cash };

        var entradas =
            listaLancamentos.filter { lancamento -> lancamento.Type == TipoLancamento.money_in };
        var valorTotalEntradas = entradas.sumOf { lancamento -> lancamento.Cash };

        var valorSaldo = valorTotalEntradas - valorTotalSaidas;

        findViewById<TextView>(R.id.dashboard_lancamento_saldo_valor).text =
            getString(R.string.real_prefix_simbolo) + valorSaldo.toString();
    }
}