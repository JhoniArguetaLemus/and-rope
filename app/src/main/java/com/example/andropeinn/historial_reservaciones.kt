package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class historial_reservaciones : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial_reservaciones)

        val historialReservaciones=findViewById<ImageButton>(R.id.soporte_historial)
            .setOnClickListener{
                val intent=Intent(this, activity_soporte::class.java)
                intent.putExtra("url", "https://forms.gle/9V3EiPANpchiMyyQ6")
                startActivity(intent)
            }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.historial, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.historial_reservas->{
               startActivity(Intent(this, historial_reservaciones::class.java))
           }

        }
        return super.onOptionsItemSelected(item)
    }
}