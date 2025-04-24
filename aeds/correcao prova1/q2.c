#include <stdio.h>
int main(void)
{
    int i, n, npares =0, nimpares=0, resultado; //inicializar com 0
    scanf("%d", &n);
    while (n != 0)
    {
        i = 9; // ponto e virgula
        while ((n - i) % 10 != 0)
        {
            i--;
        }
        n = (n - i) / 10;
        if (i % 2 == 0)
        {
            if (npares == 0)
            {
                npares = 1;
            }
            npares *= i;
        }
        else if (nimpares == 0)
        {
            if (nimpares == 0)
            {
                nimpares = 1;
            }
            nimpares *= i;
        }
    }
    resultado = npares + nimpares;
    printf("%d", resultado);
    return 1;
}
