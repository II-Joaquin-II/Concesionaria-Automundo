document.addEventListener('DOMContentLoaded', function () {
    const ctx = document.getElementById('estadoChart').getContext('2d');
    const estadoChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Pendiente', 'Entregado', 'En Proceso'],
            datasets: [{
                data: [pendientes, entregados, enProceso],
                backgroundColor: ['#f39c12', '#27ae60', '#e74c3c'],
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const ctx = document.getElementById('accesoriosChart').getContext('2d');
    const coloresFijos = ['#f39c12', '#27ae60', '#e74c3c'];
    const colores = nombresAccesorios.map((_, index) => coloresFijos[index % coloresFijos.length]);

    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: nombresAccesorios,
            datasets: [{
                label: 'Accesorios vendidos',
                data: cantidadesAccesorios,
                backgroundColor: colores,
                borderColor: '#ffffff',
                borderWidth: 1
            }]
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            const label = context.label || '';
                            const value = context.raw || 0;
                            return `${label}: ${value}`;
                        }
                    }
                }
            }
        }
    });
});


