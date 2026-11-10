<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.hojaldrita.modelo.Producto, com.hojaldrita.util.Html" %>
<%@ include file="/includes/encabezado.jspf" %>

<%-- Declaracion JSP: un metodo que se puede usar en toda la pagina --%>
<%!
    private String formatoPrecio(double precio) {
        return String.format("$%,.0f", precio).replace(',', '.');
    }
%>

<%-- Scriptlet: se recupera la lista que armo el servlet --%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    int total = (productos == null) ? 0 : productos.size();
%>

<h1>Productos</h1>

<%-- Mensaje de exito o error que viene del servlet (EL) --%>
<% if (request.getAttribute("mensaje") != null) { %>
    <div class="aviso ${tipoMensaje}">${mensaje}</div>
<% } %>

<%-- Formulario de busqueda con metodo GET --%>
<form class="busqueda" method="get" action="${pageContext.request.contextPath}/productos">
    <input type="text" name="q" value="<%= Html.escapar((String) request.getAttribute("textoBusqueda")) %>"
           placeholder="Buscar por nombre...">
    <button type="submit" class="boton">Buscar</button>
    <a class="boton secundario" href="${pageContext.request.contextPath}/productos">Ver todos</a>
</form>

<p class="total">Productos encontrados: <strong><%= total %></strong></p>

<% if (total == 0) { %>
    <p class="vacio">No hay productos para mostrar.</p>
<% } else { %>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Categoría</th>
            <th>Precio</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
    <% for (Producto p : productos) { %>
        <tr>
            <td><%= p.getIdProducto() %></td>
            <td><%= Html.escapar(p.getNombre()) %></td>
            <td><%= Html.escapar(p.getDescripcion()) %></td>
            <td><%= Html.escapar(p.getCategoria()) %></td>
            <td><%= formatoPrecio(p.getPrecioVenta()) %></td>
            <td class="acciones">
                <%-- Editar es una consulta, por eso va por GET --%>
                <a class="boton chico" href="${pageContext.request.contextPath}/productos?accion=editar&id=<%= p.getIdProducto() %>">Editar</a>

                <%-- Eliminar cambia datos, por eso va por POST --%>
                <form method="post" action="${pageContext.request.contextPath}/productos"
                      onsubmit="return confirm('¿Seguro que quieres eliminar este producto?');">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idProducto" value="<%= p.getIdProducto() %>">
                    <button type="submit" class="boton chico peligro">Eliminar</button>
                </form>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>
<% } %>

<%@ include file="/includes/pie.jspf" %>
