package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



import android.widget.TextView
import android.widget.Toast


class crear_cuenta : AppCompatActivity() {
    private lateinit var btnCrearCuenta:Button
    private lateinit var mAuth:FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        try{

            mAuth=FirebaseAuth.getInstance()
            btnCrearCuenta=findViewById(R.id.crearCuenta)
            val edtUser=findViewById<EditText>(R.id.edt_usuario)
            val edtContrasenna=findViewById<EditText>(R.id.edtContra)
            val passwordErrorTextView=findViewById<TextView>(R.id.passwordErrorTextView)



            btnCrearCuenta.setOnClickListener {
                val contrasena=edtContrasenna.text.toString()
                val email=edtUser.text.toString()


                if(!validarEmail(email)){
                    edtUser.error="El usuario no cumple con las caracteristicas de un correo"

                }else if(!( contrasena.length>=8 &&
                    contrasena.any { it.isLetter() } &&
                    contrasena.any { it.isDigit() } &&
                    contrasena.any { it.isUpperCase() } &&
                    contrasena.any { it.isLowerCase() })){

                    edtContrasenna.error="La contraseña debe contener al menos 8 caracteres entre mayúsculas, minúsculas y números"

                }else{

                    crearCuenta(edtUser.text.toString(),edtContrasenna.text.toString())

                }



            }


        }catch (e:Exception){
            val alertDialog=AlertDialog.Builder(this)
                .setMessage(e.message)
                .setPositiveButton("Aceptar"){_, _->}
                .setCancelable(true)
                .show()
        }

    }



    private fun validarEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun crearCuenta(email: String, contrasena:String){

        mAuth.createUserWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    val user:FirebaseUser?=mAuth.currentUser
                    Toast.makeText(this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))


                }else{

                    val dialog=AlertDialog.Builder(this)
                        .setMessage(task.exception?.message)
                        .setPositiveButton("Aceptar"){_, _->}
                        .setCancelable(true)
                        .show()

                }


            }

    }


}

