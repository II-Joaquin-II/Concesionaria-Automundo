  document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('autos-container');

  
  async function obtenerAutos() {
    try {
      const response = await fetch('http://localhost:8081/api/autos'); 
      const autos = await response.json();
      container.innerHTML = '';  

      autos.forEach(auto => {
       
        const imagenes = auto.imagenes.map(imagen => `/img/${imagen.nombreArchivo}`);
        const imageUrl = imagenes.length > 0 ? imagenes[0] : 'http://localhost:8081/img/default-car.jpg'; 

        
        const colores = auto.colores && auto.colores.length > 0 
          ? auto.colores.map(color => color.nombreColor).join(", ")
          : 'No disponible';

        // Crear la tarjeta
        const card = document.createElement('div');
        card.classList.add('col');
        card.innerHTML = `
          <div class="card h-100">
            <div class="card-img-container">
              <img id="main-image-${auto.idAuto}" src="${imageUrl}" class="card-img-top car-image" alt="${auto.modelo}">
            </div>
            <div class="card-body">
              <h5 class="card-title text-danger">${auto.marca} ${auto.modelo}</h5>
              <p class="card-text"><strong>Precio:</strong> $${auto.precio}</p>
              <ul class="list-unstyled mb-3">
                <li><strong>Año:</strong> ${auto.ano}</li>
                <li><strong>Color:</strong> ${colores}</li> 
                <li><strong>Transmisión:</strong> ${auto.transmision}</li>
                <li><strong>Estado:</strong> ${auto.estado}</li> 
              </ul>
              <!-- Aquí se agregarán las miniaturas de las imágenes si hay más de una imagen -->
              ${imagenes.length > 1 ? `
                <div class="d-flex gap-2 mt-3">
                  ${imagenes.map((img, index) => `
                    <img src="${img}" class="img-thumbnail rounded-circle thumbnail-image" width="40" height="40" style="cursor: pointer;" onclick="changeImage(${auto.idAuto}, '${img}')">
                  `).join('')}
                </div>
              ` : ''}
              <div class="d-flex gap-2 mb-3">
                <button class="btn btn-outline-primary ">ALQUILAR</button>
                <button class="btn btn-outline-success">COMPRAR</button>
              </div>
            </div>
          </div>
        `;
        container.appendChild(card);
      });
    } catch (err) {
      container.innerHTML = '<p class="text-danger">Error al cargar los autos.</p>';
      console.error(err);
    }
  }

  
  window.changeImage = function(id, newImageUrl) {
    const mainImage = document.getElementById(`main-image-${id}`);
    mainImage.src = newImageUrl;
  }

 
  obtenerAutos();
});