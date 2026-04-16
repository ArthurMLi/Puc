#include <stdio.h>

char vogais[5] = {'a', 'e', 'i', 'o', 'u'};

int forLetra(char c)
{
    if (c >= 'a' && c <= 'z')
    {
        return 1;
    }
    else
    {
        if (c >= 'A' && c <= 'Z')
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
}

int ehVogal(char c, int i)
{
    if (i == 5)
    {
        return 0;
    }
    if (c == vogais[i])
    {
        return 1;
    }
    return ehVogal(c, i + 1);
}

int soVogal(char s[1000], int i)
{
    if (s[i] == '\0')
    {
        return 1;
    }
    if (forLetra(s[i]) != 1 || ehVogal(s[i], 0) == 0)
    {
        return 0;
    }
    return soVogal(s, i + 1);
}

int soConsoante(char s[1000], int i)
{
    if (s[i] == '\0')
    {
        return 1;
    }
    if (forLetra(s[i]) != 1 || ehVogal(s[i], 0) == 1)
    {
        return 0;
    }
    return soConsoante(s, i + 1);
}

int soNumero(char s[1000], int i)
{
    if (s[i] == '\0')
    {
        return 1;
    }
    if (!(s[i] >= '0' && s[i] <= '9'))
    {
        return 0;
    }
    return soNumero(s, i + 1);
}

int soNumeroReal(char s[1000], int i, int ponto)
{
    if (s[i] == '\0')
    {
        return 1;
    }

    if (s[i] >= '0' && s[i] <= '9')
    {
        return soNumeroReal(s, i + 1, ponto);
    }
    else
    {
        if (s[i] == '.' || s[i] == ',')
        {
            if (ponto)
            {
                return 0;
            }
            return soNumeroReal(s, i + 1, 1);
        }
        else
        {
            return 0;
        }
    }
}

int main()
{
    char s1[1000], fim[4] = "FIM";
    while (fgets(s1, sizeof(s1), stdin) != NULL)
    {
        int len = 0;
        int condicaoParada = 0;

        while (s1[len] != '\n' && s1[len] != '\0')
        {
            len++;
        }
        if (s1[len] == '\n')
        {
            s1[len] = '\0';
        }

        for (int i = 0; i < 3; i++)
        {
            if (s1[i] == fim[i])
            {
                condicaoParada++;
            }
        }
        if (condicaoParada == 3 && s1[3] == '\0')
        {
            break;
        }

        printf("%s %s %s %s\n",
               soVogal(s1, 0) ? "SIM" : "NAO",
               soConsoante(s1, 0) ? "SIM" : "NAO",
               soNumero(s1, 0) ? "SIM" : "NAO",
               soNumeroReal(s1, 0, 0) ? "SIM" : "NAO");
    }
    return 0;
}
