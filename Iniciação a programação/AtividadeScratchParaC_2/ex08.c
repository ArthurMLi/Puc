#include <stdio.h>
int main(void)
{
    int n, total, i;
    printf("Digite um numero para ver sua tabuada:\n");
    scanf("%d", &n);

    for (i = 1; i <= 10; i++)
    {
        total = n*i;
        printf(" %d x  %d =   %d\n",n,i,total);
    }
    return 0;
}
