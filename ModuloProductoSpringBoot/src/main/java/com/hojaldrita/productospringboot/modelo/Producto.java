package com.hojaldrita.productospringboot.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representa un producto de la panaderia (pan, torta, bebida, etc).
 * Es la misma idea del Producto que ya tenia en el modulo JDBC, solo que
 * aqui le pongo las anotaciones de Spring Data JDBC para que sepa a que
 * tabla y columnas de la base de datos corresponde cada campo.
 */
@Table("Producto")
public class Producto {

    @Id
    @Column("id_producto")
    private Integer idProducto;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("precio_venta")
    private double precioVenta;

    @Column("categoria")
    private String categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precioVenta, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.categoria = categoria;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
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