package com.hojaldrita.app;

import com.hojaldrita.dao.ProductoDAO;
import com.hojaldrita.modelo.Producto;

import java.util.List;
import java.util.Scanner;

/**
 * Clase principal con un menu de consola para probar las 4 operaciones
 * del modulo de Producto: insertar, consultar, actualizar y eliminar.
 */
public class PruebaProductoDAO {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();
        int opcion;

        do {
            System.out.println("\n===== MODULO DE PRODUCTO - LA HOJALDRITA =====");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto por id");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.print("Elige una opcion: ");

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    registrarProducto(teclado, productoDAO);
                    break;
                case 2:
                    listarProductos(productoDAO);
                    break;
                case 3:
                    buscarProducto(teclado, productoDAO);
                    break;
                case 4:
                    modificarProducto(teclado, productoDAO);
                    break;
                case 5:
                    eliminarProducto(teclado, productoDAO);
                    break;
                case 6:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);

        teclado.close();
    }

    private static void registrarProducto(Scanner teclado, ProductoDAO productoDAO) {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Descripcion: ");
        String descripcion = teclado.nextLine();
        System.out.print("Precio de venta: ");
        double precio = Double.parseDouble(teclado.nextLine());
        System.out.print("Categoria: ");
        String categoria = teclado.nextLine();

        Producto producto = new Producto(nombre, descripcion, precio, categoria);
        boolean exito = productoDAO.insertarProducto(producto);

        System.out.println(exito ? "Producto registrado correctamente." : "No se pudo registrar el producto.");
    }

    private static void listarProductos(ProductoDAO productoDAO) {
        List<Producto> productos = productoDAO.consultarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados todavia.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private static void buscarProducto(Scanner teclado, ProductoDAO productoDAO) {
        System.out.print("Id del producto a buscar: ");
        int id = Integer.parseInt(teclado.nextLine());

        Producto producto = productoDAO.consultarProductoPorId(id);

        if (producto == null) {
            System.out.println("No existe un producto con ese id.");
        } else {
            System.out.println(producto);
        }
    }

    private static void modificarProducto(Scanner teclado, ProductoDAO productoDAO) {
        System.out.print("Id del producto a modificar: ");
        int id = Integer.parseInt(teclado.nextLine());

        Producto producto = productoDAO.consultarProductoPorId(id);
        if (producto == null) {
            System.out.println("No existe un producto con ese id.");
            return;
        }

        System.out.print("Nuevo nombre (" + producto.getNombre() + "): ");
        producto.setNombre(teclado.nextLine());
        System.out.print("Nueva descripcion (" + producto.getDescripcion() + "): ");
        producto.setDescripcion(teclado.nextLine());
        System.out.print("Nuevo precio de venta (" + producto.getPrecioVenta() + "): ");
        producto.setPrecioVenta(Double.parseDouble(teclado.nextLine()));
        System.out.print("Nueva categoria (" + producto.getCategoria() + "): ");
        producto.setCategoria(teclado.nextLine());

        boolean exito = productoDAO.actualizarProducto(producto);
        System.out.println(exito ? "Producto actualizado correctamente." : "No se pudo actualizar el producto.");
    }

    private static void eliminarProducto(Scanner teclado, ProductoDAO productoDAO) {
        System.out.print("Id del producto a eliminar: ");
        int id = Integer.parseInt(teclado.nextLine());

        boolean exito = productoDAO.eliminarProducto(id);
        System.out.println(exito ? "Producto eliminado correctamente." : "No se pudo eliminar el producto.");
    }
}
