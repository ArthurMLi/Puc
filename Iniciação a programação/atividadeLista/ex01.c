#include <stdio.h>
int main()
{
    int n, d, j, lista[100], maior, menor;
    printf("Digite a quantidade de desafios:\n");
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        printf("Digite o 1 elemento do %d desafio:\n", i + 1);
        scanf("%d", &d);
        lista[0] = d;
        menor = d;
        maior = d;
        if (d > 0)
        {
            j = 2;
            while (d >= 0)
            {
                printf("Digite o %d elemento do %d desafio:\n", j, i + 1);
                scanf("%d", &d);
                if (d >= 0)
                {
                    lista[j - 1] = d;
                    if (d > maior)
                    {
                        maior = d;
                    }
                    if (d < menor)
                    {
                        menor = d;
                    }
                }
                j++;
            }
            printf("Elementos do %d desafio:", i + 1);
            for (int k = 0; k < j - 2; k++)
            {
                
                if (k != j-3)
                {
                    printf("%d,", lista[k]);
                }else{

                    printf("%d.\n", lista[k]);
                }
                
            }

            printf("Desafio mais facil:%d\n", menor);
            printf("Desafio mais dificil:%d\n", maior);
        }
        else
        {
            printf("Desafio nao possui elementos!\n");
        }
    }

    return 1;
}