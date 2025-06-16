document.addEventListener('DOMContentLoaded', async () => {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    

    try {
        // Carregar favoritos do usuário
        const respostaFavoritos = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}`);
        const favoritos = await respostaFavoritos.json();
        
        // Carregar todas as notícias
        const respostaNoticias = await fetch('http://localhost:3000/noticias');
        const todasNoticias = await respostaNoticias.json();
        
        // Filtrar notícias favoritas
        const noticiasFavoritas = todasNoticias.filter(noticia => 
            favoritos.some(f => f.noticiaId === noticia.id)
        );

        // Exibir notícias favoritas
        const container = document.getElementById('favoritos-container');
        if (noticiasFavoritas.length > 0) {
            container.innerHTML = noticiasFavoritas.map(noticia => `
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <img src="${noticia.imagem_principal}" class="card-img-top" alt="${noticia.titulo}">
                        <div class="card-body d-flex flex-column">
                            <h5 class="card-title">${noticia.titulo}</h5>
                            <p class="card-text text-muted">${formatarData(noticia.data)} - ${noticia.categoria}</p>
                            <div class="d-flex justify-content-between mt-auto">
                                <a href="detalhe.html?id=${noticia.id}" class="btn btn-outline-primary">Ver detalhes</a>
                                <button class="btn btn-danger" onclick="removerFavorito(${noticia.id}, this)">
                                    <i class="bi bi-heart-fill"></i> Remover
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `).join('');
        } else {
            container.innerHTML = '<div class="col-12"><p>Nenhuma notícia favoritada ainda.</p></div>';
        }
    } catch (erro) {
        console.error('Erro ao carregar favoritos:', erro);
    }
    if (usuario) {
    sessionStorage.setItem('usuarioLogado', JSON.stringify(usuario));
    window.location.href = 'index.html';
    atualizarNavbar(); // Atualiza o navbar após o login
}
});

async function removerFavorito(noticiaId, botao) {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    
    try {
        // Encontrar o ID do favorito
        const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}&noticiaId=${noticiaId}`);
        const favoritos = await resposta.json();
        
        if (favoritos.length > 0) {
            const respostaDelete = await fetch(`http://localhost:3000/favoritos/${favoritos[0].id}`, {
                method: 'DELETE'
            });
            
            if (respostaDelete.ok) {
                // Remover o card da notícia
                botao.closest('.col-md-4').remove();
                
                // Atualizar ícones de favorito em outras páginas
                if (window.atualizarIconesFavorito) {
                    atualizarIconesFavorito();
                }
            }
        }
    } catch (erro) {
        console.error('Erro ao remover favorito:', erro);
    }
}

function formatarData(dataStr) {
    const data = new Date(dataStr);
    return data.toLocaleDateString('pt-BR');
}
document.addEventListener('DOMContentLoaded', () => {
    // Verificar se já está logado
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
    if (usuarioLogado) {
        window.location.href = 'index.html';
        return;
    }

    const formLogin = document.getElementById('form-login');
    if (formLogin) {
        formLogin.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const login = document.getElementById('login').value;
            const senha = document.getElementById('senha').value;

            try {
                const resposta = await fetch('http://localhost:3000/usuarios');
                const usuarios = await resposta.json();
                
                const usuario = usuarios.find(u => u.login === login && u.senha === senha);
                
                if (usuario) {
                    sessionStorage.setItem('usuarioLogado', JSON.stringify(usuario));
                    window.location.href = 'index.html';
                } else {
                    alert('Usuário ou senha incorretos');
                }
            } catch (erro) {
                console.error('Erro ao fazer login:', erro);
                alert('Erro ao fazer login');
            }
        });
    }
});
formLogin.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const usuario = {
        login: document.getElementById('login').value,
        senha: document.getElementById('senha').value
    };

    try {
        const resposta = await fetch('http://localhost:3000/usuarios');
        const usuarios = await resposta.json();
        
        const usuarioValido = usuarios.find(u => 
            u.login === usuario.login && u.senha === usuario.senha
        );

        if (usuarioValido) {
            sessionStorage.setItem('usuarioLogado', JSON.stringify(usuarioValido));
            
            // Forçar atualização completa
            window.location.href = 'index.html';
        } else {
            alert('Credenciais inválidas');
        }
    } catch (error) {
        console.error('Erro no login:', error);
        alert('Erro ao conectar ao servidor');
    }
});
// Exportar funções para uso global
window.removerFavorito = removerFavorito;