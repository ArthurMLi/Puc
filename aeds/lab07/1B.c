#include <stdio.h>

int main()
{
    int n;
    int coluna, qntLetra;
    scanf("%d", &n);

    char str[100001];

    for (int i = 0; i < n; i++)
    {
        scanf("%s", str);
        coluna = 0, qntLetra = 0;
        int j = 0, caso = 1, temp = 0;

        // Verifica se há dígito seguido por letra maiúscula
        while (str[j] != '\0')
        {
            if (str[j] >= '0' && str[j] <= '9')
            {
                temp = 1;
            }

            if (str[j] >= 'A' && str[j] <= 'Z')
            {
                qntLetra++;
                if (temp)
                {

                    caso = 2; // número seguido de letra
                    
                }
            }
            j++;
        }

        // Se for caso 1, faz o cálculo
        if (caso == 1)
        {
            j = 0;

            printf("R");
            while (str[j] != '\0')
            {
                if (str[j] >= '0' && str[j] <= '9')
                {
                    printf("%c", str[j]);
                }
                else
                {
                    if (qntLetra == 1)
                    {
                        coluna += (str[j] - 'A' + 1); // A = 1, B = 2, ...
                    }
                    else
                    {

                        coluna += ((qntLetra - 1) * 26) * (str[j] - 'A' + 1); // A = 1, B = 2, ...
                    }
                }
                qntLetra--;
                j++;
            }

            printf("C%d\n", coluna);
        }
    }

    return 0;
}
