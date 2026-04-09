

/* Autor: Arthur Mendes Lima */

#include <stdio.h>

int main() {
    long long n, k;
    scanf("%lld %lld", &n, &k);

    // Calcula o número máximo de diplomas possível
    long long d = n / (2 * (k + 1));
    long long c = k * d;
    long long nw = n - d - c;

    printf("%lld %lld %lld\n", d, c, nw);
    return 0;
}
