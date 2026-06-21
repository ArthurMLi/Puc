#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

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
    bool aberto;
} Restaurante;

void liberar_restaurante(Restaurante *r)
{
    free(r->nome);
    free(r->cidade);
    for (int i = 0; i < r->n_tipos_cozinha; i++)
    {
        free(r->tipos_cozinha[i]);
    }
    free(r->tipos_cozinha);
    free(r);
}

Restaurante *parse_restaurante(char *s)
{
    Restaurante *r = malloc(sizeof(Restaurante));

    char *token = strsep(&s, ",");
    r->id = atoi(token);

    token = strsep(&s, ",");
    r->nome = strdup(token);

    token = strsep(&s, ",");
    r->cidade = strdup(token);

    token = strsep(&s, ",");
    r->capacidade = atoi(token);

    token = strsep(&s, ",");
    r->avaliacao = atof(token);

    token = strsep(&s, ",");
    r->n_tipos_cozinha = 1;
    for (int i = 0; token[i] != '\0'; i++)
    {
        if (token[i] == ';')
        {
            r->n_tipos_cozinha++;
        }
    }
    r->tipos_cozinha = malloc(r->n_tipos_cozinha * sizeof(char *));
    char *tipo = strsep(&token, ";");
    int i = 0;
    while (tipo != NULL)
    {
        r->tipos_cozinha[i] = strdup(tipo);
        tipo = strsep(&token, ";");
        i++;
    }

    token = strsep(&s, ",");
    r->faixa_preco = strlen(token);

    token = strsep(&s, ",");
    char *horario = strsep(&token, "-");
    r->horario_abertura = parse_hora(horario);
    horario = strsep(&token, "-");
    r->horario_fechamento = parse_hora(horario);

    token = strsep(&s, ",");
    r->data_abertura = parse_data(token);

    token = strsep(&s, ",");
    r->aberto = strcmp(token, "true") == 0;

    return r;
}

void formatar_restaurante(Restaurante *r, char *buffer)
{
    char buf_tipos_cozinha[100] = "";
    for (int i = 0; i < r->n_tipos_cozinha; i++)
    {
        sprintf(buf_tipos_cozinha + strlen(buf_tipos_cozinha), "%s", r->tipos_cozinha[i]);
        if (i < r->n_tipos_cozinha - 1)
        {
            sprintf(buf_tipos_cozinha + strlen(buf_tipos_cozinha), ",");
        }
    }

    char buf_faixa_preco[10] = "";
    for (int i = 0; i < r->faixa_preco; i++)
    {
        sprintf(buf_faixa_preco + strlen(buf_faixa_preco), "$");
    }

    char buf_horario_abertura[6];
    formatar_hora(&r->horario_abertura, buf_horario_abertura);

    char buf_horario_fechamento[6];
    formatar_hora(&r->horario_fechamento, buf_horario_fechamento);

    char buf_data_abertura[11];
    formatar_data(&r->data_abertura, buf_data_abertura);

    sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, buf_tipos_cozinha,
            buf_faixa_preco, buf_horario_abertura, buf_horario_fechamento, buf_data_abertura,
            r->aberto ? "true" : "false");
}

int ler_restaurantes(char *file_name, Restaurante **restaurantes)
{
    FILE *f = fopen(file_name, "r");
    if (f == NULL)
    {
        return 0;
    }

    char line[1000];
    fgets(line, sizeof(line), f);

    int count = 0;
    while (fgets(line, sizeof(line), f) != NULL)
    {
        if (line[strlen(line) - 1] == '\n')
            line[strlen(line) - 1] = '\0';
        if (line[strlen(line) - 1] == '\r')
            line[strlen(line) - 1] = '\0';

        restaurantes[count] = parse_restaurante(line);
        count++;
    }

    fclose(f);
    return count;
}

Restaurante *pesquisar_restaurante_por_id(Restaurante **restaurantes, int count, int id)
{
    for (int i = 0; i < count; i++)
    {
        if (restaurantes[i]->id == id)
        {
            return restaurantes[i];
        }
    }
    return NULL;
}


// Trie Node
typedef struct NoTrie {
    char elemento;
    struct NoListaTrie *filhos;
    bool isFim;
    Restaurante *r;
} NoTrie;

// Lista de filhos
typedef struct NoListaTrie {
    char caracter;
    NoTrie *no;
    struct NoListaTrie *prox;
} NoListaTrie;

NoTrie* novoNoTrie(char elemento) {
    NoTrie *no = (NoTrie*)malloc(sizeof(NoTrie));
    no->elemento = elemento;
    no->filhos = NULL;
    no->isFim = false;
    no->r = NULL;
    return no;
}

NoTrie* getFilho(NoTrie *no, char c, int *comparacoes) {
    NoListaTrie *tmp = no->filhos;
    while (tmp != NULL) {
        (*comparacoes)++;
        if (tmp->caracter == c) {
            return tmp->no;
        }
        tmp = tmp->prox;
    }
    return NULL;
}

void setFilho(NoTrie *no, char c, NoTrie *filho) {
    NoListaTrie *novo = (NoListaTrie*)malloc(sizeof(NoListaTrie));
    novo->caracter = c;
    novo->no = filho;
    novo->prox = NULL;

    // Inserir no final (mantem a ordem de criacao que eh util e previne bugs de loop/inversao)
    if (no->filhos == NULL) {
        no->filhos = novo;
    } else {
        NoListaTrie *tmp = no->filhos;
        while (tmp->prox != NULL) {
            tmp = tmp->prox;
        }
        tmp->prox = novo;
    }
}

typedef struct {
    NoTrie *raiz;
} ArvoreTrie;

ArvoreTrie* novaArvoreTrie() {
    ArvoreTrie *arvore = (ArvoreTrie*)malloc(sizeof(ArvoreTrie));
    arvore->raiz = novoNoTrie(' ');
    return arvore;
}

void inserirArvoreTrie(ArvoreTrie *arvore, Restaurante *r) {
    char *s = r->nome;
    NoTrie *no = arvore->raiz;
    int len = strlen(s);
    for (int i = 0; i < len; i++) {
        char c = s[i];
        int lixo = 0;
        NoTrie *filho = getFilho(no, c, &lixo);
        if (filho == NULL) {
            filho = novoNoTrie(c);
            setFilho(no, c, filho);
        }
        no = filho;
    }
    no->isFim = true;
    no->r = r;
}

int comparacoes = 0;

void pesquisarTrie(ArvoreTrie *arvore, char *nome) {
    NoTrie *no = arvore->raiz;
    int len = strlen(nome);
    char caminho[1000] = "";

    for (int i = 0; i < len; i++) {
        char c = nome[i];
        NoTrie *filho = getFilho(no, c, &comparacoes);
        if (filho != NULL) {
            no = filho;
            int clen = strlen(caminho);
            caminho[clen] = c;
            caminho[clen+1] = ' ';
            caminho[clen+2] = '\0';
        } else {
            printf("%sNAO\n", caminho);
            return;
        }
    }

    if (no->isFim) {
        char buf[1000];
        formatar_restaurante(no->r, buf);
        printf("%sSIM %s\n", caminho, buf);
    } else {
        printf("%sNAO\n", caminho);
    }
}


int main()
{
    clock_t inicio = clock();

    Restaurante *restaurantes[1000];
    int count = ler_restaurantes("/tmp/restaurantes.csv", restaurantes);
    if (count == 0) {
        count = ler_restaurantes("../restaurantes.csv", restaurantes);
    }

    ArvoreTrie *arvore = novaArvoreTrie();

    int id;
    scanf("%d", &id);
    while (id != -1)
    {
        Restaurante *r = pesquisar_restaurante_por_id(restaurantes, count, id);
        if (r != NULL) {
            inserirArvoreTrie(arvore, r);
        }
        scanf("%d", &id);
    }

    char nome[1000];
    scanf(" %[^\n]", nome);
    while (strcmp(nome, "FIM") != 0)
    {
        pesquisarTrie(arvore, nome);
        scanf(" %[^\n]", nome);
    }

    clock_t fim = clock();
    double tempo = ((double) (fim - inicio)) / CLOCKS_PER_SEC * 1000;

    FILE *log = fopen("885134_arvore_trie_lista.txt", "w");
    fprintf(log, "885134\t%.0f\t%d\n", tempo, comparacoes);
    fclose(log);

    // free everything
    for (int i = 0; i < count; i++)
    {
        liberar_restaurante(restaurantes[i]);
    }

    return 0;
}
