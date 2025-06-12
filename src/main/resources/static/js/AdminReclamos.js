const motivosReclamoMap = {
    vehiculo_no_entregado: "Vehículo no entregado",
    vehiculo_en_malas_condiciones: "Vehículo en malas condiciones",
    publicidad_engañosa: "Publicidad engañosa",
    problemas_documentacion: "Problemas con la documentación",
    deficiencias_servicio_postventa: "Deficiencias en el servicio postventa",
    cobro_incorrecto: "Cobro incorrecto",
    atencion_cliente_deficiente: "Atención al cliente deficiente",
    otro: "Otro"
};

const estadoreclamoMap = {
    pendiente: "Pendiente",
    en_proceso: "En proceso",
    resuelto: "Resuelto"
};


const formatearFecha = (fechaIso) => {
    const fecha = new Date(fechaIso);
    return fecha.toLocaleString();
};


function listarReclamos() {
    fetch('/api/reclamos')
            .then(response => response.json())
            .then(data => {
                let tabla = `
        <div class="table-responsive">
          <table class="table table-sm table-striped table-hover align-middle text-center">
            <thead class="table-dark">
              <tr>
                <th>ID</th>
                <th>ID Usuario</th>
                <th>Fecha Incidente</th>
                <th>Motivo del Reclamo</th>
                <th>Tipo de Vehiculo</th>
                <th>Detalle</th>
                <th>Estado</th>
                <th>Fecha del Reclamo</th>
              </tr>
            </thead>
            <tbody>
      `;

                data.forEach(reclamo => {
                    tabla += `
          <tr>
            <td>${reclamo.idReclamo}</td>
            <td>${reclamo.idCliente?.id_usuario}</td>
            <td>${reclamo.fechaincidente}</td>
            <td>${motivosReclamoMap[reclamo.motivoReclamo] || reclamo.motivoReclamo}</td>
            <td>${reclamo.tipo_Vehiculo}</td>
            <td>${reclamo.detalle}</td>
            <td>${estadoreclamoMap[reclamo.estadoReclamo] || reclamo.estadoReclamo}</td>
            <td>${formatearFecha(reclamo.fechaReclamo)}</td>
          </tr>
        `;
                });

                tabla += `
            </tbody>
          </table>
        </div>
      `;

                document.getElementById('tablaResultados').innerHTML = tabla;
            })
            .catch(error => {
                console.error('Error al listar reclamos:', error);
                alert('Error al cargar los reclamos.');
            });
}

function pedirIdReclamo() {
    Swal.fire({
        title: 'Buscar Reclamo',
        input: 'number',
        inputLabel: 'Ingrese el ID del reclamo',
        inputPlaceholder: 'Ej. 12',
        showCancelButton: true,
        confirmButtonText: 'Buscar',
        cancelButtonText: 'Cancelar',
        inputValidator: (value) => {
            if (!value) {
                return 'Por favor ingrese un ID válido';
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            buscarReclamoPorId(result.value);
        }
    });
}


function buscarReclamoPorId(id) {

    fetch(`/api/reclamos/${id}`)
            .then(response => {
                if (!response.ok)
                    throw new Error("Reclamo no encontrado");
                return response.json();
            })
            .then(reclamo => {
                Swal.fire({
                    title: `Reclamo ID: ${reclamo.idReclamo}`,
                    html: `
                    <div class="container text-start">
                        <div class="row">
                            <div class="col-md-6 mb-2">
                                <p><strong>ID Reclamo:</strong> ${reclamo.idReclamo}</p>
                                <p><strong>ID Usuario:</strong> ${reclamo.idCliente?.id_usuario || 'N/A'}</p>
                                <p><strong>Fecha Incidente:</strong> ${reclamo.fechaincidente}</p>
                                <p><strong>Motivo del Reclamo:</strong> ${motivosReclamoMap[reclamo.motivoReclamo] || reclamo.motivoReclamo}</p>
                            </div>
                            <div class="col-md-6 mb-2">
                                <p><strong>Tipo de Vehículo:</strong> ${reclamo.tipo_Vehiculo}</p>
                                <p><strong>Estado:</strong> ${estadoreclamoMap[reclamo.estadoReclamo] || reclamo.estadoReclamo}</p>
                                <p><strong>Fecha Reclamo:</strong> ${formatearFecha(reclamo.fechaReclamo)}</p>
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col-12">
                                <p><strong>Detalle:</strong></p>
                                <div style="white-space: pre-wrap; border: 1px solid #ccc; padding: 10px; border-radius: 6px; max-height: 200px; overflow-y: auto; background-color: #f9f9f9;">
                                    ${reclamo.detalle}
                                </div>
                            </div>
                        </div>
                        <div class="d-flex flex-wrap justify-content-center gap-2 mt-4">
                            <button id="btnEditar" class="btn btn-outline-warning">Editar Reclamo</button>
                            <button id="btnEliminar" class="btn btn-outline-danger">Eliminar Reclamo</button>
                            <button id="btnVolver" class="btn btn-outline-secondary">Volver</button>
                        </div>
                    </div>
                `,
                    showConfirmButton: false,
                    showCancelButton: false,
                    width: '90%',
                    didOpen: () => {
                        document.getElementById('btnEditar').addEventListener('click', () => editarReclamo(reclamo));
                        document.getElementById('btnEliminar').addEventListener('click', () => eliminarReclamo(reclamo.idReclamo));
                        document.getElementById('btnVolver').addEventListener('click', () => Swal.close());
                    }
                });
            })
            .catch(error => {
                console.error('Error al buscar reclamo:', error);
                Swal.fire('No encontrado', 'No se encontró ningún reclamo con ese ID.', 'warning');
            });
}


function editarReclamo(reclamo) {
    Swal.fire({
        title: 'Editar Estado del Reclamo',
        html: `
        <div class="container text-start" style="max-height: 70vh; overflow-y: auto;">
            <div class="row g-2">
                <div class="col-md-6 mb-2">
                    <label><strong>ID Reclamo:</strong></label>
                    <input class="swal2-input w-100" value="${reclamo.idReclamo}" readonly>
                </div>

                <div class="col-md-6 mb-2">
                    <label><strong>ID Usuario:</strong></label>
                    <input class="swal2-input w-100" value="${reclamo.idCliente?.id_usuario || 'N/A'}" readonly>
                </div>

                <div class="col-md-6 mb-2">
                    <label><strong>Fecha Incidente:</strong></label>
                    <input class="swal2-input w-100" value="${reclamo.fechaincidente}" readonly>
                </div>

                <div class="col-md-6 mb-2">
                    <label><strong>Motivo del Reclamo:</strong></label>
                    <input class="swal2-input w-100" value="${motivosReclamoMap[reclamo.motivoReclamo] || reclamo.motivoReclamo}" readonly>
                </div>

                <div class="col-md-6 mb-2">
                    <label><strong>Tipo de Vehículo:</strong></label>
                    <input class="swal2-input w-100" value="${reclamo.tipo_Vehiculo}" readonly>
                </div>

                <div class="col-md-6 mb-2">
                    <label><strong>Fecha del Reclamo:</strong></label>
                    <input class="swal2-input w-100" value="${new Date(reclamo.fechaReclamo).toLocaleString()}" readonly>
                </div>

                <div class="col-12 mb-2">
                    <label><strong>Detalle:</strong></label>
                    <textarea class="swal2-textarea w-100" readonly rows="3">${reclamo.detalle}</textarea>
                </div>

                <div class="col-12 mb-2">
                    <label><strong>Estado del Reclamo:</strong></label>
                    <select id="estadoEdit" class="swal2-input w-100">
                        <option value="pendiente" ${reclamo.estadoReclamo === 'pendiente' ? 'selected' : ''}>Pendiente</option>
                        <option value="en_proceso" ${reclamo.estadoReclamo === 'en_proceso' ? 'selected' : ''}>En Proceso</option>
                        <option value="resuelto" ${reclamo.estadoReclamo === 'resuelto' ? 'selected' : ''}>Resuelto</option>
                    </select>
                </div>
            </div>
        </div>
        `,
        width: '90%',
        customClass: {
            popup: 'swal2-responsive-popup'
        },
        showCancelButton: true,
        confirmButtonText: 'Guardar Cambios',
        cancelButtonText: 'Cancelar',

        preConfirm: () => {
            const nuevoEstado = document.getElementById('estadoEdit').value;

            const reclamoActualizado = {
                estadoReclamo: nuevoEstado
            };

            return fetch(`/api/reclamos/${reclamo.idReclamo}`, {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(reclamoActualizado)
            })
                .then(response => {
                    if (!response.ok)
                        throw new Error('Error al actualizar el estado del reclamo');
                    Swal.fire('Actualizado', 'Estado del reclamo actualizado con éxito.', 'success');
                    listarReclamos();
                })
                .catch(() => {
                    Swal.fire('Error', 'No se pudo actualizar el estado del reclamo.', 'error');
                });
        }
    });
}


function eliminarReclamo(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "Esta acción no se puede deshacer.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/reclamos/${id}`, {method: 'DELETE'})
                    .then(response => {
                        if (!response.ok)
                            throw new Error();
                        Swal.fire('Eliminado', 'El reclamo ha sido eliminado.', 'success');
                        listarReclamos();
                    })
                    .catch(() => Swal.fire('Error', 'No se pudo eliminar el reclamo.', 'error'));
        }
    });
}