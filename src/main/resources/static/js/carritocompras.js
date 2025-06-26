
function agregarAlCarrito(id, nombre, precio, color) {
    const params = new URLSearchParams({id, nombre, precio, color});
    fetch("/carrito/agregar", {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: params
    })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    actualizarVistaCarrito(data);
                }
            });
}
function eliminarDelCarrito(id) {
    const params = new URLSearchParams({id});
    fetch("/carrito/eliminar", {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: params
    })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    actualizarVistaCarrito(data);
                }
            });
}

function actualizarVistaCarrito(data) {
    const contenedor = document.getElementById("carrito-items");
    const totalSpan = document.getElementById("carrito-total");
    if (!contenedor || !totalSpan)
        return;

    const idAuto = parseInt(localStorage.getItem("idAutoElegido") || "-1", 10);

    contenedor.innerHTML = "";

    if (data.items.length === 0) {
        contenedor.innerHTML = `
            <tr>
                <td colspan="4" class="text-center text-muted">Tu carrito está vacío</td>
            </tr>`;
    } else {
        data.items.forEach(item => {
            const esAuto = parseInt(item.id) === idAuto;

            contenedor.innerHTML += `
                <tr>
                    <td>${item.nombre}</td>
                    <td>S/. ${parseFloat(item.precio).toFixed(2)}</td>
                    <td>${item.color}</td>
                    <td>
                        ${esAuto ? "" : `
                            <button class="btn btn-danger btn-sm"
                                    onclick="eliminarDelCarrito(${item.id})">✕</button>`}
                    </td>
                </tr>`;
        });
    }

    totalSpan.innerText = "S/. " + data.total.toFixed(2);
}

document.addEventListener('DOMContentLoaded', () => {
    // Cargar carrito al abrir la página
    /* carritocompras.js – al cargar accesorios */
    fetch('/carrito/obtener', {credentials: 'include'})   // incluye cookies de sesión
            .then(r => r.json())
            .then(d => actualizarVistaCarrito(d))
            .catch(err => console.error('Error obteniendo carrito', err));
    // Acciones de agregar accesorio al carrito
    document.querySelectorAll(".btn-agregar-accesorio").forEach(button => {
        button.addEventListener("click", () => {
            const id = button.dataset.id;
            const nombre = button.dataset.nombre;
            const precio = button.dataset.precio;
            const index = button.dataset.index;

            const colorSelect = document.getElementById(`color-select-${index}`);
            const color = colorSelect ? colorSelect.value : "";

            agregarAlCarrito(id, nombre, precio, color);
            fetch("/carrito/seleccionAuto", {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                body: new URLSearchParams({
                    idAutoReal: auto.idAuto,
                    colorAuto: color
                })
            }).then(() => {
                const nombreArchivo = document.getElementById("auto-imagen").dataset.nombreArchivo || "default-car.jpg";
                const encoded = encodeURIComponent(nombreArchivo);
                window.location.href = `/vista-accesorios.html?idAuto=${auto.idAuto}&img=${encoded}`;
            });
        });
    });

    // Mostrar/ocultar carrito flotante
    const cartButton = document.getElementById("cart-button");
    const floatingCart = document.getElementById("floating-cart");

    if (cartButton && floatingCart) {
        cartButton.addEventListener("click", () => {
            floatingCart.style.display = floatingCart.style.display === "none" ? "block" : "none";
        });
    }
});
