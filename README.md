# Módulo Producto Web (GA7-220501096-AA2-EV02)

Proyecto: Sistema de Gestión y Control de Panadería "La Hojaldrita"
Presentado por: Mabel Fernanda Bernal Amaya

## Qué es

En la evidencia anterior hice el módulo de Producto por consola con JDBC. Ahora lo pasé a la web.
Es una aplicación con servlets y JSP que usa la misma tabla `Producto` y el mismo DAO de antes.
Se puede ver la lista de productos, buscar por nombre, agregar, editar y eliminar.

Usé el DAO que ya tenía porque ya estaba probado y así no repetí código. Solo le agregué un método
para buscar por nombre.

## Estructura

```
ModuloProductoWeb/
├── src/main/java/com/hojaldrita/
│   ├── conexion/ConexionBD.java      conexión a la base de datos
│   ├── modelo/Producto.java          la clase Producto
│   ├── dao/ProductoDAO.java          insertar, consultar, actualizar, eliminar y buscar
│   ├── servlet/ProductoServlet.java  recibe las peticiones GET y POST
│   └── util/Html.java                escapa el texto antes de mostrarlo
├── src/main/webapp/
│   ├── index.jsp                     página de inicio
│   ├── productos.jsp                 lista y búsqueda
│   ├── formulario.jsp                formulario para agregar y editar
│   ├── includes/                     encabezado.jspf y pie.jspf (se incluyen en todas)
│   ├── css/estilos.css
│   └── WEB-INF/ (web.xml y lib con el conector de MySQL)
├── bd/hojaldrita_producto.sql        crea la base, la tabla y 5 productos de ejemplo
├── hojaldrita-web.war                el proyecto ya empaquetado
├── compilar.bat / compilar.sh        para volver a armar el .war
└── enlace_repositorio.txt
```

## Cómo usa GET y POST

| Acción | Método | Por qué |
|---|---|---|
| Ver la lista | GET `/productos` | solo consulta |
| Buscar por nombre | GET `/productos?q=pan` | solo consulta, y el texto queda en la URL |
| Abrir formulario vacío | GET `/productos?accion=nuevo` | solo muestra la página |
| Abrir formulario con datos | GET `/productos?accion=editar&id=3` | solo consulta |
| Guardar (agregar o editar) | POST | cambia datos |
| Eliminar | POST | cambia datos |

Eliminar lo hice con POST y no con un link GET, porque un link se puede abrir por error
(o lo puede abrir el navegador solo) y borraría un producto sin querer.
Después de cada POST el servlet hace un `redirect`, así si se recarga la página no se guarda dos veces.

## Elementos de JSP que se usaron

- Directiva `page` (contentType, pageEncoding, import).
- Directiva `include` para el encabezado y el pie.
- Declaración `<%! %>` con el método `formatoPrecio`.
- Scriptlets `<% %>` para recorrer la lista y armar las filas de la tabla.
- Expresiones `<%= %>` para mostrar los datos.
- Lenguaje de expresiones `${ }` para los mensajes y las rutas.
- Comentarios JSP `<%-- --%>`.

## Validaciones

Las hace el servlet antes de tocar la base de datos:

- Nombre obligatorio y de máximo 100 caracteres.
- Categoría tiene que ser una de la lista (Panadería, Pastelería, Bebidas, Otros).
- Precio obligatorio, numérico, no negativo (acepta coma o punto).
- Descripción de máximo 500 caracteres.
- Si algo falla, se vuelve al formulario con los errores y lo que el usuario había escrito.
- Todo lo que viene de la base de datos se escapa con `Html.escapar`, para que un `<script>` quede como texto.

## Cómo correrlo

Se necesita **Tomcat 10** (no sirve el 9, porque el código usa `jakarta.servlet`) y Java 17 o más.

### 1. Base de datos

Si usas el contenedor de Docker de las evidencias anteriores (`hojaldrita-mysql`):

```
docker start hojaldrita-mysql
type bd\hojaldrita_producto.sql | docker exec -i hojaldrita-mysql mysql -uroot -proot --default-character-set=utf8mb4
```

En Mac o Linux cambia `type bd\...` por `cat bd/hojaldrita_producto.sql`.
También se puede abrir el script en MySQL Workbench y ejecutarlo.

La conexión usa usuario `root` y contraseña `root`. Si los tuyos son otros, cámbialos en
`ConexionBD.java` y vuelve a armar el .war con `compilar.bat`.

### 2. Tomcat

1. Copia `hojaldrita-web.war` en la carpeta `webapps` de Tomcat 10.
2. Inicia Tomcat (`bin\startup.bat`).
3. Abre en el navegador: http://localhost:8080/hojaldrita-web/

En NetBeans o Eclipse también se puede abrir la carpeta como proyecto web, agregar Tomcat 10
como servidor y darle Run.

## Estándares de codificación

- Clases en PascalCase: `ProductoServlet`, `ProductoDAO`.
- Métodos y variables en camelCase: `insertarProducto`, `precioVenta`.
- Paquetes en minúscula: `com.hojaldrita.servlet`.
- Atributos `private` con getters y setters.
- Consultas con `PreparedStatement` para evitar inyección SQL.
