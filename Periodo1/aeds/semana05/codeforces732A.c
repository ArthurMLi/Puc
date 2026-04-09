#include <stdio.h>
/*Autor: Arthur Mendes Lima*/

int main(void)
{
    /*inicializando a variavel quant como 1 para representar o minimo de pás */
    int k, r, quant = 1;
    scanf("%d %d", &k, &r);
    /*while irá aumentara quantidade de pás até a quantidade de pás atual
    até ser o minimo suficiente para comprar usando só moedas de 10((k * quant) % 10 != 0)
    ou usando uma moeda de valor personalizado((k * quant) - r) % 10 != 0)*/
    while ((k * quant) % 10 != 0 && ((k * quant) - r) % 10 != 0)
    {
        quant++;
    }
    printf("%d", quant);
    return 0;
}