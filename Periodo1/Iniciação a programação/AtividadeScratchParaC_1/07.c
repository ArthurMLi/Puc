#include <stdio.h>
int main(void)
{
    int t1, t2, n, soma;

    printf("Digite o valor do 1 termo:\n");
    scanf("%d", &t1);
    printf("Digite o valor do 2 termo:\n");
    scanf("%d", &t2);
    printf("Digite o numero de termos:\n");
    scanf("%d", &n);

    soma = n * (t1 + (t1 + (n - 1) * (t2 - t1))) / 2;

    printf("Soma = %d", soma);
}