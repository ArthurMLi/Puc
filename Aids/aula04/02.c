#include <stdio.h>
int main(void)
{

    unsigned long long int n, k, certificado, diploma, winners;
    scanf("%llu %llu", &n, &k);

    if (n <= k)
    {
        winners = n;

        certificado = 0;

        diploma = 0;
    }
    else
    {
        winners = n / 2;
        certificado = winners / (k + 1);
        diploma = winners - certificado;
    }

    printf("%llu %llu %llu ", certificado, diploma, winners);
    return 0;
}