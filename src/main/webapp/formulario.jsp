<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.hojaldrita.modelo.Producto, com.hojaldrita.util.Html" %>
<%@ include file="/includes/encabezado.jspf" %>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<String> errores = (List<String>) request.getAttribute("errores");
    boolean esNuevo = producto.getIdProducto() == 0;

    // si hubo error, se muestra el precio tal cual lo escribio el usuario
    String precioEscrito = (String) request.getAttribute("precioEscrito");
    if (precioEscrito == null) {
        precioEscrito = esNuevo ? "" : String.valueOf((long) producto.getPrecioVenta());
    }
%>

<h1><%= esNuevo ? "Agregar producto" : "Editar producto" %></h1>

<% if (errores != null) { %>
    <div class="aviso error">
        <strong>Revisa estos datos:</strong>
        <ul>
        <% for (String error : errores) { %>
            <li><%= error %></li>
        <% } %>
        </ul>
    </div>
<% } %>

<%-- Formulario HTML que se envia por POST al servlet --%>
<form class="formulario" method="post" action="${pageContext.request.contextPath}/productos">
    <input type="hidden" name="accion" value="guardar">
    <input type="hidden" name="idProducto" value="<%= producto.getIdProducto() %>">

    <label for="nombre">Nombre del producto *</label>
    <input type="text" id="nombre" name="nombre" maxlength="100" required
           value="<%= Html.escapar(producto.getNombre()) %>" placeholder="Ej. Pan Hojaldrado">

    <label for="descripcion">Descripción</label>
    <textarea id="descripcion" name="descripcion" rows="3" maxlength="500"
              placeholder="Ej. Pan de hojaldre recién horneado"><%= Html.escapar(producto.getDescripcion()) %></textarea>

    <label for="categoria">Categoría *</label>
    <select id="categoria" name="categoria" required>
        <option value="">Selecciona una categoría</option>
        <% for (String c : categorias) { %>
            <option value="<%= c %>" <%= c.equals(producto.getCategoria()) ? "selected" : "" %>><%= c %></option>
        <% } %>
    </select>

    <label for="precioVenta">Precio de venta (COP) *</label>
    <input type="text" id="precioVenta" name="precioVenta" required
           value="<%= Html.escapar(precioEscrito) %>" placeholder="Ej. 2500">

    <div class="botones">
        <button type="submit" class="boton">Guardar</button>
        <a class="boton secundario" href="${pageContext.request.contextPath}/productos">Cancelar</a>
    </div>
</form>

<%@ include file="/includes/pie.jspf" %>
