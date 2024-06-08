package com.example.andropeinn


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment

import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem

import android.view.View

import android.widget.EditText
import android.widget.RadioButton

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.andropeinn.databinding.ActivityReservaBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document

import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment


class reserva : AppCompatActivity() {
   lateinit var edtTime:EditText
    var hour=0
    var minutes=0
    var savedHour=0
    var savedMinutes=0
    lateinit var txtTotal:TextView

     private var contador=20;
    lateinit var reservaBinding: ActivityReservaBinding
    private val STORAGE_CODE=1999;
    private lateinit var nombre_local:TextView
    private lateinit var cantidad:EditText
    private lateinit var edtFecha:EditText

   private lateinit var metodoPago:String

   private lateinit var fecha:String

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservaBinding=ActivityReservaBinding.inflate(layoutInflater)
        setContentView(reservaBinding.root)





        //firebase
        auth=FirebaseAuth.getInstance()

        //texto total
        txtTotal=findViewById(R.id.txtTotal)
        txtTotal.setText("Total: $ ${contador*9.50}")
        //editext cantidad
        cantidad=findViewById(R.id.edtCantidad)

        val sqLite=SQLite(this)
        val db=sqLite.readableDatabase
        val sql="SELECT * FROM LOCAL"
        val cursor=db.rawQuery(sql, null)
        var local=""

        if(cursor.moveToFirst()){
            local=cursor.getString(1)
        }



        nombre_local=findViewById<TextView>(R.id.nombre_local)
        nombre_local.setText(local)


        //editext fecha

        edtFecha=findViewById(R.id.edtFecha)


        //radio button para seleccionar el tipo de pago
        val rbPagoEfectivo=findViewById<RadioButton>(R.id.pagoEfectivo)
        var rbPagoTarjeta=findViewById<RadioButton>(R.id.pagoTarjeta)


        reservaBinding.edtCantidad.setText(contador.toString())


        reservaBinding.btnMas.setOnClickListener{

              aumentarContador()


        }

        reservaBinding.btnMenos.setOnClickListener{
           disminuirContador()
        }





        reservaBinding.btnHacerReserva.setOnClickListener {

            val cantidadPersonas=reservaBinding.edtCantidad.text.toString()
            if(reservaBinding.edtFecha.text.isNullOrEmpty()){
                reservaBinding.edtFecha.error="Debe seleccionar una fecha valida"
            }else if(reservaBinding.edtTime.text.isNullOrEmpty()){
                reservaBinding.edtTime.error="Debe seleccionar una hora valida"
            }
            else if (rbPagoEfectivo.isChecked ){

                val db=SQLite(this)
                db.insertarTotal(contador*9.50)
                db.close()


               var alertDialog=AlertDialog.Builder(this)
                   .setMessage("Reserva realizada correctamente")
                   .setCancelable(true)
                   .setPositiveButton("Aceptar"){_, _->
                       startActivity(Intent(this, fin_reserva::class.java))
                   }
                   .show()


            }else if(rbPagoTarjeta.isChecked){
                startActivity(Intent(this, pago_tarjeta::class.java))
            }else{
                selecMetodoPago(rbPagoTarjeta)
            }

        }


        //fecha

        val edtFecha=findViewById<EditText>(R.id.edtFecha)
        edtFecha.setOnClickListener{
            showDatePicker()
        }

        //hora

         edtTime=findViewById<EditText>(R.id.edtTime)

        edtTime.setOnClickListener{showTimePickerDialog()}

    }


    //accion para regresar a la activity anterior

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return  true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.locales->startActivity(Intent(this, Locales::class.java))
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

    private fun showTimePickerDialog() {
      val timePicker=TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private  fun onTimeSelected(time:String) {
       edtTime.setText(" $time")

    }

    private  fun showDatePicker(){
        val datePicker=DataPickerFragment({day, month, year->onDateSelected(day,month, year)})

        datePicker.show(supportFragmentManager, "datePicker")

    }

    private fun onDateSelected(day:Int, month:Int, year:Int){

        fecha="$day/$month/$year"
        edtFecha.setText(fecha)


    }




    private fun createPdf() {
        try {
            // Obtener la ruta del directorio de documentos
            val values = ContentValues().apply {
                put(MediaStore.Files.FileColumns.DISPLAY_NAME, "comprobante.pdf")
                put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values)

            if (uri != null) {
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val writer = PdfWriter(outputStream)
                    val pdf = PdfDocument(writer)
                    val document = Document(pdf)

                    // Agregar contenido al PDF
                    val titulo=Paragraph("ANDROPE INN")
                    titulo.setTextAlignment(TextAlignment.CENTER)
                    titulo.setFontSize(40f)

                    val local= Paragraph(nombre_local.text.toString())
                    local.setTextAlignment(TextAlignment.CENTER)
                    local.setFontSize(20f)

                    val txtFecha=Paragraph(fecha)
                    txtFecha.setTextAlignment(TextAlignment.CENTER )
                    txtFecha.setFontSize(15f)


                    val cantidad= Paragraph("Cantidad de personas: " + cantidad.text.toString())
                    cantidad.setTextAlignment(TextAlignment.CENTER )
                    cantidad.setFontSize(15f)

                    val metodo= Paragraph("Método de pago: $metodoPago")
                    metodo.setTextAlignment(TextAlignment.CENTER)
                    metodo.setFontSize(15f)


                    //color del documento


                    //agregar elementos

                    document.add(titulo)
                    document.add(local)
                    document.add(cantidad)
                    document.add(metodo)
                    document.add(txtFecha)

                    // Cerrar el documento
                    document.close()
                }
            }


            val alertDialog=AlertDialog.Builder(this)
                .setMessage("Tu reserva ha sido realizado con exito!")
                .setPositiveButton("Aceptar"){_, _->}
                .setCancelable(true)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
            // Manejar errores
            Toast.makeText(this, "Error al generar el PDF", Toast.LENGTH_SHORT).show()
        }
    }



    private fun selecMetodoPago(vista: View){
        Snackbar.make(vista, "Debes seleccionar un método de pago", Snackbar.LENGTH_SHORT).show()
    }

    private  fun aumentarContador(){
        if(contador<150){

            val valorC=reservaBinding.edtCantidad.text.toString().toInt()
            contador=valorC
            contador=contador+5
        }

        reservaBinding.edtCantidad.setText(contador.toString())

        txtTotal.setText("Total: $ ${contador*9.50}")
    }


    @SuppressLint("SetTextI18n")
    private fun disminuirContador(){
        if(contador>20){
            val valorC=reservaBinding.edtCantidad.text.toString().toInt()
            contador=valorC
            contador=contador-5
        }

        txtTotal.setText("Total: $ ${contador*9.50}")
        reservaBinding.edtCantidad.setText(contador.toString())
    }




}
