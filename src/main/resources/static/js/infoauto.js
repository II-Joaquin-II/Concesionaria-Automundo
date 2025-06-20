function getIdAutoFromUrl() {
      const params = new URLSearchParams(window.location.search);
      return params.get('idAuto');
    }

    async function cargarAuto() {
      const idAuto = getIdAutoFromUrl();
      if (!idAuto) {
        alert('No se especificó el auto a mostrar.');
        return;
      }

      try {
        const response = await fetch(`http://localhost:8081/api/autos/${idAuto}`);
        if (!response.ok) throw new Error('Auto no encontrado');
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

        
        document.getElementById('btn-alquilar').onclick = () => {
          window.location.href = '/vista-alquiler.html?idAuto=' + idAuto;
        };
        document.getElementById('btn-comprar').onclick = () => {
  const nombreArchivo = document.getElementById('auto-imagen').dataset.nombreArchivo || 'default-car.jpg';
  const encodedNombre = encodeURIComponent(nombreArchivo);
  window.location.href = `/vista-accesorios.html?idAuto=${idAuto}&img=${encodedNombre}`;
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

  } catch (error) {
    console.error(error);
    alert('Error al cargar la información del auto.');
  }
}

document.addEventListener('DOMContentLoaded', cargarAuto);