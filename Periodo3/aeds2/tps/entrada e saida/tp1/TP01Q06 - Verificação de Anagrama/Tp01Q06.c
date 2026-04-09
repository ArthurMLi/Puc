#include <stdio.h>

int main()
{
    char s1[50], s2[50];
    while (scanf("%s %s", s1, s2) != EOF)
    {
        int len1 = 0, len2 = 0;
        int resp = 0;
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
            
            for (int i = 0; i < len1 / 2; i++)
            {
                
                if (s1[i] != s2[len2 - 1 - i])
                {
                    resp = 1;
                }
            }
        }

        printf("%s\n", resp ? "NAO" : "SIM");


    }
    return 0;
}
