
#include <stdio.h>
int main(void)
{
    int n, total, j, i;
    char caracter;
    printf("Digite o tamanho do lado do quadrado:\n");
    scanf("%d", &n);
    printf("Digite um caractere para formar o quadrado:\n");
    scanf(" %c", &caracter);

    for (i = 0; i < n; i++)
    {
        for (j = 0; j < n; j++)
        {
            printf("%c ", caracter);
        }
        printf("\n");
    }
    return 0;
}
