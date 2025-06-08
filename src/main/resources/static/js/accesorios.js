function sanitizeId(text) {
    return text.replace(/\s+/g, '-').toLowerCase();
}

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('accesorios-container');
    const cartContainer = document.getElementById('cart-container');

    // Carrito: array de objetos {nombre, id, color, cantidad, precio, imagen}
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Función para renderizar carrito
    function renderCart() {
        if (!cartContainer)
            return;
        cartContainer.innerHTML = '';

        if (cart.length === 0) {
            cartContainer.innerHTML = '<p>El carrito está vacío.</p>';
            return;
        }

        const list = document.createElement('ul');
        list.classList.add('list-group');

        cart.forEach((item, index) => {
            const listItem = document.createElement('li');
            listItem.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');

            listItem.innerHTML = `
     <div>
         <strong>${item.nombre}</strong> <br/>
         Color: 
         <span style="display:inline-block; width: 15px; height: 15px; background-color: ${item.color}; border-radius: 3px; margin-left: 5px;"></span>
         <span class="color-text">${item.color}</span> <br/>
         Precio unitario: $${item.precio} <br/>
         Cantidad: 
         <button class="btn btn-sm btn-outline-secondary decrease-qty" data-index="${index}">-</button>
         <span class="mx-2">${item.cantidad}</span>
         <button class="btn btn-sm btn-outline-secondary increase-qty" data-index="${index}">+</button>
     </div>
     <div>
         <img src="${item.imagen}" alt="${item.nombre}" style="width: 60px; height: auto; object-fit: contain;" />
         <br/>
         <button class="btn btn-sm btn-danger mt-2 remove-item" data-index="${index}">Eliminar</button>
     </div>
 `;

            list.appendChild(listItem);
        });

        // Total
        const total = cart.reduce((acc, item) => acc + item.precio * item.cantidad, 0);
        const totalDiv = document.createElement('div');
        totalDiv.classList.add('mt-3', 'text-end');
        totalDiv.innerHTML = `<strong>Total: $${total.toFixed(2)}</strong>`;

        cartContainer.appendChild(list);
        cartContainer.appendChild(totalDiv);

        // Añadir eventos para botones de cantidad y eliminar
        cartContainer.querySelectorAll('.decrease-qty').forEach(btn => {
            btn.addEventListener('click', () => {
                const idx = parseInt(btn.getAttribute('data-index'));
                if (cart[idx].cantidad > 1) {
                    cart[idx].cantidad--;
                    saveAndRender();
                }
            });
        });

        cartContainer.querySelectorAll('.increase-qty').forEach(btn => {
            btn.addEventListener('click', () => {
                const idx = parseInt(btn.getAttribute('data-index'));
                cart[idx].cantidad++;
                saveAndRender();
            });
        });

        cartContainer.querySelectorAll('.remove-item').forEach(btn => {
            btn.addEventListener('click', () => {
                const idx = parseInt(btn.getAttribute('data-index'));
                cart.splice(idx, 1);
                saveAndRender();
            });
        });
    }

    function saveAndRender() {
        localStorage.setItem('cart', JSON.stringify(cart));
        renderCart();
    }

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
                                <button class="btn-quantity decrease btn btn-outline-secondary btn-sm">-</button>
                                <input type="text" class="quantity-selector form-control form-control-sm mx-2 text-center" id="quantity-${id}" value="1" readonly style="width: 50px;" />
                                <button class="btn-quantity increase btn btn-outline-secondary btn-sm">+</button>
                            </div>
                            <button class="btn btn-custom mt-3 add-to-cart btn btn-primary w-100">Agregar</button>
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
                        if (current > 1)
                            quantityInput.value = current - 1;
                    });

                    increaseBtn.addEventListener('click', () => {
                        let current = parseInt(quantityInput.value);
                        quantityInput.value = current + 1;
                    });

                    // Agregar al carrito
                    const addToCartBtn = card.querySelector('.add-to-cart');
                    addToCartBtn.addEventListener('click', () => {
                        // Obtener color seleccionado
                        const selectedColorDiv = card.querySelector('.color-option.selected-color');
                        if (!selectedColorDiv) {
                            Swal.fire('Error', 'Por favor selecciona un color.', 'error');
                            return;
                        }
                        const colorKey = selectedColorDiv.getAttribute('data-color');
                        // Obtener color CSS para mostrar en carrito
                        const colorCss = selectedColorDiv.style.backgroundColor;

                        // Obtener cantidad
                        const cantidad = parseInt(quantityInput.value);

                        // Buscar si ya existe el producto con mismo color en carrito
                        const existingIndex = cart.findIndex(item => item.nombre === accesorio.nombre && item.color === colorKey);
                        if (existingIndex >= 0) {
                            // Sumar cantidad
                            cart[existingIndex].cantidad += cantidad;
                        } else {
                            // Agregar nuevo item
                            // Obtener imagen del color seleccionado
                            let imagenColor = accesorio.imagen; // fallback
                            if (accesorio.coloresDisponibles && accesorio.coloresDisponibles[colorKey]) {
                                imagenColor = accesorio.coloresDisponibles[colorKey];
                            }

                            cart.push({
                                nombre: accesorio.nombre,
                                id: id,
                                color: colorKey,
                                colorCss: colorCss,
                                cantidad: cantidad,
                                precio: accesorio.precio,
                                imagen: imagenColor
                            });
                        }
                        // Vaciar carrito
                        document.getElementById('vaciar-carrito').addEventListener('click', () => {
                            if (cart.length === 0)
                                return;
                            Swal.fire({
                                title: '¿Vaciar carrito?',
                                text: 'Esta acción eliminará todos los productos del carrito.',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonText: 'Sí, vaciar',
                                cancelButtonText: 'Cancelar'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    cart = [];
                                    saveAndRender();
                                    Swal.fire('Carrito vaciado', '', 'success');
                                }
                            });
                        });

                        document.getElementById('finalizar-compra').addEventListener('click', () => {
                            if (cart.length === 0) {
                                Swal.fire('Carrito vacío', 'Agrega productos antes de finalizar la compra.', 'info');
                                return;
                            }

                            // Mostrar mensaje de compra exitosa
                            Swal.fire({
                                title: '¡Compra exitosa!',
                                text: 'Gracias por tu compra. Recibirás un correo con los detalles.',
                                icon: 'success',
                                confirmButtonText: 'Cerrar'
                            }).then(() => {
                                // Vaciar el carrito y actualizar la vista
                                cart = [];
                                saveAndRender(); // Llama a la función que guarda el carrito vacío y vuelve a renderizarlo
                            });
                        });

                        saveAndRender();

                        Swal.fire({
                            title: '¡Accesorio agregado!',
                            text: `El accesorio ${accesorio.nombre} (${colorKey}) se ha agregado a tu compra.`,
                            icon: 'success',
                            confirmButtonText: 'Cerrar'
                        });
                    });
                });

                renderCart();
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