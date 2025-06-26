   // Guardar selección en sesión del backend
    fetch("/carrito/seleccionAuto", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: new URLSearchParams({idAuto, color})
    })
            .then(response => {
                if (!response.ok)
                    throw new Error("Error al guardar selección del auto.");
                return response.text();
            })
            .then(() => {
                // Si todo sale bien, ahora sí enviamos el formulario
                document.getElementById("formPago").submit();
            })
            .catch(error => {
                console.error(error);
                alert("No se pudo completar el registro del auto en sesión.");
            });