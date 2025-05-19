#include <stdio.h>
int main()
{
//Nome: Arthur Mendes Lima

    int n, qntA = 0, qntD = 0; // n: número de jogos, qntA e qntD: contadores de vitórias
    char str[100001]; // string com o resultado de cada jogo (A para Anton, D para Danik)

    scanf("%d %s", &n, &str); // Lê o número de jogos e a string com os resultados

    // Conta quantas vitórias cada jogador teve
    for (int i = 0; i < n; i++)
    {
        if (str[i] == 'A')
            qntA++;
        else if (str[i] == 'D')
            qntD++;
    }

    // Compara as vitórias e imprime o vencedor ou empate
    if (qntA > qntD)
        printf("Anton");
    else if (qntA < qntD)
        printf("Danik");
    else
        printf("Friendship");

    return 0;
}