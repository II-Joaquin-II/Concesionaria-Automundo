function autosPorSlide() {
    const width = window.innerWidth;
    if (width >= 1000) return 3;
    if (width >= 850) return 2;
    return 1;
}

let autosGlobal = []; 

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const response = await fetch('http://localhost:8081/api/autos');
        autosGlobal = await response.json();
        renderCarrusel(); 
    } catch (err) {
        console.error(err);
        document.getElementById('carousel-items-container').innerHTML = '<p class="text-danger">Error al cargar los autos.</p>';
    }
});

function renderCarrusel() {
    const container = document.getElementById('carousel-items-container');
    container.innerHTML = ''; 

    const cantidadPorSlide = autosPorSlide();
    const grupos = [];

    for (let i = 0; i < autosGlobal.length; i += cantidadPorSlide) {
        grupos.push(autosGlobal.slice(i, i + cantidadPorSlide));
    }

    grupos.forEach((grupo, index) => {
        const card = document.createElement('div');
        card.classList.add('carousel-item');
        if (index === 0) card.classList.add('active');

        const row = document.createElement('div');
        row.classList.add('row', 'g-3', 'justify-content-center');

        grupo.forEach(auto => {
            const imagenes = auto.imagenes.map(imagen => `/img/${imagen.nombreArchivo}`);
            const imageUrl = imagenes.length > 0 ? imagenes[0] : 'http://localhost:8081/img/default-car.jpg';
            const colores = auto.colores?.map(color => color.nombreColor).join(", ") || 'No disponible';

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
                        ${imagenes.length > 1 ? `
                            <div class="d-flex gap-2 mt-3">
                                ${imagenes.map(img => `
                                    <img src="${img}" class="img-thumbnail rounded-circle thumbnail-image" width="40" height="40" style="cursor: pointer;" onclick="changeImage(${auto.idAuto}, '${img}')">
                                `).join('')}
                            </div>
                        ` : ''}
                        <a href="/infoauto?idAuto=${auto.idAuto}" class="btn btn-outline-danger">Ver más detalles</a>
                    </div>
                </div>
            `;
            row.appendChild(col);
        });

        card.appendChild(row);
        container.appendChild(card);
    });
}

window.changeImage = function (id, newImageUrl) {
    const mainImage = document.getElementById(`main-image-${id}`);
    mainImage.src = newImageUrl;
};

let resizeTimeout;
window.addEventListener('resize', () => {
    clearTimeout(resizeTimeout);
    resizeTimeout = setTimeout(() => {
        renderCarrusel();
    }, 200);
});
