  // Obtiene el botón de registro y agrega un evento de clic
        document.getElementById("register").addEventListener("click", function (e) {
            e.preventDefault(); // Prevenir el submit del formulario

            // Obtiene los valores de los campos
            const username = document.getElementById("username").value.trim();
            const lastname = document.getElementById("lastname").value.trim();
            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();
            const confirmPassword = document.getElementById("confirmPassword").value.trim();

            // Validaciones básicas
            if (username === "" || lastname === "" || email === "" || password === "" || confirmPassword === "") {
                swal("¡Error!", "Todos los campos son obligatorios.", "error");
                return;
            }

            // Validación de las contraseñas
            if (password !== confirmPassword) {
                swal("¡Error!", "Las contraseñas no coinciden.", "error");
                return;
            }

            // Validación del formato del correo
            const emailPattern = /\w+@\w+\.\w+/;
            if (!emailPattern.test(email)) {
                swal("¡Error!", "Por favor ingresa un correo electrónico válido.", "error");
                return;
            }
            
            // Mensaje de éxito y redireccion al login
            swal({
                title: "¡Éxito!",
                text: "Te has registrado correctamente. Por favor inicia sesión.",
                icon: "success",
                button: "Aceptar"
            }).then(function () {
                window.location.href = "login.html";
            });
        });
