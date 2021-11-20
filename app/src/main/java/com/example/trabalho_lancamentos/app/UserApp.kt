package com.example.trabalho_lancamentos.app

import android.app.Application

class UserApp : Application {
    constructor() : super()

    override fun onCreate(){
        super.onCreate()
    }

    companion object{
        private var instance: UserApp? = null

        var email: String = "NOT_FOUND"

        fun getInstance(): UserApp?{
            return instance
        }
    }
}