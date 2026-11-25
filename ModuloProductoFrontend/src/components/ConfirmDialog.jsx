// Modal de confirmacion, para no dejar borrar un producto sin preguntar
// primero. Se reutiliza para cualquier otra accion que necesite
// confirmacion mas adelante (ventas, inventario, etc).
export default function ConfirmDialog({ titulo, mensaje, onConfirmar, onCancelar }) {
  return (
    <div className="overlay">
      <div className="modal">
        <h2>{titulo}</h2>
        <p>{mensaje}</p>
        <div className="modal-botones">
          <button className="btn btn-secundario" onClick={onCancelar}>
            Cancelar
          </button>
          <button className="btn btn-peligro" onClick={onConfirmar}>
            Si, eliminar
          </button>
        </div>
      </div>
    </div>
  );
}
