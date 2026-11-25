package com.hojaldrita.productospringboot.controller;

import com.hojaldrita.productospringboot.modelo.Producto;
import com.hojaldrita.productospringboot.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller del Producto.
 * Aqui reciben las peticiones web, parecido a lo que hacia el
 * ProductoServlet, pero en Spring Boot no toca manejar manualmente el
 * request/response: cada metodo ya sabe con que verbo HTTP y con que
 * URL se conecta, por las anotaciones.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return productoRepository.findByNombreContainingIgnoreCase(nombre);
        }
        return (List<Producto>) productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> consultarPorId(@PathVariable Integer id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> insertar(@RequestBody Producto producto) {
        String error = validar(producto);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        if (productoRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String error = validar(producto);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        producto.setIdProducto(id);
        Producto actualizado = productoRepository.save(producto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        if (productoRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String validar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            return "El nombre es obligatorio.";
        }
        if (producto.getNombre().length() > 100) {
            return "El nombre no puede tener mas de 100 caracteres.";
        }
        List<String> categoriasValidas = List.of("Panadería", "Pastelería", "Bebidas", "Otros");
        if (producto.getCategoria() == null || !categoriasValidas.contains(producto.getCategoria())) {
            return "La categoria debe ser una de: " + categoriasValidas;
        }
        if (producto.getPrecioVenta() < 0) {
            return "El precio no puede ser negativo.";
        }
        if (producto.getDescripcion() != null && producto.getDescripcion().length() > 500) {
            return "La descripcion no puede tener mas de 500 caracteres.";
        }
        return null;
    }
}