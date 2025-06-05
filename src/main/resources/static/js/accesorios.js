function sanitizeId(text) {
    return text.replace(/\s+/g, '-').toLowerCase();
}

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('accesorios-container');

    fetch('/api/accesorios')
        .then(response => response.json())
        .then(accesorios => {
            accesorios.forEach(accesorio => {
                const id = sanitizeId(accesorio.nombre);
                const card = document.createElement('div');
                card.classList.add('col', 'mb-4', 'col-md-6', 'col-lg-4');
                card.innerHTML = `
                    <div class="card h-100" data-aos="fade-up">
                        <div class="image-container">
                            <img src="${accesorio.imagen}" alt="${accesorio.nombre}" class="card-img-top" id="image-${id}">
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${accesorio.nombre}</h5>
                            <p class="card-text">Precio: $${accesorio.precio}</p>
                            <p class="card-text">${accesorio.descripcion}</p>
                            <div class="color-selector">
                                <span>Colores:</span>
                                <div class="color-option" style="background-color: red;" data-color="rojo"></div>
                                <div class="color-option" style="background-color: black;" data-color="negro"></div>
                                <div class="color-option" style="background-color: blue;" data-color="azul"></div>
                            </div>
                            <div class="d-flex align-items-center mt-3">
                                <button class="btn-quantity decrease">-</button>
                                <input type="text" class="quantity-selector" id="quantity-${id}" value="1" readonly />
                                <button class="btn-quantity increase">+</button>
                            </div>
                            <button class="btn btn-custom mt-3 add-to-cart">Agregar</button>
                        </div>
                    </div>
                `;
                container.appendChild(card);

                const colorOptions = card.querySelectorAll('.color-option');

                // Evento para cambiar color e imagen
                colorOptions.forEach(colorDiv => {
                    colorDiv.addEventListener('click', () => {
                        const color = colorDiv.getAttribute('data-color');
                        changeColor(color, id, accesorios);
                        colorOptions.forEach(c => c.classList.remove('selected-color'));
                        colorDiv.classList.add('selected-color');
                    });
                });

                // Selección de color por defecto (rojo)
                const defaultColorDiv = card.querySelector('.color-option[data-color="rojo"]');
                if (defaultColorDiv) {
                    defaultColorDiv.classList.add('selected-color');
                    changeColor('rojo', id, accesorios);
                }

                // Control de cantidad
                const decreaseBtn = card.querySelector('.btn-quantity.decrease');
                const increaseBtn = card.querySelector('.btn-quantity.increase');
                const quantityInput = card.querySelector('.quantity-selector');

                decreaseBtn.addEventListener('click', () => {
                    let current = parseInt(quantityInput.value);
                    if (current > 1) quantityInput.value = current - 1;
                });

                increaseBtn.addEventListener('click', () => {
                    let current = parseInt(quantityInput.value);
                    quantityInput.value = current + 1;
                });

                // Agregar al carrito
                const addToCartBtn = card.querySelector('.add-to-cart');
                addToCartBtn.addEventListener('click', () => {
                    addToCart(accesorio.nombre);
                });
            });

            AOS.init();
        })
        .catch(error => console.error('Error cargando accesorios:', error));
});

function changeColor(color, accesorioId, accesorios) {
    const image = document.getElementById(`image-${accesorioId}`);
    if (!image) {
        console.error('No se encontró la imagen con id:', `image-${accesorioId}`);
        return;
    }
    const accesorioObj = accesorios.find(a => sanitizeId(a.nombre) === accesorioId);
    if (accesorioObj && accesorioObj.coloresDisponibles && accesorioObj.coloresDisponibles[color]) {
        image.src = accesorioObj.coloresDisponibles[color];
    } else {
        console.warn('No se encontró imagen para color:', color);
    }
}

function addToCart(accesorio) {
    Swal.fire({
        title: '¡Accesorio agregado!',
        text: `El accesorio ${accesorio} se ha agregado a tu compra.`,
        icon: 'success',
        confirmButtonText: 'Cerrar'
    });
}
