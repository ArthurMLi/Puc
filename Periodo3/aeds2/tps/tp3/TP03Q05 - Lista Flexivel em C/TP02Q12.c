#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    int ano;
    int mes;
    int dia;
} Data;

typedef struct
{
    int hora;
    int minuto;
} Hora;

typedef struct restaurante_t
{
    int id;
    char *nome;
    char *cidade;
    int capacidade;
    double avaliacao;
    int n_tipos_cozinha;
    char **tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    int aberto;
    struct restaurante_t *prox;
} restaurante;

typedef struct
{
    int tamanho;
    restaurante *primeiro;
    restaurante *ultimo;
} colecao_restaurantes;

// Parses

Data parse_data(char *s)
{
    Data data;
    sscanf(s, "%4d-%2d-%2d", &data.ano, &data.mes, &data.dia);
    return data;
}

Hora parse_hora(char *s)
{
    Hora hora;
    sscanf(s, "%2d:%2d", &hora.hora, &hora.minuto);
    return hora;
}

restaurante parse_restaurante(char *s)
{
    restaurante r;
    r.nome = (char *)malloc(100 * sizeof(char));
    r.cidade = (char *)malloc(100 * sizeof(char));
    r.tipos_cozinha = (char **)malloc(10 * sizeof(char *));

    for (int i = 0; i < 10; i++)
    {
        r.tipos_cozinha[i] = (char *)malloc(50 * sizeof(char));
    }

    char tipos[120], faixa[10], hora_abertura[7], hora_fechamento[7], data[20], aberto[10];
    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%119[^,],%9[^,],%6[^-]-%6[^,],%19[^,],%9[^,\n\r]",
           &r.id, r.nome, r.cidade, &r.capacidade, &r.avaliacao, tipos, faixa,
           hora_abertura, hora_fechamento, data, aberto);

    r.n_tipos_cozinha = 0;
    int inicio = 0;
    for (int i = 0; r.n_tipos_cozinha < 10; i++)
    {
        if (tipos[i] == ';' || tipos[i] == '\0')
        {
            int j = 0;
            for (int k = inicio; k < i && j < 49; k++)
            {
                r.tipos_cozinha[r.n_tipos_cozinha][j++] = tipos[k];
            }
            r.tipos_cozinha[r.n_tipos_cozinha][j] = '\0';
            r.n_tipos_cozinha++;
            inicio = i + 1;
            if (tipos[i] == '\0' || tipos[i] == '\n')
            {
                break;
            }
        }
    }

    int temp = 0;
    for (int i = 0; i < 6; i++)
    {
        if (faixa[i] == '$')
        {
            temp++;
        }
        else
        {
            break;
        }
    }
    r.faixa_preco = temp;
    r.horario_abertura = parse_hora(hora_abertura);
    r.horario_fechamento = parse_hora(hora_fechamento);
    r.data_abertura = parse_data(data);
    r.aberto = (strcmp(aberto, "true") == 0);
    r.prox = NULL;
    return r;
}

// Formatar

void formatar_data(Data *data, char *buffer)
{
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

void formatar_hora(Hora *hora, char *buffer)
{
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

void formatar_restaurante(restaurante *r, char *buffer)
{
    char abertura[6], fechamento[6], data[11], tipos[500] = "", faixa_preco[6];
    int tipos_tam = 0;

    formatar_hora(&r->horario_abertura, abertura);
    formatar_hora(&r->horario_fechamento, fechamento);
    formatar_data(&r->data_abertura, data);

    for (int i = 0; i < r->n_tipos_cozinha; i++)
    {
        for (int j = 0; r->tipos_cozinha[i][j] != '\0'; j++)
        {
            tipos[tipos_tam++] = r->tipos_cozinha[i][j];
        }
        if (i < r->n_tipos_cozinha - 1)
        {
            tipos[tipos_tam++] = ',';
        }
    }
    tipos[tipos_tam] = '\0';

    for (int i = 0; i < r->faixa_preco; i++)
    {
        faixa_preco[i] = '$';
    }
    faixa_preco[r->faixa_preco] = '\0';

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %s]\n",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
            tipos, faixa_preco, abertura, fechamento, data, r->aberto ? "true" : "false");
}

// free

void free_restaurante(restaurante *r)
{
    if (r->nome != NULL)
    {
        free(r->nome);
    }
    if (r->cidade != NULL)
    {
        free(r->cidade);
    }
    if (r->tipos_cozinha != NULL)
    {
        for (int i = 0; i < 10; i++)
        {
            free(r->tipos_cozinha[i]);
        }
        free(r->tipos_cozinha);
    }
    free(r);
}

void free_colecao(colecao_restaurantes *colecao)
{
    free_restaurante(colecao->primeiro);
    free(colecao);
}

restaurante *buscar_id(colecao_restaurantes *colecao, int id)
{
    if (colecao == NULL)
    {
        return NULL;
    }

    restaurante *restaurante;
    for (restaurante = colecao->primeiro; restaurante->id != id && restaurante != NULL; restaurante = restaurante->prox)
    {
    }
    // to contando que todos os ids pesquisados existem
    return restaurante;
}

// lista

void inserir_inicio(colecao_restaurantes *base, int id, colecao_restaurantes *colecao)
{
    restaurante *r = buscar_id(base, id);
    restaurante *temp = colecao->primeiro;
    if (colecao->primeiro == NULL)
    {
        colecao->primeiro = r;
        colecao->ultimo = colecao->primeiro;
    }
    else
    {

        colecao->primeiro = r;
        colecao->primeiro->prox = temp;
    }
    colecao->tamanho++;
}

void inserir_fim(colecao_restaurantes *base, int id, colecao_restaurantes *colecao)
{
    restaurante *r = buscar_id(base, id);
    if (colecao->primeiro == NULL)
    {
        colecao->primeiro = r;
        colecao->ultimo = colecao->primeiro;
    }
    else
    {
        colecao->ultimo->prox = r;
        colecao->ultimo = r;
        colecao->tamanho++;
    }
}
void inserir(colecao_restaurantes *base, int posicao, int id, colecao_restaurantes *colecao)
{

    restaurante *r = buscar_id(base, id);
    restaurante *restaurante_antes = colecao->primeiro;
    // pegar um antes da posicao que quer inserir
    // to contando que a posicao 1 é a posicao 0 do array
    for (int i = 2; i < posicao; i++)
    {
        restaurante_antes = restaurante_antes->prox;
    }
    restaurante *temp = restaurante_antes->prox;
    restaurante_antes->prox = r;
    r->prox = temp;
    colecao->tamanho++;
}
void inserir_base(colecao_restaurantes *base, restaurante *r)
{
    if (base->primeiro == NULL)
    {
        base->primeiro = r;
        base->ultimo = base->primeiro;
    }
    else
    {
        base->ultimo->prox = r;
        base->ultimo = base->ultimo->prox;
        base->tamanho++;
    }
}

restaurante *remover(colecao_restaurantes *colecao, int pos)
{

    restaurante *restaurante_antes = colecao->primeiro;
    // pegar um antes da posicao que quer remover
    // to contando que a posicao 1 é a posicao 0 do array
    for (int i = 2; i < pos; i++)
    {
        restaurante_antes = restaurante_antes->prox;
    }
    restaurante *removido = restaurante_antes->prox;
    restaurante_antes->prox = restaurante_antes->prox->prox;
    colecao->tamanho--;
    return removido;
}

restaurante *remover_inicio(colecao_restaurantes *colecao)
{

    restaurante *removido = colecao->primeiro;
    colecao->primeiro = colecao->primeiro->prox;
    colecao->tamanho--;
    return removido;
}

restaurante *remover_fim(colecao_restaurantes *colecao)
{

    restaurante *restaurante_antes = colecao->primeiro;
    restaurante *removido = colecao->ultimo;

    // pegar um antes da posicao que quer remover
    // to contando que a posicao 1 é a posicao 0 do array
    for (; restaurante_antes->prox != colecao->ultimo;)
    {
        restaurante_antes = restaurante_antes->prox;
    }
    restaurante_antes->prox = NULL;
    colecao->ultimo = restaurante_antes;
    colecao->tamanho--;
    return removido;
}

void ler_linha(colecao_restaurantes *base, colecao_restaurantes *colecao)
{
    restaurante *removido;
    char c[10];
    int id;
    scanf("%s ", c);
    if (c[0] == 'I')
    {
        scanf("%d", &id);
        if (strcmp(c, "II") == 0)
        {
            inserir_inicio(base, id, colecao);
        }
        else
        {
            if (strcmp(c, "I*") == 0)
            {
                int pos;
                scanf("%d ", &pos);
                inserir(base, pos, id, colecao);
            }
            else
            {
                inserir_fim(base, id, colecao);
            }
        }
    }
    else
    {
        if (strcmp(c, "RI") == 0)
        {
            removido = remover_inicio(colecao);
        }
        else
        {
            if (strcmp(c, "R*") == 0)
            {
                int pos;
                scanf("%d ", &pos);
                removido = remover(colecao, pos);
            }
            else
            {
                removido = remover_fim(colecao);
            }
        }
        printf("(R)%s", removido->nome);
    }
}

// main ta tudo errado
// temn  que arrumar

// csv

void ler_csv_colecao(colecao_restaurantes *colecao, char *path)
{
    FILE *arquivo;
    char linha[512];
    char teste[2];

    colecao->tamanho = 0;
    colecao->primeiro = NULL;
    colecao->ultimo = NULL;

    arquivo = fopen(path, "r");
    if (arquivo == NULL)
    {
        return;
    }

    if (fgets(linha, sizeof(linha), arquivo) == NULL)
    {
        fclose(arquivo);
        return;
    }

    while (fgets(linha, sizeof(linha), arquivo) != NULL)
    {
        if (sscanf(linha, " %1s", teste) != 1)
        {
            continue;
        }

        restaurante *item = (restaurante *)malloc(sizeof(restaurante));
        if (item == NULL)
        {
            break;
        }
        *item = parse_restaurante(linha);

        inserir_base(colecao, item);
    }

    fclose(arquivo);
}

colecao_restaurantes *ler_csv()
{
    colecao_restaurantes *colecao = (colecao_restaurantes *)malloc(sizeof(colecao_restaurantes));
    if (colecao == NULL)
    {
        return NULL;
    }

    ler_csv_colecao(colecao, "../restaurantes.csv");
    return colecao;
}

int main()
{
    colecao_restaurantes *base = ler_csv();
    colecao_restaurantes *selecionados = (colecao_restaurantes *)malloc(sizeof(colecao_restaurantes));
    selecionados->primeiro = NULL;
    selecionados->ultimo = NULL;
    selecionados->tamanho = 0;

    if (base == NULL)
    {
        return 1;
    }
    
    int id;
    while (scanf("%d", &id) != 0 && id != -1)
    {
        inserir_fim(base, id, selecionados);
    }

    int n;
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        ler_linha(base, selecionados);
    }

    restaurante *r = selecionados->primeiro;
    char restaurante_formatado[500];
    for (int i = 0; i < selecionados->tamanho; i++)
    {
        formatar_restaurante(r, restaurante_formatado);
        printf("%s", restaurante_formatado);
        r = r->prox;
    }

    free_colecao(base);
    free(base);
    free_colecao(selecionados);
    free(selecionados);

    return 0;
}
