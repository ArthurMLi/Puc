#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

static const char *MATRICULA = "885134";

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
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

typedef struct {
    int tamanho;
    restaurante **restaurantes;
} colecao_restaurantes;

Data parse_data(char *s) {
    Data data;
    sscanf(s, "%4d-%2d-%2d", &data.ano, &data.mes, &data.dia);
    return data;
}

Hora parse_hora(char *s) {
    Hora hora;
    sscanf(s, "%2d:%2d", &hora.hora, &hora.minuto);
    return hora;
}

void formatar_data(Data *data, char *buffer) {
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

void formatar_hora(Hora *hora, char *buffer) {
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

void free_restaurante(restaurante *r) {
    if (r->nome != NULL) {
        free(r->nome);
    }
    if (r->cidade != NULL) {
        free(r->cidade);
    }
    if (r->tipos_cozinha != NULL) {
        for (int i = 0; i < 10; i++) {
            free(r->tipos_cozinha[i]);
        }
        free(r->tipos_cozinha);
    }
}

restaurante parse_restaurante(char *s) {
    restaurante r;
    r.nome = (char *) malloc(100 * sizeof(char));
    r.cidade = (char *) malloc(100 * sizeof(char));
    r.tipos_cozinha = (char **) malloc(10 * sizeof(char *));

    for (int i = 0; i < 10; i++) {
        r.tipos_cozinha[i] = (char *) malloc(50 * sizeof(char));
    }

    char tipos[120], faixa[10], hora_abertura[7], hora_fechamento[7], data[20], aberto[10];
    sscanf(s, "%d,%99[^,],%99[^,],%d,%lf,%119[^,],%9[^,],%6[^-]-%6[^,],%19[^,],%9[^,\n\r]",
           &r.id, r.nome, r.cidade, &r.capacidade, &r.avaliacao, tipos, faixa,
           hora_abertura, hora_fechamento, data, aberto);

    r.n_tipos_cozinha = 0;
    int inicio = 0;
    for (int i = 0; r.n_tipos_cozinha < 10; i++) {
        if (tipos[i] == ';' || tipos[i] == '\0') {
            int j = 0;
            for (int k = inicio; k < i && j < 49; k++) {
                r.tipos_cozinha[r.n_tipos_cozinha][j++] = tipos[k];
            }
            r.tipos_cozinha[r.n_tipos_cozinha][j] = '\0';
            r.n_tipos_cozinha++;
            inicio = i + 1;
            if (tipos[i] == '\0' || tipos[i] == '\n') {
                break;
            }
        }
    }

    int temp = 0;
    for (int i = 0; i < 6; i++) {
        if (faixa[i] == '$') {
            temp++;
        } else {
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

void formatar_restaurante(restaurante *r, char *buffer) {
    char abertura[6], fechamento[6], data[11], tipos[500] = "", faixa_preco[6];
    int tipos_tam = 0;

    formatar_hora(&r->horario_abertura, abertura);
    formatar_hora(&r->horario_fechamento, fechamento);
    formatar_data(&r->data_abertura, data);

    for (int i = 0; i < r->n_tipos_cozinha; i++) {
        for (int j = 0; r->tipos_cozinha[i][j] != '\0'; j++) {
            tipos[tipos_tam++] = r->tipos_cozinha[i][j];
        }
        if (i < r->n_tipos_cozinha - 1) {
            tipos[tipos_tam++] = ',';
        }
    }
    tipos[tipos_tam] = '\0';

    for (int i = 0; i < r->faixa_preco; i++) {
        faixa_preco[i] = '$';
    }
    faixa_preco[r->faixa_preco] = '\0';

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %s]\n",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
            tipos, faixa_preco, abertura, fechamento, data, r->aberto ? "true" : "false");
}

void ler_csv_colecao(colecao_restaurantes *colecao, char *path) {
    FILE *arquivo;
    char linha[512];
    char teste[2];

    colecao->tamanho = 0;
    colecao->restaurantes = NULL;

    arquivo = fopen(path, "r");
    if (arquivo == NULL) {
        return;
    }

    if (fgets(linha, sizeof(linha), arquivo) == NULL) {
        fclose(arquivo);
        return;
    }

    while (fgets(linha, sizeof(linha), arquivo) != NULL) {
        if (sscanf(linha, " %1s", teste) != 1) {
            continue;
        }

        restaurante *item = (restaurante *) malloc(sizeof(restaurante));
        if (item == NULL) {
            break;
        }
        *item = parse_restaurante(linha);

        restaurante **novo = (restaurante **) realloc(colecao->restaurantes,
                (colecao->tamanho + 1) * sizeof(restaurante *));

        if (novo == NULL) {
            free_restaurante(item);
            free(item);
            break;
        }

        colecao->restaurantes = novo;
        colecao->restaurantes[colecao->tamanho++] = item;
    }

    fclose(arquivo);
}

void free_colecao(colecao_restaurantes *colecao) {
    for (int i = 0; i < colecao->tamanho; i++) {
        free_restaurante(colecao->restaurantes[i]);
        free(colecao->restaurantes[i]);
    }
    free(colecao->restaurantes);
}

colecao_restaurantes *ler_csv() {
    colecao_restaurantes *colecao = (colecao_restaurantes *) malloc(sizeof(colecao_restaurantes));
    if (colecao == NULL) {
        return NULL;
    }

    ler_csv_colecao(colecao, "/tmp/restaurantes.csv");
    return colecao;
}

restaurante *buscar_por_id(colecao_restaurantes *colecao, int id) {
    for (int i = 0; i < colecao->tamanho; i++) {
        if (colecao->restaurantes[i]->id == id) {
            return colecao->restaurantes[i];
        }
    }
    return NULL;
}

colecao_restaurantes *ler_entradas(colecao_restaurantes *base) {
    colecao_restaurantes *selecionados = (colecao_restaurantes *) malloc(sizeof(colecao_restaurantes));
    if (selecionados == NULL) {
        return NULL;
    }

    selecionados->tamanho = 0;
    selecionados->restaurantes = NULL;

    int id;
    while (scanf("%d", &id) == 1 && id != -1) {
        restaurante *item = buscar_por_id(base, id);
        if (item == NULL) {
            continue;
        }

        restaurante **novo = (restaurante **) realloc(selecionados->restaurantes,
                (selecionados->tamanho + 1) * sizeof(restaurante *));

        if (novo == NULL) {
            free(selecionados->restaurantes);
            free(selecionados);
            return NULL;
        }

        selecionados->restaurantes = novo;
        selecionados->restaurantes[selecionados->tamanho++] = item;
    }

    return selecionados;
}

int comparar_avaliacao_nome(restaurante *a, restaurante *b, long long *comparacoes) {
    (*comparacoes)++;
    if (a->avaliacao < b->avaliacao) {
        return -1;
    }
    if (a->avaliacao > b->avaliacao) {
        return 1;
    }

    (*comparacoes)++;
    return strcmp(a->nome, b->nome);
}

void swap(colecao_restaurantes *colecao, int a, int b, long long *movimentacoes) {
    restaurante *tmp = colecao->restaurantes[a];
    colecao->restaurantes[a] = colecao->restaurantes[b];
    colecao->restaurantes[b] = tmp;
    (*movimentacoes) += 3;
}

void quicksort(colecao_restaurantes *colecao, int esq, int dir, long long *comparacoes, long long *movimentacoes) {
    int i = esq;
    int j = dir;
    restaurante *pivo = colecao->restaurantes[(esq + dir) / 2];

    while (i <= j) {
        while (comparar_avaliacao_nome(colecao->restaurantes[i], pivo, comparacoes) < 0) {
            i++;
        }
        while (comparar_avaliacao_nome(colecao->restaurantes[j], pivo, comparacoes) > 0) {
            j--;
        }

        if (i <= j) {
            swap(colecao, i, j, movimentacoes);
            i++;
            j--;
        }
    }

    if (esq < j) {
        quicksort(colecao, esq, j, comparacoes, movimentacoes);
    }
    if (i < dir) {
        quicksort(colecao, i, dir, comparacoes, movimentacoes);
    }
}

void escrever_log(long long comparacoes, long long movimentacoes, double tempo_ms) {
    char nome_arquivo[64];
    snprintf(nome_arquivo, sizeof(nome_arquivo), "%s_quicksort.txt", MATRICULA);

    FILE *arquivo = fopen(nome_arquivo, "w");
    if (arquivo == NULL) {
        return;
    }

    fprintf(arquivo, "%s\t%lld\t%lld\t%.3f\n", MATRICULA, comparacoes, movimentacoes, tempo_ms);
    fclose(arquivo);
}

int main() {
    colecao_restaurantes *base = ler_csv();
    if (base == NULL) {
        return 1;
    }

    colecao_restaurantes *selecionados = ler_entradas(base);
    if (selecionados == NULL) {
        free_colecao(base);
        free(base);
        return 1;
    }

    long long comparacoes = 0;
    long long movimentacoes = 0;

    clock_t inicio = clock();
    if (selecionados->tamanho > 1) {
        quicksort(selecionados, 0, selecionados->tamanho - 1, &comparacoes, &movimentacoes);
    }
    clock_t fim = clock();

    double tempo_ms = ((double) (fim - inicio) * 1000.0) / CLOCKS_PER_SEC;

    for (int i = 0; i < selecionados->tamanho; i++) {
        char s[2048];
        formatar_restaurante(selecionados->restaurantes[i], s);
        printf("%s", s);
    }

    escrever_log(comparacoes, movimentacoes, tempo_ms);

    free(selecionados->restaurantes);
    free(selecionados);
    free_colecao(base);
    free(base);

    return 0;
}
