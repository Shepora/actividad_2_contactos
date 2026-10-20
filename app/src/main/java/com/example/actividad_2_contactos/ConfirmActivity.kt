package com.example.actividad_2_contactos

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        title = getString(R.string.confirmar_datos)

        val nombre = intent.getStringExtra(MainActivity.EXTRA_NOMBRE).orEmpty()
        val fecha = intent.getStringExtra(MainActivity.EXTRA_FECHA).orEmpty()
        val telefono = intent.getStringExtra(MainActivity.EXTRA_TELEFONO).orEmpty()
        val email = intent.getStringExtra(MainActivity.EXTRA_EMAIL).orEmpty()
        val descripcion = intent.getStringExtra(MainActivity.EXTRA_DESCRIPCION).orEmpty()

        findViewById<TextView>(R.id.tvNombre).text = nombre
        findViewById<TextView>(R.id.tvFecha).text = "Fecha Nacimiento: $fecha"
        findViewById<TextView>(R.id.tvTelefono).text = "Tel. $telefono"
        findViewById<TextView>(R.id.tvEmail).text = "Email: $email"
        findViewById<TextView>(R.id.tvDescripcion).text = "Descripción: $descripcion"

        findViewById<View>(R.id.btnEditar).setOnClickListener {
            // Regresa a MainActivity enviando los datos para precargarlos
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(MainActivity.EXTRA_NOMBRE, nombre)
                putExtra(MainActivity.EXTRA_FECHA, fecha)
                putExtra(MainActivity.EXTRA_TELEFONO, telefono)
                putExtra(MainActivity.EXTRA_EMAIL, email)
                putExtra(MainActivity.EXTRA_DESCRIPCION, descripcion)
            })
            finish()
        }
    }
}