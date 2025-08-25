#include <stdio.h>

/*
Autor: Arthur Mendes Lima
  Função para contar números que tem apenas os dígitos '0' e '1'.
 */
int contar_numeros(long long numero, long long limite)
{
    if (numero > limite)
        return 0;  /* Se ultrapassar o limite, retorna 0*/
    return 1 + contar_numeros(numero * 10, limite) + contar_numeros(numero * 10 + 1, limite);
     /* Conta e continua gerando números*/
}

int main()
{
    long long limite;
    scanf("%lld", &limite);

    /* Chama a função para contar números válidos começando de 1 */
    int resultado = contar_numeros(1, limite);

    printf("%d\n", resultado);
    return 0;
}
