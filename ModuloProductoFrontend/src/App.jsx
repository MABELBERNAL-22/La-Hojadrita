import { useEffect, useState } from 'react';
import ProductoList from './components/ProductoList.jsx';
import ProductoForm from './components/ProductoForm.jsx';
import ConfirmDialog from './components/ConfirmDialog.jsx';
import Alert from './components/Alert.jsx';
import { listarProductos, eliminarProducto } from './api/productoApi.js';

// Componente principal del modulo de Producto. Junta la barra de
// busqueda, la tabla, el formulario (que aparece como modal) y el
// modal de confirmar antes de eliminar.
export default function App() {
  const [productos, setProductos] = useState([]);
  const [busqueda, setBusqueda] = useState('');
  const [cargando, setCargando] = useState(false);

  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [productoEditando, setProductoEditando] = useState(null);
  const [productoAEliminar, setProductoAEliminar] = useState(null);

  const [error, setError] = useState(null);
  const [exito, setExito] = useState(null);

  async function cargarProductos(nombre) {
    setCargando(true);
    setError(null);
    try {
      const datos = await listarProductos(nombre);
      setProductos(datos);
    } catch (e) {
      setError(e.message);
    } finally {
      setCargando(false);
    }
  }

  // Carga inicial de productos apenas entra a la pantalla.
  useEffect(() => {
    cargarProductos();
  }, []);

  function manejarBusqueda(evento) {
    evento.preventDefault();
    cargarProductos(busqueda);
  }

  function abrirNuevo() {
    setProductoEditando(null);
    setMostrarFormulario(true);
  }

  function abrirEditar(producto) {
    setProductoEditando(producto);
    setMostrarFormulario(true);
  }

  function manejarGuardado() {
    setMostrarFormulario(false);
    setExito(productoEditando ? 'Producto actualizado.' : 'Producto creado.');
    cargarProductos(busqueda);
  }

  async function confirmarEliminar() {
    try {
      await eliminarProducto(productoAEliminar.idProducto);
      setExito('Producto eliminado.');
      setProductoAEliminar(null);
      cargarProductos(busqueda);
    } catch (e) {
      setProductoAEliminar(null);
      setError(e.message);
    }
  }

  return (
    <div className="app">
      <h1>La Hojaldrita - Productos</h1>

      <Alert tipo="error" mensaje={error} onCerrar={() => setError(null)} />
      <Alert tipo="exito" mensaje={exito} onCerrar={() => setExito(null)} />

      <div className="barra-superior">
        <form onSubmit={manejarBusqueda} style={{ display: 'flex', gap: 8, flex: 1 }}>
          <input
            className="buscador"
            placeholder="Buscar producto por nombre..."
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
          />
          <button type="submit" className="btn btn-secundario">
            Buscar
          </button>
        </form>
        <button className="btn btn-primario" onClick={abrirNuevo}>
          + Nuevo producto
        </button>
      </div>

      {cargando ? (
        <p>Cargando productos...</p>
      ) : (
        <ProductoList
          productos={productos}
          onEditar={abrirEditar}
          onEliminar={(producto) => setProductoAEliminar(producto)}
        />
      )}

      {mostrarFormulario && (
        <ProductoForm
          productoEditar={productoEditando}
          onGuardado={manejarGuardado}
          onCancelar={() => setMostrarFormulario(false)}
          onError={setError}
        />
      )}

      {productoAEliminar && (
        <ConfirmDialog
          titulo="Eliminar producto"
          mensaje={`¿Seguro que quieres eliminar "${productoAEliminar.nombre}"?`}
          onConfirmar={confirmarEliminar}
          onCancelar={() => setProductoAEliminar(null)}
        />
      )}
    </div>
  );
}
