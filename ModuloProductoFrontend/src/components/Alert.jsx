// Componente chiquito para mostrar mensajes de error o de exito.
// Se usa en el formulario de producto para sacar los mensajes que
// manda el backend (por ejemplo "El nombre es obligatorio.").
export default function Alert({ tipo = 'error', mensaje, onCerrar }) {
  if (!mensaje) return null;

  const clase = tipo === 'exito' ? 'alerta alerta-exito' : 'alerta alerta-error';

  return (
    <div className={clase}>
      {mensaje}
      {onCerrar && (
        <button
          className="btn btn-secundario"
          style={{ marginLeft: 12, padding: '2px 8px' }}
          onClick={onCerrar}
        >
          x
        </button>
      )}
    </div>
  );
}
