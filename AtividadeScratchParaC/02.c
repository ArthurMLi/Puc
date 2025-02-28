#include <stdio.h>
int main(void){
    float base,altura,area;
    
    //printf("Tarefa - Ex02. Desafio:Calculadora de Area de Triangulos");
    
    printf("Digite o valor da base:\n");
    scanf("%f",&base);
    
    printf("Digite o valor da altura:\n");
    scanf("%f",&altura);
    
    area = (base * altura) / 2;
    
    printf("Area do triangulo = %.2f",area);

    return 1;}