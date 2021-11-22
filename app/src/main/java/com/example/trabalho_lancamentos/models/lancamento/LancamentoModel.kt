package com.example.trabalho_lancamentos.models.lancamento

import java.io.Serializable

class LancamentoModel : Serializable {

    var User: String? = null
    var Cash: Double? = null
    var Month: Int? = null
    var Year: Int? = null
    var Category: String? = null
    var Description: String? = null
    var Type: TipoLancamento = TipoLancamento.money_in

}