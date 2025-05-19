#include <stdio.h>
//Nome: Arthur Mendes Lima

int main()
{
    int n;
    scanf("%d", &n);

    char str[100001];

    for (int i = 0; i < n; i++)
    {
        scanf("%s", str);

        int coluna = 0;
        int j = 0, caso = 1, temp = 0;

        // Verifica o formato da string
        while (str[j] != '\0')
        {
            if (str[j] >= '0' && str[j] <= '9')
            {
                temp = 1;
            }

            if (str[j] >= 'A' && str[j] <= 'Z')
            {
                if (temp)
                {
                    caso = 2;
                }
            }

            j++;
        }

        if (caso == 1)
        {
            // Caso 1: letras+número (tipo "ABD815") → "R815C732"
            int row = 0;
            j = 0;

            // Parte 1: calcular a coluna (letras → número)
            while (str[j] >= 'A' && str[j] <= 'Z')
            {
                coluna = coluna * 26 + (str[j] - 'A' + 1);
                j++;
            }

            // Parte 2: pegar a linha (número após letras)
            while (str[j] >= '0' && str[j] <= '9')
            {
                row = row * 10 + (str[j] - '0');
                j++;
            }

            printf("R%dC%d\n", row, coluna);
        }
        else
        {
            // Caso 2: "R23C55" → "BC23"
            int row = 0, col = 0;
            int k = 1;

            // Extrai número da linha
            while (str[k] >= '0' && str[k] <= '9')
            {
                row = row * 10 + (str[k] - '0');
                k++;
            }

            k++; // pula o 'C'

            // Extrai número da coluna
            while (str[k] >= '0' && str[k] <= '9')
            {
                col = col * 10 + (str[k] - '0');
                k++;
            }

            // Converte número da coluna para letras
            char colStr[20];
            int idx = 0;

            while (col > 0)
            {
                col--;
                colStr[idx++] = 'A' + (col % 26);
                col /= 26;
            }

            // imprime em ordem reversa
            for (int m = idx - 1; m >= 0; m--)
            {
                printf("%c", colStr[m]);
            }

            printf("%d\n", row);
        }
    }

    return 0;
}
