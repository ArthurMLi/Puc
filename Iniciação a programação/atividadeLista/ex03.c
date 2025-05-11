#include <stdio.h>
int main()
{
    int n, d, j, lista[100], cont = 0, maior = 0, menor = 0;
    float media, soma;
    printf("Digite a quantidade de receitas:\n");
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        maior = 0;
        menor = 0;
        soma = 0;
        printf("Digite o 1 ingrediente da %d receita:\n", i + 1);
        scanf("%d", &d);
        lista[0] = d;
        if (d > 0)
        {
            soma += d;
            cont = 1;
            j = 2;
            while (d >= 0)
            {
                printf("Digite o %d ingrediente da %d receita:\n", j, i + 1);
                scanf("%d", &d);
                if (d >= 0)
                {
                    lista[j - 1] = d;
                    cont++;
                    soma += d;
                }
                j++;
            }
            printf("Ingredientes da %d receita:", i + 1);
            media = soma / cont;
            for (int k = 0; k < j - 2; k++)
            {

                if (k != j - 3)
                {
                    if (lista[k] > media)
                    {
                        maior++;
                    }
                    else
                    {
                        if (lista[k] < media)
                        {
                            menor++;
                        }
                    }

                    printf("%d,", lista[k]);
                }
                else
                {
                    if (lista[k] > media)
                    {
                        maior++;
                    }
                    else
                    {
                        if (lista[k] < media)
                        {
                            menor++;
                        }
                    }   
                    printf("%d.\n", lista[k]);
                }
            }

            printf("%d pistas sao maiores que a media %.2f\n", maior, media);
            printf("%d pistas sao menores que a media %.2f\n", menor, media);

        }
        else
        {
            printf("Essa receita nao possui ingredientes!\n");
        }
    }

    return 1;
}