#include <stdio.h>
int n1, menor = -1, series, j = 1;
int main(void)
{
    printf("Digite o numero de series:\n");
    scanf("%d", &series);
    for (int i = 1; i <= series; i++)
    {

        printf("Digite o %d numero da %d serie:\n", j, i);
        scanf("%d", &n1);
        while (n1 >= 0)
        {
            j++;
            {
                if (n1 < menor || menor == -1)
                {
                    menor = n1;
                }
            }
            printf("Digite o %d numero da %d serie (Flag valor negativo):\n", j, i);
            scanf("%d", &n1);
        }
        if (menor == -1)
        {
            printf("Nao existem numeros para essa serie!\n");
        }
        else
        {

            printf("O menor numero encontrado da %d serie = %d\n", i, menor);
        }
        j = 1;
        menor = -1;
    }
}