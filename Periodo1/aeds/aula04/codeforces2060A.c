#include <stdio.h>

/* Autor: Arthur Mendes Lima */

int main(void)
{
    /* inicializando as variaveis sendo as saidas com 1 para evitar if's redundante perante o codigo */
    int a1, a2, a3, a4, a5, n, i, saida1 = 1, saida2 = 1, saida3 = 1;

    scanf("%d", &n);
    i = 0;
    while (i < n)
    {
        saida1 = 1;
        saida2 = 1;
        saida3 = 1;
        i++;
        scanf("%d %d %d %d", &a1, &a2, &a4, &a5);
        /* testando caso 1 */
        a3 = a1 + a2;
        if (a4 == a2 + a3)
        {
            saida1++;
        }
        if (a5 == a3 + a4)
        {
            saida1++;
        }
        /* testando caso 2 */

        a3 = a4 - a2;
        if (a3 == a2 + a1)
        {
            saida2++;
        }
        if (a5 == a3 + a4)
        {
            saida2++;
        }
        /* testando caso 3 */

        a3 = a5 - a4;
        if (a3 == a2 + a1)
        {
            saida3++;
        }
        if (a4 == a3 + a2)
        {
            saida3++;
        }

        /* checando qual o caso com maior Fibonacciness */
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
    return 0;
}
