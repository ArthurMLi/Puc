#include <stdio.h>
#include <string.h>

int isVogal(char c)
{
    return (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
}

int soVogais(char s[], int i)
{
    if (s[i] == '\0')
        return (i > 0);
    if (!isVogal(s[i]))
        return 0;
    return soVogais(s, i + 1);
}

int soConsoantes(char s[], int i)
{
    if (s[i] == '\0')
        return (i > 0);
    char c = s[i];
    int letraMaiuscula = (c >= 'A' && c <= 'Z');
    int letraMinuscula = (c >= 'a' && c <= 'z');
    if (!(letraMaiuscula || letraMinuscula))
        return 0;
    if (isVogal(c))
        return 0;
    return soConsoantes(s, i + 1);
}

int inteiro(char s[], int i, int started)
{
    if (s[i] == '\0')
        return started; // precisa ter ao menos 1 dígito
    if (i == 0 && (s[i] == '+' || s[i] == '-'))
    {
        return inteiro(s, i + 1, 0);
    }
    if (!(s[i] >= '0' && s[i] <= '9'))
        return 0;
    return inteiro(s, i + 1, 1);
}

int real(char s[], int i, int started, int ponto)
{
    if (s[i] == '\0')
        return started;
    if (i == 0 && (s[i] == '+' || s[i] == '-'))
    {
        return real(s, i + 1, 0, ponto);
    }
    if (s[i] == '.' || s[i] == ',')
    {
        if (ponto)
            return 0; // já tinha ponto antes
        return real(s, i + 1, started, 1);
    }
    if (!(s[i] >= '0' && s[i] <= '9'))
        return 0;
    return real(s, i + 1, 1, ponto);
}

int main()
{
    char s[1000];

    while (1)
    {
        fgets(s, 1000, stdin);

        // remover \n
        int len = strlen(s);
        if (len > 0 && s[len - 1] == '\n')
        {
            s[len - 1] = '\0';
        }

        if (strcmp(s, "FIM") == 0)
            break;

        printf("%s ", soVogais(s, 0) ? "SIM" : "NAO");
        printf("%s ", soConsoantes(s, 0) ? "SIM" : "NAO");
        printf("%s ", inteiro(s, 0, 0) ? "SIM" : "NAO");
        printf("%s\n", real(s, 0, 0, 0) ? "SIM" : "NAO");
    }

    return 0;
}
