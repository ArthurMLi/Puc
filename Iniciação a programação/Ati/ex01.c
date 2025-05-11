
#include <stdio.h>
int n1, n2, maior;
int main(void)
{
    printf("Digite o primeiro numero competidor:\n");
    scanf("%d", &n1);
    printf("Digite o segundo numero competidor:\n");
    scanf("%d", &n2);

    while (n1 >= 0 || n2 >= 0)
    {

        if (n1 > n2)
        {
            printf("O numero %d e o vencedor!\n", n1);
            if (n1 > maior)
            {
                maior = n1;
            }
        }
        else if (n2 > n1)
        {
            printf("O numero %d e o vencedor!\n", n2);
            if (n1 > maior)
            {
                maior = n2;
            }
        }
        else
        {
            printf("Os numeros sao iguais!\n");
            if (n1 > maior)
            {
                maior = n1;
            }
        }
        printf("Digite o primeiro numero competidor:\n");
        scanf("%d", &n1);
        printf("Digite o segundo numero competidor:\n");
        scanf("%d", &n2);
    }

    printf("Numero maior de todos:%d", maior);
}