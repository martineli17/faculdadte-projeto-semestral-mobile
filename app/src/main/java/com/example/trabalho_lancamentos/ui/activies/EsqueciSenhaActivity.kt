package com.example.trabalho_lancamentos.ui.activies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.app.UserApp
import com.example.trabalho_lancamentos.models.login.ContaModel
import com.example.trabalho_lancamentos.services.login.ContaRetrofitInitializer
import retrofit2.Call
import retrofit2.Response

class EsqueciSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha)

        var voltarLogin = findViewById<Button>(R.id.TelaLogin)
        voltarLogin.setOnClickListener {
            VoltarLogin()
        }

        var novaSenhaClick = findViewById<Button>(R.id.Confirmar)
        novaSenhaClick.setOnClickListener {
            NovaSenhaClick()
        }
    }

    fun VoltarLogin() {
        var intent = Intent(this@EsqueciSenhaActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun NovaSenhaClick() {

        var email = findViewById<EditText>(R.id.UserEmail)
        var senha = findViewById<EditText>(R.id.UserSenha)

        var conta      = ContaModel()
        conta.email    = email.text.toString()

        val s = ContaRetrofitInitializer().ServiceConta()
        val call = s.forgot(conta)

        call.enqueue(object : retrofit2.Callback<ContaModel> {
            override fun onResponse(call: Call<ContaModel>, response: Response<ContaModel>) {
                if (response.code() == 204) {

                    response.body()?.let {
                        var intent = Intent(this@EsqueciSenhaActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Toast.makeText(
                        this@EsqueciSenhaActivity,
                        "Usuário ou senha inválidos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ContaModel>, t: Throwable) {
                Toast.makeText(
                    this@EsqueciSenhaActivity,
                    "Não foi possível concluir a operação!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

}