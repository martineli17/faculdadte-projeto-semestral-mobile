package com.example.trabalho_lancamentos.ui.activies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trabalho_lancamentos.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        sharedPreferences.edit().putString("user_email", "user@gmail.com").commit();
    }
}