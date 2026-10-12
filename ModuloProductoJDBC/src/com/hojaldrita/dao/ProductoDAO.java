package com.hojaldrita.dao;

import com.hojaldrita.conexion.ConexionBD;
import com.hojaldrita.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) del Producto.
 * Aqui estan las 4 operaciones que pide la evidencia: insertar, consultar,
 * actualizar y eliminar, todas usando PreparedStatement para evitar
 * inyeccion SQL.
 */
public class ProductoDAO {

    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO Producto (nombre, descripcion, precio_venta, categoria) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, producto.getNombre());
            sentencia.setString(2, producto.getDescripcion());
            sentencia.setDouble(3, producto.getPrecioVenta());
            sentencia.setString(4, producto.getCategoria());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> consultarProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los productos: " + e.getMessage());
        }

        return productos;
    }

    public Producto consultarProductoPorId(int idProducto) {
        String sql = "SELECT * FROM Producto WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idProducto);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearProducto(resultado);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el producto: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE Producto SET nombre = ?, descripcion = ?, precio_venta = ?, categoria = ? WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, producto.getNombre());
            sentencia.setString(2, producto.getDescripcion());
            sentencia.setDouble(3, producto.getPrecioVenta());
            sentencia.setString(4, producto.getCategoria());
            sentencia.setInt(5, producto.getIdProducto());

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM Producto WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idProducto);

            int filasAfectadas = sentencia.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    private Producto mapearProducto(ResultSet resultado) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(resultado.getInt("id_producto"));
        producto.setNombre(resultado.getString("nombre"));
        producto.setDescripcion(resultado.getString("descripcion"));
        producto.setPrecioVenta(resultado.getDouble("precio_venta"));
        producto.setCategoria(resultado.getString("categoria"));
        return producto;
    }
}
