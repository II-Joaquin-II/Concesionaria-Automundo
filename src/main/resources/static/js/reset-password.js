document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form.formulario");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const newPassword = document.getElementById("newPassword").value.trim();
        const token = form.querySelector("input[name='token']").value;

        if (!newPassword) {
            Swal.fire("Error", "Debes ingresar una nueva contraseña", "error");
            return;
        }

        try {
            const response = await fetch("/reset-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams({
                    newPassword,
                    token
                })
            });

            if (response.ok) {
                Swal.fire({
                    icon: "success",
                    title: "Contraseña actualizada",
                    text: "Ahora puedes iniciar sesión",
                    timer: 3000,
                    showConfirmButton: false,
                    willOpen: () => {
                        document.body.style.overflow = "hidden";
                    },
                    didClose: () => {
                        document.body.style.overflow = "auto";
                        window.location.href = "/login";
                    }
                });
            } else {
                const errorText = await response.text();
                Swal.fire("Error", errorText, "error");
            }
        } catch (error) {
            console.error("Error:", error);
            Swal.fire("Error", "Ocurrió un error al actualizar la contraseña", "error");
        }
    });
});
