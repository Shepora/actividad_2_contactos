package com.example.actividad_2_contactos

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var etFecha: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etDescripcion: TextInputEditText

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_name)


        etNombre = findViewById(R.id.etNombre)
        etFecha = findViewById(R.id.etFecha)
        etTelefono = findViewById(R.id.etTelefono)
        etEmail = findViewById(R.id.etEmail)
        etDescripcion = findViewById(R.id.etDescripcion)

        // Datos precargados cuando venimos de "Editar datos"
        intent.extras?.let {
            etNombre.setText(it.getString(EXTRA_NOMBRE, ""))
            etFecha.setText(it.getString(EXTRA_FECHA, ""))
            etTelefono.setText(it.getString(EXTRA_TELEFONO, ""))
            etEmail.setText(it.getString(EXTRA_EMAIL, ""))
            etDescripcion.setText(it.getString(EXTRA_DESCRIPCION, ""))
            parseFecha(etFecha.text.toString())
        }

        etFecha.setOnClickListener { mostrarDatePicker() }
        findViewById<TextInputLayout>(R.id.tilFecha)
            .setEndIconOnClickListener { mostrarDatePicker() }

        findViewById<View>(R.id.btnSiguiente).setOnClickListener {
            if (validar()) {
                startActivity(Intent(this, ConfirmActivity::class.java).apply {
                    putExtra(EXTRA_NOMBRE, etNombre.text.toString().trim())
                    putExtra(EXTRA_FECHA, etFecha.text.toString())
                    putExtra(EXTRA_TELEFONO, etTelefono.text.toString().trim())
                    putExtra(EXTRA_EMAIL, etEmail.text.toString().trim())
                    putExtra(EXTRA_DESCRIPCION, etDescripcion.text.toString().trim())
                })
            }
        }
    }

    private fun parseFecha(texto: String) {
        val partes = texto.split("/")
        if (partes.size == 3) {
            val dia = partes[0].toIntOrNull()
            val mes = partes[1].toIntOrNull()
            val anio = partes[2].toIntOrNull()
            if (dia != null && mes != null && anio != null) {
                calendar.set(anio, mes - 1, dia)
            }
        }
    }

    private fun mostrarDatePicker() {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                etFecha.setText(String.format("%02d/%02d/%04d", day, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }

    private fun validar(): Boolean {
        var ok = true
        val requerido = getString(R.string.error_requerido)

        fun check(til: Int, et: TextInputEditText): Boolean {
            val layout = findViewById<TextInputLayout>(til)
            return if (et.text.isNullOrBlank()) {
                layout.error = requerido
                false
            } else {
                layout.error = null
                true
            }
        }

        ok = check(R.id.tilNombre, etNombre) and ok
        ok = check(R.id.tilFecha, etFecha) and ok
        ok = check(R.id.tilTelefono, etTelefono) and ok
        ok = check(R.id.tilDescripcion, etDescripcion) and ok

        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val email = etEmail.text.toString().trim()
        if (email.isEmpty()) {
            tilEmail.error = requerido
            ok = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = getString(R.string.error_email)
            ok = false
        } else {
            tilEmail.error = null
        }
        return ok
    }

    companion object {
        const val EXTRA_NOMBRE = "nombre"
        const val EXTRA_FECHA = "fecha"
        const val EXTRA_TELEFONO = "telefono"
        const val EXTRA_EMAIL = "email"
        const val EXTRA_DESCRIPCION = "descripcion"
    }
}