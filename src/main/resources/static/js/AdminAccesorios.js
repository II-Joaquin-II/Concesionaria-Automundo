function toggleTabla() {
    const tabla = document.getElementById("tablaAccesorios");
    tabla.style.display = (tabla.style.display === "none") ? "block" : "none";
}

function cargarEditar(id) {
    fetch("/editarAccesorio/" + id)
            .then(res => res.json())
            .then(data => {
                document.getElementById("edit-id").value = data.id_acc;
                document.getElementById("edit-nombre").value = data.nombre;
                document.getElementById("edit-desc").value = data.descripcion;
                document.getElementById("edit-precio").value = data.precio;
                document.getElementById("edit-colores").value = data.colores;
                document.getElementById("edit-imagen").value = data.imagen;
                new bootstrap.Modal(document.getElementById("modalEditar")).show();
            });
}