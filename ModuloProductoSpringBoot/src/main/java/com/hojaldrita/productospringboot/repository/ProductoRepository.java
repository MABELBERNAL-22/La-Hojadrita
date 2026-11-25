package com.hojaldrita.productospringboot.repository;

import com.hojaldrita.productospringboot.modelo.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository del Producto.
 * Con Spring Data JDBC no toca escribir el SQL de insertar, consultar,
 * actualizar ni eliminar: eso ya viene gratis en CrudRepository (save,
 * findById, findAll, deleteById). Solo agrego el metodo de busqueda por
 * nombre, que es el que no viene por defecto.
 */
public interface ProductoRepository extends CrudRepository<Producto, Integer> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}