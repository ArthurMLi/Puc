#include <stdio.h>
int main(void)
{
    int n, n2, i;
    printf("Digite o valor inicial:\n");
    scanf("%d", &n);
    if (n % 2 == 0)
    {
        n++;
    }
    while (i < 10)
    {
        printf("%d,", n);
        n = n + 2;
        i++;
    }
    return 0;
}
