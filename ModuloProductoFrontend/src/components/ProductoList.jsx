// Tabla que lista los productos. Recibe los datos y las funciones de
// editar/eliminar por props, no sabe nada de la API directamente,
// para que se pueda reusar despues con otros listados si hace falta.
export default function ProductoList({ productos, onEditar, onEliminar }) {
  if (productos.length === 0) {
    return <p>No hay productos para mostrar.</p>;
  }

  return (
    <table>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Precio</th>
          <th>Categoría</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        {productos.map((producto) => (
          <tr key={producto.idProducto}>
            <td>{producto.nombre}</td>
            <td>{producto.descripcion}</td>
            <td>${Number(producto.precioVenta).toLocaleString('es-CO')}</td>
            <td>{producto.categoria}</td>
            <td className="acciones-fila">
              <button className="btn btn-secundario" onClick={() => onEditar(producto)}>
                Editar
              </button>
              <button className="btn btn-peligro" onClick={() => onEliminar(producto)}>
                Eliminar
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
