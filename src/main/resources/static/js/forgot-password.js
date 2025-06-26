document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("forgot-password-form");

    form.addEventListener("submit", async function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value.trim();

        if (!email) {
            Swal.fire("Error", "Debes ingresar un correo electrónico", "error");
            return;
        }

        try {
            const response = await fetch("/forgot-password", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: new URLSearchParams({ email })
            });

            if (response.ok) {
                Swal.fire({
                    icon: "success",
                    title: "Correo enviado",
                    text: "Revisa tu correo para restablecer la contraseña",
                    timer: 3000,
                    showConfirmButton: false,
                    willOpen: () => {
                        document.documentElement.scrollTop = 0;
                        document.body.scrollTop = 0;
                        document.documentElement.classList.add("swal2-shown");
                        document.body.classList.add("swal2-shown");
                    },
                    didClose: () => {
                        document.documentElement.classList.remove("swal2-shown");
                        document.body.classList.remove("swal2-shown");
                        window.location.href = "/login";
                    }
                });
            } else {
                const errorText = await response.text();
                Swal.fire("Error", errorText, "error");
            }

        } catch (error) {
            console.error("Error al enviar solicitud:", error);
            Swal.fire("Error", "No se pudo enviar el correo", "error");
        }
    });
});



