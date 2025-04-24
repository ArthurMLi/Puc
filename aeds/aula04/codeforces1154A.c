#include <stdio.h>
/* Autor: Arthur Mendes Lima*/
int main()
{
    /* define temp e sorted para usar no agoritimo de ordenar as variaveis*/
    int temp, x1, x2, x3, x4, a, b, c;


    scanf("%d %d %d %d", &x1, &x2, &x3, &x4);
    /* separar o maior valor(a+b+c) na variavel x4 usando uma variavel temporaria*/
    if (x1 > x4)
    {
        temp = x4;
        x4 = x1;
        x1 = temp;
    }
    

    if (x2 > x4)
    {
        temp = x4;
        x4 = x2;
        x2 = temp;
    }

    if (x3 > x4)
    {
        temp = x4;
        x4 = x3;
        x3 = temp;
    }

    a = x4 - x3;
    b = x4 - x2;
    c = x4 - x1;

    printf("%d %d %d\n", a, b, c);

    return 0;
}
