document.addEventListener("DOMContentLoaded", function () {
    function validateName(inputElement) {
        inputElement.addEventListener("input", function () {
            this.value = this.value.replace(/[^A-Za-záéíóúÁÉÍÓÚñÑ\s]/g, '');
        });
    }

    function validateDni(inputElement) {
        inputElement.addEventListener("input", function () {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }

    function validateCelular(inputElement) {
        inputElement.addEventListener("input", function () {
            this.value = this.value.replace(/[^0-9]/g, '');

            if (this.value.length > 9) {
                this.value = this.value.slice(0, 9);
            }
        });
    }


    const usernameInput = document.getElementById("username");
    const lastnameInput = document.getElementById("lastname");
    const dniInput = document.getElementById("dni");
    const celularInput = document.getElementById("celular");

    validateName(usernameInput);
    validateName(lastnameInput);
    validateDni(dniInput);
    validateCelular(celularInput);
});


document.getElementById('register').addEventListener('click', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const lastname = document.getElementById('lastname').value;
    const dni = document.getElementById('dni').value;
    const celular = document.getElementById('celular').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const fechaNacInput = document.getElementById('Fecha').value;
    const fechaNac = new Date(fechaNacInput);
    const hoy = new Date();
    const fechaLimite = new Date();
    fechaLimite.setFullYear(hoy.getFullYear() - 18);

    if (!username || !lastname || !dni || !celular || !email || !password || !confirmPassword || !fechaNacInput) {
        Swal.fire('Campos vacíos', 'Por favor, completa todos los campos', 'error');
        return;
    }

    if (isNaN(fechaNac.getTime())) {
        Swal.fire("Fecha inválida", "Por favor ingresa una fecha válida.", "error");
        return;
    }

    if (fechaNac > fechaLimite) {
        Swal.fire("Debes ser mayor de 18 años", "Tu fecha de nacimiento indica que eres menor de edad.", "warning");
        return;
    }

    if (password !== confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
        return;
    }

    if (celular.length !== 9) {
        Swal.fire('Error', 'El número de celular debe tener 9 dígitos', 'error');
        return;
    }

    const cliente = {
        nombre_cli: username,
        apellidos_cli: lastname,
        dni: dni,
        fecha_nac: fechaNacInput,
        celular: celular,
        email: email,
        usuario_cli: document.getElementById('usuario').value,
        pass_cli: password
    };

    fetch('/api/clientes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(cliente)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    const errorMessage = errorData.error || 'No se pudo completar el registro';
                    Swal.fire('Error', errorMessage, 'error');
                    throw new Error(errorMessage);
                });
            }
            return response.json();
        })
        .then(data => {
            Swal.fire('¡Registro exitoso!', 'Tu cuenta ha sido creada', 'success').then(() => {
                window.location.href = '/login'; 
            });
        })
        .catch(error => {
            console.error(error);
        });
});
