#include <stdio.h>
/*Autor: Arthur Mendes Lima*/

int main(void)
{
    int n, frente, r1, r2, r3, r4;
    scanf("%d", &n);
    for (int i = 0; i < n; i++)
    {
        /*variavel frente para guardar a quantidade de pessoas na frente*/
        frente = 0;
        scanf("%d %d %d %d", &r1, &r2, &r3, &r4);
        /*testar quantas pessoas estão a frente do Timur e guradar na varioavel frente*/
        if (r2 > r1)
        {
            frente++;
        }
        if (r3 > r1)
        {
            frente++;
        }
        if (r4 > r1)
        {
            frente++;
        }
        printf("%d\n", frente);
    }
    return 0;
}