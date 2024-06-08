package com.example.andropeinn

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class ReservaAdapter(val context: Context,val list:List<ReservaData>):RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imagen=itemView.findViewById<ImageView>(R.id.img_reserva)
        val txtTitulo=itemView.findViewById<TextView>(R.id.titulo)
        val precio=itemView.findViewById<TextView>(R.id.precio)
        val cantidad=itemView.findViewById<TextView>(R.id.cantidad)
        val total=itemView.findViewById<TextView>(R.id.total)
        val btnEliminar=itemView.findViewById<Button>(R.id.btnEliminar)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.reserva_model, parent, false)
        return ReservaAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista=list[position]
        holder.imagen.setImageResource(lista.imagen)
        holder.txtTitulo.text=lista.titulo
        holder.precio.text="Precio: $ "+lista.precio
        holder.cantidad.text="Cantidad: "+lista.cantidad
        holder.total.text="Total: $ "+lista.total




        holder.btnEliminar.setOnClickListener {
            val database=SQLite(context)
            database.eliminarComida(lista.id)
        }

    }
}