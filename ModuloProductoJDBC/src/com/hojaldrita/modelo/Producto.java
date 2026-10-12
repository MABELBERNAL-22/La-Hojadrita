package com.hojaldrita.modelo;

/**
 * Representa un producto de la panaderia (pan, torta, bebida, etc).
 * Esta clase solo guarda los datos, no tiene logica de base de datos.
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precioVenta;
    private String categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precioVenta, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
    }

    public Producto(int idProducto, String nombre, String descripcion, double precioVenta, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto [id=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", precioVenta=" + precioVenta + ", categoria=" + categoria + "]";
    }
}
