package com.example.trabalho_lancamentos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho_lancamentos.R
import com.example.trabalho_lancamentos.models.lancamento.LancamentoModel

class ListagemAdapter
    (var context: Context, var list: List<LancamentoModel>)
    : RecyclerView.Adapter<ListagemAdapter.ListagemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListagemHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_listagem, parent, false)
        return ListagemHolder(view)
    }

    override fun onBindViewHolder(holder: ListagemHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ListagemHolder(var view: View) : RecyclerView.ViewHolder(view){

        fun bind(list: LancamentoModel){

            var descricao = view.findViewById<TextView>(R.id.descricao)
            descricao.text = list.Description

            var mes = view.findViewById<TextView>(R.id.Mes)
            mes.text = list.Month.toString()

            var ano = view.findViewById<TextView>(R.id.Ano)
            ano.text = list.Year.toString()

            var dinheiro = view.findViewById<TextView>(R.id.Dinheiro)
            dinheiro.text = list.Cash.toString()

            var tipo = view.findViewById<TextView>(R.id.tipo)
            if(list.Type.toString() == "money_in"){
                tipo.text = "ENTRADA";
            }else{
                tipo.text = "SAIDA";
            }




        }
    }

}