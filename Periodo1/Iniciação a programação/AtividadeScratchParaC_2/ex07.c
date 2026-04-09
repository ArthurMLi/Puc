#include <stdio.h>
int main(void)
{
    int n, a = 0, b = 1, temp = -1, total, i;
    printf("Digite um numero maior que 2:\n");
    scanf("%d", &n);
    while (n <= 2)
    {
        printf("Numero invalido! Deve ser maior que 2.\n");
        printf("Digite um numero maior que 2:\n");
        scanf("%d", &n);
    }
    printf("A sequencia de Fibonaacci ate o %d termo: ", n);

    for (i = 0; i < n; i++)
    {
        if (temp == -1)
        {
            printf("1");
            temp = 0;
        }
        else
        {

            temp = a + b;
            a = b;
            b = temp;
            printf(",%d", temp);
        }
    }
    return 0;
}
