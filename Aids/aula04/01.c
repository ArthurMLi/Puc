#include <stdio.h>
int main(void)
{
    int a1, a2, a3, a4, a5, n, i, saida1 = 1, saida2 = 1, saida3 = 1;

    scanf("%d", &n);
    i = 0;
    while (i < n)
    {
        i++;
        scanf("%d %d %d %d", &a1, &a2, &a4, &a5);
        a3 = a1 + a2;
        if (a4 == a2 + a3)
        {
            saida1++;
        }
        if (a5 == a3 + a4)
        {
            saida1++;
        }

        a3 = a4 - a2;
        if (a3 == a2 + a1)
        {
            saida2++;
        }
        if (a5 == a3 + a4)
        {
            saida2++;
        }

        a3 = a5 - a4;
        if (a3 == a2 + a1)
        {
            saida3++;
        }
        if (a4 == a3 + a2)
        {
            saida3++;
                
        }

        if (saida1 < saida2)
        {
            saida1 = saida2;
        }
        if (saida1 < saida3)
        {
            saida1 = saida3;
        }
    printf("%d\n", saida1);
    }
    return 1;
}
