#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// data
typedef struct
{
    int ano;
    int mes;
    int dia;
} Data;
Data parse_data(char *s)
{
    Data data;
    sscanf(s, "%4d-%2d-%2d", &data.ano, &data.mes, &data.dia);

    return data;
}
void formatar_data(Data *data, char *buffer)
{
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}
// hora
typedef struct
{
    int hora;
    int minuto;
} Hora;

Hora parse_hora(char *s)
{
    Hora hora;
    sscanf(s, "%2d:%2d", &hora.hora, &hora.minuto);
    return hora;
}
void formatar_hora(Hora *hora, char *buffer)
{
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

// restuarante

typedef struct
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
} restaurante;

void free_restaurante(restaurante *restaurante)
{
    if (restaurante->nome != NULL)
    {
        free(restaurante->nome);
    }
    if (restaurante->cidade != NULL)
    {
        free(restaurante->cidade);
    }
    if (restaurante->tipos_cozinha != NULL)
    {
        for (int i = 0; i < 10; i++)
        {
            free(restaurante->tipos_cozinha[i]);
        }
        free(restaurante->tipos_cozinha);
    }
}
restaurante parse_restaurante(char *s)
{
    restaurante r;
    r.nome = malloc(100 * sizeof(char));
    r.cidade = malloc(100 * sizeof(char));
    r.tipos_cozinha = malloc(10 * sizeof(char *));
    for (int i = 0; i < 10; i++)
    {
        r.tipos_cozinha[i] = malloc(50 * sizeof(char));
    }

    char tipos[120], faixa[10], hora_abertura[7], hora_fechamento[7], data[20], aberto[10];
    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%119[^,],%9[^,],%6[^-]-%6[^,],%19[^,],%9[^,\n\r]",
           &r.id, r.nome, r.cidade, &r.capacidade, &r.avaliacao, tipos, faixa, hora_abertura, hora_fechamento, data, aberto);

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
    return r;
}

void formatar_restaurante(restaurante *restaurante, char *buffer)
{
    char abertura[6];
    char fechamento[6];
    char data[11];
    char tipos[500] = "";
    int tipos_tam = 0;
    char faixa_preco[6]; 
    formatar_hora(&restaurante->horario_abertura, abertura);
    formatar_hora(&restaurante->horario_fechamento, fechamento);
    formatar_data(&restaurante->data_abertura, data);

    for (int i = 0; i < restaurante->n_tipos_cozinha; i++)
    {
        for (int j = 0; restaurante->tipos_cozinha[i][j] != '\0'; j++)
        {
            tipos[tipos_tam++] = restaurante->tipos_cozinha[i][j];
        }
        if (i < restaurante->n_tipos_cozinha - 1)
        {
            tipos[tipos_tam++] = ',';
        }
    }
    for (int i = 0; i < restaurante->faixa_preco; i++)
    {
        faixa_preco[i] = '$';
    }
    faixa_preco[restaurante->faixa_preco] = '\0';
    

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %s]\n",
            restaurante->id,
            restaurante->nome,
            restaurante->cidade,
            restaurante->capacidade,
            restaurante->avaliacao,
            tipos,
            faixa_preco,
            abertura,
            fechamento,
            data,
            restaurante->aberto ? "true" : "false");
}

// colecao restaurante
typedef struct
{
    int tamanho;
    restaurante **restaurantes;
} colecao_restaurantes;

void ler_csv_colecao(colecao_restaurantes *colecao, char *path)
{
    FILE *arquivo;
    char linha[512];
    char teste[2];

    if (colecao == NULL)
    {
        return;
    }

    colecao->tamanho = 0;
    colecao->restaurantes = NULL;

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
        restaurante *item = malloc(sizeof(restaurante));
        restaurante **novo_vetor;

        if (item == NULL)
        {
            break;
        }

        *item = parse_restaurante(linha);

        novo_vetor = (restaurante **)realloc(colecao->restaurantes, (colecao->tamanho + 1) * sizeof(restaurante *));
        if (novo_vetor == NULL)
        {
            free_restaurante(item);
            free(item);
            break;
        }

        colecao->restaurantes = novo_vetor;
        colecao->restaurantes[colecao->tamanho] = item;
        colecao->tamanho++;
    }
    fclose(arquivo);
}
void free_colecao(colecao_restaurantes *colecao)
{
    for (int i = 0; i < colecao->tamanho; i++)
    {
        free_restaurante(colecao->restaurantes[i]);
        free(colecao->restaurantes[i]);
    }
    free(colecao->restaurantes);
}
colecao_restaurantes *ler_csv()
{
    colecao_restaurantes *colecao = (colecao_restaurantes *)malloc(sizeof(colecao_restaurantes));
    if (colecao == NULL)
    {
        return NULL;
    }

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv");
    return colecao;
}

int main()
{
    colecao_restaurantes *colecao = ler_csv();
    int id;

    while (scanf("%d", &id), id != -1)
    {
        for (int i = 0; i < colecao->tamanho; i++)
        {
            if (colecao->restaurantes[i]->id == id)
            {
                char s[1024];
                formatar_restaurante(colecao->restaurantes[i], s);
                printf("%s", s);
                break;
            }
        }
    }
    free_colecao(colecao);
    free(colecao);

}
