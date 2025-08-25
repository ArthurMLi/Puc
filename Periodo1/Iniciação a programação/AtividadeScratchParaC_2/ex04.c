#include <stdio.h>
int main(void)
{
    int n, total = 1, i = 1;
    printf("Digite um numero:\n");
    scanf("%d", &n);
    while (i <= n)
    {
        total *= i;
        i++;
    }
    printf("O fatorial de %d = %d", n, total);
    return 0;
}
