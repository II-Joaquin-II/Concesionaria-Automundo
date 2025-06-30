function getIdAutoFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('idAuto');
}

function agregarAlCarrito(id, nombre, precio, color) {
    const params = new URLSearchParams({id, nombre, precio, color});
    return fetch("/carrito/agregar", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: params
    }).then(r => r.json());          // ← devuelve la promesa
}
async function cargarAuto() {
    const idAuto = getIdAutoFromUrl();
    if (!idAuto) {
        alert('No se especificó el auto a mostrar.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`);
        if (!response.ok)
            throw new Error('Auto no encontrado');
        const auto = await response.json();


        let imgUrl = 'http://localhost:8081/img/default-car.jpg';
        if (auto.imagenes && auto.imagenes.length > 0) {
            imgUrl = `/img/${auto.imagenes[0].nombreArchivo}`;
        }
        document.getElementById('auto-imagen').src = imgUrl;
        document.getElementById('auto-imagen').dataset.nombreArchivo = auto.imagenes[0]?.nombreArchivo || 'default-car.jpg';

        const miniaturasContainer = document.getElementById('miniaturas-container');
        miniaturasContainer.innerHTML = '';

        if (auto.imagenes && auto.imagenes.length > 1) {
            auto.imagenes.forEach(imagen => {
                const thumb = document.createElement('img');
                thumb.src = `/img/${imagen.nombreArchivo}`;
                thumb.alt = imagen.nombreColor || 'Miniatura';
                thumb.dataset.nombreArchivo = imagen.nombreArchivo; //
                thumb.style.width = '50px';
                thumb.style.height = '50px';
                thumb.classList.add('rounded-circle', 'border', 'shadow-sm');
                thumb.style.cursor = 'pointer';
                thumb.onclick = () => {
                    document.getElementById('auto-imagen').src = `/img/${imagen.nombreArchivo}`;
                    document.getElementById('auto-imagen').dataset.nombreArchivo = imagen.nombreArchivo;
                };
                miniaturasContainer.appendChild(thumb);
            });
        }

        document.getElementById('auto-imagen').alt = auto.marca + ' ' + auto.modelo;
        document.getElementById('auto-nombre').textContent = `${auto.marca} ${auto.modelo}`;
        document.getElementById('auto-precio').textContent = `$${auto.precio.toLocaleString()}`;




document.getElementById("btn-alquilar").onclick = () => {

  const color = document.getElementById("color-select").value || "";

  /* 1. Guarda info en localStorage (opcional, por si la necesitas luego) */
  localStorage.setItem("idAutoReal", auto.idAuto);
  localStorage.setItem("colorAuto",  color);

  /* 2. Añade el placeholder al carrito (id = 1000) */
  agregarAlCarrito(
      1000,
      `${auto.marca} ${auto.modelo}`,
      auto.pagoalquiler,
      color
  )
  /* 3. Envia el id real + color a la sesión del backend */
  .then(() => {
      return fetch("/carrito/seleccionAuto", {
          method: "POST",
          headers: {"Content-Type": "application/x-www-form-urlencoded"},
          body: new URLSearchParams({
              idAuto: auto.idAuto,  // id real
              color:  color,
              pagoAlquiler: auto.pagoAlquiler
          })
      });
  })
  /* 4. Redirige directo a Pagos */
  .then(() => {
      window.location.href = "/pagos";
  })
  .catch(err => {
      console.error(err);
      alert("No se pudo procesar la solicitud de alquiler.");
  });
};
        /* infoauto.js (fragmento) */
        document.getElementById("btn-comprar").onclick = () => {
            const color = document.getElementById("color-select").value || "";

            /* 1. Guardamos en localStorage el ID REAL del auto  */
            localStorage.setItem("idAutoReal", auto.idAuto);   // <— id real
            localStorage.setItem("colorAuto", color);

            /* 2. Añadimos el auto al carrito con ID 1000 para la UI */
            agregarAlCarrito(
                    1000, // ← placeholder
                    `${auto.marca} ${auto.modelo}`,
                    auto.precio,
                    color
                    )
                    .then(() => {
                        /* 3. Antes de redirigir, mandamos el ID real al backend
                         para guardarlo en sesión                                     */
                        return fetch("/carrito/seleccionAuto", {
                            method: "POST",
                            headers: {"Content-Type": "application/x-www-form-urlencoded"},
                            body: new URLSearchParams({
                                idAuto: auto.idAuto, // real
                                color: color
                            })
                        });
                    })
                    .then(() => {
                        /* 4. Redirigimos a accesorios */
                        window.location.href =
                                `/vista-accesorios.html?idAuto=${auto.idAuto}`;
                    })
                    .catch(err => {
                        console.error(err);
                        alert("No se pudo registrar la selección del auto.");
                    });
        };

        const coloresDisponibles = auto.colores && auto.colores.length > 0 ? auto.colores.map(c => c.nombreColor).join(', ') : 'N/A';
        const equipamientoList = [auto.equipamiento1, auto.equipamiento2, auto.equipamiento3, auto.equipamiento4].filter(e => e && e.trim() !== '');
        const equipamientoHtml = `
  <div class="detail-item" style="grid-column: 1 / -1;">
    <ul class="equipamiento-list">
      ${
                equipamientoList.length > 0
                ? equipamientoList.map(e => `<li>${e}</li>`).join('')
                : '<li>No disponible</li>'
                }
    </ul>
  </div>
`;

        const detallesHtml = `
  <div class="details-container">
    <h5 class="mb-3 border-bottom pb-2" style="grid-column: 1 / -1;">
      <i class="bi bi-info-circle-fill text-primary me-2"></i> General
    </h5>
    <div class="detail-item"><span class="detail-label">Año:</span> ${auto.ano}</div>
    <div class="detail-item"><span class="detail-label">Kilometraje:</span> ${auto.kilometraje.toLocaleString()} km</div>
    <div class="detail-item"><span class="detail-label">Transmisión:</span> ${auto.transmision}</div>
    <div class="detail-item"><span class="detail-label">Categoría:</span> ${auto.categoria}</div>
    <div class="detail-item"><span class="detail-label">Disponible alquiler:</span> ${auto.disponibleAlquiler || 'No'}</div>
    <div class="detail-item"><span class="detail-label">Pago alquiler:</span> ${auto.pagoalquiler} USD/día</div>
    <div class="detail-item"><span class="detail-label">Combustible:</span> ${auto.combustible}</div>
    <div class="detail-item"><span class="detail-label">Estado:</span> ${auto.estado}</div>
<div class="detail-item colores-item">
  <span class="detail-label">Colores:</span>
  <div class="colores-list">${coloresDisponibles}</div>
</div>
  </div>

  <div class="details-container">
    <h5 class="mb-3 border-bottom pb-2" style="grid-column: 1 / -1;">
      <i class="bi bi-tools text-success me-2"></i> Equipamiento
    </h5>
    ${equipamientoHtml}
  </div>
`;
        document.getElementById('auto-detalles').innerHTML = detallesHtml;

        const colorSelect = document.getElementById('color-select');
        colorSelect.innerHTML = ''; // Limpiar opciones previas

        if (auto.colores && auto.colores.length > 0) {
            auto.colores.forEach((color, index) => {
                const option = document.createElement('option');
                option.value = color.nombreColor;
                option.textContent = color.nombreColor;

                // Selecciona automáticamente el primer color
                if (index === 0) {
                    option.selected = true;
                    localStorage.setItem('colorSeleccionado', color.nombreColor);
                    localStorage.setItem('idAutoSeleccionado', idAuto);
                }

                colorSelect.appendChild(option);
            });
        } else {
            const option = document.createElement('option');
            option.disabled = true;
            option.textContent = 'No hay colores disponibles';
            colorSelect.appendChild(option);
        }

// Guardar color seleccionado en localStorage cuando el usuario lo cambie
        colorSelect.addEventListener('change', (e) => {
            const colorSeleccionado = e.target.value;
            localStorage.setItem('colorSeleccionado', colorSeleccionado);
            localStorage.setItem('idAutoSeleccionado', idAuto);
        });
    } catch (error) {
        console.error(error);
        alert('Error al cargar la información del auto.');
    }
}

document.addEventListener('DOMContentLoaded', cargarAuto);
