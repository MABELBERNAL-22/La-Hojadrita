// Todo lo que habla con el backend de Spring Boot queda en un solo lado,
// asi si despues cambia la URL o algo del API solo se toca este archivo
// y no toca ir componente por componente.

const BASE_URL = '/api/productos';

// Lee la respuesta y si vino un error del backend (los que manda
// ProductoController cuando falla una validacion), lo saca como texto
// para poder mostrarlo tal cual en la alerta.
async function manejarRespuesta(respuesta) {
  if (respuesta.ok) {
    // 204 No Content (el delete) no trae body
    if (respuesta.status === 204) return null;
    return respuesta.json();
  }
  const mensajeError = await respuesta.text();
  throw new Error(mensajeError || 'Ocurrio un error con el servidor.');
}

export async function listarProductos(nombre) {
  const url = nombre ? `${BASE_URL}?nombre=${encodeURIComponent(nombre)}` : BASE_URL;
  const respuesta = await fetch(url);
  return manejarRespuesta(respuesta);
}

export async function crearProducto(producto) {
  const respuesta = await fetch(BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(producto),
  });
  return manejarRespuesta(respuesta);
}

export async function actualizarProducto(id, producto) {
  const respuesta = await fetch(`${BASE_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(producto),
  });
  return manejarRespuesta(respuesta);
}

export async function eliminarProducto(id) {
  const respuesta = await fetch(`${BASE_URL}/${id}`, { method: 'DELETE' });
  return manejarRespuesta(respuesta);
}
