import { useState, useEffect } from 'react';
import { crearProducto, actualizarProducto } from '../api/productoApi.js';

// Mismas categorias que acepta el backend (ProductoController.validar).
// Si se le agrega una categoria nueva aca, toca agregarla tambien alla.
const CATEGORIAS = ['Panadería', 'Pastelería', 'Bebidas', 'Otros'];

const PRODUCTO_VACIO = {
  nombre: '',
  descripcion: '',
  precioVenta: '',
  categoria: CATEGORIAS[0],
};

// Formulario de producto, sirve tanto para crear uno nuevo como para
// editar uno existente. Si llega "productoEditar" se precargan los
// campos y al guardar se hace PUT en vez de POST.
export default function ProductoForm({ productoEditar, onGuardado, onCancelar, onError }) {
  const [form, setForm] = useState(PRODUCTO_VACIO);
  const [guardando, setGuardando] = useState(false);

  useEffect(() => {
    if (productoEditar) {
      setForm({
        nombre: productoEditar.nombre ?? '',
        descripcion: productoEditar.descripcion ?? '',
        precioVenta: productoEditar.precioVenta ?? '',
        categoria: productoEditar.categoria ?? CATEGORIAS[0],
      });
    } else {
      setForm(PRODUCTO_VACIO);
    }
  }, [productoEditar]);

  function cambiarCampo(evento) {
    const { name, value } = evento.target;
    setForm((anterior) => ({ ...anterior, [name]: value }));
  }

  async function manejarSubmit(evento) {
    evento.preventDefault();
    setGuardando(true);
    onError(null);

    const producto = {
      ...form,
      precioVenta: Number(form.precioVenta),
    };

    try {
      if (productoEditar) {
        await actualizarProducto(productoEditar.idProducto, producto);
      } else {
        await crearProducto(producto);
      }
      onGuardado();
    } catch (error) {
      // El backend manda el texto exacto del error de validacion,
      // asi que se muestra directo (ej: "La categoria debe ser una de: ...").
      onError(error.message);
    } finally {
      setGuardando(false);
    }
  }

  return (
    <div className="overlay">
      <div className="modal">
        <h2>{productoEditar ? 'Editar producto' : 'Nuevo producto'}</h2>
        <form onSubmit={manejarSubmit}>
          <div className="campo">
            <label htmlFor="nombre">Nombre</label>
            <input
              id="nombre"
              name="nombre"
              value={form.nombre}
              onChange={cambiarCampo}
              required
              maxLength={100}
            />
          </div>

          <div className="campo">
            <label htmlFor="descripcion">Descripción</label>
            <textarea
              id="descripcion"
              name="descripcion"
              value={form.descripcion}
              onChange={cambiarCampo}
              maxLength={500}
              rows={3}
            />
          </div>

          <div className="campo">
            <label htmlFor="precioVenta">Precio de venta</label>
            <input
              id="precioVenta"
              name="precioVenta"
              type="number"
              min="0"
              step="0.01"
              value={form.precioVenta}
              onChange={cambiarCampo}
              required
            />
          </div>

          <div className="campo">
            <label htmlFor="categoria">Categoría</label>
            <select
              id="categoria"
              name="categoria"
              value={form.categoria}
              onChange={cambiarCampo}
            >
              {CATEGORIAS.map((categoria) => (
                <option key={categoria} value={categoria}>
                  {categoria}
                </option>
              ))}
            </select>
          </div>

          <div className="modal-botones">
            <button type="button" className="btn btn-secundario" onClick={onCancelar}>
              Cancelar
            </button>
            <button type="submit" className="btn btn-primario" disabled={guardando}>
              {guardando ? 'Guardando...' : 'Guardar'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
