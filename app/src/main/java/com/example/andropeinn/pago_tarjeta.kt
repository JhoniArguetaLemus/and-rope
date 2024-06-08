package com.example.andropeinn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.andropeinn.databinding.ActivityPagoTarjetaBinding

class pago_tarjeta : AppCompatActivity() {
    private lateinit var pagoTarjetaBinding: ActivityPagoTarjetaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagoTarjetaBinding=ActivityPagoTarjetaBinding.inflate(layoutInflater)
        setContentView(pagoTarjetaBinding.root)


        //soporte tecnico

        val imgSoporte=findViewById<ImageButton>(R.id.preguntas_tarjeta)
            .setOnClickListener{
                val intent=Intent(this, activity_soporte::class.java)
                intent.putExtra("url", "https://forms.gle/fMw8vgRV7MrNQXLJA")
                startActivity(intent)
            }

        val edtNumTarjeta=findViewById<EditText>(R.id.edtNumero_tarjeta)

       val nameEditText: EditText = findViewById(R.id.edtNombreTarjeta)

        val dateEditText: EditText = findViewById(R.id.edtFechaExpericion)
        val threeDigitsEditText: EditText = findViewById(R.id.edtCodigoSeguridad)


       pagoTarjetaBinding.btnPagarTarjeta .setOnClickListener {
            val name = nameEditText.text.toString()
            val cardNumber = edtNumTarjeta.text.toString()
            val date = dateEditText.text.toString()
            val threeDigits = threeDigitsEditText.text.toString()

            if (!isValidName(name)) {

                nameEditText.error="Nombre invalido"
            } else if (!isValidCardNumber(cardNumber)) {
                edtNumTarjeta.error="Número de tarjeta inválido"

            } else if (!isValidDate(date)) {
                dateEditText.error="Fecha inválida"

            } else if (!isValidThreeDigits(threeDigits)) {
                threeDigitsEditText.error="CVV inválido"

            } else {


                val alertDialog=AlertDialog.Builder(this)
                    .setMessage("Pago realizado correctamente")
                    .setIcon(R.drawable.dollarsing)
                    .setCancelable(true)
                    .setPositiveButton("Aceptar"){_, _->
                        startActivity(Intent(this, fin_reserva::class.java ))
                    }
                    .show()
                nameEditText.text.clear()
                edtNumTarjeta.text.clear()
                dateEditText.text.clear()
                threeDigitsEditText.text.clear()
            }
        }



    }


    fun isValidName(name: String): Boolean {
        return name.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+\$"))
    }

    fun isValidCardNumber(cardNumber: String): Boolean {
        return cardNumber.matches(Regex("^\\d{16}\$"))
    }



    fun isValidDate(date: String): Boolean {
        // Verificar el formato con una expresión regular
        val regex = Regex("^(0[1-9]|1[0-2])/\\d{2}\$")
        if (!date.matches(regex)) {
            return false
        }else{
            return true
        }


    }



    fun isValidThreeDigits(input: String): Boolean {
        return input.matches(Regex("^\\d{3}\$"))
    }



}