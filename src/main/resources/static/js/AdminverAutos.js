async function listarAutos() {
    try {
        const response = await fetch('http://localhost:8081/api/autos');
        const autos = await response.json();

        if (!response.ok) throw new Error('Error al obtener los autos');

        const tablaResultados = document.getElementById('tablaResultados');
        tablaResultados.innerHTML = '';

        let tabla = `
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle text-center">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Modelo</th>
                    <th>Marca</th>
                    <th>Año</th>
                    <th>Precio</th>
                    <th>Kilometraje</th>
                    <th>Transmisión</th>
                    <th>Combustible</th>
        
                    <th>Alquiler</th>
                    <th>Pago por Alquiler</th>
                    <th>Categoría</th>
                    <th>Estado</th>
                    <th>Colores</th>
                    <th>Imágenes</th>
                </tr>
            </thead>
            <tbody>
        `;

        autos.forEach(auto => {
            const colores = auto.colores.map(color => color.nombreColor).join(", ");
            const alquilerDisponible = auto.disponibleAlquiler;
            const pagoalquiler = auto.pagoalquiler;
            
            const imagenes = auto.imagenes.map(imagen => 
                `<img src="/img/${imagen.nombreArchivo}" class="img-thumbnail" width="90" height="90" alt="Imagen del auto">`
            ).join(" ");

            tabla += `
            <tr>
                <td>${auto.idAuto}</td>
                <td>${auto.modelo}</td>
                <td>${auto.marca}</td>
                <td>${auto.ano}</td>
                <td>${auto.precio}</td>
                <td>${auto.kilometraje}</td>
                <td>${auto.transmision}</td>
                <td>${auto.combustible}</td>
                
                <td>${pagoalquiler}</td>
                <td>${alquilerDisponible}</td>
                <td>${auto.categoria}</td>
                <td>${auto.estado}</td>
                <td>${colores}</td>
                <td>${imagenes}</td>
            </tr>
            `;
        });

        tabla += `
           </tbody>
          </table>
        </div>`;

        tablaResultados.innerHTML = tabla;

    } catch (error) {
        console.error(error);
        alert("Error al listar los autos");
    }
}
 
 async function InsertarAuto() {
    const modalHTML = `
    <div id="modalFormularioInsertarAuto" class="modal fade" tabindex="-1" aria-labelledby="modalFormularioLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg"> 
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalFormularioLabel">Agregar Nuevo Auto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <form id="formAuto" onsubmit="event.preventDefault(); guardarAuto();">

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="modelo" class="form-label">Modelo</label>
                                <input type="text" class="form-control" id="modelo" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="marca" class="form-label">Marca</label>
                                <input type="text" class="form-control" id="marca" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="ano" class="form-label">Año</label>
                                <input type="number" class="form-control" id="ano" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="precio" class="form-label">Precio</label>
                                <input type="number" class="form-control" id="precio" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="kilometraje" class="form-label">Kilometraje</label>
                                <input type="number" class="form-control" id="kilometraje" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="transmision" class="form-label">Transmisión</label>
                                <input type="text" class="form-control" id="transmision" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="combustible" class="form-label">Combustible</label>
                                <input type="text" class="form-control" id="combustible" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="categoria" class="form-label">Categoría</label>
                                <input type="text" class="form-control" id="categoria" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <input type="text" class="form-control" id="estado" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento1" class="form-label">Equipamiento 1</label>
                                <input type="text" class="form-control" id="equipamiento1">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento2" class="form-label">Equipamiento 2</label>
                                <input type="text" class="form-control" id="equipamiento2">
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento3" class="form-label">Equipamiento 3</label>
                                <input type="text" class="form-control" id="equipamiento3">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento4" class="form-label">Equipamiento 4</label>
                                <input type="text" class="form-control" id="equipamiento4">
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                            <label for="pagoAlquiler" class="form-label">Precio de Alquiler (USD/día)</label>
                            <input type="number" class="form-control" id="pagoAlquiler">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Colores</label>
                            <div id="checkboxColores"></div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Imágenes para cada color (nombre del archivo con extensión)</label>
                            <div id="imagenesPorColor"></div>
                        </div>
                        
                        <div class="mb-3">
                <label class="form-label">¿Disponible para alquiler?</label>
                    <div class="form-check">
                  <input class="form-check-input" type="radio" name="disponibleAlquiler" id="alquilerSi" value="sí" checked>
                 <label class="form-check-label" for="alquilerSi">
                             Sí
                       </label>
                    </div>  
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="disponibleAlquiler" id="alquilerNo" value="no">
              <label class="form-check-label" for="alquilerNo">
                              No
                 </label>
                     </div>
                        </div>
                        
                        <div class="d-flex justify-content-center gap-3">
                            <button type="submit" class="btn btn-primary">Insertar Auto</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    `;

    
    const contenedor = document.getElementById('tablaResultados');
    contenedor.innerHTML = modalHTML;

    // Obtener los colores desde la base de datos
    try {
        const response = await fetch('/api/colores');
        if (!response.ok) throw new Error('No se pudo obtener los colores.');
        const colores = await response.json();

        // Insertar los checkboxes de colores
        const contenedorColores = document.getElementById('checkboxColores');
        const contenedorImagenes = document.getElementById('imagenesPorColor');

        colores.forEach(color => {
            const checkboxHTML = `
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="${color.idColor}" id="color${color.idColor}" name="colores" data-nombre="${color.nombreColor}">
                    <label class="form-check-label" for="color${color.idColor}">
                        ${color.nombreColor}
                    </label>
                </div>
            `;
            contenedorColores.insertAdjacentHTML('beforeend', checkboxHTML);
        });

        // Evento para mostrar inputs de nombre de imagen cuando seleccionen colores
        contenedorColores.addEventListener('change', () => {
        const seleccionados = document.querySelectorAll('input[name="colores"]:checked');
        const coloresSeleccionados = Array.from(seleccionados).map(cb => ({
        idColor: cb.value,
        nombreColor: cb.dataset.nombre
        }));

       const imagenesExistentes = Array.from(document.querySelectorAll('#imagenesPorColor input'))
        .reduce((mapa, input) => {
            const id = input.id.replace('imagenColor', '');
            mapa[id] = input.value;
            return mapa;
        }, {});

        // Limpiar el contenedor
         contenedorImagenes.innerHTML = '';

        coloresSeleccionados.forEach(({ idColor, nombreColor }) => {
        const valorAnterior = imagenesExistentes[idColor] || '';
        const div = document.createElement('div');
        div.classList.add('mb-2');
        div.innerHTML = `
            <label for="imagenColor${idColor}">Imagen para ${nombreColor}:</label>
            <input type="text" class="form-control" id="imagenColor${idColor}" name="imagenColor${idColor}" value="${valorAnterior}" placeholder="ejemplo: auto_${nombreColor.toLowerCase()}.jpg" required>
        `;
        contenedorImagenes.appendChild(div);
             });
        });

    } catch (error) {
        console.error('Error al cargar los colores:', error);
        alert("Hubo un error al cargar los colores.");
    }

   
    const myModal = new bootstrap.Modal(document.getElementById('modalFormularioInsertarAuto'));
    myModal.show();
}




async function guardarAuto() {
    
    const modelo = document.getElementById('modelo').value.trim();
    const marca = document.getElementById('marca').value.trim();
    const ano = parseInt(document.getElementById('ano').value);
    const precio = parseFloat(document.getElementById('precio').value);
    const kilometraje = parseInt(document.getElementById('kilometraje').value);
    const transmision = document.getElementById('transmision').value.trim();
    const combustible = document.getElementById('combustible').value.trim();
    const disponibleAlquiler = document.querySelector('input[name="disponibleAlquiler"]:checked').value;
    const pagoalquiler = parseFloat(document.getElementById('pagoAlquiler').value);
    const equipamiento1 = document.getElementById('equipamiento1').value.trim();
    const equipamiento2 = document.getElementById('equipamiento2').value.trim();
    const equipamiento3 = document.getElementById('equipamiento3').value.trim();
    const equipamiento4 = document.getElementById('equipamiento4').value.trim();
    const categoria = document.getElementById('categoria').value.trim();
    const estado = document.getElementById('estado').value.trim();

    
    if (!modelo || !marca || !ano || !precio || isNaN(kilometraje) || !transmision || !combustible || !categoria || !estado) {
        Swal.fire('Error', 'Por favor, complete todos los campos obligatorios.', 'error');
        return;
    }
    
        if (pagoalquiler === null || isNaN(pagoalquiler) || pagoalquiler <= 0) {
         Swal.fire('Error', 'Por favor, ingrese un pago de alquiler válido y mayor que cero.', 'error');
         return;
                }

    
    const checkboxesColores = document.querySelectorAll('input[name="colores"]:checked');
    if (checkboxesColores.length === 0) {
        Swal.fire('Error', 'Por favor, seleccione al menos un color.', 'error');
        return;
    }

   
    const coloresSeleccionados = Array.from(checkboxesColores).map(cb => ({
        idColor: parseInt(cb.value),
        nombreColor: cb.dataset.nombre
    }));

    // Obtener nombres de imagen para cada color
    let imagenes;
    try {
        imagenes = coloresSeleccionados.map(color => {
            const inputImagen = document.getElementById(`imagenColor${color.idColor}`);
            if (!inputImagen || !inputImagen.value.trim()) {
                throw new Error(`Falta imagen para color ${color.nombreColor}`);
            }
            return {
                idColor: color.idColor,
                nombreColor: color.nombreColor,
                nombreArchivo: inputImagen.value.trim()
            };
        });
    } catch (error) {
        Swal.fire('Error', error.message, 'error');
        return;
    }

    
    const autoDTO = {
        modelo,
        marca,
        ano,
        precio,
        kilometraje,
        transmision,
        combustible,
        disponibleAlquiler,
        pagoalquiler,
        equipamiento1,
        equipamiento2,
        equipamiento3,
        equipamiento4,
        categoria,
        estado,
        colores: coloresSeleccionados,
        imagenes
    };

    // Enviar datos al servidor
    try {
        const response = await fetch('/api/autos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(autoDTO)
        });

        if (response.ok) {
            
            Swal.fire({
                icon: 'success',
                title: '¡Éxito!',
                text: 'Auto guardado correctamente',
                confirmButtonText: 'Cerrar'
            });

            document.getElementById('formAuto').reset();
            const modalFormulario = bootstrap.Modal.getInstance(document.getElementById('modalFormularioInsertarAuto'));
            modalFormulario.hide();

            //listar Autos de manera automatica, es opcional
            //listarAutos();

        } else {
            Swal.fire('Error', 'Hubo un error al guardar el auto. Inténtelo de nuevo.', 'error');
        }
    } catch (error) {
        console.error('Error al guardar el auto:', error);
        Swal.fire('Error', 'Hubo un error al guardar el auto. Inténtelo de nuevo.', 'error');
    }
}

async function pedirIdAuto() {
   
    const { value: idAuto } = await Swal.fire({
        title: 'Buscar Auto',
        text: 'Ingrese el ID del auto a buscar:',
        input: 'text',
        inputPlaceholder: 'ID del auto',
        showCancelButton: true,
        confirmButtonText: 'Buscar',
        cancelButtonText: 'Cancelar',
        inputValidator: (value) => {
            if (!value) {
                return '¡El ID no puede estar vacío!';
            }
        }
    });

    if (idAuto) {
        try {
            const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`);
            const auto = await response.json();

            if (!response.ok) throw new Error('Auto no encontrado');

           
            verDetalles(auto);

        } catch (error) {
            console.error(error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No se pudo encontrar el auto. Verifica el ID e intenta de nuevo.'
            });
        }
    }
}

async function verDetalles(auto) {
   
    const modalHTML = `
    <div id="modalDetallesAuto" class="modal fade" tabindex="-1" aria-labelledby="modalDetallesLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg"> 
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalDetallesLabel">Detalles del Auto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <h4>Modelo: ${auto.modelo}</h4>
                    <p><strong>Marca:</strong> ${auto.marca}</p>
                    <p><strong>Año:</strong> ${auto.ano}</p>
                    <p><strong>Precio:</strong> ${auto.precio}</p>
                    <p><strong>Kilometraje:</strong> ${auto.kilometraje}</p>
                    <p><strong>Transmisión:</strong> ${auto.transmision}</p>
                    <p><strong>Combustible:</strong> ${auto.combustible}</p>
                    <p><strong>Disponible para alquiler:</strong> ${auto.disponibleAlquiler === 'sí' ? 'Sí' : 'No'}</p>
                    <p><strong>Precio de Alquiler:</strong> ${auto.pagoalquiler ? `${auto.pagoalquiler} USD/día` : 'N/A'}</p>
                    <p><strong>Equipamiento 1:</strong> ${auto.equipamiento1}</p>
                    <p><strong>Equipamiento 2:</strong> ${auto.equipamiento2}</p>
                    <p><strong>Equipamiento 3:</strong> ${auto.equipamiento3}</p>
                    <p><strong>Equipamiento 4:</strong> ${auto.equipamiento4}</p>
                    <p><strong>Categoría:</strong> ${auto.categoria}</p>
                    <p><strong>Estado:</strong> ${auto.estado}</p>
                    <p><strong>Colores:</strong> ${auto.colores.map(color => color.nombreColor).join(", ")}</p>
                    <div><strong>Imágenes:</strong></div>
                    <div class="row">
                        ${auto.imagenes.map(imagen => `
                            <div class="col-12 col-sm-6 col-md-4">
                                <img src="/img/${imagen.nombreArchivo}" class="img-fluid img-thumbnail" alt="Imagen de color">
                            </div>`).join('')}
                    </div>

                    <div class="d-flex justify-content-center gap-3 mt-3">
                        <button type="button" class="btn btn-warning" onclick="editarAuto(${auto.idAuto})">Editar</button>
                        <button type="button" class="btn btn-danger" onclick="eliminarAuto(${auto.idAuto})">Eliminar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Volver Atrás</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    `;

  
    const contenedor = document.getElementById('tablaResultados');
    contenedor.innerHTML = modalHTML;

  
    const myModal = new bootstrap.Modal(document.getElementById('modalDetallesAuto'));
    myModal.show();
}

async function editarAuto(idAuto) {
    try {
        // Obtener los detalles del auto
        const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`);
        const auto = await response.json();

        if (!response.ok) throw new Error('Auto no encontrado');

        
        const modalHTML = `
    <div id="modalFormularioEditarAuto" class="modal fade" tabindex="-1" aria-labelledby="modalFormularioLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg"> 
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalFormularioLabel">Editar Auto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <form id="formAutoEditar" onsubmit="event.preventDefault(); guardarEdicionAuto(${auto.idAuto});">

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="modelo" class="form-label">Modelo</label>
                                <input type="text" class="form-control" id="modelo" value="${auto.modelo}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="marca" class="form-label">Marca</label>
                                <input type="text" class="form-control" id="marca" value="${auto.marca}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="ano" class="form-label">Año</label>
                                <input type="number" class="form-control" id="ano" value="${auto.ano}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="precio" class="form-label">Precio</label>
                                <input type="number" class="form-control" id="precio" value="${auto.precio}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="kilometraje" class="form-label">Kilometraje</label>
                                <input type="number" class="form-control" id="kilometraje" value="${auto.kilometraje}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="transmision" class="form-label">Transmisión</label>
                                <input type="text" class="form-control" id="transmision" value="${auto.transmision}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="combustible" class="form-label">Combustible</label>
                                <input type="text" class="form-control" id="combustible" value="${auto.combustible}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="categoria" class="form-label">Categoría</label>
                                <input type="text" class="form-control" id="categoria" value="${auto.categoria}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="estado" class="form-label">Estado</label>
                                <input type="text" class="form-control" id="estado" value="${auto.estado}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento1" class="form-label">Equipamiento 1</label>
                                <input type="text" class="form-control" id="equipamiento1" value="${auto.equipamiento1}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento2" class="form-label">Equipamiento 2</label>
                                <input type="text" class="form-control" id="equipamiento2" value="${auto.equipamiento2}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento3" class="form-label">Equipamiento 3</label>
                                <input type="text" class="form-control" id="equipamiento3" value="${auto.equipamiento3}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12 col-md-6 mb-3">
                                <label for="equipamiento4" class="form-label">Equipamiento 4</label>
                                <input type="text" class="form-control" id="equipamiento4" value="${auto.equipamiento4}" required>
                            </div>
                            <div class="col-12 col-md-6 mb-3">
                             <label for="pagoAlquiler" class="form-label">Precio de Alquiler (USD/día)</label>
                                 <input type="number" class="form-control" id="pagoAlquiler" value="${auto.pagoalquiler ?? ''}">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Colores</label>
                            <div id="checkboxColores"></div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Imágenes para cada color (nombre del archivo con extensión)</label>
                            <div id="imagenesPorColor"></div>
                        </div>

                        
                        <div class="mb-3">
                        <label class="form-label">¿Disponible para alquiler?</label>
                     <div class="form-check">
                     <input class="form-check-input" type="radio" name="disponibleAlquiler" id="alquilerSi" value="sí" ${auto.disponibleAlquiler === 'sí' ? 'checked' : ''}>
                         <label class="form-check-label" for="alquilerSi">Sí</label>
                        </div>
                         <div class="form-check">
                         <input class="form-check-input" type="radio" name="disponibleAlquiler" id="alquilerNo" value="no" ${auto.disponibleAlquiler === 'no' ? 'checked' : ''}>
                         <label class="form-check-label" for="alquilerNo">No</label>
                            </div>
                            </div>
        
                        <div class="d-flex justify-content-center gap-3">
                            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
`;

        
        const contenedor = document.getElementById('tablaResultados');
        contenedor.innerHTML = modalHTML;

        // Obtener los colores desde la base de datos
        const coloresResponse = await fetch('/api/colores');
        const colores = await coloresResponse.json();

        
        const contenedorColores = document.getElementById('checkboxColores');
        colores.forEach(color => {
            const checkboxHTML = `
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="${color.idColor}" id="color${color.idColor}" name="colores" data-nombre="${color.nombreColor}" ${auto.colores.some(c => c.idColor === color.idColor) ? 'checked' : ''}>
                    <label class="form-check-label" for="color${color.idColor}">${color.nombreColor}</label>
                </div>
            `;
            contenedorColores.insertAdjacentHTML('beforeend', checkboxHTML);
        });

        // Cargar imágenes de cada color
        const contenedorImagenes = document.getElementById('imagenesPorColor');
        auto.imagenes.forEach(imagen => {
            const div = document.createElement('div');
            div.classList.add('mb-2');
            div.innerHTML = ` 
                <label for="imagenColor${imagen.idColor}">Imagen para ${imagen.nombreColor}:</label>
                <input type="text" class="form-control" id="imagenColor${imagen.idColor}" value="${imagen.nombreArchivo}" required>
            `;
            contenedorImagenes.appendChild(div);
        });

        
        contenedorColores.addEventListener('change', (event) => {
            const checkboxes = document.querySelectorAll('input[name="colores"]:checked');
            const contenedorImagenes = document.getElementById('imagenesPorColor');

            // Eliminar imágenes de los colores desmarcados
            const coloresMarcados = Array.from(checkboxes).map(cb => cb.value);
            const imagenesActuales = Array.from(contenedorImagenes.children).map(child => child.querySelector('input').id);

            
            imagenesActuales.forEach(imagenId => {
                const colorId = imagenId.replace('imagenColor', '');
                if (!coloresMarcados.includes(colorId)) {
                    const imagenDiv = document.getElementById(imagenId).closest('div');
                    imagenDiv.remove();
                }
            });

            // Agregar imágenes para los nuevos colores seleccionados
            Array.from(checkboxes).forEach(checkbox => {
                const idColor = checkbox.value;
                if (!imagenesActuales.includes(`imagenColor${idColor}`)) {
                    const div = document.createElement('div');
                    div.classList.add('mb-2');
                    div.innerHTML = `
                        <label for="imagenColor${idColor}">Imagen para ${checkbox.dataset.nombre}:</label>
                        <input type="text" class="form-control" id="imagenColor${idColor}" required>
                    `;
                    contenedorImagenes.appendChild(div);
                }
            });
        });

       
        const myModal = new bootstrap.Modal(document.getElementById('modalFormularioEditarAuto'));
        myModal.show();
        
         
        document.getElementById('modalFormularioEditarAuto').addEventListener('hidden.bs.modal', () => {
           
            const backdrop = document.querySelector('.modal-backdrop');
            if (backdrop) {
                backdrop.remove();
            }

            
            document.body.style.overflow = '';
        });

    } catch (error) {
        console.error('Error al cargar los detalles del auto:', error);
        alert('Hubo un error al cargar los detalles del auto.');
    }
}

async function guardarEdicionAuto(idAuto) {
    
    const modelo = document.getElementById('modelo').value.trim();
    const marca = document.getElementById('marca').value.trim();
    const ano = parseInt(document.getElementById('ano').value);
    const precio = parseFloat(document.getElementById('precio').value);
    const kilometraje = parseInt(document.getElementById('kilometraje').value);
    const transmision = document.getElementById('transmision').value.trim();
    const combustible = document.getElementById('combustible').value.trim();
    const disponibleAlquiler = document.querySelector('input[name="disponibleAlquiler"]:checked').value;
    const pagoalquiler = parseFloat(document.getElementById('pagoAlquiler').value);
    const equipamiento1 = document.getElementById('equipamiento1').value.trim();
    const equipamiento2 = document.getElementById('equipamiento2').value.trim();
    const equipamiento3 = document.getElementById('equipamiento3').value.trim();
    const equipamiento4 = document.getElementById('equipamiento4').value.trim();
    const categoria = document.getElementById('categoria').value.trim();
    const estado = document.getElementById('estado').value.trim();

    
   

    
    if (!modelo || !marca || !ano || !precio || isNaN(kilometraje) || !transmision || !combustible || !categoria || !estado) {
        Swal.fire('Error', 'Por favor, complete todos los campos obligatorios.', 'error');
        return;
    }
    
     if (pagoalquiler === null || isNaN(pagoalquiler) || pagoalquiler <= 0) {
         Swal.fire('Error', 'Por favor, ingrese un pago de alquiler válido y mayor que cero.', 'error');
         return;
                }

    
    const checkboxesColores = document.querySelectorAll('input[name="colores"]:checked');
    if (checkboxesColores.length === 0) {
        Swal.fire('Error', 'Por favor, seleccione al menos un color.', 'error');
        return;
    }

    
    const coloresSeleccionados = Array.from(checkboxesColores).map(cb => ({
        idColor: parseInt(cb.value),
        nombreColor: cb.dataset.nombre
    }));

    
    let imagenes;
    try {
        imagenes = coloresSeleccionados.map(color => {
            const inputImagen = document.getElementById(`imagenColor${color.idColor}`);
            if (!inputImagen || !inputImagen.value.trim()) {
                throw new Error(`Falta imagen para color ${color.nombreColor}`);
            }
            return {
                idColor: color.idColor,
                nombreColor: color.nombreColor,
                nombreArchivo: inputImagen.value.trim()
            };
        });
    } catch (error) {
        Swal.fire('Error', error.message, 'error');
        return;
    }

    
    const autoDTO = {
        modelo,
        marca,
        ano,
        precio,
        kilometraje,
        transmision,
        combustible,
        disponibleAlquiler,
        pagoalquiler,
        categoria,
        estado,
        equipamiento1,
        equipamiento2,
        equipamiento3,
        equipamiento4,
        colores: coloresSeleccionados,
        imagenes
    };

    
    try {
        const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(autoDTO)
        });

        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: '¡Éxito!',
                text: 'Auto actualizado correctamente',
                confirmButtonText: 'Cerrar'
            });
           
            const modalFormulario = bootstrap.Modal.getInstance(document.getElementById('modalFormularioEditarAuto'));
            modalFormulario.hide();
        } else {
            Swal.fire('Error', 'Hubo un error al actualizar el auto. Inténtelo de nuevo.', 'error');
        }
    } catch (error) {
        console.error('Error al actualizar el auto:', error);
        Swal.fire('Error', 'Hubo un error al actualizar el auto. Inténtelo de nuevo.', 'error');
    }
}

async function eliminarAuto(idAuto) {
   
    const { value: confirmacion } = await Swal.fire({
        title: '¿Estás seguro de que deseas eliminar este auto?',
        text: 'Esta acción no se puede deshacer.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        reverseButtons: true
    });

    if (confirmacion) {
        try {
            
            const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`, {
                method: 'DELETE',  
                headers: {
                    'Content-Type': 'application/json'  
                }
            });

            
            if (response.ok) {
              
                Swal.fire({
                    icon: 'success',
                    title: '¡Auto Eliminado!',
                    text: 'El auto ha sido eliminado correctamente.',
                    confirmButtonText: 'Cerrar'
                }).then(() => {
                   
                    const myModal = bootstrap.Modal.getInstance(document.getElementById('modalFormularioVerAuto'));
                    if (myModal) {
                        myModal.hide(); 
                    }

                   
                    const backdrop = document.querySelector('.modal-backdrop');
                    if (backdrop) {
                        backdrop.remove();
                    }

                    
                    document.body.style.overflow = '';

                   
                    listarAutos();  
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'No se pudo eliminar el auto. Inténtalo de nuevo.',
                    confirmButtonText: 'Cerrar'
                });
            }
        } catch (error) {
            console.error('Error al eliminar el auto:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un error al eliminar el auto. Inténtalo de nuevo.',
                confirmButtonText: 'Cerrar'
            });
        }
    }
}