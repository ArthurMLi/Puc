#include <stdio.h>
int main(void)
{

    int n, n2, i;
    while (i < 5)
    {
        i++;
        printf("Digite um numero:\n");
        scanf("%d", &n);
        n2 = n * 3;
        printf("O tripo de %d = %d\n", n, n2);
    }

    return 0;
}
