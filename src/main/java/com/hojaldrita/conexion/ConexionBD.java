package com.hojaldrita.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abre la conexion hacia la base de datos hojaldrita_db.
 * Es la misma clase de la evidencia anterior. Solo le agregue que los datos
 * se puedan cambiar con propiedades del sistema, para no tener que
 * recompilar si cambia la contrasena.
 */
public class ConexionBD {

    private static final String URL = System.getProperty("hojaldrita.url",
            "jdbc:mysql://localhost:3306/hojaldrita_db?useUnicode=true&characterEncoding=UTF-8");
    private static final String USUARIO = System.getProperty("hojaldrita.usuario", "root");
    private static final String CONTRASENA = System.getProperty("hojaldrita.clave", "root");

    private static final String DRIVER = System.getProperty("hojaldrita.driver", "com.mysql.cj.jdbc.Driver");

    public static Connection obtenerConexion() throws SQLException {
        // dentro de Tomcat hay que cargar el driver a mano, si no sale "No suitable driver"
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de MySQL en WEB-INF/lib", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
