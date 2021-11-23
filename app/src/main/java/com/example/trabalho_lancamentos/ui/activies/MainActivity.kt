package com.example.trabalho_lancamentos.ui.activies

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.app.UserApp
import com.example.trabalho_lancamentos.models.login.ContaModel
import com.example.trabalho_lancamentos.services.login.ContaRetrofitInitializer
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var login = findViewById<Button>(R.id.Entrar)
        login.setOnClickListener {
            EntrarClick()
        }

        var cadastro = findViewById<Button>(R.id.Cadastro)
        cadastro.setOnClickListener {
            CadastroClick()
        }

        var esqueceuSenha = findViewById<Button>(R.id.EsqueceuSenha)
        esqueceuSenha.setOnClickListener {
            EsqueceuSenha()
        }
    }

    fun EntrarClick(){

        var email = findViewById<EditText>(R.id.UserLogin)
        var senha = findViewById<EditText>(R.id.UserSenha)

        var conta = ContaModel()
        conta.email = email.text.toString()
        conta.password = senha.text.toString()

        val s = ContaRetrofitInitializer().ServiceConta()
        val call = s.auth(conta)

        call.enqueue(object : retrofit2.Callback<ContaModel> {
            override fun onResponse(call: Call<ContaModel>, response: Response<ContaModel>) {
                if (response.code() == 200) {

                    response.body()?.let {
                        var intent = Intent(this@MainActivity, ListagemActivity::class.java)
                        UserApp.email = conta.email.toString()
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.usuario_senha_invalidos),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ContaModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.error_api), Toast.LENGTH_LONG).show()
            }
        })

    }

    fun CadastroClick(){
        var intent = Intent(this@MainActivity, CadastroMainActivity::class.java)
        startActivity(intent)
    }

    fun EsqueceuSenha(){
        var intent = Intent(this@MainActivity, EsqueciSenhaActivity::class.java)
        startActivity(intent)
    }
}