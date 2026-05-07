#include <stdio.h>

int main()
{
    char s1[50], s2[50];
    while (1)
    {
        int leitura = scanf("%s", s1);
        if (leitura == EOF)
        {
            break;
        }
        if (s1[0] == 'F' && s1[1] == 'I' && s1[2] == 'M' && s1[3] == '\0')
        {
            break;
        }
        if (scanf("%s", s2) == EOF)
        {
            break;
        }
        int len1 = 0, len2 = 0;
        int resp = 0;
        int letras[256];
        while (s1[len1] != '\n' && s1[len1] != '\0')
        {
            len1++;
        }
        
        while (s2[len2] != '\n' && s2[len2] != '\0')
        {
            len2++;
        }
        
        if (len1 != len2)
        {

            resp = 1;
        }
        else
        {
            for (int i = 0; i < len1; i++)
            {
                if (s1[i] >= 'a')
                {
                    s1[i] = s1[i] - 'a' + 'A';
                }

                if (s2[i] >= 'a')
                {
                    s2[i] = s2[i] - 'a' + 'A';
                }

            }

            for (int i = 0; i < 256; i++)
            {
                letras[i] = 0;
            }

            for (int i = 0; i < len1; i++)
            {
                letras[(char)s1[i]]++;
                letras[(char)s2[i]]--;
            }

            for (int i = 0; i < 256; i++)
            {
                if (letras[i] != 0)
                {
                    resp = 1;
                    break;
                }
            }
        }

        printf("%s\n", resp ? "NAO" : "SIM");


    }
    return 0;
}
