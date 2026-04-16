#include <stdio.h>

int main()
{
    char s1[50], s2[50];
    s2[0] = '\0';

    char fim[4] = "FIM";
    int condicaoDeParada = 0;

    for (fgets(s1, sizeof(s1), stdin); condicaoDeParada != 1; fgets(s1, sizeof(s1), stdin))
    {
        int len = 0;
        while (s1[len] != '\n' && s1[len] != '\r' && s1[len] != '\0')
        {
            len++;
        }
        if (s1[len] == '\n' || s1[len] == '\r')
        {
            s1[len] = '\0';
        }
        int temp = 0;
        for (int i = 0; i < 3; i++)
        {
            if (s1[i] == fim[i])
            {
                temp++;
            }
        }
        if (temp == 3 && s1[3] == '\0')
        {
            break;
        }
        else
        {
            for (int i = 0; i < len; i++)
            {
                s2[i] = s1[len - 1 - i];
            }
            s2[len] = '\0';
            printf("%s\n", s2);
            s2[0] = '\0';
        }
    }
    return 0;
}
