// detalhes.js
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const destinationId = parseInt(urlParams.get('id'));
    
    const destination = destinations.find(d => d.id === destinationId);
    const detailContainer = document.getElementById('destination-detail');
    
    if (destination) {
        detailContainer.innerHTML = `
            <div class="col-md-8 mx-auto">
                <h1 class="mb-4">${destination.titulo}</h1>
                <img src="${destination.imagem}" 
                     alt="${destination.titulo}" 
                     class="img-fluid rounded mb-4 w-100">
                
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Sobre este destino</h5>
                        <p class="card-text">${destination.conteudo}</p>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h5 class="card-title">Informações</h5>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <strong>Localização:</strong> ${destination.localizacao}
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Destaques:</strong> ${destination.destaques}
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Melhor época:</strong> ${destination.melhorEpoca}
                                    </li>
                                    <li class="list-group-item">
                                        <strong>Categoria:</strong> ${destination.categoria}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Avaliações</h5>
                                <div class="d-flex align-items-center mb-3">
                                    <div class="me-3">
                                        <span class="display-4 text-primary">4.8</span>/5
                                    </div>
                                    <div>
                                        <div class="text-warning">
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star"></i>
                                            <i class="fas fa-star-half-alt"></i>
                                        </div>
                                        <small class="text-muted">Baseado em 1,245 avaliações</small>
                                    </div>
                                </div>
                                <button class="btn btn-primary w-100">
                                    <i class="fas fa-pencil-alt me-2"></i>Escrever uma avaliação
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } else {
        detailContainer.innerHTML = `
            <div class="col-12 text-center py-5">
                <h2>Destino não encontrado</h2>
                <p>O destino que você está procurando não existe ou foi removido.</p>
                <a href="index.html" class="btn btn-primary">Voltar para a página inicial</a>
            </div>
        `;
    }
});