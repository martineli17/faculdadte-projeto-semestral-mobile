package com.example.trabalho_lancamentos.ui.activies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.models.lancamento.AddLancamentoModel
import com.example.trabalho_lancamentos.services.lancamento.LancamentoRetrofitFactory
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response

class NovoLancamentoActivity : AppCompatActivity() {
    var tipoNovoLancamento: String = "money_in";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_lancamento)

        setClicksTiposLancamento();
        setEnviodNovoLancamento();
    }

    fun setClicksTiposLancamento(){
        var buttonSaida = findViewById<Button>(R.id.novo_lancamento_saida);
        var buttonEntrada = findViewById<Button>(R.id.novo_lancamento_entrada);

        buttonSaida.setOnClickListener {
            buttonSaida.setBackgroundColor(getColor(R.color.red_select));
            buttonSaida.setTextColor(getColor(R.color.white));

            buttonEntrada.setBackgroundColor(getColor(R.color.white));
            buttonEntrada.setTextColor(getColor(R.color.green));

            tipoNovoLancamento = "money_out";
        }

        buttonEntrada.setOnClickListener {
            buttonEntrada.setBackgroundColor(getColor(R.color.green_select));
            buttonEntrada.setTextColor(getColor(R.color.white));

            buttonSaida.setBackgroundColor(getColor(R.color.white));
            buttonSaida.setTextColor(getColor(R.color.red));

            tipoNovoLancamento = "money_in";
        }

    }

    fun createModelAdd():AddLancamentoModel{
        var model = AddLancamentoModel();
        model.Cash = findViewById<EditText>(R.id.novo_lancamento_valor).text.toString().toDouble();
        model.Category = findViewById<EditText>(R.id.novo_lancamento_categoria).text.toString();
        model.Month = findViewById<EditText>(R.id.novo_lancamento_data_mes).text.toString().toInt();
        model.Year = findViewById<EditText>(R.id.novo_lancamento_data_ano).text.toString().toInt();
        model.Title = findViewById<EditText>(R.id.novo_lancamento_descricao).text.toString();
        model.Type = tipoNovoLancamento;

        return model;
    }

    fun setEnviodNovoLancamento(){
        findViewById<Button>(R.id.novo_lancamento_enviar).setOnClickListener {
            var model = createModelAdd();
            var responseCall = LancamentoRetrofitFactory().lancamentoService().enviarLancamento(model);
            responseCall.enqueue(object : retrofit2.Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.code() == 201){
                        Toast.makeText(this@NovoLancamentoActivity, "Lançamento registrado", Toast.LENGTH_LONG).show();
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@NovoLancamentoActivity, getString(R.string.error_api), Toast.LENGTH_LONG).show();
                }

            });
        }
    }
}