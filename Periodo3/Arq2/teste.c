#include <stdio.h>
#include <stdlib.h>


int main()
{

    int preco, tamx, tamy, n, quant, pagar;
    int x, y, z, w;
    char comando;
    scanf("%d %d %d", &tamx, &tamy,&preco);
    scanf(" %d", &n);

    int **array = (int**) malloc(sizeof(int *) * tamx);
    for (int i = 0; i < tamx; i++)
    {
        array[i] = (int*)malloc(sizeof(int) * tamy);
        for (int j = 0; j < tamy; j++)
        {
            array[i][j] = 0;
        }
    }

    for (int i = 0; (i < n); i++)
    {
        scanf(" %c ", &comando);
        if (comando == 'A')
        {
            scanf("%d %d %d", &quant, &x, &y);
            array[x][y] += quant;
        }
        else
        {
            if (comando == 'P')
            {

                scanf("%d %d %d %d", &x, &y, &z, &w);
                pagar = 0;
                if (x > z)
                {
                    int temp = x;
                    x=z;
                    z=temp;
                }
                if (y> w)
                {
                    int temp = y;
                    y=w;
                    w=temp;
                }
                
                for (int j = x; j <= z; j++)
                {
                    for (int k = y; k <= w; k++)
                    {
                        pagar += array[j][k]*preco;
                    }
                }
                printf("%d\n", pagar);
            }
            else
            {
                break;
            }
        }
    }

    return 0;
}