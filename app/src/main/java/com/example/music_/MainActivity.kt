package com.example.music_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Escribe tu correo y contraseña", Toast.LENGTH_SHORT).show()
            } else {
                iniciarSesion(correo, password, btnIngresar)
            }
        }

        // Abre la pantalla de registro
        findViewById<TextView>(R.id.tvCrearCuenta).setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    // Revisa el correo y la contraseña con login.php (en XAMPP)
    private fun iniciarSesion(correo: String, password: String, boton: Button) {
        boton.isEnabled = false

        thread {
            val respuesta = enviarPost("login.php", "correo" to correo, "password" to password)

            runOnUiThread {
                boton.isEnabled = true
                if (respuesta.startsWith("ok:")) {
                    Toast.makeText(this, "Bienvenido, " + respuesta.removePrefix("ok:"), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
