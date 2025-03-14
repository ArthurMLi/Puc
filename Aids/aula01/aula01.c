/*
  *Lab 01
  *Autor: Arthur Mendes
*/
#include <stdio.h>
    
int main (void){
/*Parte 1*/
    printf("Parte 1:\nPrint o nome\n");
    printf("Olá Mundo!\n");
    printf("Arthur Mendes\n");
    
/*Parte 2*/
    printf("//\nParte 2:\nPrint idade digitada\n");
    int idade;
    
    scanf("%d%*c",&idade);
    printf("Tenho %d anos\n",idade);
    
/*Parte 3*/
    printf("//\nParte 3:\nPrint soma de numeros digitados\n");
    int x;
    int y;
    int z;
    
    scanf("%d%*c",&x);
    scanf("%d%*c",&y);
    z=x+y;
    printf("o resultado é %d\n",z);

    /*Parte 4*/
    printf("//\nParte 4:\nPrint subtração, multiplicação e divisao de numeros digitados\n");
    float n1;
    float n2;
    scanf("%f%*c",&n1);
    scanf("%f%*c",&n2);
    float resultadoT;
    resultadoT = n1 - n2;
    printf("o resultado da subtração é %.0f\n",resultadoT);
    resultadoT = n1 * n2;
    printf("o resultado da multiplicação é %.0f\n",resultadoT);
    resultadoT = n1 / n2;
    printf("o resultado da divisão é %.2f\n",resultadoT);
    
    return 0;
    }