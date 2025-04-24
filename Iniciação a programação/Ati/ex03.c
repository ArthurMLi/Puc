#include <stdio.h>
int n1, menor = -1, maior = -1, series, j = 0;
float soma = 0;
int main(void)
{
    printf("Digite o numero de series:\n");
    scanf("%d", &series);
    for (int i = 1; i <= series; i++)
    {
        menor = -1, maior = -1, soma = 0;
        printf("Digite o %d numero da %d serie:\n", j + 1, i);
        scanf("%d", &n1);
        while (n1 >= 0)
        {
            if (n1 > maior)
            {
                maior = n1;
            }

            if (n1 < menor || menor == -1)
            {
                menor = n1;
            }
            soma += n1;
            j++;
            printf("Digite o %d numero da %d serie (Flag valor negativo):\n", j + 1, i);
            scanf("%d", &n1);
        }
        printf("Resultado para a %d serie:\n", i);

        if (menor == -1)
        {
            printf("Nao existem numeros para essa serie!\n");
        }
        else
        {

            printf("O menor numero encontrado = %d\n", menor);
            printf("O maior numero encontrado = %d\n", maior);

            printf("A media = %.2f\n", soma / j);
        }
        j = 0;
        menor = -1;
    }
}