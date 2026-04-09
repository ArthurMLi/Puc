#include <stdio.h>
/*Autor: Arthur Mendes Lima*/
int main(void)
{
    int numeroAmigos, numeroBebida, quantidadePorBebida, numeroLimao, numeroFatiaLimao, gramasSal, quantidadeBebidaPorPessoa, quantidadeSalPorPessoa; /* Variáveis lidas*/
    int quantidadeLimaoTotal, temp, bebidaP, salP;                                                                                                    /* Outras variáveis*/
    scanf("%d %d %d %d %d %d %d %d", &numeroAmigos, &numeroBebida, &quantidadePorBebida, &numeroLimao, &numeroFatiaLimao, &gramasSal, &quantidadeBebidaPorPessoa, &quantidadeSalPorPessoa);
    /*calculo da quantidade total de limoes*/
    quantidadeLimaoTotal = numeroFatiaLimao * numeroLimao;
    /*calculo da quantidade das bebidas, sal e limao para cada pessoa*/

    bebidaP = (quantidadePorBebida * numeroBebida) / (quantidadeBebidaPorPessoa * numeroAmigos);
    salP = gramasSal / (quantidadeSalPorPessoa * numeroAmigos);
    temp = quantidadeLimaoTotal / numeroAmigos;
    /*verificar qual a menor é a quantidade para cada, pois ela que ira limitar a quantidade de torradas para cada um*/
    if (temp > bebidaP)
    {
        temp = bebidaP;
    }
    if (temp > salP)
    {
        temp = salP;
    }

    printf("%d \n", temp);

    return 0;
}