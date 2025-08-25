const API_URL = 'http://localhost:3000/noticias';

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const resposta = await fetch(API_URL);
        const noticias = await resposta.json();

        carregarCarrossel(noticias);
        carregarCards(noticias);
        configurarPesquisa()
    } catch (erro) {
        console.error('Erro ao carregar notícias:', erro);
    }
    atualizarNavbar();
});
function atualizarNavbar() {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    const navbar = document.querySelector('.navbar-nav');

    if (!navbar) { console.error('AS'); return; }

    // Elementos do navbar
    const loginItem = navbar.querySelector('#login-item');
    const logoutItem = navbar.querySelector('#logout-item');
    const favoritosItem = navbar.querySelector('#favoritos-item');
    const cadastroItem = navbar.querySelector('#cadastro-item');

    if (usuarioLogado) {
        // Usuário logado
        if (loginItem) loginItem.style.display = 'none';
        if (logoutItem) logoutItem.style.display = 'block';
        if (favoritosItem) favoritosItem.style.display = 'block';
        if (cadastroItem) cadastroItem.style.display = usuarioLogado.admin ? 'block' : 'none';

        // Configurar logout
        logoutItem.onclick = (e) => {
            e.preventDefault();
            sessionStorage.removeItem('usuarioLogado');
            window.location.href = 'index.html';
        };
    } else {
        // Usuário não logado
        if (loginItem) loginItem.style.display = 'block';
        if (logoutItem) logoutItem.style.display = 'none';
        if (favoritosItem) favoritosItem.style.display = 'none';
        if (cadastroItem) cadastroItem.style.display = 'none';
    }
}
function carregarCarrossel(noticias) {
    const carouselInner = document.getElementById('carousel-items');
    const carouselIndicators = document.getElementById('carousel-indicators');

    if (!carouselInner || !carouselIndicators) return;

    carouselInner.innerHTML = '';
    carouselIndicators.innerHTML = '';

    noticias.slice(0, 3).forEach((noticia, index) => {
        carouselIndicators.innerHTML += `
            <button type="button" data-bs-target="#carouselDestaques" data-bs-slide-to="${index}" ${index === 0 ? 'class="active" aria-current="true"' : ''} aria-label="Slide ${index + 1}"></button>
        `;

        carouselInner.innerHTML += `
            <div class="carousel-item ${index === 0 ? 'active' : ''}">
                <img src="${noticia.imagem_principal}" class="d-block w-100" alt="${noticia.titulo}">
                <div class="carousel-caption d-none d-md-block">
                    <h5>${noticia.titulo}</h5>
                    <p>${noticia.categoria} - ${formatarData(noticia.data)}</p>
                    <a href="detalhe.html?id=${noticia.id}" class="btn btn-primary">Leia mais</a>
                </div>
            </div>
        `;
    });
}

function carregarCards(noticias) {
    const container = document.getElementById('noticias-container');
    if (!container) return;

    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));

    container.innerHTML = noticias.map(noticia => `
        <div class="col-md-4 mb-4">
            <div class="card h-100" data-noticia-id="${noticia.id}">
                <img src="${noticia.imagem_principal}" class="card-img-top" alt="${noticia.titulo}">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${noticia.titulo}</h5>
                    <p class="card-text text-muted">${formatarData(noticia.data)} - ${noticia.categoria}</p>
                    <div class="d-flex justify-content-between mt-auto">
                        <a href="detalhe.html?id=${noticia.id}" class="btn btn-outline-primary">Ver detalhes</a>
                        ${usuarioLogado ? `
                            <button class="btn btn-outline-danger btn-favorito" data-noticia-id="${noticia.id}">
                            <i class="bi bi-heart"></i> Favoritar
                            </button>
                        ` : ''}
                    </div>
                </div>
            </div>
        </div>
    `).join('');

    // Atualizar ícones se usuário estiver logado
    if (usuarioLogado) {
        atualizarIconesFavorito();
    }
    const botoesFavorito = document.querySelectorAll('.btn-favorito');
    console.log(botoesFavorito);

    botoesFavorito.forEach(botao => {
        botao.addEventListener('click', () => {
            const noticiaId = botao.getAttribute('data-noticia-id');
            console.log('ID da notícia clicada:', noticiaId);
            toggleFavorito(noticiaId);
        });
    });
}

function formatarData(dataStr) {
    const data = new Date(dataStr);
    return data.toLocaleDateString('pt-BR', {
        day: '2-digit', month: '2-digit', year: 'numeric'
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get('id');
    if (id) {
        carregarDetalhesNoticia(id);
    }
});
// Código para a página de detalhes
document.addEventListener('DOMContentLoaded', async () => {
    // Captura o ID da notícia da URL
    const urlParams = new URLSearchParams(window.location.search);
    const noticiaId = urlParams.get('id');

    //if (!noticiaId) {
    //    alert('ID da notícia não encontrado.');
    //    return;
    //}

    try {
        // Requisição para buscar os detalhes da notícia
        console.log('ID da notícia recebido:', noticiaId);
        const resposta = await fetch(`${API_URL}/${noticiaId}`);
        const noticia = await resposta.json();

        // Carregar detalhes da notícia e galeria de imagens
        carregarDetalhesNoticia(noticia);
        carregarGaleriaImagens(noticia);
    } catch (erro) {
        console.error('Erro ao carregar os detalhes da notícia:', erro);
    }
});

function carregarDetalhesNoticia(noticia) {
    const detalhesContainer = document.getElementById('detalhes-container');

    if (!detalhesContainer) return;

    detalhesContainer.innerHTML = `
        <div class="row">
            <div class="col-md-8">
                <img src="${noticia.imagem_principal}" class="noticia-img" alt="${noticia.titulo}">
                <div class="info-noticia mt-4">
                    <h1>${noticia.titulo}</h1>
                    <span class="categoria-badge">${noticia.categoria}</span>
                    <p class="autor-data">${formatarData(noticia.data)} | Autor: ${noticia.autor}</p>
                    <p>${noticia.conteudo}</p>
                </div>
            </div>
            
        </div>
    `;
}

function carregarGaleriaImagens(noticia) {
    const galeriaContainer = document.getElementById('galeria-container');
    if (!galeriaContainer) return;

    galeriaContainer.innerHTML = '';  // Limpar o conteúdo atual

    noticia.imagens_complementares.forEach(imagem => {
        const div = document.createElement('div');
        div.classList.add('col-md-4', 'mb-4');  // Para manter responsividade

        const img = document.createElement('img');
        img.src = imagem.src;
        img.alt = imagem.descricao;
        img.classList.add('galeria-img', 'img-fluid');

        const descricao = document.createElement('p');
        descricao.textContent = imagem.descricao;
        descricao.classList.add('text-center');

        div.appendChild(img);
        div.appendChild(descricao);
        galeriaContainer.appendChild(div);
    });
}

function formatarData(dataStr) {
    const data = new Date(dataStr);
    return data.toLocaleDateString('pt-BR', {
        day: '2-digit', month: '2-digit', year: 'numeric'
    });
}

// Função para pesquisar notícias
function configurarPesquisa() {
    const campoPesquisa = document.getElementById('campo-pesquisa');
    if (campoPesquisa) {
        campoPesquisa.addEventListener('input', async (e) => {
            const termo = e.target.value.toLowerCase();

            try {
                const resposta = await fetch(API_URL);
                const noticias = await resposta.json();

                const noticiasFiltradas = noticias.filter(noticia =>
                    noticia.titulo.toLowerCase().includes(termo) ||
                    noticia.descricao.toLowerCase().includes(termo) ||
                    noticia.conteudo.toLowerCase().includes(termo)
                );

                carregarCards(noticiasFiltradas);
            } catch (erro) {
                console.error('Erro ao pesquisar notícias:', erro);
            }
        });
    }
}

// Função para configurar favoritos
async function configurarFavoritos() {
    // Verificar conexão primeiro
    if (!await verificarConexao()) {
        console.warn('Servidor offline - favoritos desativados');
        desativarFavoritos();
        return;
    }

    document.addEventListener('click', async (e) => {
        const btn = e.target.closest('.btn-favorito');
        if (!btn) return;

        const card = btn.closest('.card');
        const noticiaId = parseInt(card.dataset.noticiaId);

        btn.disabled = true;
        btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span>';

        try {
            await toggleFavorito(noticiaId);
            await atualizarIconesFavorito();
        } catch (error) {
            console.error('Erro ao favoritar:', error);
            btn.innerHTML = '<i class="bi bi-exclamation-triangle"></i> Erro';
            setTimeout(() => atualizarIconesFavorito(), 2000);
        }
    });

    await atualizarIconesFavorito();
}

async function toggleFavorito(noticiaId) {
    try {
        // Garantir que seja um número (se seus IDs forem numéricos)
        noticiaId = parseInt(noticiaId);

        const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
        if (!usuarioLogado) throw new Error('Usuário não logado');

        console.log('Usuário logado:', usuarioLogado);
        console.log('ID da notícia:', noticiaId);

        // Verifica se o favorito já existe
        const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}&noticiaId=${noticiaId}`);
        const favoritos = await resposta.json();

        console.log('Favoritos encontrados:', favoritos);

        if (favoritos.length > 0) {
            // Já está favoritado – remover
            const favoritoId = favoritos[0].id;
            console.log(`Removendo favorito com ID ${favoritoId}...`);

            const del = await fetch(`http://localhost:3000/favoritos/${favoritoId}`, {
                method: 'DELETE'
            });

            if (del.ok) {
                console.log('Favorito removido com sucesso.');
            } else {
                console.error('Erro ao remover favorito:', del.status);
            }
        } else {
            // Ainda não está favoritado – adicionar
            console.log('Adicionando novo favorito...');

            const post = await fetch('http://localhost:3000/favoritos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    usuarioId: usuarioLogado.id,
                    noticiaId: noticiaId,
                    data: new Date().toISOString()
                })
            });

            if (post.ok) {
                const resultado = await post.json();
                console.log('Favorito adicionado:', resultado);
            } else {
                console.error('Erro ao adicionar favorito:', post.status);
            }
        }

    } catch (e) {
        console.error('Erro no toggleFavorito:', e);
    }
}




async function atualizarIconesFavorito() {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    if (!usuarioLogado) return;

    try {
        const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}`);
        const favoritos = await resposta.json();

        document.querySelectorAll('.btn-favorito').forEach(btn => {
            const card = btn.closest('.card');
            const noticiaId = parseInt(card.dataset.noticiaId);
            const isFavorito = favoritos.some(f => f.noticiaId === noticiaId);

            btn.innerHTML = isFavorito
                ? '<i class="bi bi-heart-fill"></i> Favorito'
                : '<i class="bi bi-heart"></i> Favoritar';

            btn.classList.toggle('btn-danger', isFavorito);
            btn.classList.toggle('btn-outline-danger', !isFavorito);
            btn.disabled = false;
        });
    } catch (error) {
        console.error('Erro ao atualizar ícones:', error);
        desativarFavoritos();
    }
}

function desativarFavoritos() {
    document.querySelectorAll('.btn-favorito').forEach(btn => {
        btn.innerHTML = '<i class="bi bi-cloud-off"></i> Offline';
        btn.classList.add('btn-secondary');
        btn.disabled = true;
    });
}

async function verificarConexao() {
    try {
        const resposta = await fetch('http://localhost:3000/noticias?_limit=1');
        return resposta.ok;
    } catch {
        return false;
    }
}

// Função para adicionar/remover favoritos

// Função para atualizar ícones de favorito
async function atualizarIconesFavorito() {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    if (!usuarioLogado) return;

    async function atualizarIconesFavorito() {
        const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
        if (!usuarioLogado) {
            console.log('Usuário não logado - ícones de favorito desabilitados');
            return;
        }

        // 1. Primeiro verifica se existem botões na página
        const botoesFavorito = document.querySelectorAll('.btn-favorito');
        if (botoesFavorito.length === 0) {
            console.log('Nenhum botão de favorito encontrado na página');
            return;
        }

        // 2. Configuração do timeout
        const controller = new AbortController();
        const timeoutId = setTimeout(() => {
            controller.abort();
            console.warn('Timeout ao carregar favoritos');
        }, 5000);

        try {
            // 3. Tentativa de fetch com tratamento de erro
            const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}`, {
                signal: controller.signal
            }).catch(error => {
                if (error.name === 'AbortError') {
                    throw new Error('Timeout: O servidor demorou muito para responder');
                }
                throw error;
            });

            clearTimeout(timeoutId);

            // 4. Verificação da resposta
            if (!resposta.ok) {
                throw new Error(`Erro HTTP ${resposta.status}: ${resposta.statusText}`);
            }

            // 5. Processamento dos dados
            const favoritos = await resposta.json().catch(error => {
                throw new Error('Erro ao parsear JSON: ' + error.message);
            });

            // 6. Atualização da UI
            botoesFavorito.forEach(botao => {
                try {
                    const card = botao.closest('.card');
                    if (!card || !card.dataset.noticiaId) {
                        console.warn('Card inválido para botão de favorito', botao);
                        return;
                    }

                    const noticiaId = parseInt(card.dataset.noticiaId);
                    if (isNaN(noticiaId)) {
                        throw new Error(`ID de notícia inválido: ${card.dataset.noticiaId}`);
                    }

                    const estaFavoritado = favoritos.some(f => f.noticiaId === noticiaId);

                    botao.classList.toggle('btn-outline-danger', !estaFavoritado);
                    botao.classList.toggle('btn-danger', estaFavoritado);
                    botao.innerHTML = estaFavoritado
                        ? '<i class="bi bi-heart-fill"></i> Favoritado'
                        : '<i class="bi bi-heart"></i> Favoritar';

                    botao.disabled = false;

                } catch (error) {
                    console.error('Erro ao atualizar botão:', error);
                    botao.innerHTML = '<i class="bi bi-exclamation-triangle"></i> Erro';
                    botao.classList.add('btn-warning');
                }
            });

        } catch (error) {
            clearTimeout(timeoutId);
            console.error('Erro ao carregar favoritos:', error);

            // Estado de fallback para todos os botões
            botoesFavorito.forEach(botao => {
                botao.innerHTML = '<i class="bi bi-cloud-off"></i> Offline';
                botao.classList.add('btn-secondary');
                botao.disabled = true;
            });
        }
    }
}
// Adicionar chamadas no DOMContentLoaded

function verificarAutenticacao(paginaRestrita = false) {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));

    // Páginas que requerem login
    if (paginaRestrita && !usuarioLogado) {
        window.location.href = 'login.html';
        return false;
    }

    // Página de login - se já estiver logado, redireciona
    if (window.location.pathname.includes('login.html') && usuarioLogado) {
        window.location.href = 'index.html';
        return false;
    }

    return true;
}


// Exportar funções para uso global
window.atualizarIconesFavorito = atualizarIconesFavorito;