package com.example.music_

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

// Carpeta "music" de XAMPP. 10.0.2.2 es tu computadora vista desde el emulador.
// Si usas un celular físico, cambia 10.0.2.2 por la IP de tu computadora.
const val URL_SERVIDOR = "http://10.0.2.2/music/"

// Envía los campos por POST a un archivo PHP y regresa lo que responde el servidor.
// Se debe llamar dentro de un thread, porque Android no deja usar internet en el hilo principal.
fun enviarPost(archivo: String, vararg campos: Pair<String, String>): String {
    val datos = campos.joinToString("&") { it.first + "=" + URLEncoder.encode(it.second, "UTF-8") }

    return try {
        val conexion = URL(URL_SERVIDOR + archivo).openConnection() as HttpURLConnection
        conexion.requestMethod = "POST"
        conexion.connectTimeout = 5000
        conexion.readTimeout = 5000
        conexion.doOutput = true
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conexion.outputStream.use { it.write(datos.toByteArray()) }
        conexion.inputStream.bufferedReader().use { it.readText() }.trim()
    } catch (e: Exception) {
        "No se pudo conectar con el servidor"
    }
}
