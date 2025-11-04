#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX_LINE_SIZE 4096
#define MAX_FIELD_SIZE 1024
#define MAX_ARRAY_ITEMS 100
#define CSV_FILE_PATH "/tmp/games.csv"

typedef struct Game {
    int id;
    char name[200];
    char releaseDate[20];
    int estimatedOwners;
    float price;
    char* supportedLanguages[MAX_ARRAY_ITEMS];
    int numLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    char* publishers[MAX_ARRAY_ITEMS];
    int numPublishers;
    char* developers[MAX_ARRAY_ITEMS];
    int numDevelopers;
    char* categories[MAX_ARRAY_ITEMS];
    int numCategories;
    char* genres[MAX_ARRAY_ITEMS];
    int numGenres;
    char* tags[MAX_ARRAY_ITEMS];
    int numTags;
    struct Game* prox;
} Game;

typedef struct Fila {
    Game* primeiro;
    Game* ultimo;
    int tamanho;
} Fila;

char* trim(char* s) {
    char* start = s;
    while (isspace((unsigned char)*start)) start++;
    char* end = s + strlen(s) - 1;
    while (end > start && isspace((unsigned char)*end)) end--;
    end[1] = '\0';
    return start;
}

void trimQuotes(char* s) {
    if (s[0] == '\"') {
        memmove(s, s + 1, strlen(s));
    }
    if (strlen(s) > 0 && s[strlen(s) - 1] == '\"') {
        s[strlen(s) - 1] = '\0';
    }
}

int mapMonth(char* monthName) {
    if (strncmp(monthName, "Jan", 3) == 0) return 1;
    if (strncmp(monthName, "Feb", 3) == 0) return 2;
    if (strncmp(monthName, "Mar", 3) == 0) return 3;
    if (strncmp(monthName, "Apr", 3) == 0) return 4;
    if (strncmp(monthName, "May", 3) == 0) return 5;
    if (strncmp(monthName, "Jun", 3) == 0) return 6;
    if (strncmp(monthName, "Jul", 3) == 0) return 7;
    if (strncmp(monthName, "Aug", 3) == 0) return 8;
    if (strncmp(monthName, "Sep", 3) == 0) return 9;
    if (strncmp(monthName, "Oct", 3) == 0) return 10;
    if (strncmp(monthName, "Nov", 3) == 0) return 11;
    if (strncmp(monthName, "Dec", 3) == 0) return 12;
    return 1;
}

void normalizeDate(char* dest, char* src) {
    char temp[MAX_FIELD_SIZE];
    strcpy(temp, src);
    trimQuotes(temp);

    char monthStr[10], dayStr[5], yearStr[5];
    int monthNum = 1, dayNum = 1, yearNum = 1970;

    if (sscanf(temp, "%s %s %s", monthStr, dayStr, yearStr) == 3) {
        dayStr[strlen(dayStr) - 1] = '\0';
        monthNum = mapMonth(monthStr);
        dayNum = atoi(dayStr);
        yearNum = atoi(yearStr);
    } else if (sscanf(temp, "%s %s", monthStr, yearStr) == 2) {
        monthNum = mapMonth(monthStr);
        yearNum = atoi(yearStr);
    } else if (sscanf(temp, "%s", yearStr) == 1 && strlen(yearStr) == 4) {
        yearNum = atoi(yearStr);
    }
    sprintf(dest, "%02d/%02d/%04d", dayNum, monthNum, yearNum);
}

int normalizeOwners(char* s) {
    char clean[50];
    int j = 0;
    for (int i = 0; s[i] != '\0' && s[i] != ' '; i++) {
        if (isdigit((unsigned char)s[i])) {
            clean[j++] = s[i];
        }
    }
    clean[j] = '\0';
    return (j > 0) ? atoi(clean) : 0;
}

float normalizePrice(char* s) {
    if (strstr(s, "Free to Play") != NULL) return 0.0f;
    return atof(s);
}

void parseList(char* dest[], int* count, char* src, bool bracket) {
    *count = 0;
    char temp[MAX_FIELD_SIZE];
    strcpy(temp, src);
    trimQuotes(temp);

    if (strlen(temp) <= 1) return;

    if (bracket) {
        if (temp[0] == '[') memmove(temp, temp + 1, strlen(temp));
        if (temp[strlen(temp) - 1] == ']') temp[strlen(temp) - 1] = '\0';
    }

    char* token = strtok(temp, ",");
    while (token != NULL && *count < MAX_ARRAY_ITEMS) {
        char* cleanToken = trim(token);
        if (bracket) {
            if (cleanToken[0] == '\'') memmove(cleanToken, cleanToken + 1, strlen(cleanToken));
            if (strlen(cleanToken) > 0 && cleanToken[strlen(cleanToken) - 1] == '\'') {
                cleanToken[strlen(cleanToken) - 1] = '\0';
            }
        }
        if (strlen(cleanToken) > 0) {
            dest[*count] = strdup(cleanToken);
            (*count)++;
        }
        token = strtok(NULL, ",");
    }
}

void freeGame(Game* game) {
    if (game == NULL) return;
    for (int i = 0; i < game->numLanguages; i++) free(game->supportedLanguages[i]);
    for (int i = 0; i < game->numPublishers; i++) free(game->publishers[i]);
    for (int i = 0; i < game->numDevelopers; i++) free(game->developers[i]);
    for (int i = 0; i < game->numCategories; i++) free(game->categories[i]);
    for (int i = 0; i < game->numGenres; i++) free(game->genres[i]);
    for (int i = 0; i < game->numTags; i++) free(game->tags[i]);
    free(game);
}

Game* newGame() {
    Game* game = (Game*)malloc(sizeof(Game));
    game->id = 0;
    strcpy(game->name, "");
    strcpy(game->releaseDate, "01/01/1970");
    game->estimatedOwners = 0;
    game->price = 0.0f;
    game->numLanguages = 0;
    game->metacriticScore = -1;
    game->userScore = -1.0f;
    game->achievements = 0;
    game->numPublishers = 0;
    game->numDevelopers = 0;
    game->numCategories = 0;
    game->numGenres = 0;
    game->numTags = 0;
    game->prox = NULL;
    return game;
}

void parseLine(Game* game, char* line) {
    char fields[14][MAX_FIELD_SIZE];
    memset(fields, 0, sizeof(fields));
    int fieldIndex = 0;
    int start = 0;
    bool inQuotes = false;
    char lineCopy[MAX_LINE_SIZE];
    strcpy(lineCopy, line);
    
    lineCopy[strcspn(lineCopy, "\n")] = '\0';
    lineCopy[strcspn(lineCopy, "\r")] = '\0';

    for (int i = 0; lineCopy[i] != '\0'; i++) {
        if (lineCopy[i] == '\"') inQuotes = !inQuotes;
        if (lineCopy[i] == ',' && !inQuotes) {
            strncpy(fields[fieldIndex], lineCopy + start, i - start);
            fields[fieldIndex][i - start] = '\0';
            trim(fields[fieldIndex]);
            fieldIndex++;
            start = i + 1;
        }
    }
    strcpy(fields[fieldIndex], lineCopy + start);
    trim(fields[fieldIndex]);

    if (fieldIndex != 13) {
        return;
    }

    game->id = atoi(fields[0]);
    trimQuotes(fields[1]);
    strcpy(game->name, fields[1]);
    normalizeDate(game->releaseDate, fields[2]);
    game->estimatedOwners = normalizeOwners(fields[3]);
    game->price = normalizePrice(fields[4]);
    parseList(game->supportedLanguages, &game->numLanguages, fields[5], true);
    game->metacriticScore = (strlen(fields[6]) > 0) ? atoi(fields[6]) : -1;
    game->userScore = (strlen(fields[7]) > 0 && strcmp(fields[7], "tbd") != 0) ? atof(fields[7]) : -1.0f;
    game->achievements = (strlen(fields[8]) > 0) ? atoi(fields[8]) : 0;
    parseList(game->publishers, &game->numPublishers, fields[9], false);
    parseList(game->developers, &game->numDevelopers, fields[10], false);
    parseList(game->categories, &game->numCategories, fields[11], false);
    parseList(game->genres, &game->numGenres, fields[12], false);
    parseList(game->tags, &game->numTags, fields[13], false);
}

void printArray(char* arr[], int count) {
    printf("[");
    for (int i = 0; i < count; i++) {
        printf("%s", arr[i]);
        if (i < count - 1) printf(", ");
    }
    printf("]");
}

void printGame(Game* game, int index) {
    printf("[%d] => %d ## %s ## %s ## %d ## %.2f ## ",
           index, game->id, game->name, game->releaseDate, game->estimatedOwners, game->price);
    printArray(game->supportedLanguages, game->numLanguages);
    printf(" ## %d ## %.1f ## %d ## ",
           game->metacriticScore, game->userScore, game->achievements);
    printArray(game->publishers, game->numPublishers);
    printf(" ## ");
    printArray(game->developers, game->numDevelopers);
    printf(" ## ");
    printArray(game->categories, game->numCategories);
    printf(" ## ");
    printArray(game->genres, game->numGenres);
    printf(" ## ");
    printArray(game->tags, game->numTags);
    printf(" ##\n");
}

Game* findGameById(char* id) {
    FILE* file = fopen(CSV_FILE_PATH, "r");
    if (file == NULL) {
        return NULL;
    }
    char line[MAX_LINE_SIZE];
    fgets(line, MAX_LINE_SIZE, file);

    while (fgets(line, MAX_LINE_SIZE, file)) {
        char lineId[20];
        strncpy(lineId, line, 19);
        lineId[strcspn(lineId, ",")] = '\0';
        
        if (strcmp(lineId, id) == 0) {
            Game* game = newGame();
            parseLine(game, line);
            fclose(file);
            return game;
        }
    }
    fclose(file);
    return NULL;
}

Fila* newFila() {
    Fila* fila = (Fila*)malloc(sizeof(Fila));
    fila->primeiro = NULL;
    fila->ultimo = NULL;
    fila->tamanho = 0;
    return fila;
}

void inserirInicio(Fila* fila, Game* game) {
    if (fila->primeiro == NULL) {
        fila->primeiro = game;
        fila->ultimo = game;
    } else {
        game->prox = fila->primeiro;
        fila->primeiro = game;
    }
    fila->tamanho++;
}

void inserirFim(Fila* fila, Game* game) {
    if (fila->primeiro == NULL) {
        fila->primeiro = game;
        fila->ultimo = game;
    } else {
        fila->ultimo->prox = game;
        fila->ultimo = game;
        game->prox = NULL;
    }
    fila->tamanho++;
}

void inserir(Fila* fila, Game* game, int p) {
    if (p < 0 || p > fila->tamanho) {
        return;
    }
    if (p == 0) {
        inserirInicio(fila, game);
    } else if (p == fila->tamanho) {
        inserirFim(fila, game);
    } else {
        Game* tmp = fila->primeiro;
        for (int i = 0; i < p - 1; i++) {
            tmp = tmp->prox;
        }
        game->prox = tmp->prox;
        tmp->prox = game;
        fila->tamanho++;
    }
}

Game* removerInicio(Fila* fila) {
    if (fila->primeiro == NULL) {
        return NULL;
    }
    Game* tmp = fila->primeiro;
    fila->primeiro = fila->primeiro->prox;
    if (fila->primeiro == NULL) {
        fila->ultimo = NULL;
    }
    fila->tamanho--;
    tmp->prox = NULL;
    return tmp;
}

Game* removerFim(Fila* fila) {
    if (fila->primeiro == NULL) {
        return NULL;
    }
    
    Game* removido = fila->ultimo;
    if (fila->primeiro == fila->ultimo) {
        fila->primeiro = NULL;
        fila->ultimo = NULL;
        fila->tamanho = 0;
        return removido;
    }

    Game* anterior = fila->primeiro;
    while (anterior->prox != fila->ultimo) {
        anterior = anterior->prox;
    }
    
    anterior->prox = NULL;
    fila->ultimo = anterior;
    fila->tamanho--;
    return removido;
}

Game* remover(Fila* fila, int p) {
    if (fila->primeiro == NULL || p < 0 || p >= fila->tamanho) {
        return NULL;
    }
    Game* removido = NULL;
    if (p == 0) {
        removido = removerInicio(fila);
    } else if (p == fila->tamanho - 1) {
        removido = removerFim(fila);
    } else {
        Game* tmp = fila->primeiro;
        for (int i = 0; i < p - 1; i++) {
            tmp = tmp->prox;
        }
        removido = tmp->prox;
        tmp->prox = removido->prox;
        fila->tamanho--;
        removido->prox = NULL;
    }
    return removido;
}

void mostrar(Fila* fila) {
    int j = 0;
    Game* i = fila->primeiro;
    while (i != NULL) {
        printGame(i, j);
        i = i->prox;
        j++;
    }
}

void freeFila(Fila* fila) {
    Game* atual = fila->primeiro;
    while (atual != NULL) {
        Game* proximo = atual->prox;
        freeGame(atual);
        atual = proximo;
    }
    free(fila);
}

int main() {
    Fila* filaDeGames = newFila();
    char idInput[100];

    while (fgets(idInput, 100, stdin) && strncmp(idInput, "FIM", 3) != 0) {
        idInput[strcspn(idInput, "\n")] = '\0';
        Game* game = findGameById(idInput);
        if (game != NULL) {
            inserirFim(filaDeGames, game);
        }
    }

    int n;
    scanf("%d", &n);
    getchar();

    for (int i = 0; i < n; i++) {
        char commandLine[200];
        if (fgets(commandLine, 200, stdin) == NULL) {
            break;
        }
        commandLine[strcspn(commandLine, "\n")] = '\0';
        
        char command[5];
        char arg1[100], arg2[100];
        Game* game;
        Game* removido;

        int parts = sscanf(commandLine, "%s %s %s", command, arg1, arg2);

        if (strcmp(command, "II") == 0 && parts >= 2) {
            game = findGameById(arg1);
            if (game) inserirInicio(filaDeGames, game);
        } else if (strcmp(command, "IF") == 0 && parts >= 2) {
            game = findGameById(arg1);
            if (game) inserirFim(filaDeGames, game);
        } else if (strcmp(command, "I*") == 0 && parts >= 3) {
            int posI = atoi(arg1);
            game = findGameById(arg2);
            if (game) inserir(filaDeGames, game, posI);
        } else if (strcmp(command, "RI") == 0) {
            removido = removerInicio(filaDeGames);
            if (removido) {
                printf("(R) %s\n", removido->name);
                freeGame(removido);
            }
        } else if (strcmp(command, "RF") == 0) {
            removido = removerFim(filaDeGames);
            if (removido) {
                printf("(R) %s\n", removido->name);
                freeGame(removido);
            }
        } else if (strcmp(command, "R*") == 0 && parts >= 2) {
            int posR = atoi(arg1);
            removido = remover(filaDeGames, posR);
            if (removido) {
                printf("(R) %s\n", removido->name);
                freeGame(removido);
            }
        }
    }
    mostrar(filaDeGames);
    freeFila(filaDeGames);
    return 0;
}