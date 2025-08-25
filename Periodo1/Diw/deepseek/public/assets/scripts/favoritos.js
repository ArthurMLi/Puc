document.addEventListener('DOMContentLoaded', async () => {
    // Verificar se usuário está logado
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    if (!usuarioLogado) {
        alert('Você precisa estar logado para acessar esta página');
        window.location.href = 'login.html';
        return;
    }
    
    // Carregar e exibir notícias favoritas
    await carregarFavoritos();
    
    // Configurar evento de logout
    const logoutLink = document.getElementById('logout-link');
    if (logoutLink) {
        logoutLink.addEventListener('click', (e) => {
            e.preventDefault();
            sessionStorage.removeItem('usuarioLogado');
            window.location.href = 'index.html';
        });
    }
});

async function carregarFavoritos() {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    const container = document.getElementById('favoritos-container');
    
    try {
        // 1. Carregar todos os favoritos do usuário
        const respostaFavoritos = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}`);
        const favoritos = await respostaFavoritos.json();
        
        // 2. Carregar todas as notícias
        const respostaNoticias = await fetch('http://localhost:3000/noticias');
        const todasNoticias = await respostaNoticias.json();
        
        // 3. Filtrar apenas as notícias favoritadas (convertendo IDs para string para garantir comparação correta)
        const noticiasFavoritas = todasNoticias.filter(noticia => 
            favoritos.some(f => String(f.noticiaId) === String(noticia.id))
        );
        
        // 4. Exibir notícias favoritas ou mensagem se não houver
        if (noticiasFavoritas.length > 0) {
            container.innerHTML = noticiasFavoritas.map(noticia => `
                <div class="col-md-4 mb-4">
                    <div class="card h-100" data-noticia-id="${noticia.id}">
                        <img src="${noticia.imagem_principal}" class="card-img-top" alt="${noticia.titulo}">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${noticia.titulo}</h5>
                            <p class="card-text text-muted">${formatarData(noticia.data)} - ${noticia.categoria}</p>
                            <div class="d-flex justify-content-between mt-auto">
                                <a href="detalhe.html?id=${noticia.id}" class="btn btn-outline-primary">Ver detalhes</a>
                                <button class="btn btn-danger btn-remover-favorito">
                                    <i class="bi bi-heart-fill"></i> Remover
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `).join('');

            // 5. Adicionar eventos aos botões de remover
            document.querySelectorAll('.btn-remover-favorito').forEach(botao => {
                botao.addEventListener('click', async () => {
                    const card = botao.closest('.card');
                    const noticiaId = card.dataset.noticiaId;
                    await removerFavorito(noticiaId);
                    card.remove();

                    if (document.querySelectorAll('.card').length === 0) {
                        container.innerHTML = '<div class="col-12 text-center py-5"><h5>Nenhuma notícia favoritada</h5><p>Volte à página inicial para adicionar favoritos</p></div>';
                    }
                });
            });
        } else {
            container.innerHTML = `
                <div class="col-12 text-center py-5">
                    <h5>Nenhuma notícia favoritada ainda</h5>
                    <p>Você pode favoritar notícias clicando no ícone <i class="bi bi-heart"></i> na página inicial</p>
                    <a href="index.html" class="btn btn-primary mt-3">Voltar à página inicial</a>
                </div>
            `;
        }
    } catch (erro) {
        console.error('Erro ao carregar favoritos:', erro);
        container.innerHTML = `
            <div class="col-12 text-center py-5">
                <h5 class="text-danger">Erro ao carregar favoritos</h5>
                <p>Por favor, tente recarregar a página</p>
                <button onclick="window.location.reload()" class="btn btn-outline-primary">Recarregar</button>
            </div>
        `;
    }
}


async function removerFavorito(noticiaId) {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    
    try {
        // 1. Encontrar o favorito específico
        const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}&noticiaId=${noticiaId}`);
        const favoritos = await resposta.json();
        
        // 2. Se existir, remover
        if (favoritos.length > 0) {
            const respostaDelete = await fetch(`http://localhost:3000/favoritos/${favoritos[0].id}`, {
                method: 'DELETE'
            });
            
            if (!respostaDelete.ok) {
                throw new Error('Erro ao remover favorito');
            }
            
            // 3. Atualizar ícones em outras páginas
            if (window.atualizarIconesFavorito) {
                atualizarIconesFavorito();
            }
            
            return true;
        }
        return false;
    } catch (erro) {
        console.error('Erro ao remover favorito:', erro);
        alert('Erro ao remover favorito');
        return false;
    }
}

function formatarData(dataStr) {
    const data = new Date(dataStr);
    return data.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
}

// Adicionar funções ao escopo global para reutilização
window.removerFavorito = removerFavorito;
window.carregarFavoritos = carregarFavoritos;