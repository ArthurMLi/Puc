#include <stdio.h>
#include <string.h>
//Nome: Arthur Mendes Lima

int main()
{
    char guest[101], host[101], pile[201];
    int count[26] = {0};      // Para guest + host
    int count_pile[26] = {0}; // Para pile

    scanf("%s", guest);
    scanf("%s", host);
    scanf("%s", pile);

    // Contar letras de guest
    for (int i = 0; guest[i]; i++)
        count[guest[i] - 'A']++;

    // Contar letras de host
    for (int i = 0; host[i]; i++)
        count[host[i] - 'A']++;

    // Contar letras do monte (pile)
    for (int i = 0; pile[i]; i++)
        count_pile[pile[i] - 'A']++;

    // Comparar frequências
    for (int i = 0; i < 26; i++)
    {
        if (count[i] != count_pile[i])
        {
            printf("NO\n");
            return 0;
        }
    }

    printf("YES\n");
    return 0;
}
