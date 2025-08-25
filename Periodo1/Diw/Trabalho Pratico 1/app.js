// app.js
const destinations = [
    {
        "id": 1,
        "titulo": "Praias do Mundo",
        "descricao": "As praias mais deslumbrantes do planeta",
        "conteudo": "Descubra as praias vencedoras do Travellers' Choice 2025, selecionadas por milhões de viajantes. Desde o Caribe até o Sudeste Asiático, estas praias oferecem águas cristalinas, areias brancas e paisagens paradisíacas.",
        "localizacao": "Vários países",
        "destaques": "Águas cristalinas, Vida marinha, Pôr do sol espetacular",
        "melhorEpoca": "Ano todo",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2e/c1/4f/f7/caption.jpg",
        "categoria": "Praias",
        "cardSection": "proxima-viagem"
    },
    {
        "id": 2,
        "titulo": "Europa",
        "descricao": "Destinos clássicos e tesouros escondidos",
        "conteudo": "A Europa oferece uma incrível diversidade de culturas, paisagens e histórias em um espaço relativamente pequeno. Das praias mediterrâneas às montanhas dos Alpes, das cidades históricas às vilas pitorescas.",
        "localizacao": "Continente Europeu",
        "destaques": "Arquitetura histórica, Gastronomia diversificada, Transporte eficiente",
        "melhorEpoca": "Primavera e Outono",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2e/c1/50/7b/caption.jpg",
        "categoria": "Continentes",

        "cardSection":  "proxima-viagem"
    },
    {
        "id": 3,
        "titulo": "Rio de Janeiro",
        "descricao": "Cidade maravilhosa com paisagens deslumbrantes",
        "conteudo": "O Rio de Janeiro combina belezas naturais com uma vibrante vida urbana. Do Cristo Redentor às praias de Copacabana e Ipanema, da floresta da Tijuca aos bairros históricos como Santa Teresa.",
        "localizacao": "Brasil",
        "destaques": "Cristo Redentor, Pão de Açúcar, Carnaval",
        "melhorEpoca": "Abril a Junho",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/33/f4/3d/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "proxima-viagem"
    }, {
        "id": 4,
        "titulo": "Ilhas Fiji",
        "descricao": "Paraíso tropical no coração do Pacífico Sul",
        "conteudo": "As Ilhas Fiji são um arquipélago de mais de 300 ilhas no Pacífico Sul, conhecidas por suas praias de areia branca, águas cristalinas e recifes de coral vibrantes. O destino oferece uma combinação perfeita de luxo e cultura tradicional, com resorts de classe mundial e vilas autênticas. As Fiji são famosas por sua incrível vida marinha, tornando-as um dos melhores lugares do mundo para mergulho e snorkeling.",
        "localizacao": "Oceano Pacífico Sul",
        "destaques": "Resorts de luxo, Mergulho com tubarões, Cultura tradicional fijiana, Praias intocadas",
        "melhorEpoca": "Maio a Outubro (estação seca)",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2e/94/2a/bc/caption.jpg",
        "categoria": "Praias",
        "cardSection": "proxima-viagem"
    }, {
        "id": 5,
        "titulo": "Europa",
        "descricao": "Destinos clássicos e tesouros escondidos",
        "conteudo": "A Europa oferece uma incrível diversidade de culturas, paisagens e histórias em um espaço relativamente pequeno. Das praias mediterrâneas às montanhas dos Alpes, das cidades históricas às vilas pitorescas.",
        "localizacao": "Continente Europeu",
        "destaques": "Arquitetura histórica, Gastronomia diversificada, Transporte eficiente",
        "melhorEpoca": "Primavera e Outono",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2e/c1/50/7b/caption.jpg",
        "categoria": "Continentes",

        "cardSection": "melhores-destinos"
    }, {
        "id": 6,
        "titulo": "Rio de Janeiro",
        "descricao": "Cidade maravilhosa com paisagens deslumbrantes",
        "conteudo": "O Rio de Janeiro combina belezas naturais com uma vibrante vida urbana. Do Cristo Redentor às praias de Copacabana e Ipanema, da floresta da Tijuca aos bairros históricos como Santa Teresa, a cidade oferece experiências únicas. O carioca hospitaleiro, a música vibrante e a gastronomia diversificada completam uma das cidades mais icônicas do mundo.",
        "localizacao": "Brasil",
        "destaques": "Cristo Redentor, Pão de Açúcar, Praias de Copacabana e Ipanema, Carnaval",
        "melhorEpoca": "Abril a Junho, Setembro a Novembro",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1b/33/f4/3d/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "melhores-destinos"
    },
    {
        "id": 7,
        "titulo": "Ásia",
        "descricao": "O maior e mais diverso continente do planeta",
        "conteudo": "A Ásia é um vasto território de contrastes marcantes e diversidade cultural incomparável. Das praias tropicais do Sudeste Asiático às montanhas do Himalaia, das metrópoles futuristas do Leste às tradições antigas do Sul, o continente oferece experiências para todos os tipos de viajantes. A rica história, gastronomia variada e as tradições milenares fazem da Ásia um destino de infinitas possibilidades.",
        "localizacao": "Continente Asiático",
        "destaques": "Templos antigos, Culinária diversa, Paisagens dramáticas, Megacidades",
        "melhorEpoca": "Varia de acordo com a região",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0f/a1/95/18/vista-do-balneario-da.jpg",
        "categoria": "Continentes",
        "cardSection": "melhores-destinos"
    },
    {
        "id": 8,
        "titulo": "Pacífico Sul",
        "descricao": "Arquipélagos paradisíacos e culturas polinésias",
        "conteudo": "O Pacífico Sul é o refúgio definitivo para os amantes de praias e culturas insulares. Composto por milhares de ilhas espalhadas pelo maior oceano do mundo, esta região oferece desde destinos de luxo como Bora Bora e Fiji até aventuras remotas em Papua Nova Guiné e Ilhas Salomão. A vida marinha abundante, as tradições polinésias e os cenários de cartão postal definem esta região idílica.",
        "localizacao": "Oceano Pacífico",
        "destaques": "Praias de areia branca, Recifes de coral, Cultura polinésia, Resorts sobre a água",
        "melhorEpoca": "Maio a Outubro (estação seca)",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/08/c9/a3/93/cachoeira-veu-das-noivas.jpg",
        "categoria": "Ilhas",
        "cardSection": "melhores-destinos"
    },
    {
        "id": 9,
        "titulo": "Roma, Itália",
        "descricao": "A cidade eterna com 3.000 anos de história",
        "conteudo": "Roma é um museu a céu aberto onde cada rua e praça conta histórias de um império que moldou o mundo ocidental. Do majestoso Coliseu às fontes barrocas, das pizzarias tradicionais aos museus renomados, a capital italiana oferece uma experiência cultural inesquecível. A culinária local, o estilo de vida relaxado e a mistura perfeita entre passado e presente fazem de Roma um destino obrigatório.",
        "localizacao": "Itália",
        "destaques": "Coliseu, Vaticano, Fontana di Trevi, Fórum Romano, Culinária italiana",
        "melhorEpoca": "Abril a Junho, Setembro a Outubro",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1c/c9/6c/08/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "praias-deslumbrantes"
    },
    {
        "id": 10,
        "titulo": "Paris, França",
        "descricao": "A cidade luz, capital mundial da arte e gastronomia",
        "conteudo": "Paris seduz com sua arquitetura elegante, boulevards arborizados e atmosfera romântica. Além dos ícones como a Torre Eiffel e a Catedral de Notre-Dame, a cidade encanta com seus bairros charmosos, cafés de calçada e museus de classe mundial. Centro da moda, gastronomia e arte, Paris combina perfeitamente sua rica história com uma vibrante vida cultural contemporânea.",
        "localizacao": "França",
        "destaques": "Torre Eiffel, Museu do Louvre, Arco do Triunfo, Montmartre, Gastronomia francesa",
        "melhorEpoca": "Abril a Junho, Setembro a Outubro",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1c/c9/6c/08/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "praias-deslumbrantes"
    },
    {
        "id": 11,
        "titulo": "Las Vegas, NV",
        "descricao": "A capital mundial do entretenimento e dos cassinos",
        "conteudo": "Las Vegas é uma explosão de luzes, entretenimento e experiências únicas em pleno deserto de Nevada. A famosa Strip concentra cassinos temáticos grandiosos, shows espetaculares e uma vida noturna incomparável. Além do jogo, a cidade oferece gastronomia de alto nível, compras de luxo e acesso fácil a maravilhas naturais como o Grand Canyon, tornando-a um destino completo para todos os tipos de visitantes.",
        "localizacao": "Nevada, Estados Unidos",
        "destaques": "Cassinos temáticos, Shows de entretenimento, Vida noturna, Gastronomia internacional",
        "melhorEpoca": "Março a Maio, Setembro a Novembro",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2a/34/2d/28/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "praias-deslumbrantes"
    },
    {
        "id": 12,
        "titulo": "Reykjavik, Islândia",
        "descricao": "Portal para as maravilhas naturais da terra do gelo e fogo",
        "conteudo": "Reykjavik, a capital mais setentrional do mundo, combina charme nórdico com natureza selvagem ao alcance da cidade. Compacta e vibrante, a cidade islandesa impressiona com sua arquitetura colorida, design contemporâneo e cena cultural efervescente. É o ponto de partida perfeito para explorar as auroras boreais, gêiseres, glaciares, fontes termais e paisagens vulcânicas que fazem da Islândia um destino único e surreal.",
        "localizacao": "Islândia",
        "destaques": "Auroras boreais, Lagoa Azul, Círculo Dourado, Cultura viking, Vida noturna",
        "melhorEpoca": "Maio a Setembro (dias longos), Setembro a Março (auroras boreais)",
        "imagem": "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/27/84/4d/17/caption.jpg",
        "categoria": "Cidades",
        "cardSection": "praias-deslumbrantes"
    }
];