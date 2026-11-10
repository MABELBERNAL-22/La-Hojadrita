package com.hojaldrita.servlet;

import com.hojaldrita.dao.ProductoDAO;
import com.hojaldrita.modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet del modulo Producto.
 *
 * GET  (consultas, no cambian datos):
 *   /productos                     -> lista todos los productos
 *   /productos?q=pan               -> busca por nombre
 *   /productos?accion=nuevo        -> muestra el formulario vacio
 *   /productos?accion=editar&id=3  -> muestra el formulario con los datos del producto
 *
 * POST (cambian datos):
 *   accion=guardar   -> inserta (si id = 0) o actualiza
 *   accion=eliminar  -> borra el producto
 */
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private static final List<String> CATEGORIAS = Arrays.asList("Panadería", "Pastelería", "Bebidas", "Otros");

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "nuevo":
                mostrarFormulario(request, response, new Producto());
                break;
            case "editar":
                editar(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "guardar":
                guardar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/productos");
                break;
        }
    }

    // ---------------------- metodos del GET ----------------------

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String texto = request.getParameter("q");
        List<Producto> productos;

        if (texto != null && !texto.trim().isEmpty()) {
            productos = productoDAO.buscarProductosPorNombre(texto.trim());
        } else {
            productos = productoDAO.consultarProductos();
        }

        // el mensaje de "guardado" o "eliminado" viene de la sesion (se muestra una sola vez)
        HttpSession sesion = request.getSession();
        request.setAttribute("mensaje", sesion.getAttribute("mensaje"));
        request.setAttribute("tipoMensaje", sesion.getAttribute("tipoMensaje"));
        sesion.removeAttribute("mensaje");
        sesion.removeAttribute("tipoMensaje");

        request.setAttribute("productos", productos);
        request.setAttribute("textoBusqueda", texto == null ? "" : texto);
        request.getRequestDispatcher("/productos.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = convertirEntero(request.getParameter("id"));
        Producto producto = productoDAO.consultarProductoPorId(id);

        if (producto == null) {
            guardarMensaje(request, "No se encontró el producto que se quería editar.", "error");
            response.sendRedirect(request.getContextPath() + "/productos");
            return;
        }
        mostrarFormulario(request, response, producto);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Producto producto)
            throws ServletException, IOException {

        request.setAttribute("producto", producto);
        request.setAttribute("categorias", CATEGORIAS);
        request.getRequestDispatcher("/formulario.jsp").forward(request, response);
    }

    // ---------------------- metodos del POST ----------------------

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = convertirEntero(request.getParameter("idProducto"));
        String nombre = recortar(request.getParameter("nombre"));
        String descripcion = recortar(request.getParameter("descripcion"));
        String categoria = recortar(request.getParameter("categoria"));
        String precioTexto = recortar(request.getParameter("precioVenta"));

        // lo que escribio el usuario se devuelve al formulario si algo sale mal
        Producto producto = new Producto(id, nombre, descripcion, 0, categoria);
        List<String> errores = new ArrayList<>();

        // ----- validaciones -----
        if (nombre.isEmpty()) {
            errores.add("El nombre es obligatorio.");
        } else if (nombre.length() > 100) {
            errores.add("El nombre no puede tener más de 100 caracteres.");
        }

        if (!CATEGORIAS.contains(categoria)) {
            errores.add("Escoge una categoría de la lista.");
        }

        double precio = 0;
        if (precioTexto.isEmpty()) {
            errores.add("El precio es obligatorio.");
        } else {
            try {
                precio = Double.parseDouble(precioTexto.replace(',', '.'));
                if (precio < 0) {
                    errores.add("El precio no puede ser negativo.");
                } else if (precio > 99999999) {
                    errores.add("El precio es demasiado grande.");
                }
            } catch (NumberFormatException e) {
                errores.add("El precio debe ser un número, por ejemplo 2500.");
            }
        }
        producto.setPrecioVenta(precio);

        if (descripcion.length() > 500) {
            errores.add("La descripción no puede tener más de 500 caracteres.");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("precioEscrito", precioTexto);
            mostrarFormulario(request, response, producto);
            return;
        }

        // ----- guardar en la base de datos -----
        boolean ok;
        String texto;
        if (id == 0) {
            ok = productoDAO.insertarProducto(producto);
            texto = ok ? "Producto agregado correctamente." : "No se pudo agregar el producto. Revisa la base de datos.";
        } else {
            ok = productoDAO.actualizarProducto(producto);
            texto = ok ? "Producto actualizado correctamente." : "No se pudo actualizar el producto.";
        }

        guardarMensaje(request, texto, ok ? "ok" : "error");
        // redirect despues del POST para que al recargar la pagina no se repita el guardado
        response.sendRedirect(request.getContextPath() + "/productos");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = convertirEntero(request.getParameter("idProducto"));
        boolean ok = productoDAO.eliminarProducto(id);

        guardarMensaje(request,
                ok ? "Producto eliminado." : "No se pudo eliminar el producto (puede tener ventas o inventario asociado).",
                ok ? "ok" : "error");
        response.sendRedirect(request.getContextPath() + "/productos");
    }

    // ---------------------- auxiliares ----------------------

    private void guardarMensaje(HttpServletRequest request, String texto, String tipo) {
        HttpSession sesion = request.getSession();
        sesion.setAttribute("mensaje", texto);
        sesion.setAttribute("tipoMensaje", tipo);
    }

    private String recortar(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private int convertirEntero(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
