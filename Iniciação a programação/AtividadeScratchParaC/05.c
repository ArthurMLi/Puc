#include <stdio.h>
int main(void)
{
    float n1, n2, n3, r;

    printf("Digite o 1 Numero:\n");
    scanf("%f", &n1);
    printf("Digite o 2 Numero:\n");
    scanf("%f", &n2);
    printf("Digite o 3 Numero:\n");
    scanf("%f", &n3);

    r = (n1 + n2 + n3) / 3;
    
    printf("A media dos valores %.1f,%.1f e %.1f = %.3f", n1, n2, n3, r);
}