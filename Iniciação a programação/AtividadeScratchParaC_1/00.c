#include <stdio.h>
int main(void)
{
    int v1, v2, soma;

    printf("Digite o 1 valor:\n");
    scanf("%d", &v1);
    printf("Digite o 2 valor:\n");
    scanf("%d", &v2);
    soma = v1 + v2;

    printf("Soma = %d", soma);
}