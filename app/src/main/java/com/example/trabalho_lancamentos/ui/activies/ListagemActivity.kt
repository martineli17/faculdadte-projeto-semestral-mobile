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

class ListagemActivity : AppCompatActivity() {

    private lateinit var listAll : List<LancamentoModel>;

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
            PesquisarClick()
        }

        getLancamentos()
    }

    fun PesquisarClick() {

        if (findViewById<EditText>(R.id.Mes).text.toString() == "" &&
            findViewById<EditText>(R.id.Ano).text.toString() == "") {

            showLancamentos(listAll)

        }else if (findViewById<EditText>(R.id.Mes).text.toString() != "" &&
            findViewById<EditText>(R.id.Ano).text.toString() == ""){

            var mes = findViewById<EditText>(R.id.Mes).text.toString().toInt();

            if(mes > 0 && mes < 13){
                var entradas =
                    listAll.filter { lancamento -> lancamento.Month == mes };

                showLancamentos(entradas)
            }else{
                Toast.makeText(
                    this@ListagemActivity,
                    getString(R.string.novo_lancamento_mes_error),
                    Toast.LENGTH_LONG
                ).show()
            }

        }else if (findViewById<EditText>(R.id.Ano).text.toString() != "" &&
            findViewById<EditText>(R.id.Mes).text.toString() == ""){

            var ano = findViewById<EditText>(R.id.Ano).text.toString().toInt()

            var entradas =
                listAll.filter { lancamento -> lancamento.Year == ano };
            showLancamentos(entradas)

        }else{
            var mes = findViewById<EditText>(R.id.Mes).text.toString().toInt();
            var ano = findViewById<EditText>(R.id.Ano).text.toString().toInt();

            if(mes > 0 && mes < 13){
                var entradas =
                    listAll.filter { lancamento -> lancamento.Month == mes && lancamento.Year == ano};

                showLancamentos(entradas)
            }else{
                Toast.makeText(
                    this@ListagemActivity,
                    getString(R.string.novo_lancamento_mes_error),
                    Toast.LENGTH_LONG
                ).show()
            }


        }




    }

    fun DashboardClick() {
        var intent = Intent(this@ListagemActivity, DashboardActivity()::class.java)
        startActivity(intent)
        finish()
    }

    fun NovoClick() {
        var intent = Intent(this@ListagemActivity, NovoLancamentoActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun getLancamentos() {
        val s = LancamentoRetrofitFactory().lancamentoService()
        var call = s.buscarLancamentosTodos(UserApp.email)

        call.enqueue(object : retrofit2.Callback<List<LancamentoModel>> {
            override fun onResponse(
                call: Call<List<LancamentoModel>>,
                response: Response<List<LancamentoModel>>
            ) {
                if (response.code() == 200) {
                    response.body()?.let { list ->
                        listAll = list;
                        showLancamentos(list)
                    }
                } else {
                    Toast.makeText(
                        this@ListagemActivity,
                        "Não foi possível realizar o cadastro!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<LancamentoModel>>, t: Throwable) {
                Toast.makeText(
                    this@ListagemActivity,
                    "Não foi possível concluir a operação!",
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