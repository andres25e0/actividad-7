package com.example.music_

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val password = etPassword.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Llena nombre, correo y contraseña", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
            } else {
                registrar(nombre, correo, telefono, password, btnRegistrar)
            }
        }

        // Regresa al inicio de sesión
        findViewById<TextView>(R.id.tvIniciarSesion).setOnClickListener { finish() }
    }

    // Envía los datos a registro.php (en XAMPP) y muestra la respuesta del servidor
    private fun registrar(nombre: String, correo: String, telefono: String, password: String, boton: Button) {
        boton.isEnabled = false

        thread {
            val respuesta = enviarPost("registro.php",
                "nombre" to nombre, "correo" to correo, "telefono" to telefono, "password" to password)

            runOnUiThread {
                boton.isEnabled = true
                if (respuesta == "ok") {
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
