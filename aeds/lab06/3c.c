#include <stdio.h>
#include <stdlib.h>

int main()
{
    int qntx = 0, qnt0 = 0, forA = 0, draw;
    char **m1, temp;
    m1 = malloc(3 * sizeof(char *));
    for (int i = 0; i < 3; i++)
    {
        m1[i] = malloc(3 * sizeof(char));
    }

    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            scanf(" %c", &temp);
            m1[i][j] = temp;

            if (temp == 'X')
            {
                qntx++;
            }
            else
            {
                if (temp == '0')
                {
                    qnt0++;
                }
                else
                {
                    draw++;
                }
            }
        }
    }

    if (qntx > qnt0 + 1 || qntx < qnt0)
    {
        printf("illegal");
    }
    else
    {
        for (int i = 0; i < 3; i++)
        {
            if ((m1[i][0] == m1[i][1] && m1[i][0] == m1[i][2] && m1[i][0] == 'X') || (m1[0][i] == m1[1][i] && m1[0][i] == m1[2][i] && m1[0][i] == 'X') || (m1[0][0] == m1[1][1] && m1[0][0] == m1[2][2] && m1[0][0] == 'X') || (m1[2][0] == m1[1][1] && m1[2][0] == m1[0][2] && m1[2][0] == 'X'))
            {
                printf("the first player won");
                forA++;
            }
            else
            {
                if ((m1[i][0] == m1[i][1] && m1[i][0] == m1[i][2] && m1[i][0] == '0') || (m1[0][i] == m1[1][i] && m1[0][i] == m1[2][i] && m1[0][i] == '0') || (m1[0][0] == m1[1][1] && m1[0][0] == m1[2][2] && m1[0][0] == '0') || (m1[2][0] == m1[1][1] && m1[2][0] == m1[0][2] && m1[2][0] == '0'))
                {
                    printf("the second player won");
                    forA++;
                }
            }
        }
        if (forA > 1)
        {
            printf("illegal");
        }
        else
        {
            if (forA == 0)
            {
                if (draw == 0)
                {
                    printf("draw");
                }
                else
                {

                    if (qntx == qnt0)
                    {
                        printf("first");
                    }
                    else
                    {
                        printf("second");
                    }
                }
            }
        }
    }
}

