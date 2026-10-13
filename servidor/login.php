<?php
// Revisa que el correo y la contraseña coincidan con un usuario de la tabla usuarios
$conexion = new mysqli("localhost", "root", "", "music_db");
$conexion->set_charset("utf8mb4");

$correo = trim($_POST["correo"] ?? "");
$password = $_POST["password"] ?? "";

$consulta = $conexion->prepare("SELECT nombre, password FROM usuarios WHERE correo = ?");
$consulta->bind_param("s", $correo);
$consulta->execute();
$usuario = $consulta->get_result()->fetch_assoc();

// La contraseña está en texto plano, así que se compara directamente
if ($usuario && $usuario["password"] === $password) {
    echo "ok:" . $usuario["nombre"];
} else {
    echo "Correo o contraseña incorrectos";
}
