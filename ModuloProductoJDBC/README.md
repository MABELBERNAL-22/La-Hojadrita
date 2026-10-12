# Módulo Producto - JDBC (GA7-220501096-AA2-EV01)

Proyecto: Sistema de Gestión y Control de Panadería "La Hojaldrita"
Presentado por: Mabel Fernanda Bernal Amaya

## Qué es esto

Módulo de **Producto** codificado en Java puro, conectado a la base de datos
`hojaldrita_db` (MySQL/MariaDB) por medio de JDBC. Tiene las 4 funcionalidades que pide
la guía: insertar, consultar, actualizar y eliminar productos.

## Estructura del proyecto

```
ModuloProductoJDBC/
├── src/com/hojaldrita/
│   ├── conexion/ConexionBD.java     -> abre la conexión a la base de datos
│   ├── modelo/Producto.java         -> clase que representa un producto
│   ├── dao/ProductoDAO.java         -> aquí están el insert, select, update y delete
│   └── app/PruebaProductoDAO.java   -> clase Main con un menú de consola para probar todo
├── lib/                             -> aquí va el .jar del conector de MySQL (ver abajo)
└── README.md
```

## Antes de correrlo

1. Ten corriendo tu base de datos. Si la levantaste con Docker, el contenedor se llama
   `hojaldrita-mysql` y ya tiene creada la base `hojaldrita_db` con la tabla `Producto`.
   Si usas XAMPP, enciende el módulo MySQL y crea la base con el script de GA6-AA2-EV02.
2. Abre `src/com/hojaldrita/conexion/ConexionBD.java` y revisa que `USUARIO` y
   `CONTRASENA` coincidan con los tuyos. Si usaste el comando de Docker que armamos
   (`MYSQL_ROOT_PASSWORD=root`), ya están puestos correctamente: usuario `root`,
   contraseña `root`.
3. Descarga el conector de MySQL para Java (MySQL Connector/J):
   https://dev.mysql.com/downloads/connector/j/
   Escoge la opción "Platform Independent" y descarga el `.jar` (por ejemplo
   `mysql-connector-j-9.1.0.jar`). Ponlo dentro de la carpeta `lib/` de este proyecto.

## Cómo correrlo

### Opción A: NetBeans / Eclipse / IntelliJ

1. Crea un proyecto Java nuevo y copia adentro la carpeta `src/`.
2. Agrega el `.jar` de `lib/` como librería del proyecto:
   - NetBeans: clic derecho en el proyecto -> Properties -> Libraries -> Add JAR/Folder.
   - Eclipse: clic derecho en el proyecto -> Build Path -> Add External Archives.
   - IntelliJ: File -> Project Structure -> Libraries -> + -> selecciona el jar.
3. Corre la clase `PruebaProductoDAO.java` (botón derecho -> Run).

### Opción B: por consola (sin IDE)

Windows:
```
javac -d bin -cp "lib/mysql-connector-j-9.1.0.jar" src/com/hojaldrita/conexion/*.java src/com/hojaldrita/modelo/*.java src/com/hojaldrita/dao/*.java src/com/hojaldrita/app/*.java
java -cp "bin;lib/mysql-connector-j-9.1.0.jar" com.hojaldrita.app.PruebaProductoDAO
```

Mac/Linux (el classpath usa `:` en vez de `;`):
```
javac -d bin -cp "lib/mysql-connector-j-9.1.0.jar" src/com/hojaldrita/conexion/*.java src/com/hojaldrita/modelo/*.java src/com/hojaldrita/dao/*.java src/com/hojaldrita/app/*.java
java -cp "bin:lib/mysql-connector-j-9.1.0.jar" com.hojaldrita.app.PruebaProductoDAO
```

## Estándares de codificación aplicados

- Clases en PascalCase: `Producto`, `ProductoDAO`, `ConexionBD`, `PruebaProductoDAO`.
- Métodos y atributos en camelCase: `insertarProducto`, `consultarProductoPorId`, `precioVenta`.
- Paquetes en minúscula: `com.hojaldrita.modelo`, `com.hojaldrita.dao`, etc.
- Atributos `private` con sus `getters`/`setters` `public`.
- Código comentado explicando qué hace cada parte.
