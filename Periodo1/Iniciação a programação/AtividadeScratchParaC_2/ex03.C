#include <stdio.h>
int main(void)
{
    int n, total = 0, i = 1;
    printf("Digite um numero:\n");
    scanf("%d", &n);
    while (i <= n)
    {
        total += i;
        i++;
    }
    printf("A soma de todos os numeros de 1 ate %d = %d", n, total);
    return 0;
}
