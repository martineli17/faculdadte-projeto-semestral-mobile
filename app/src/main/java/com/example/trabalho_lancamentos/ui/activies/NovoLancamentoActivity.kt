package com.example.trabalho_lancamentos.ui.activies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.app.UserApp
import com.example.trabalho_lancamentos.models.lancamento.AddLancamentoModel
import com.example.trabalho_lancamentos.models.lancamento.TipoLancamento
import com.example.trabalho_lancamentos.services.lancamento.LancamentoRetrofitFactory
import retrofit2.Call
import retrofit2.Response
import java.util.*

class NovoLancamentoActivity : AppCompatActivity() {
    var model: AddLancamentoModel = AddLancamentoModel();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_lancamento)

        model.User = UserApp.email

        setHabilitarButtonEnvio();
        setClicksTiposLancamento();
        setValidacoes();
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

            model.Type = TipoLancamento.money_out;
        }

        buttonEntrada.setOnClickListener {
            buttonEntrada.setBackgroundColor(getColor(R.color.green_select));
            buttonEntrada.setTextColor(getColor(R.color.white));

            buttonSaida.setBackgroundColor(getColor(R.color.white));
            buttonSaida.setTextColor(getColor(R.color.red));

            model.Type = TipoLancamento.money_in;
        }
    }

    fun setValidacoes(){
        var fieldValor = findViewById<EditText>(R.id.novo_lancamento_valor);
        var fieldMes = findViewById<EditText>(R.id.novo_lancamento_data_mes);
        var fieldAno = findViewById<EditText>(R.id.novo_lancamento_data_ano);
        var fieldDescricao = findViewById<EditText>(R.id.novo_lancamento_descricao);
        var fieldCategoria = findViewById<EditText>(R.id.novo_lancamento_categoria);

        fieldValor.setOnFocusChangeListener { view, focus ->
            if(!focus){
                if(!fieldValor.text.toString().isNullOrEmpty())
                    model.Cash = fieldValor.text.toString().toDouble();
                if(!model.validarCash())
                    fieldValor.error = getString(R.string.novo_lancamento_valor_error);
                else
                    fieldValor.error = null;

                setHabilitarButtonEnvio();
            }
        }

        fieldMes.setOnFocusChangeListener { view, focus ->
            if(!focus){
               if(!fieldMes.text.toString().isNullOrEmpty())
                    model.Month = fieldMes.text.toString().toInt();
                if(!model.validarMonth())
                    fieldMes.error = getString(R.string.novo_lancamento_mes_error);
                else
                    fieldMes.error = null;

                setHabilitarButtonEnvio();
            }
        }

        fieldAno.setOnFocusChangeListener { view, focus ->
            if(!focus){
                if(!fieldAno.text.toString().isNullOrEmpty())
                    model.Year = fieldAno.text.toString().toInt();
                if(!model.validarYear())
                    fieldAno.error = getString(R.string.novo_lancamento_ano_error)
                else
                    fieldAno.error = null;

                setHabilitarButtonEnvio();
            }
        }

        fieldDescricao.setOnFocusChangeListener { view, focus ->
            if(!focus){
                model.Description = fieldDescricao.text.toString();
                if(!model.validarDescription())
                    fieldDescricao.error = getString(R.string.novo_lancamento_descricao_error)
                else
                    fieldDescricao.error = null;

                setHabilitarButtonEnvio();
            }
        }

        fieldCategoria.setOnFocusChangeListener { view, focus ->
            if(!focus){
                model.Category = fieldCategoria.text.toString();
                if(!model.validarCategory())
                    fieldCategoria.error = getString(R.string.novo_lancamento_categoria_error)
                else
                    fieldCategoria.error = null;

                setHabilitarButtonEnvio();
            }
        }
    }

    fun setHabilitarButtonEnvio(){
        var habilitar = model.validarDados();
        var button = findViewById<Button>(R.id.novo_lancamento_enviar);
        button.isEnabled = habilitar;
        if(habilitar)
            button.setBackgroundColor(getColor(R.color.primary));
        else
            button.setBackgroundColor(getColor(R.color.primary_disabled));

    }

    fun setModelAdd(){
        model.Cash = findViewById<EditText>(R.id.novo_lancamento_valor).text.toString().toDouble();
        model.Category = findViewById<EditText>(R.id.novo_lancamento_categoria).text.toString();
        model.Month = findViewById<EditText>(R.id.novo_lancamento_data_mes).text.toString().toInt();
        model.Year = findViewById<EditText>(R.id.novo_lancamento_data_ano).text.toString().toInt();
        model.Description = findViewById<EditText>(R.id.novo_lancamento_descricao).text.toString();
    }

    fun setEnviodNovoLancamento(){
        findViewById<Button>(R.id.novo_lancamento_enviar).setOnClickListener {
            setModelAdd();
            if(model.validarDados()){
                var responseCall = LancamentoRetrofitFactory().lancamentoService().enviarLancamento(model);
                responseCall.enqueue(object : retrofit2.Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.code() == 201){
                            Toast.makeText(this@NovoLancamentoActivity, getString(R.string.novo_lancamento_salvo_sucesso), Toast.LENGTH_LONG).show();
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@NovoLancamentoActivity, getString(R.string.error_api), Toast.LENGTH_LONG).show();
                    }

                });
            }
            else
                setHabilitarButtonEnvio();
        }
    }
}