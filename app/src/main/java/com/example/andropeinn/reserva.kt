package com.example.andropeinn


import android.content.ContentValues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment

import android.provider.MediaStore

import android.view.View

import android.widget.EditText
import android.widget.RadioButton

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.andropeinn.databinding.ActivityReservaBinding
import com.google.android.material.snackbar.Snackbar

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document

import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment




class reserva : AppCompatActivity() {

     private var contador=1;
    lateinit var reservaBinding: ActivityReservaBinding
    private val STORAGE_CODE=1999;
    private lateinit var nombre_local:TextView
    private lateinit var cantidad:EditText
    private lateinit var edtFecha:EditText

   private lateinit var metodoPago:String

   private lateinit var fecha:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservaBinding=ActivityReservaBinding.inflate(layoutInflater)
        setContentView(reservaBinding.root)


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

            if (rbPagoEfectivo.isChecked){

                metodoPago="efectivo"
                createPdf()
            }else if(rbPagoTarjeta.isChecked){
                metodoPago="tarjeta de credito/debito"
                createPdf()
            }else{
                selecMetodoPago(rbPagoTarjeta)
            }




        }


        //fecha

        val edtFecha=findViewById<EditText>(R.id.edtFecha)
        edtFecha.setOnClickListener{
            showDatePicker()
        }

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
            contador ++
        }

        reservaBinding.edtCantidad.setText(contador.toString())
    }


    private fun disminuirContador(){
        if(contador>1){
            val valorC=reservaBinding.edtCantidad.text.toString().toInt()
            contador=valorC
            contador--
        }

        reservaBinding.edtCantidad.setText(contador.toString())
    }


    



}
