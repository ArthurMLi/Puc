#include <stdio.h>

int main()
{
    int len2 = 26;
    int s2[len2];
    char s1[50],s3[4]="FIM";
    while (fgets(s1, sizeof(s1), stdin) != NULL)
    {
        int condicaoParada = 0;
        int len1 = 0, maior = 0, cont = 0,temp = 0;
        for (int i = 0; i < 3; i++)
        {
            if (s1[i] == s3[i])
            {
                condicaoParada++;
            }
        }
        if (condicaoParada)
        {
            condicaoParada = 0;
            break;
        }
        while (s1[len1] != '\n' && s1[len1] != '\0')
        {
            len1++;
        }
        for (int i = 0; i < len1; i++) // i=0 j=0
        {
            for (int j = i; j < len1 - cont + 1; j++)
            {
                printf("%c", s1[j]);
                if (s2[s1[j] - 'a'] != 1)
                {
                    temp++;
                    s2[s1[j] - 'a'] = 1;
                }
                else
                {
                    cont++;
                    break;
                }
            }
            if (temp > maior)
            {
                maior = temp;
            }
            
            for (int i = 0; i < 26; i++)
            {
                s2[i] = '\0';
            }
            temp=0;
        }

        printf("%d\n", maior);
    }
    return 0;
}
