document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('carousel-items-container');

    async function obtenerAutos() {
        try {
            const response = await fetch('http://localhost:8081/api/autos');
            const autos = await response.json();
            container.innerHTML = '';

            const grupos = [];
            for (let i = 0; i < autos.length; i += 3) {
                grupos.push(autos.slice(i, i + 3));
            }

            grupos.forEach((grupo, index) => {
                const card = document.createElement('div');
                card.classList.add('carousel-item');
                if (index === 0) {
                    card.classList.add('active');
                }

                const row = document.createElement('div');
                row.classList.add('row', 'row-cols-1', 'row-cols-md-3');

                grupo.forEach(auto => {
                    const imagenes = auto.imagenes.map(imagen => `/img/${imagen.nombreArchivo}`);
                    const imageUrl = imagenes.length > 0 ? imagenes[0] : 'http://localhost:8081/img/default-car.jpg';

                    const colores = auto.colores && auto.colores.length > 0
                            ? auto.colores.map(color => color.nombreColor).join(", ")
                            : 'No disponible';

                    const col = document.createElement('div');
                    col.classList.add('col');
                    col.innerHTML = `
                        <div class="card h-100">
                            <div class="card-img-container">
                                <img id="main-image-${auto.idAuto}" src="${imageUrl}" class="card-img-top car-image" alt="${auto.modelo}">
                            </div>
                            <div class="card-body">
                                <h5 class="card-title text-danger">${auto.marca} ${auto.modelo}</h5>
                                <p class="card-text"><strong>Precio:</strong> $${auto.precio}</p>
                                <ul class="list-unstyled">
                                    <li><strong>Año:</strong> ${auto.ano}</li>
                                    <li><strong>Color:</strong> ${colores}</li>
                                    <li><strong>Transmisión:</strong> ${auto.transmision}</li>
                                    <li><strong>Estado:</strong> ${auto.estado}</li>
                                </ul>
                                
                                <!-- Miniaturas de las imágenes -->
                                ${imagenes.length > 1 ? `
                                    <div class="d-flex gap-2 mt-3">
                                    ${imagenes.map((img, index) => `
                                        <img src="${img}" class="img-thumbnail rounded-circle thumbnail-image" width="40" height="40" style="cursor: pointer;" onclick="changeImage(${auto.idAuto}, '${img}')">
                                    `).join('')}
                                    </div>
                                ` : ''}

                                <a href="/infoauto?idAuto=${auto.idAuto}" class="btn btn-outline-primary">Ver más detalles</a>
                            </div>
                        </div>
                        `;
                            row.appendChild(col);
                        });

                card.appendChild(row);
                container.appendChild(card);
            });
        } catch (err) {
            console.error(err);
            container.innerHTML = '<p class="text-danger">Error al cargar los autos.</p>';
        }
    }

    obtenerAutos();
});


window.changeImage = function (id, newImageUrl) {
    const mainImage = document.getElementById(`main-image-${id}`);
    mainImage.src = newImageUrl;
}
