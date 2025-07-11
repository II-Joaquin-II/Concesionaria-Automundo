function limpiarCampos() {
    document.getElementById('dni').value = '';
    document.getElementById('celular').value = '';
}

function listarClientes() {
    fetch('/api/clientes')
        .then(r => r.json())
        .then(data => {
            let tabla = `
                <div class="table-responsive">
                <table class="table table-sm table-striped table-hover align-middle text-center">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>DNI</th>
                    <th>Fecha Nacimiento</th>
                    <th>Celular</th>
                    <th>Email</th>
                    <th>Usuario</th>
                </tr>
                </thead>
                <tbody>`;
            data.forEach(l => {
                tabla += `<tr><td>${l.id_usuario}</td><td>${l.nombre_usuario}</td><td>${l.apellidos_usuario}</td><td>${l.dni}</td><td>${l.fecha_nac}</td><td>${l.celular}</td>
                          <td>${l.email}</td><td>${l.usuario}</td></tr>`;
            });
            tabla += `</tbody></table></div>`;
            document.getElementById('tablaResultados').innerHTML = tabla;
            limpiarCampos();
        });
}

function abrirEliminarCliente() {
    Swal.fire({
        title: 'Eliminar cliente',
        input: 'text',
        inputLabel: 'Ingrese DNI del cliente',
        inputAttributes: {
            maxlength: 8,
            autocapitalize: 'off',
            autocorrect: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Eliminar',
        preConfirm: (dni) => {
            if (!/^[0-9]{8}$/.test(dni)) {
                Swal.showValidationMessage('DNI inválido');
                return false;
            }
            return fetch(`/api/clientes/dni/${dni}`, { method: 'DELETE' })
                .then(r => {
                    if (!r.ok) throw new Error();
                    Swal.fire('Éxito', 'Cliente eliminado', 'success');
                    listarClientes();
                })
                .catch(() => Swal.fire('Error', 'No se pudo eliminar', 'error'));
        }
    });
}

function abrirInsertarCliente() {
    Swal.fire({
        title: 'Insertar cliente',
        html: `
            <input id="swal_nombre" class="swal2-input" placeholder="Nombre">
            <input id="swal_apellido" class="swal2-input" placeholder="Apellido">
            <input id="swal_dni" class="swal2-input" placeholder="DNI" maxlength="8">
            <input id="swal_fecha_nac" type="date" class="swal2-input">
            <input id="swal_celular" class="swal2-input" placeholder="Celular" maxlength="9">
            <input id="swal_email" type="email" class="swal2-input" placeholder="Email">
            <input id="swal_usuario" class="swal2-input" placeholder="Usuario">
            <input id="swal_password" type="password" class="swal2-input" placeholder="Contrase\u00f1a">
        `,
        confirmButtonText: 'Insertar',
        showCancelButton: true,
        focusConfirm: false,
        preConfirm: () => {
            const nombre = document.getElementById('swal_nombre').value.trim();
            const apellido = document.getElementById('swal_apellido').value.trim();
            const dni = document.getElementById('swal_dni').value.trim();
            const fecha_nac = document.getElementById('swal_fecha_nac').value;
            const celular = document.getElementById('swal_celular').value.trim();
            const email = document.getElementById('swal_email').value.trim();
            const usuario = document.getElementById('swal_usuario').value.trim();
            const password = document.getElementById('swal_password').value.trim();

            if (!nombre || !apellido || !dni || !fecha_nac || !celular || !email || !usuario || !password) {
                Swal.showValidationMessage('Completa todos los campos');
                return false;
            }

            const fecha = new Date(fecha_nac);
            const hoy = new Date();
            hoy.setFullYear(hoy.getFullYear() - 18);
            if (fecha > hoy) {
                Swal.showValidationMessage('Debes ser mayor de 18 a\u00f1os.');
                return false;
            }

            if (!/^\d{8}$/.test(dni) || !/^\d{9}$/.test(celular)) {
                Swal.showValidationMessage('DNI o celular inv\u00e1lido');
                return false;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                Swal.showValidationMessage('Correo no v\u00e1lido');
                return false;
            }

            return {
                nombre_usuario: nombre,
                apellidos_usuario: apellido,
                dni,
                fecha_nac,
                celular,
                email,
                usuario,
                pass: password
            };
        },
        didOpen: () => {
            const letras = el => el.addEventListener('input', () => { el.value = el.value.replace(/[^A-Za-z\u00e1\u00e9\u00ed\u00f3\u00fa\u00d1\u00f1\s]/g, ''); });
            const numeros = el => el.addEventListener('input', () => { el.value = el.value.replace(/[^0-9]/g, ''); });
            letras(document.getElementById('swal_nombre'));
            letras(document.getElementById('swal_apellido'));
            numeros(document.getElementById('swal_dni'));
            numeros(document.getElementById('swal_celular'));
        }
    }).then(result => {
        if (result.isConfirmed) {
            fetch('/api/clientes', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(result.value)
            })
            .then(r => {
                if (!r.ok) return r.text().then(t => { throw new Error(t); });
                return r.json();
            })
            .then(() => {
                Swal.fire('\u00c9xito', 'Cliente insertado correctamente', 'success');
                listarClientes();
            })
            .catch(e => Swal.fire('Error', e.message, 'error'));
        }
    });
}


function buscarClienteDni() {
    const dni = document.getElementById('dni').value.trim();
    if (dni) {
        fetch(`/api/clientes/dni/${dni}`)
            .then(r => {
                if (!r.ok) throw new Error();
                return r.json();
            })
            .then(data => {
                const cliente = data.cliente;
                if (!cliente) throw new Error();
                let tabla = `
                    <div class="table-responsive">
                    <table class="table table-sm table-striped table-hover align-middle text-center">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>DNI</th>
                        <th>Fecha Nacimiento</th>
                        <th>Celular</th>
                        <th>Email</th>
                        <th>Usuario</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${cliente.id_usuario}</td>
                            <td>${cliente.nombre_usuario}</td>
                            <td>${cliente.apellidos_usuario}</td>
                            <td>${cliente.dni}</td>
                            <td>${cliente.fecha_nac}</td>
                            <td>${cliente.celular}</td>
                            <td>${cliente.email}</td>
                            <td>${cliente.usuario}</td>
                        </tr>
                    </tbody>
                    </table>
                    </div>`;
                document.getElementById('tablaResultados').innerHTML = tabla;
            })
            .catch(() => Swal.fire('Error', 'Cliente no encontrado por DNI', 'error'));
    } else {
        Swal.fire('Advertencia', 'Ingrese un DNI válido', 'warning');
    }
}

function buscarClienteCelular() {
    const celular = document.getElementById('celular').value.trim();
    if (celular) {
        fetch(`/api/clientes/celular/${celular}`)
            .then(r => {
                if (!r.ok) throw new Error();
                return r.json();
            })
            .then(data => {
                const cliente = data.cliente;
                if (!cliente) throw new Error();

                let tabla = `
                    <div class="table-responsive">
                    <table class="table table-sm table-striped table-hover align-middle text-center">
                    <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>DNI</th>
                        <th>Fecha Nacimiento</th>
                        <th>Celular</th>
                        <th>Email</th>
                        <th>Usuario</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>${cliente.id_usuario}</td>
                            <td>${cliente.nombre_usuario}</td>
                            <td>${cliente.apellidos_usuario}</td>
                            <td>${cliente.dni}</td>
                            <td>${cliente.fecha_nac}</td>
                            <td>${cliente.celular}</td>
                            <td>${cliente.email}</td>
                            <td>${cliente.usuario}</td>
                        </tr>
                    </tbody>
                    </table>
                    </div>`;
                document.getElementById('tablaResultados').innerHTML = tabla;
            })
            .catch(() => Swal.fire('Error', 'Cliente no encontrado por celular', 'error'));
    } else {
        Swal.fire('Advertencia', 'Ingrese un número de celular válido', 'warning');
    }
}

function abrirActualizarCliente(cliente) {
    Swal.fire({
        title: 'Actualizar cliente',
        html: `
            <input id="swal_id_usuario" type="number" class="swal2-input" placeholder="ID Cliente" value="${cliente.id_usuario || ''}" readonly>
            <input id="swal_nombre" class="swal2-input" placeholder="Nombre" value="${cliente.nombre_usuario || ''}">
            <input id="swal_apellido" class="swal2-input" placeholder="Apellido" value="${cliente.apellidos_usuario || ''}">
            <input id="swal_dni" class="swal2-input" placeholder="DNI" maxlength="8" value="${cliente.dni || ''}">
            <input id="swal_fecha_nac" type="date" class="swal2-input" value="${cliente.fecha_nac || ''}">
            <input id="swal_celular" class="swal2-input" placeholder="Celular" maxlength="9" value="${cliente.celular || ''}">
            <input id="swal_email" type="email" class="swal2-input" placeholder="Email" value="${cliente.email || ''}">
            <input id="swal_usuario" class="swal2-input" placeholder="Usuario" value="${cliente.usuario || ''}">
            <input id="swal_password" type="password" class="swal2-input" placeholder="Contraseña" value="">
        `,
        confirmButtonText: 'Actualizar',
        showCancelButton: true,
        focusConfirm: false,
        preConfirm: () => {
            const id_usuario = document.getElementById('swal_id_usuario').value.trim();
            const nombre = document.getElementById('swal_nombre').value.trim();
            const apellido = document.getElementById('swal_apellido').value.trim();
            const dni = document.getElementById('swal_dni').value.trim();
            const fecha_nac = document.getElementById('swal_fecha_nac').value;
            const celular = document.getElementById('swal_celular').value.trim();
            const email = document.getElementById('swal_email').value.trim();
            const usuario = document.getElementById('swal_usuario').value.trim();
            const password = document.getElementById('swal_password').value.trim();

            if (!nombre || !apellido || !dni || !fecha_nac || !celular || !email || !usuario) {
                Swal.showValidationMessage('Completa todos los campos obligatorios');
                return false;
            }

            const fechaNacDate = new Date(fecha_nac);
            const hoy = new Date();
            const fechaLimite = new Date();
            fechaLimite.setFullYear(hoy.getFullYear() - 18);

            if (fechaNacDate > fechaLimite) {
                Swal.showValidationMessage('Debes ser mayor de 18 años.');
                return false;
            }

            if (dni.length !== 8 || celular.length !== 9) {
                Swal.showValidationMessage('Número de celular inválido');
                return false;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                Swal.showValidationMessage('Correo no válido');
                return false;
            }

            const dataToSend = {
                nombre_usuario: nombre,
                apellidos_usuario: apellido,
                dni,
                fecha_nac,
                celular,
                email,
                usuario
            };

            if (password) {
                dataToSend.pass = password;
            }

            return {
                id_usuario,
                data: dataToSend
            };
        },
        didOpen: () => {
            const validarSoloLetras = input => {
                input.addEventListener('input', () => {
                    input.value = input.value.replace(/[^A-Za-záéíóúÁÉÍÓÚñÑ\s]/g, '');
                });
            };
            const validarSoloNumeros = input => {
                input.addEventListener('input', () => {
                    input.value = input.value.replace(/[^0-9]/g, '');
                });
            };

            validarSoloLetras(document.getElementById('swal_nombre'));
            validarSoloLetras(document.getElementById('swal_apellido'));
            validarSoloNumeros(document.getElementById('swal_dni'));
            validarSoloNumeros(document.getElementById('swal_celular'));
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const { id_usuario, data } = result.value;

            fetch(`/api/clientes/${id_usuario}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(errorData => {
                            const msg = errorData.error || 'No se pudo actualizar';
                            return Swal.fire('Error', msg, 'error');
                        });
                    }
                    return response.json();
                })
                .then(() => {
                    Swal.fire('Éxito', 'Cliente actualizado correctamente', 'success');
                    listarClientes();
                })
                .catch(error => {
                    console.error(error);
                    Swal.fire('Error', 'Error inesperado', 'error');
                });
        }
    });
}

document.getElementById('abrirActualizarCliente').addEventListener('click', function () {
    Swal.fire({
        title: 'Ingresa el DNI del cliente a actualizar',
        input: 'text',
        inputAttributes: {
            maxlength: 8,
            autocapitalize: 'off',
            autocorrect: 'off',
            inputmode: 'numeric'
        },
        showCancelButton: true,
        confirmButtonText: 'Buscar',
        cancelButtonText: 'Cancelar',
        inputValidator: (value) => {
            if (!value || value.trim().length !== 8 || !/^\d{8}$/.test(value.trim())) {
                return 'Por favor ingresa un DNI válido de 8 dígitos';
            }
            return null;
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const dni = result.value.trim();
            fetch(`/api/clientes/dni/${dni}`)
                .then(response => {
                    if (!response.ok) throw new Error('Cliente no encontrado');
                    return response.json();
                })
                .then(data => {
                    const cliente = data.cliente;
                    if (!cliente) throw new Error('Cliente no encontrado');
                    abrirActualizarCliente(cliente);
                })
                .catch(error => {
                    Swal.fire('Error', error.message, 'error');
                });
        }
    });
});
