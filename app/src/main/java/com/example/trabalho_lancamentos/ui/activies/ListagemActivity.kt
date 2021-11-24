package com.example.trabalho_lancamentos.ui.activies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.app.UserApp
import com.example.trabalho_lancamentos.models.lancamento.LancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.TipoLancamento
import com.example.trabalho_lancamentos.models.login.ContaModel
import com.example.trabalho_lancamentos.services.lancamento.LancamentoRetrofitFactory
import com.example.trabalho_lancamentos.ui.adapters.ListagemAdapter
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ListagemActivity : AppCompatActivity() {

    private var dataAtual = SimpleDateFormat("MM/yyyy").format(Date());
    private var mesAtual = dataAtual.split("/").get(0);
    private var anoAtual = dataAtual.split("/").get(1);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        var novo = findViewById<Button>(R.id.novoDado)
        novo.setOnClickListener {
            NovoClick()
        }

        var dashboard = findViewById<Button>(R.id.dashboard)
        dashboard.setOnClickListener {
            DashboardClick()
        }

        var pesquisar = findViewById<Button>(R.id.Pesquisar)
        pesquisar.setOnClickListener {
            getLancamentos()
        }

        getLancamentos()
    }

    fun DashboardClick() {
        var mes = findViewById<EditText>(R.id.Mes).text.toString()
        var ano = findViewById<EditText>(R.id.Ano).text.toString()

        if(mes.isNullOrEmpty())
            mes = mesAtual;

        if(ano.isNullOrEmpty())
            ano = anoAtual;

        var intent = Intent(this, DashboardActivity()::class.java)
        intent.putExtra("mes", mes)
        intent.putExtra("ano", ano)
        startActivity(intent)
    }

    fun NovoClick() {
        var intent = Intent(this, NovoLancamentoActivity::class.java)
        startActivity(intent)
    }


    fun getLancamentos() {
        val s = LancamentoRetrofitFactory().lancamentoService()
        var call = s.buscarLancamentosTodos(UserApp.email)
        var mes = findViewById<EditText>(R.id.Mes).text.toString()
        var ano = findViewById<EditText>(R.id.Ano).text.toString()

        if(mes.isNullOrEmpty())
            mes = mesAtual;

        if(ano.isNullOrEmpty())
            ano = anoAtual;


        call.enqueue(object : retrofit2.Callback<List<LancamentoModel>> {
            override fun onResponse(
                call: Call<List<LancamentoModel>>,
                response: Response<List<LancamentoModel>>
            ) {
                if (response.code() == 200) {
                    response.body()?.let { list ->
                        var firstList = list.filter { lancamento -> lancamento.Month == mes.toInt()
                                && lancamento.Year == ano.toInt()    };
                        showLancamentos(firstList)
                    }
                }
            }

            override fun onFailure(call: Call<List<LancamentoModel>>, t: Throwable) {
                Toast.makeText(
                    this@ListagemActivity,
                    getString(R.string.error_api),
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    fun showLancamentos(list: List<LancamentoModel>) {

        var lista = findViewById<RecyclerView>(R.id.itens_listagem)
        lista.adapter = ListagemAdapter(this, list)

        lista.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}