#include <stdio.h>
int main(void)
{
    int n, total = 0;
    printf("Digite um numero (-1) para ternimar:\n");
    scanf("%d", &n);
    while (-1 != n)
    {
        total += n;

        printf("Digite um numero (-1) para ternimar:\n");
        scanf("%d", &n);
    }
    printf("A soma dos numeros fornecidos = %d", total);
    return 0;
}
