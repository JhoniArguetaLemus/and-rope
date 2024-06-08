package com.example.andropeinn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient

import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var googleApiClient:GoogleApiClient
    private val RC_SIGN_IN = 9001
    private lateinit var edtUsuario:EditText
    private lateinit var edtContrasenna:EditText
    private lateinit var btnLogin:Button
    lateinit var recuperarContrasena:TextView
    lateinit var recordar:CheckBox
    lateinit var mAuth:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        recordar=findViewById(R.id.cbRecordarme)

        try{
            if(usuarios() .isNotEmpty()==true){
                val intent=Intent(this, Locales::class.java)
                intent.putExtra("usuario", usuarios())
                startActivity(intent)

            }

        }catch (e:Exception){
            val alertDialog=AlertDialog.Builder(this)
                .setMessage(e.message)
                .setPositiveButton("Aceptar"){_, _->}
                .show()
        }


        try{



            recuperarContrasena=findViewById(R.id.txtRecuperar)
            recuperarContrasena.setOnClickListener {
                   startActivity(Intent(this, Recuperar::class.java))
            }




        sesionGoogle()

        edtUsuario=findViewById(R.id.edt_usuario)
            btnLogin=findViewById<Button>(R.id.btnLogin)
        edtContrasenna=findViewById<EditText>(R.id.edt_contrasenna)


        mAuth=FirebaseAuth.getInstance()


        btnLogin.setOnClickListener {
            val user=edtUsuario.text.toString()
            val contrasena=edtContrasenna.text.toString()

            if(!validarEmail(user)){
                edtUsuario.error="El usuario no cumple con las caracteristicas de un correo"

            }else if(!( contrasena.length>=8 &&
                        contrasena.any { it.isLetter() } &&
                        contrasena.any { it.isDigit() } &&
                        contrasena.any { it.isUpperCase() } &&
                        contrasena.any { it.isLowerCase() })){

                edtContrasenna.error="La contraseña debe contener al menos 8 caracteres entre mayúsculas, minúsculas y números"

            }else{
                login(edtUsuario.text.toString(), edtContrasenna.text.toString())



            }


        }

        val btnGoogle=findViewById<Button>(R.id.btnLoginGoogle)
        btnGoogle.setOnClickListener {
            val singInIntent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(singInIntent, RC_SIGN_IN)

        }


        val txtCrearCuenta=findViewById<TextView>(R.id.txtCrearCuenta)
        txtCrearCuenta.setOnClickListener{
            startActivity(Intent(this, crear_cuenta::class.java))
        }


        }catch (e:Exception){
            val alertDialog=AlertDialog.Builder(this)
                .setMessage(e.message)
                .setPositiveButton("Aceptar"){_, _->}
                .show()
        }


        //soporte tecnico

        val btnSoporte=findViewById<ImageButton>(R.id.btnSoporte)
            .setOnClickListener{
                startActivity(Intent(this, soporte_inicio_sesion::class.java))
            }

    }




    private  fun sesionGoogle(){

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleApiClient=GoogleApiClient.Builder(this)
            .enableAutoManage(this){connectionResult->
                Log.d("GoogleSignIn", "Error de conexion"+connectionResult)

            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result=Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)

        if(result!!.isSuccess){


            val intent=Intent(this@Login, Locales::class.java)

            startActivity(intent)

            finish()
        }else{
            Log.e("GoogleSingIn", "Inicio de sesion con google fallo")
        }

    }


    private fun validarEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun recordar(email:String, contrasena:String){
        val db=SQLite(this)
        db.insertarUsuario(email, contrasena)
    }

    private fun usuarios():String{
        val db=SQLite(this)
        val database=db.readableDatabase
        var email=""


        val query = "SELECT *  FROM usuario"
        val cursor = database.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                email = cursor.getString(1)
            }
            cursor.close()
        }
        return email
        


    }


    private fun login(email:String, contrasena:String){
        mAuth.signInWithEmailAndPassword(email, contrasena)
            .addOnCompleteListener {task->
                if(task.isSuccessful){

                    if(recordar.isChecked){
                        recordar(email, contrasena)
                    }

                    val intent=Intent(this, Locales::class.java)
                    intent.putExtra("usuario", edtUsuario.text.toString())
                    edtUsuario.text.clear()
                    edtContrasenna.text.clear()
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Error: Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()

                }

            }
    }
}