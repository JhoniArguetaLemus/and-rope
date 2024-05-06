package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class Recuperar : AppCompatActivity() {
    lateinit var edtUsuario:EditText
    lateinit var btnRecuperar:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        edtUsuario=findViewById(R.id.edt_usuario)
        btnRecuperar=findViewById(R.id.btnRecuperar)
        val auth = FirebaseAuth.getInstance()


        btnRecuperar.setOnClickListener {

            val email = edtUsuario.text.toString().trim()
            if(validarEmail(email)) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val alertDialog = AlertDialog.Builder(this)
                                .setMessage("Se ha enviado un correo electrónico de restablecimiento de contraseña.")
                                .setCancelable(true)
                                .setPositiveButton("OK") { _, _ ->
                                    edtUsuario.text.clear()
                                    startActivity(Intent(this, Login::class.java))
                                }
                                .show()


                        } else {
                            // Se produjo un error al enviar el correo electrónico de restablecimiento
                            Toast.makeText(
                                this,
                                "Error al enviar el correo electrónico de restablecimiento de contraseña. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else{
                edtUsuario.error="El email no es valido"
            }

        }

    }

    private fun validarEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}