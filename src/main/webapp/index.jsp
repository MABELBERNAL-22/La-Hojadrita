<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/includes/encabezado.jspf" %>

<%-- Pagina de inicio. Aqui se usa una expresion JSP para mostrar la fecha de hoy --%>
<section class="tarjeta">
    <h1>Bienvenida a La Hojaldrita</h1>
    <p>Hoy es <strong><%= java.time.LocalDate.now() %></strong>.</p>
    <p>Este es el módulo web de productos. Desde aquí se pueden consultar, agregar, editar y eliminar
       los productos de la panadería.</p>
    <p>
        <a class="boton" href="${pageContext.request.contextPath}/productos">Ver productos</a>
        <a class="boton secundario" href="${pageContext.request.contextPath}/productos?accion=nuevo">Agregar producto</a>
    </p>
</section>

<%@ include file="/includes/pie.jspf" %>
