#include <stdio.h>
int main(void)
{
    float altura,raio,bazinga;

    printf("Digite o valor da altura:\n");
    scanf("%f",&altura);
    printf("Digite o valor do raio:\n");
    scanf("%f",&raio);
    bazinga = 3.14159 * raio * raio * altura;
    printf("O Volume do cilindro:%.3f",bazinga);

}