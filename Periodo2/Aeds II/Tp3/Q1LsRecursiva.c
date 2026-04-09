#include <stdio.h>
#include <string.h>

int verificarVogalc(char s[], int i, int resp)
{

    if (s[i] == '\0')
    {
        return resp;
    }
    char c = s[i];
    if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U'){
        return 0;
    }
    else{
        verificarVogalc(s,i+1,resp);
    }
}

int verificarConsoantec(char s[], int i, int resp)
{

    if (s[i] == '\0')
    {
        return resp;
    }
    if (s[i] == 'a' && s[i] == 'e' && s[i] == 'i' && s[i] == 'o' && s[i] == 'u' && s[i] == 'A' && s[i] == 'E' && s[i] == 'I' && s[i] == 'O' && s[i] == 'U'){
        return 0;
    }
    else{
        verificarConsoantec(s,i+1,resp);
    }
}

int verificarConsoante(char s[])
{
    return verificarConsoantec(s, 0, 1);
}
int verificarVogal(char s[])
{
    return verificarVogalc(s, 0, 1);
}
int main()
{
    char s[1];
    scanf("%s", s);
    printf("%s", s);
    printf("%d\n", verificarVogal(s));
    printf("%d", verificarConsoante(s));


}   