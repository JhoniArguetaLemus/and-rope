package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class resumen_reserva : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_reserva)

        val imagen=findViewById<ImageView>(R.id.img_local)
        val titulo=findViewById<TextView>(R.id.txtTitle)
        val floReserva=findViewById<FloatingActionButton>(R.id.floReserva)
        floReserva.setOnClickListener{
            startActivity(Intent(this, reserva::class.java))
        }



        try{
            val database=SQLite(this)
            val db=database.readableDatabase

            val query="Select * from local"
            val cursor=db.rawQuery(query, null)
            var text=""
            var imagenID=0

            if(cursor.moveToFirst()){
                text=cursor.getString(1)
                imagenID=cursor.getInt(2)

            }
            titulo.setText(text)
            imagen.setImageResource(imagenID)
            cursor.close()
            db.close()



        }catch (e:Exception){

            var alertDialog=AlertDialog.Builder(this)
                .setMessage(e.message)
                .setPositiveButton("Aceptar"){_, _->}
                .show()

        }



        try {


            val database = SQLite(this)
            val db = database.readableDatabase
            val query = "SELECT * FROM comida"
            val cursor = db.rawQuery(query, null)

            val dataList = ArrayList<ReservaData>()

            if (cursor.moveToFirst()) {
                do {
                    val id=cursor.getInt(0)
                    val imagen = cursor.getInt(1)
                    val titulo = cursor.getString(2)
                    val cantidad = cursor.getInt(3)
                    val precio = cursor.getFloat(4)
                    val total = precio * cantidad


                    val miObjeto = ReservaData(id,

                        imagen,
                        titulo,
                        cantidad.toString(),
                        precio.toString(),
                        total.toString()
                    )
                    dataList.add(miObjeto)
                } while (cursor.moveToNext())
            }

            cursor.close()

            val recycler = findViewById<RecyclerView>(R.id.recycler_reserva)
            recycler.layoutManager = LinearLayoutManager(this)
            val adapter = ReservaAdapter(this, dataList)
            recycler.adapter = adapter

        }catch (e:Exception){
            val alertDialog=AlertDialog.Builder(this)
                .setMessage(e.message)
                .setPositiveButton("Aceptar"){_, _->}
                .show()
        }

        val btnElimianarLocal=findViewById<Button>(R.id.btnEliminarLocal)
        btnElimianarLocal.setOnClickListener {
            eliminarLocal()
        }


    }

    private fun eliminarLocal(){
        val db=SQLite(this)
        db.eliminarLocal()
        startActivity(Intent(this, Locales::class.java))
    }
}