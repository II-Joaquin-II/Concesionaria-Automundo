function agregarAlCarrito(id, nombre, precio, color) {
    const params = new URLSearchParams({ id, nombre, precio, color });
    fetch("/carrito/agregar", {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
    const params = new URLSearchParams({ id });
    fetch("/carrito/eliminar", {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

    contenedor.innerHTML = "";

    if (data.items.length === 0) {
        contenedor.innerHTML = `
            <tr>
                <td colspan="4" class="text-center text-muted">Tu carrito está vacío</td>
            </tr>`;
    } else {
        data.items.forEach(item => {
            contenedor.innerHTML += `
                <tr>
                    <td>${item.nombre}</td>
                    <td>S/. ${item.precio.toFixed(2)}</td>
                    <td>${item.color}</td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="eliminarDelCarrito(${item.id})">✕</button>
                    </td>
                </tr>`;
        });
    }

    totalSpan.innerText = "S/. " + data.total.toFixed(2);
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".btn-agregar-accesorio").forEach(button => {
        button.addEventListener("click", () => {
            const id = button.dataset.id;
            const nombre = button.dataset.nombre;
            const precio = button.dataset.precio;
            const index = button.dataset.index;

            const colorSelect = document.getElementById(`color-select-${index}`);
            const color = colorSelect ? colorSelect.value : "";

            agregarAlCarrito(id, nombre, precio, color);
        });
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const cartButton = document.getElementById("cart-button");
    const floatingCart = document.getElementById("floating-cart");

    cartButton.addEventListener("click", () => {
        floatingCart.style.display = floatingCart.style.display === "none" ? "block" : "none";
    });
});