  // Obtiene el botón de login y agrega un evento de clic
        document.getElementById("login").addEventListener("click", function (e) {
            e.preventDefault(); // Prevenir el submit del formulario

            // Obtener valores del formulario
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();

            // Validaciones
            if (email === "" || password === "") {
                swal("¡Error!", "Por favor, ingresa todos los campos.", "error");
                return;
            }

            // Validación del formato del correo
            const emailPattern = /\w+@\w+\.\w+/;
            if (!emailPattern.test(email)) {
                swal("¡Error!", "Por favor ingresa un correo electrónico válido.", "error");
                return;
            }
            if (user) {
             swal("¡Éxito!", `Inicio de sesión exitoso, ¡Hola ${user.username}!`, "success");
                window.location.href = "VistaPrincipal.html";
            } else {
                swal("¡Error!", "Correo o contraseña incorrectos.", "error");
            }
        });