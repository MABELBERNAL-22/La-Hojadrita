package com.hojaldrita.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada unicamente de abrir la conexion hacia la base de datos.
 * Se deja separada del resto del codigo para que, si cambia el servidor,
 * el usuario o la contrasena, solo haya que modificar esta clase.
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/hojaldrita_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
