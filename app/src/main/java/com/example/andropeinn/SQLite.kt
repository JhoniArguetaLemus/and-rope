package com.example.andropeinn

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLite(val context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {
    companion object{
        private const val DATABASE_NAME="comida.db"
        private const val DATABASE_VERSION=1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val local="""
            create table local (id integer primary key autoincrement, nombre_local TEXT, foto INTEGER)
        """.trimIndent()
        val sql="""
            CREATE TABLE comida (id integer primary key autoincrement, imagen INTEGER,  nombre TEXT, cantidad INTEGER, precio REAL)
        """.trimIndent()

        val usuario="""
            create table usuario (id integer primary key autoincrement, email TEXT, contrasena TEXT)
        """.trimIndent()

        val total="""
            create table total(id integer primary key autoincrement, total REAL )
        """.trimIndent()
        db!!.execSQL(total)
        db.execSQL(usuario)
        db.execSQL(sql)
        db.execSQL(local)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertarTotal(total: Double){

        val db=this.writableDatabase
        val values=ContentValues()
        values.put("total", total)
        db.insert("total", null, values)
        db.close()

    }


    public fun insertarComida(imagen:Int, nombre:String, cantidad:Int, precio:Float){
        val db=this.writableDatabase
        val values=ContentValues()
        values.put("imagen", imagen)
        values.put("nombre", nombre)
        values.put("cantidad", cantidad)
        values.put("precio", precio )
        db.insert("comida", null, values)
        db.close()


    }

    fun insertarLocal(nombreLocal:String, foto:Int){
        val db=this.writableDatabase
        val datos=ContentValues()
        datos.put("foto",  foto)
        datos.put("nombre_local ", nombreLocal)
        db.insert("local", null, datos)
        db.close()


    }

    fun eliminarComida(id: Int){
        val db = writableDatabase
        val whereClause = "id = ?"
        val whereArgs = arrayOf(id.toString())
         db.delete("comida", whereClause, whereArgs)
        db.close()

        Toast.makeText(context, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show()
    }


    fun eliminarLocal(){
        val db=writableDatabase
        db.delete("local", null, null)
        db.close()
    }

    fun insertarUsuario(email:String, contrasenna:String){
        val datos=ContentValues()
        datos.put("email", email)
        datos.put("contrasena", contrasenna)
        val db=writableDatabase
        db.insert("usuario", null, datos)
        db.close()
       


    }

    fun eliminarUsuario(){
        val db=writableDatabase
        db.delete("usuario", null, null)
        db.close()

    }

}