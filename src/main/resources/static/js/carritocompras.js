const cart = [];

function toggleCart() {
    const cartEl = document.getElementById('floating-cart');
    cartEl.style.display = (cartEl.style.display === 'none' || cartEl.style.display === '') ? 'block' : 'none';
}

function updateCartDisplay() {
    const tbody = document.getElementById('cart-items');
    const totalEl = document.getElementById('cart-total');

    tbody.innerHTML = '';

    if (cart.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" class="text-center text-muted">Tu carrito está vacío</td></tr>';
        totalEl.textContent = 'S/. 0.00';
        return;
    }

    let total = 0;
    cart.forEach((item, index) => {
        total += item.precio;
        const row = document.createElement('tr');
        row.innerHTML = `
      <td>${item.nombre}</td>
      <td>S/. ${item.precio.toFixed(2)}</td>
      <td>${item.color || '-'}</td>
      <td>${item.tipo === 'auto' ? '' : `<button class="btn btn-sm btn-danger" onclick="removeFromCart(${index})">✕</button>`}</td>
    `;
        tbody.appendChild(row);
    });

    totalEl.textContent = 'S/. ' + total.toFixed(2);
    localStorage.setItem('carrito', JSON.stringify(cart));
}

function removeFromCart(index) {
    cart.splice(index, 1);
    updateCartDisplay();
}

function addToCart(nombre, precio, tipo = 'accesorio', color = '') {
    cart.push({nombre, precio, tipo, color});
    updateCartDisplay();
}

async function cargarVistaAccesorios() {
    const params = new URLSearchParams(window.location.search);
    const idAuto = params.get("idAuto");

    if (!idAuto) {
        alert("No se proporcionó el ID del auto.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`);
        if (!response.ok)
            throw new Error("No se pudo cargar la información del auto.");

        const auto = await response.json();

        const nombreAuto = `${auto.marca} ${auto.modelo}`;
        const precioAuto = parseFloat(auto.precio);
        const color = localStorage.getItem('colorSeleccionado') || 'No especificado';
        // Agregar el auto al carrito con tipo "auto"
        addToCart(nombreAuto, precioAuto, 'auto', color);

    } catch (error) {
        console.error(error);
        alert("Error al cargar la información del auto.");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    cargarVistaAccesorios();
    document.getElementById('cart-button')?.addEventListener('click', toggleCart);

    // Asocia botones de accesorios (si existieran) al carrito
    document.querySelectorAll('.btn-agregar-accesorio').forEach(button => {
        button.addEventListener('click', function (e) {
            e.preventDefault();
            const card = button.closest('.card');
            const nombre = card.querySelector('.card-title').innerText;
            const precioText = card.querySelector('.card-text.price span').innerText;
            // Obtener color seleccionado
            const colorSelect = card.querySelector('.color-select');
            const color = colorSelect ? colorSelect.value : '';
            const precio = parseFloat(precioText.replace(',', '.'));
            addToCart(nombre, precio, 'accesorio', color);
        });
    });
});
