document.addEventListener('DOMContentLoaded', async () => {
    const container = document.getElementById('favorito-container'); // Onde o botão será inserido
    const urlParams = new URLSearchParams(window.location.search);
    const noticiaId = urlParams.get('id');
    const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));

    if (!container || !noticiaId || !usuarioLogado) return;

    const botaoFavorito = document.createElement('button');
    botaoFavorito.id = 'btn-favorito';
    botaoFavorito.className = 'btn btn-outline-danger';
    botaoFavorito.innerHTML = '<i class="bi bi-heart"></i> Favoritar';
    container.appendChild(botaoFavorito);

    async function atualizarBotao() {
        const resposta = await fetch(`http://localhost:3000/favoritos?usuarioId=${usuarioLogado.id}&noticiaId=${noticiaId}`);
        const favoritos = await resposta.json();

        if (favoritos.length > 0) {
            botaoFavorito.innerHTML = '<i class="bi bi-heart-fill"></i> Remover';
            botaoFavorito.classList.remove('btn-outline-danger');
            botaoFavorito.classList.add('btn-danger');
        } else {
            botaoFavorito.innerHTML = '<i class="bi bi-heart"></i> Favoritar';
            botaoFavorito.classList.remove('btn-danger');
            botaoFavorito.classList.add('btn-outline-danger');
        }
    }

    await atualizarBotao();

    botaoFavorito.addEventListener('click', async () => {
        await toggleFavorito(noticiaId);
        await atualizarBotao();
    });
});