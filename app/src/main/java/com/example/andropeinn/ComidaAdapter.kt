package com.example.andropeinn

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ComidaAdapter( val context: Context,  val list:List<ComidaData>): RecyclerView.Adapter<ComidaAdapter.ViewHolder> (){
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {

        val imageView: ImageView = itemView.findViewById(R.id.img_reserva)
        val textViewTitle: TextView = itemView.findViewById(R.id.titulo)
        val textViewPrecio: TextView = itemView.findViewById(R.id.total)
        val btnAgregar: Button = itemView.findViewById(R.id.buttonAgregar)
        val imgMenos=itemview.findViewById<ImageView>(R.id.imgMenos)
        val imgMas=itemview.findViewById<ImageView>(R.id.imgMas)
        val editextCant=itemview.findViewById<EditText>(R.id.editextCantidad)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.comida_model, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var contador=1
        holder.editextCant.setText(contador.toString())
        val comida=list[position]
        holder.imageView.setImageResource(comida.imageResource)
        holder.textViewTitle.text=comida.title
        holder.textViewPrecio.text="Precio: " + "$ " +comida.precio


        holder.imgMas.setOnClickListener {
            contador+=1
            holder.editextCant.setText(contador.toString())
        }

        holder.imgMenos.setOnClickListener {
            if(contador<=0){
                contador=1
                holder.editextCant.setText(contador.toString())
            }else{
                contador -=1
                holder.editextCant.setText(contador.toString())
            }

        }


        holder.btnAgregar.setOnClickListener {
            try {

                val database=SQLite(context)
                val db=database.writableDatabase
                database.insertarComida(
                    comida.imageResource,
                    comida.title,
                    holder.editextCant.text.toString().toInt(),
                    comida.precio.toFloat()

                )

                Toast.makeText(context, "${comida.title} agregado correctamente", Toast.LENGTH_SHORT).show()


            }catch (e:Exception){
                val alertDialog=AlertDialog.Builder(context)
                    .setMessage(e.message)
                    .setCancelable(true)
                    .setPositiveButton("Aceptar"){_, _->}
                    .show()
            }



        }



    }


}