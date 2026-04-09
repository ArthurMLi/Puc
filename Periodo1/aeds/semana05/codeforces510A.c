#include <stdio.h>
/*Autor: Arthur Mendes Lima*/

int main(void)
{
    int n, m;
    scanf("%d %d", &n, &m);

    for (int i = 1; i <= n; i++)
    {
        /* Se a linha for múltipla de 4(quando a cobrinha sobe pela esquerda), imprime "#" na primeira posição e "." nas demais */
        if ((i % 4) == 0)
        {
            printf("#");
            for (int j = 1; j < m; j++)
            {
                printf(".");
            }
            printf("\n");
        }
        else
            /* Se a linha for par (mas não múltipla de 4)(quando a cobrinha sobe pela direita), imprime "." nas primeiras posições e "#" na última */
            if ((i % 2) == 0)
            {
                for (int j = 1; j < m; j++)
                {
                    printf(".");
                }
                printf("#\n");
            }
            else
                /* Se a linha for ímpar(quando nao está subindo), imprime "#" em todas as posições */
                for (int j = 1; j <= m; j++)
                {
                    printf("#");
                    if (j == m)
                    {
                        printf("\n");
                    }
                }
    }
    return 0;
}
