package com.example.trabalho_lancamentos.models.lancamento

class AddLancamentoModel {
    var User: String = "";
    var Cash: Double = 0.0;
    var Month: Int = 0;
    var Year: Int = 0;
    var Category: String = "";
    var Description: String = "";
    var Type: TipoLancamento = TipoLancamento.money_in;

    fun validarCash():Boolean{
        return Cash > 0.0;
    }

    fun validarMonth():Boolean{
        return Month > 0 && Month < 13;
    }

    fun validarYear():Boolean{
        return Year > 0;
    }

    fun validarCategory():Boolean{
        return !Category.isNullOrEmpty();
    }

    fun validarDescription():Boolean{
        return !Description.isNullOrEmpty();
    }

    fun validarDados():Boolean{
        return validarCash() && validarCategory() && validarMonth() && validarDescription() && validarYear();
    }
}
