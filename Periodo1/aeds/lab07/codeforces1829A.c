#include <stdio.h>
//Nome: Arthur Mendes Lima
int main()
{
    int n, r;
    char str[11], code[11] = "codeforces"; // Palavra de referência com 10 caracteres

    scanf("%d", &n); // Lê o número de casos de teste

    for (int i = 0; i < n; i++)
    {
        scanf("%s", str); // Lê a string de entrada
        r = 0;

        // Conta quantas posições diferem de "codeforces"
        for (int j = 0; j < 10; j++)
        {
            if (str[j] != code[j])
            {
                r++;
            }
        }

        printf("%d\n", r); // Imprime o número de diferenças
    }

    return 0;
}
