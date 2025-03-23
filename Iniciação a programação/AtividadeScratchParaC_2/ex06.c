#include <stdio.h>
int main(void)
{
    float n, total, i;
    printf("Digite um numero (-1 para terminar):\n");
    scanf("%f", &n);
    while (-1 != n)
    {
        total += n;
        printf("Digite um numero (-1 para terminar):\n");
        scanf("%f", &n);
        i++;
    }
    total = total / i;
    printf("A media dos numeros fornecidos = %.2f", total);
    return 0;
}
