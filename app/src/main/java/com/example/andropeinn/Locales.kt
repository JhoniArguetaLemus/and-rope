package com.example.andropeinn

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem


import android.widget.TextView
import androidx.appcompat.app.AlertDialog


import com.example.andropeinn.databinding.ActivityLocalesBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth


class Locales : AppCompatActivity() {
    private lateinit var binding:ActivityLocalesBinding
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLocalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //autenticacion
        auth=FirebaseAuth.getInstance()




        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient=GoogleSignIn.getClient(this, gso)

        mGoogleApiClient=GoogleApiClient.Builder(this)
            .enableAutoManage(this){_->

            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        //obtener el usuario de la persona que inicio sesion

        val extra=intent.extras
        val usuario=extra?.getString("usuario", "")
        val txtUsuario=findViewById<TextView>(R.id.txtUsuario)
        if(!usuario.isNullOrEmpty()){
            txtUsuario.setText("Bienvenido: ${usuario}")
        }else{
            val account=GoogleSignIn.getLastSignedInAccount(this)
            if(account !=null){
                val email=account.email

                txtUsuario.setText("Bienvenido: ${email}")
            }
        }






        //botones que llevan a las activies donde se muestran los detalles de cada local
        binding.btnJardin.setOnClickListener{ cambiarActivity(el_jardin()) }

        binding.btnEspejo.setOnClickListener{ cambiarActivity(espejo_encantado()) }

        binding.btnVerTaberna.setOnClickListener{ cambiarActivity(la_taberna()) }

        binding.btnEstrellas.setOnClickListener{ cambiarActivity(las_estrellas()) }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.historial, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.historial_reservas->startActivity(Intent(this, historial_reservaciones::class.java))
            R.id.cerrarSesion->{
                val alertDialog=AlertDialog.Builder(this)
                    .setMessage("¿Deseas cerrar sesión?")
                    .setCancelable(true)
                    .setPositiveButton("Aceptar"){_, _->
                        auth.signOut()
                        startActivity(Intent(this, Login::class.java))

                        val db=SQLite(this)
                        db.eliminarUsuario()
                    }
                    .setNegativeButton("Cancelar"){_, _->}
                    .show()

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun cambiarActivity(activity:Activity){
        startActivity(Intent(this,activity::class.java ))
    }

    private fun logout(){
        val alertDialog=AlertDialog.Builder(this)
            .setMessage("¿Deseas cerrar sesión?")
            .setCancelable(true)
            .setPositiveButton("Aceptar"){_, _->
                auth.signOut()
                startActivity(Intent(this, Login::class.java))

                val db=SQLite(this)
                db.eliminarUsuario()
            }
            .setNegativeButton("Cancelar"){_, _->}
            .show()


    }


}