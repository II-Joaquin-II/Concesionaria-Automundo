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

document.getElementById('register').addEventListener('click', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const lastname = document.getElementById('lastname').value.trim();
    const dni = document.getElementById('dni').value.trim();
    const celular = document.getElementById('celular').value.trim();
    const email = document.getElementById('email').value.trim();
    const usuario = document.getElementById('usuario').value.trim();
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const fechaNacInput = document.getElementById('Fecha').value;
    const fechaNac = new Date(fechaNacInput);
    const hoy = new Date();
    const fechaLimite = new Date();
    fechaLimite.setFullYear(hoy.getFullYear() - 18);

    if (!username || !lastname || !dni || !celular || !email || !password || !confirmPassword || !fechaNacInput || !usuario) {
        Swal.fire('Campos vacíos', 'Por favor, completa todos los campos', 'error');
        return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        Swal.fire('Correo inválido', 'Por favor ingresa un correo electrónico válido.', 'error');
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
        nombre_usuario: username,
        apellidos_usuario: lastname,
        dni: dni,
        fecha_nac: fechaNacInput,
        celular: celular,
        email: email,
        usuario: usuario,
        pass: password
    };

    try {
        let dniData = { cliente: null };
        try {
            const res = await fetch(`/api/clientes/dni/${dni}`);
            if (res.ok) dniData = await res.json();
        } catch (err) {
            console.warn("Error al verificar DNI", err);
        }
        if (dniData.cliente) {
            Swal.fire('DNI existente', 'El DNI ya está registrado.', 'error');
            return;
        }

        let celularData = { cliente: null };
        try {
            const res = await fetch(`/api/clientes/celular/${celular}`);
            if (res.ok) celularData = await res.json();
        } catch (err) {
            console.warn("Error al verificar celular", err);
        }
        if (celularData.cliente) {
            Swal.fire('Celular existente', 'El número de celular ya está registrado.', 'error');
            return;
        }

        let emailData = { cliente: null };
        try {
            const res = await fetch(`/api/clientes/email/${email}`);
            if (res.ok) emailData = await res.json();
        } catch (err) {
            console.warn("Error al verificar email", err);
        }
        if (emailData.cliente) {
            Swal.fire('Correo existente', 'El correo electrónico ya está registrado.', 'error');
            return;
        }

        let usuarioData = { cliente: null };
        try {
            const res = await fetch(`/api/clientes/usuario/${usuario}`);
            if (res.ok) usuarioData = await res.json();
        } catch (err) {
            console.warn("Error al verificar nombre de usuario", err);
        }
        if (usuarioData.cliente) {
            Swal.fire('Usuario existente', 'El nombre de usuario ya está registrado.', 'error');
            return;
        }

        const response = await fetch('/api/clientes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cliente)
        });

        if (!response.ok) {
            const errorText = await response.text();
            Swal.fire('Error', errorText, 'error');
            return;
        }

        await response.json();
        Swal.fire('¡Registro exitoso!', 'Tu cuenta ha sido creada', 'success').then(() => {
            window.location.href = '/login';
        });

    } catch (error) {
        console.error('Error en el registro:', error);
        Swal.fire('Error', 'Hubo un problema al procesar tu registro.', 'error');
    }
});
