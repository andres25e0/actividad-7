<?php
// Recibe los datos del registro desde la app y los guarda en la tabla usuarios
$conexion = new mysqli("localhost", "root", "", "music_db");
$conexion->set_charset("utf8mb4");

$nombre = trim($_POST["nombre"] ?? "");
$correo = trim($_POST["correo"] ?? "");
$telefono = trim($_POST["telefono"] ?? "");
$password = $_POST["password"] ?? "";

if ($nombre == "" || $correo == "" || $password == "") {
    exit("Faltan datos");
}

// Revisa que el correo no esté registrado
$consulta = $conexion->prepare("SELECT id FROM usuarios WHERE correo = ?");
$consulta->bind_param("s", $correo);
$consulta->execute();
if ($consulta->get_result()->num_rows > 0) {
    exit("El correo ya está registrado");
}

// Guarda el usuario (por ahora la contraseña se guarda en texto plano)
$consulta = $conexion->prepare("INSERT INTO usuarios (nombre, correo, telefono, password) VALUES (?, ?, ?, ?)");
$consulta->bind_param("ssss", $nombre, $correo, $telefono, $password);
$consulta->execute();

echo "ok";
