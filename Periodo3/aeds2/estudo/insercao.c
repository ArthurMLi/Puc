#include <stdio.h>
#include <stdlib.h>
typedef struct celula
{
    celula *prox;
    int valor;
} celula;

celula *insercao(celula *cabeca)
{
    celula *c = cabeca;
    celula co;
    co.valor = c->valor;
    celula *ordenado = &co;
    celula *ordenadotemp = ordenado;

    while (c != NULL)
    {
        if (ordenadotemp->prox == NULL)
        {
            
        }
        
        while (c->valor > ordenadotemp->prox->valor)
        {

        }
        c = c->prox;
    }
}
int main()
{
}

