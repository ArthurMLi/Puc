#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct no_t
{
    int elemento;
    struct no_t *dir;
    struct no_t *esq;

} no;
typedef struct
{
    no *raiz;
} arvore;
int pesquisa(no *n, int x)
{
    if (n == NULL)
    {
        return 0;
    }
    printf("%d ", n->elemento);
    int resp = 0;
    if (n->elemento > x)
    {
        if (n->esq != NULL)
        {
            resp = pesquisa(n->esq, x);
        }
    }
    else
    {
        if (n->elemento < x)
        {
            if (n->dir != NULL)
            {
                resp = pesquisa(n->dir, x);
            }
        }
        else
        {
            resp = 1;
        }
    }
    return resp;
}
void caminhar_pre(no *n)
{
    if (n == NULL)
    {

        return;
    }

    printf("%d ", n->elemento);
    caminhar_pre(n->esq);
    caminhar_pre(n->dir);
}

void caminhar_pos(no *n)
{
    if (n == NULL)
    {

        return;
    }
    caminhar_pos(n->esq);
    caminhar_pos(n->dir);
    printf("%d ", n->elemento);
}

void caminhar_central(no *n)
{
    if (n == NULL)
    {

        return;
    }
    caminhar_central(n->esq);
    printf("%d ", n->elemento);
    caminhar_central(n->dir);
}

void inserir(arvore *a, no *n, int x)
{
    if (a->raiz == NULL)
    {
        n = (no *)malloc(sizeof(no));
        n->elemento = x;
        n->dir = NULL;
        n->esq = NULL;
        a->raiz = n;
    }
    else
    {

        if (n->elemento > x)
        {
            if (n->esq == NULL)
            {
                n->esq = (no *)malloc(sizeof(no));
                n->esq->dir = NULL;
                n->esq->esq = NULL;
                n->esq->elemento = x;
            }
            else
            {
                inserir(a, n->esq, x);
            }
        }
        else
        {
            if (n->dir == NULL)
            {
                n->dir = (no *)malloc(sizeof(no));
                n->dir->dir = NULL;
                n->dir->esq = NULL;
                n->dir->elemento = x;
            }
            else
            {
                inserir(a, n->dir, x);
            }
        }
    }
}

int main()
{
    arvore *a = (arvore *)malloc(sizeof(arvore));
    a->raiz = NULL;
    char c[4];
    int x;
    while (scanf("%s", c) != EOF)
    {
        if (strcmp(c, "I") == 0 || strcmp(c, "P") == 0)
        {
            scanf("%d", &x);
        }
        if (strcmp(c, "I") == 0)
        {
            inserir(a, a->raiz, x);
        }
        else
        {
            if (strcmp(c, "P") == 0)
            {
                printf("%c\n", ((pesquisa(a->raiz, x) == 1) ? 'S' : 'N'));
            }
            else
            {
                if (strcmp(c, "PRE") == 0)
                {
                    if (a->raiz == NULL)
                    {
                        printf("V");
                    }

                    caminhar_pre(a->raiz);
                    printf("\n");
                }
                else
                {
                    if (strcmp(c, "POS") == 0)
                    {
                        if (a->raiz == NULL)
                        {
                            printf("V");
                        }
                        caminhar_pos(a->raiz);
                        printf("\n");
                    }
                    else
                    {
                        if (a->raiz == NULL)
                        {
                            printf("V");
                        }
                        caminhar_central(a->raiz);
                        printf("\n");
                    }
                }
            }
        }
    }
}
