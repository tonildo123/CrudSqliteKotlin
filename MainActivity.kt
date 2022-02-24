package com.example.controlimagebuttonkotlin

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val code = findViewById<EditText>(R.id.et1)
        val descripcion = findViewById<EditText>(R.id.et2)
        val precio = findViewById<EditText>(R.id.et3)
        val create = findViewById<Button>(R.id.buttonCreate)
        val read = findViewById<Button>(R.id.buttonRead)
        val update = findViewById<Button>(R.id.buttonUpdate)
        val delete = findViewById<Button>(R.id.buttonDelete)
        val resultado = findViewById<TextView>(R.id.tvResul)


        // create
        create.setOnClickListener {
            val admin = admSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            val txt_codigo = code.getText().toString()
            val txt_desciption = descripcion.getText().toString()
            val txt_price = precio.getText().toString()

            registro.put("codigo", txt_codigo)
            registro.put("descripcion", txt_desciption)
            registro.put("precio", txt_price)
            bd.insert("articulos", null, registro)
            bd.close()

            //
            resultado.text = "los datos son ${txt_desciption} con el precio $ ${txt_price}"
            // limpiamos los campos
            code.setText("")
            descripcion.setText("")
            precio.setText("")


        }

        // read
        read.setOnClickListener {
            val admin = admSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion,precio from articulos where codigo=${code.text.toString()}", null)
            if (fila.moveToFirst()) {
                val first_date = fila.getString(0).toString()
                val second_date = fila.getString(1).toString()
                resultado.text ="Descripcion : ${first_date} ,  precio $ ${second_date}"
            } else
                resultado.text ="No existe un artículo con dicho código"
            bd.close()
        }
// read by description
        /*
        boton3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,precio from articulos where descripcion='${et2.text.toString()}'", null)
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0))
                et3.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
            bd.close()
        }
        */

        /// delete
        delete.setOnClickListener {
            val admin = admSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("articulos", "codigo=${code.text.toString()}", null)
            bd.close()
            code.setText("")
            descripcion.setText("")
            precio.setText("")
            if (cant == 1)
                resultado.text = "Se elimino registro"
            else
                resultado.text = "Error al eliminar registro"
        }
// update
        update.setOnClickListener {
            val admin = admSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("descripcion", descripcion.text.toString())
            registro.put("precio", precio.text.toString())
            val cant = bd.update("articulos", registro, "codigo=${code.text.toString()}", null)
            bd.close()
            if (cant == 1)
                resultado.text = "Se actualizo registro"
            else
                resultado.text = "error al actulizar registro"
        }














    }


}