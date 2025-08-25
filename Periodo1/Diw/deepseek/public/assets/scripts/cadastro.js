document.addEventListener('DOMContentLoaded', () => {
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
        if (!usuarioLogado) {
            window.location.href = 'login.html';
            return false;
        }
    carregarNoticias();
    configurarFormulario();
});

const API_URL = 'http://localhost:3000/noticias';
const FAVORITOS_URL = 'http://localhost:3000/favoritos';

function configurarFormulario() {
    const form = document.getElementById('form-noticia');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const noticia = {
            titulo: document.getElementById('titulo').value,
            descricao: document.getElementById('descricao').value,
            conteudo: document.getElementById('conteudo').value,
            categoria: document.getElementById('categoria').value,
            autor: document.getElementById('autor').value,
            data: document.getElementById('data').value,
            destaque: document.getElementById('destaque').value === 'true',
            imagem_principal: document.getElementById('imagem-principal').value,
            imagens_complementares: []
        };

        // Adicionar imagens complementares
        const inputsImagens = document.querySelectorAll('#imagens-container input[type="url"]');
        const inputsDescricoes = document.querySelectorAll('#imagens-container input[type="text"]');
        
        for (let i = 0; i < inputsImagens.length; i++) {
            if (inputsImagens[i].value) {
                noticia.imagens_complementares.push({
                    id: i + 1,
                    src: inputsImagens[i].value,
                    descricao: inputsDescricoes[i].value || 'Imagem complementar'
                });
            }
        }

        const noticiaId = document.getElementById('noticia-id').value;
        let resposta;

        try {
            if (noticiaId) {
                // Atualizar notícia existente
                resposta = await fetch(`${API_URL}/${noticiaId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(noticia)
                });
            } else {
                // Criar nova notícia
                resposta = await fetch(API_URL, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(noticia)
                });
            }

            if (resposta.ok) {
                alert('Notícia salva com sucesso!');
                limparFormulario();
                carregarNoticias();
            } else {
                throw new Error('Erro ao salvar notícia');
            }
        } catch (erro) {
            console.error('Erro:', erro);
            alert('Erro ao salvar notícia');
        }
    });
}

async function carregarNoticias() {
    try {
        const resposta = await fetch(API_URL);
        const noticias = await resposta.json();
        preencherTabela(noticias);
    } catch (erro) {
        console.error('Erro ao carregar notícias:', erro);
    }
}

function preencherTabela(noticias) {
    const tbody = document.getElementById('tabela-noticias');
    tbody.innerHTML = '';

    noticias.forEach(noticia => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${noticia.titulo}</td>
            <td>${noticia.categoria}</td>
            <td>${formatarData(noticia.data)}</td>
            <td>
                <button class="btn btn-sm btn-primary" onclick="editarNoticia(${noticia.id})">Editar</button>
                <button class="btn btn-sm btn-danger" onclick="excluirNoticia(${noticia.id})">Excluir</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function editarNoticia(id) {
    try {
        const resposta = await fetch(`${API_URL}/${id}`);
        const noticia = await resposta.json();

        document.getElementById('noticia-id').value = noticia.id;
        document.getElementById('titulo').value = noticia.titulo;
        document.getElementById('descricao').value = noticia.descricao;
        document.getElementById('conteudo').value = noticia.conteudo;
        document.getElementById('categoria').value = noticia.categoria;
        document.getElementById('autor').value = noticia.autor;
        document.getElementById('data').value = noticia.data;
        document.getElementById('destaque').value = noticia.destaque;
        document.getElementById('imagem-principal').value = noticia.imagem_principal;

        // Limpar imagens complementares
        document.getElementById('imagens-container').innerHTML = '';
        
        // Adicionar imagens complementares
        noticia.imagens_complementares.forEach(imagem => {
            adicionarImagem(imagem.src, imagem.descricao);
        });

        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (erro) {
        console.error('Erro ao editar notícia:', erro);
    }
}

async function excluirNoticia(id) {
    if (confirm('Tem certeza que deseja excluir esta notícia?')) {
        try {
            const resposta = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (resposta.ok) {
                alert('Notícia excluída com sucesso!');
                carregarNoticias();
            } else {
                throw new Error('Erro ao excluir notícia');
            }
        } catch (erro) {
            console.error('Erro:', erro);
            alert('Erro ao excluir notícia');
        }
    }
}

function limparFormulario() {
    document.getElementById('form-noticia').reset();
    document.getElementById('noticia-id').value = '';
    document.getElementById('imagens-container').innerHTML = '';
}

function adicionarImagem(src = '', descricao = '') {
    const container = document.getElementById('imagens-container');
    const div = document.createElement('div');
    div.className = 'input-group mb-2';
    div.innerHTML = `
        <input type="url" class="form-control" placeholder="URL da imagem" value="${src}">
        <input type="text" class="form-control" placeholder="Descrição" value="${descricao}">
        <button type="button" class="btn btn-danger" onclick="removerImagem(this)">Remover</button>
    `;
    container.appendChild(div);
}

function removerImagem(botao) {
    botao.parentElement.remove();
}

function formatarData(dataStr) {
    const data = new Date(dataStr);
    return data.toLocaleDateString('pt-BR');
}

// Exportar funções para uso global
window.editarNoticia = editarNoticia;
window.excluirNoticia = excluirNoticia;
window.adicionarImagem = adicionarImagem;
window.removerImagem = removerImagem;
window.limparFormulario = limparFormulario;