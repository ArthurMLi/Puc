#include <stdio.h>
#include <string.h>
#include <ctype.h>

int soVogais(char s[])
{
    for (int i = 0; s[i] != '\0'; i++)
    {
        char c = tolower(s[i]);
        if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u')
            return 0;
    }
    return 1;
}

int soConsoantes(char s[])
{
    for (int i = 0; s[i] != '\0'; i++)
    {
        char c = tolower(s[i]);
        if (!(c >= 'a' && c <= 'z'))
            return 0; // só letras
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
            return 0;
    }
    return 1;
}

int inteiro(char s[])
{
    int i = 0;
    if (s[0] == '+' || s[0] == '-')
        i++;
    for (; s[i] != '\0'; i++)
    {
        if (!isdigit(s[i]))
            return 0;
    }
    return 1;
}

int real(char s[])
{
    int i = 0, sep = 0;
    if (s[0] == '+' || s[0] == '-')
        i++;
    for (; s[i] != '\0'; i++)
    {
        if (s[i] == '.' || s[i] == ',')
        {
            if (sep)
                return 0;
            sep = 1;
        }
        else if (!isdigit(s[i]))
            return 0;
    }
    return sep;
}

int main()
{
    char linha[1000];

    while (fgets(linha, sizeof(linha), stdin))
    {
        linha[strcspn(linha, "\n")] = '\0'; // remove quebra de linha
        if (strcmp(linha, "FIM") == 0)
            break;

        printf("%s %s %s %s\n",
               soVogais(linha) ? "SIM" : "NAO",
               soConsoantes(linha) ? "SIM" : "NAO",
               inteiro(linha) ? "SIM" : "NAO",
               real(linha) ? "SIM" : "NAO");
    }
    return 0;
}
