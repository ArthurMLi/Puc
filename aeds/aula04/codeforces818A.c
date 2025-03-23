#include <stdio.h>

/* Autor: Arthur Mendes Lima */

int main(void)
{
    /* iniciação de variavel unsigned long long int pra conseguir receber o numero: 10¹² */
    unsigned long long int n, k, certificado, diploma, nwinners;
    scanf("%llu %llu", &n, &k);

    /* checar se a proporção de vitoriosos é maior que a quantidade de alunos */  
    if (n <= k)
    {
        /* caso for maior e n ter vitoriosos */
        nwinners = n;

        certificado = 0;

        diploma = 0;
    }
    else
    {
        /* caso for menor e tiver vitoriosos */
        nwinners = n / 2;
        certificado = nwinners / (k + 1);
        diploma = nwinners - certificado;
    }

    printf("%llu %llu %llu ", certificado, diploma, nwinners);
    return 0;
}
