package com.example.andropeinn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.andropeinn.databinding.ActivityMusicaBinding

class musica : AppCompatActivity() {
    private lateinit var musicaBinding: ActivityMusicaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        musicaBinding=ActivityMusicaBinding.inflate(layoutInflater)
        setContentView(musicaBinding.root)

        val generosMusicales = arrayOf(
            "Sin musica",
            "Rock",
            "Pop",
            "Hip Hop / Rap",
            "Jazz",
            "Blues",
            "Reggae",
            "Country",
            "Electronic / EDM",
            "R&B",
            "Funk",
            "Metal",
            "Indie",
            "Folk",
            "Classical",
            "Punk",
            "Alternative",
            "Gospel",
            "Soul",
            "Ska",
            "Techno",
            "House",
            "Dubstep",
            "Drum and Bass",
            "Ambient",
            "Reggaeton",
            "Latin",
            "Dancehall",
            "Disco",
            "Trance",
            "Grunge"
        )


        val adapter=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generosMusicales)
        musicaBinding.generoSpinner.adapter=adapter


        musicaBinding.generoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val generoSeleccionado = parent.getItemAtPosition(position).toString()
                Toast.makeText(this@musica, "Seleccionado: $generoSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción a tomar cuando no se selecciona nada (opcional)
            }
        }

        musicaBinding.btnAceptar.setOnClickListener {
            startActivity(Intent(this, reserva::class.java))
        }
    }
}