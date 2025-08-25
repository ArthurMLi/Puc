#include <stdio.h>
#include <math.h>

/*Autor: Arthur Mendes Lima
Função recursiva que calcula a probabilidade de alcançar a posição alvo.
  - idx: Índice da pergunta atual
  - alvo: Posição que queremos alcançar
  - atual: Posição atual
  - perguntas: Número de perguntas
*/ 
double probabilidade(int idx, int alvo, int atual, int perguntas)
{
    /* Se todas as perguntas foram processadas */
    if (idx == perguntas)
    {
        if (atual == alvo)
        {
            return 1.0;
        }
        else
        {
            return 0.0;
        }
    }

    /* Se encontrarmos '?' temos duas opções: '+' ou '-' */
    double prob = 0.0;
    prob += probabilidade(idx + 1, alvo, atual + 1, perguntas); /* Escolhendo '+' */
    prob += probabilidade(idx + 1, alvo, atual - 1, perguntas); /* Escolhendo '-' */

    return prob / 2.0; /* duas opções igualmente prováveis, dividimos por 2 */
}

int main()
{
    char s1[11], s2[11];
    int posicao_alvo = 0, posicao_atual = 0, num_perguntas = 0;
    scanf("%s", s1);
    scanf("%s", s2);

    /* Calcula a posição alvo a partir de s1 uso de '\0'para verificação de fim da string */
    for (int i = 0; s1[i] != '\0'; i++)
    {
        if (s1[i] == '+')
            posicao_alvo++;
        if (s1[i] == '-')
            posicao_alvo--;
    }

    /* Calcula a posição atual de s2 e conta as "pergunta" uso de '\0'para verificação de fim da string*/
    for (int i = 0; s2[i] != '\0'; i++)
    {
        if (s2[i] == '+')
            posicao_atual++;
        if (s2[i] == '-')
            posicao_atual--;
        if (s2[i] == '?')
            num_perguntas++;
    }

    /* Ajusta a posição alvo com base na posição atual */
    posicao_alvo -= posicao_atual;

    /* Verifica se é possivel a posição alvo ser alcançada com o número de perguntas disponíveis */
    if ((posicao_alvo + num_perguntas) % 2 != 0 || posicao_alvo < -num_perguntas || posicao_alvo > num_perguntas)
    {
        /* quando n é possivel a probabilidade é 0 */
        printf("0.000000000000\n");
    }
    else
    {
        /* Calcula quantos '+' são necessários para alcançar a posição alvo */
        int x = (num_perguntas + posicao_alvo) / 2;
        if (x < 0 || x > num_perguntas)
        {
            printf("0.000000000000\n");
        }
        else
        {
            /* Calcula a probabilidade */
            double resultado = probabilidade(0, posicao_alvo, 0, num_perguntas);
            printf("%.12f\n", resultado);
        }
    }

    return 0;
}
