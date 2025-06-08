document.getElementById('btnEnviarReclamo').addEventListener('click', function (event) {
    event.preventDefault();

    const fechaIncidente = document.querySelector('input[type="date"]').value;
    const motivoReclamo = document.querySelector('select[name="motivo_reclamo"]').value;
    const tipoVehiculo = document.querySelector('select[name="tipo_vehiculo"]').value;
    const detalle = document.getElementById('detalle').value.trim();
    const checkTerminos = document.getElementById('flexCheckDefault').checked;

    // Validaciones
    if (
            !fechaIncidente ||
            !motivoReclamo || motivoReclamo === "Selecciona un motivo de reclamo" ||
            !tipoVehiculo || tipoVehiculo === "Selecciona una opción" ||
            !detalle
            ) {
        Swal.fire('Campos vacíos', 'Por favor, completa todos los campos del reclamo', 'error');
        return;
    }

    if (!checkTerminos) {
        Swal.fire('Términos no aceptados', 'Debes aceptar los términos y condiciones para continuar', 'warning');
        return;
    }

    // Datos para enviar al backend
    const reclamo = {
        fechaincidente: fechaIncidente,
        motivoReclamo: motivoReclamo,
        tipo_Vehiculo: tipoVehiculo,
        detalle: detalle,
        // El estado se asignará automáticamente en el backend como "pendiente"
        // El id_usuario será obtenido del usuario autenticado en el controlador backend
    };

    fetch('/api/reclamos/Nuevo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reclamo)
    })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        Swal.fire('Error', errorMessage || 'No se pudo registrar el reclamo', 'error');
                        throw new Error(errorMessage);
                    });
                }
                return response.text();
            })
            .then(data => {
                Swal.fire('¡Reclamo enviado!', 'Tu reclamo ha sido registrado con éxito', 'success').then(() => {
                    window.location.href = '/principal'; // Redirigir o limpiar el formulario
                });
            })
            .catch(error => {
                console.error('Error al enviar el reclamo:', error);
            });
});
