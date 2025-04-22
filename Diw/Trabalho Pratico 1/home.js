// home.js
document.addEventListener('DOMContentLoaded', function () {
    // Seleciona todos os containers de grid de destinos
    const destinationGrids = document.querySelectorAll('.destination-grid');

    // Corrige a ordem das seções para bater com o HTML
    const sectionMap = {
        'proxima-viagem': destinationGrids[0],
        'melhores-destinos': destinationGrids[1],
        'praias-deslumbrantes': destinationGrids[2]
    };

    // Limpa os containers existentes
    Object.values(sectionMap).forEach(grid => {
        if (grid) grid.innerHTML = '';
    });

    // Para cada seção, filtra e renderiza os destinos correspondentes
    for (const [sectionId, container] of Object.entries(sectionMap)) {
        if (!container) continue;

        const sectionDestinations = destinations.filter(dest => {
            if (Array.isArray(dest.cardSection)) {
                return dest.cardSection.includes(sectionId);
            }
            return dest.cardSection === sectionId;
        });

        const limitedDestinations = sectionDestinations.slice(0, 4);

        limitedDestinations.forEach(destination => {
            const card = `
                <div class="destination-card position-relative">
                    <a href="detalhes.html?id=${destination.id}">
                        <img class="img-fluid rounded w-100" 
                             src="${destination.imagem}" 
                             alt="${destination.titulo}">
                        <span class="img-caption">${destination.titulo}</span>
                    </a>
                </div>
            `;
            container.insertAdjacentHTML('beforeend', card);
        });
    }

    console.log('Cards renderizados nas seções corretas');
});
