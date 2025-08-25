document.addEventListener('DOMContentLoaded', async () => {
    try {
        const usuarioLogado = JSON.parse(sessionStorage.getItem('usuarioLogado'));
        if (!usuarioLogado) {
            window.location.href = 'login.html';
            return false;
        }

        const resposta = await fetch('http://localhost:3000/noticias');
        const noticias = await resposta.json();

        // Agrupar notícias por categoria
        const categorias = {};
        noticias.forEach(noticia => {
            if (!categorias[noticia.categoria]) {
                categorias[noticia.categoria] = 0;
            }
            categorias[noticia.categoria]++;
        });

        // Preparar dados para o gráfico
        const labels = Object.keys(categorias);
        const dados = Object.values(categorias);

        // Cores aleatórias para cada categoria
        const cores = labels.map(() => `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.7)`);

        // Criar gráfico
        const ctx = document.getElementById('grafico-categorias').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Número de Notícias por Categoria',
                    data: dados,
                    backgroundColor: cores,
                    borderColor: cores.map(cor => cor.replace('0.7', '1')),
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    } catch (erro) {
        console.error('Erro ao carregar dados para visualização:', erro);
    }
});