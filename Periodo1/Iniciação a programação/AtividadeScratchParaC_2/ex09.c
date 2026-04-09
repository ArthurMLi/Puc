#include <stdio.h>
int main(void)
{
    int n, total, i, j;
    printf("Digite a altura do triangulo de estrelas:\n");
    scanf("%d", &n);

    for (i = 1; i <= n; i++)
    {
        j = 0;
        while (j != i)
        {
            printf("*");
            j++;
        }
        printf("\n");
    }
    return 0;
}
