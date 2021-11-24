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
import com.example.trabalho_lancamentos.models.lancamento.TipoLancamento

class ListagemAdapter
    (var context: Context, var list: List<LancamentoModel>)
    : RecyclerView.Adapter<ListagemAdapter.ListagemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListagemHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_listagem, parent, false)
        return ListagemHolder(view, context)
    }

    override fun onBindViewHolder(holder: ListagemHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ListagemHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view){

        fun bind(list: LancamentoModel){

            var categoria = view.findViewById<TextView>(R.id.categoriaValue)
            categoria.text = list.Category

            var descricao = view.findViewById<TextView>(R.id.descricao)
            descricao.text = list.Description

            var data = view.findViewById<TextView>(R.id.dataValue)
            data.text = list.Month.toString() + "/" + list.Year.toString()

            var dinheiro = view.findViewById<TextView>(R.id.Dinheiro)
            dinheiro.text =  list.Cash.toString()

            var tipo = view.findViewById<TextView>(R.id.tipo_lancamento)
            if(list.Type == TipoLancamento.money_in){
                tipo.text = context.getString(R.string.listagem_item_tipo_lancamento_entrada);
                tipo.setTextColor(context.getColor(R.color.green));
            }else{
                tipo.text = context.getString(R.string.listagem_item_tipo_lancamento_saida);
                tipo.setTextColor(context.getColor(R.color.red));
            }




        }
    }

}