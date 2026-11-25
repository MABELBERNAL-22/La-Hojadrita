# Modulo Producto - Front-end (React + Vite)

Este es el componente front-end del modulo de Producto, hecho en React. Se conecta
con el backend de Spring Boot que ya esta en `ModuloProductoSpringBoot` (el mismo
proyecto de la evidencia AA3-EV01/EV02).

Se hizo con Vite en vez de Create React App porque levanta mucho mas rapido y es
mas liviano para un proyecto chiquito como este.

## Componentes

- `App.jsx`: junta todo, maneja la busqueda, la tabla y los modales.
- `ProductoList.jsx`: la tabla que muestra los productos.
- `ProductoForm.jsx`: el formulario para crear o editar un producto (aparece como modal).
- `ConfirmDialog.jsx`: el modal de "¿seguro que quieres eliminar?".
- `Alert.jsx`: para mostrar los mensajes de error o de exito.
- `api/productoApi.js`: las llamadas al backend (GET, POST, PUT, DELETE a `/api/productos`).

## Como correrlo

1. Que el backend de Spring Boot este corriendo en el puerto 8080 (con el Docker
   de MySQL prendido tambien).
2. Instalar dependencias:
   ```
   npm install
   ```
3. Levantar el front:
   ```
   npm run dev
   ```
4. Abrir `http://localhost:5173`.

El `vite.config.js` ya tiene un proxy para que las peticiones a `/api` lleguen
al backend en el puerto 8080 sin problemas de CORS.
