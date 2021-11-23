package com.example.trabalho_lancamentos.ui.activies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.models.login.ContaModel
import com.example.trabalho_lancamentos.services.login.ContaRetrofitInitializer
import retrofit2.Call
import retrofit2.Response

class CadastroMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        var novaContaClick = findViewById<Button>(R.id.Cadastrar)
        novaContaClick.setOnClickListener {
            CriarContaClick()
        }

        var voltarLogin = findViewById<Button>(R.id.TelaLogin)
        voltarLogin.setOnClickListener {
            VoltarLoginClick()
        }
    }

    fun CriarContaClick(){

        var nome = findViewById<EditText>(R.id.UserName)
        var email = findViewById<EditText>(R.id.UserEmail)
        var senha = findViewById<EditText>(R.id.UserSenha)

        var novaConta = ContaModel()
        novaConta.name = nome.text.toString()
        novaConta.email = email.text.toString()
        novaConta.password = senha.text.toString()

        val s = ContaRetrofitInitializer().ServiceConta()
        val call = s.register(novaConta)

        call.enqueue(object : retrofit2.Callback<ContaModel> {
            override fun onResponse(call: Call<ContaModel>, response: Response<ContaModel>) {
                if (response.code() == 200) {

                    var intent = Intent(this@CadastroMainActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else {
                    Toast.makeText(
                        this@CadastroMainActivity,
                        getString(R.string.error_api),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ContaModel>, t: Throwable) {
                Toast.makeText(this@CadastroMainActivity, getString(R.string.error_api), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun VoltarLoginClick(){
        var intent = Intent(this@CadastroMainActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}