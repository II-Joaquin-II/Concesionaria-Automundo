let autosGlobal = [];

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const response = await fetch('http://localhost:8081/api/autos');
        autosGlobal = await response.json();
        renderCarrusel();

        new Swiper('.mySwiper', {
            loop: true,
            slidesPerView: 1,
            spaceBetween: 20,
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            breakpoints: {
                850: {
                    slidesPerView: 2,
                },
                1000: {
                    slidesPerView: 3,
                }
            }
        });
    } catch (err) {
        console.error(err);
        document.getElementById('swiper-autos-container').innerHTML = '<p class="text-danger">Error al cargar los autos.</p>';
    }
});

function renderCarrusel() {
    const container = document.getElementById('swiper-autos-container');
    container.innerHTML = '';

    autosGlobal.forEach((auto) => {
        const imagenes = auto.imagenes.map(imagen => `/img/${imagen.nombreArchivo}`);
        const imageUrl = imagenes.length > 0 ? imagenes[0] : 'http://localhost:8081/img/default-car.jpg';
        const colores = auto.colores?.map(color => color.nombreColor).join(", ") || 'No disponible';

        const slide = document.createElement('div');
        slide.classList.add('swiper-slide');

        slide.innerHTML = `
            <div class="card h-100">
                <div class="card-img-container">
                    <img id="main-image-${auto.idAuto}" src="${imageUrl}" class="card-img-top car-image" alt="${auto.modelo}">
                </div>
                <div class="card-body">
                    <h5 class="card-title text-danger">${auto.marca}</h5>
                    <h5 class="card-title text-danger">${auto.modelo}</h5>
                    <div class="card-text"><strong>Precio:</strong> $${auto.precio}</div>
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
        container.appendChild(slide);
    });
}

window.changeImage = function (id, newImageUrl) {
    const mainImage = document.getElementById(`main-image-${id}`);
    mainImage.src = newImageUrl;
};
