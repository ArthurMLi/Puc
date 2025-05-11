#include <stdio.h>

int main(void)
{
    int n1, n2, dado = 6;
    scanf("%d", &n1);
    scanf("%d", &n2);
    if (n1 <= n2)
    {
        n1 = 6-(n2 - 1);
    }
    else
    {
        n1 = 6-(n1 - 1);
    }
    if (n1 == 0)
    {
        printf("1/1");
    }else{

    while (n1 % 2 == 0 && dado %2 == 0)
    {
        n1 /= 2;
        dado /= 2;
    }
    while (n1 % 3 == 0 && dado %3 == 0)
    {
        n1 /= 3;
        dado /= 3;
    }

    printf("%d/%d", n1, dado);
}}