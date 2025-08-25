#include <stdio.h>
#include <stdlib.h>

int verificarBonito(int **m1)
{
    for (int i = 0; i < 4; i++)
    {

        if (m1[0][0] < m1[0][1] && m1[0][0] < m1[1][0] && m1[0][1] < m1[1][1] && m1[1][0] < m1[1][1])
        {
            return 1;
        }
        else
        {
            int temp;
            temp = m1[0][1];
            m1[0][1] = m1[1][1];
            m1[1][1] = m1[1][0];
            m1[1][0] = m1[0][0];
            m1[0][0] = temp;
        }
    }
    return 0;
}

int main()
{
    int n;
    int **m1;
    m1 = malloc(2 * sizeof(int *));
    for (int i = 0; i < 2; i++)
    {
        m1[i] = malloc(2 * sizeof(int));
    }

    scanf("%d", &n);

    for (int m = 0; m < n; m++)
    {
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                scanf("%d", &m1[i][j]);
            }
        }

        if (verificarBonito(m1) == 0)
        {
            printf("NO\n");
        }
        else
        {
            printf("YES\n");
        }
    }
}
