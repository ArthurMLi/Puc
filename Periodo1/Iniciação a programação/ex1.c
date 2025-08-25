#include <stdio.h>
int main(void)
{
    int n, j, m, lista[100], facil, dificil = -1;
    printf("Digite a quantidade de desafios:\n");
    scanf("%d", &n);
    for (int i = 1; i <= n; i++)
    {
        j = 1;
        printf("Digite o %d elemento do %d desafio:\n", j, i);
        scanf("%d", &m);
        while (m > 0)
        {
            if (dificil < m)
            {
                dificil = m;
            }
            if (facil > m)
            {
                facil = m;
            }
            
            lista[j - 1] = m;
            printf("Digite o %d elemento do %d desafio:\n", j, i);
            scanf("%d", &m);
            j++;
        }

        if (j > 1)
        {
            printf("Elementos do %d desafio:", i);
            for (int i = 0; i < j - 1; i++)
            {
                if (i == j - 2)
                {
                    printf("%d.\n", lista[i]);
                }
                else
                {
                    printf("%d,", lista[i]);
                }
            }
        }
    }

    return 0;
}
