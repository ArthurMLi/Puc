#include <stdio.h>
int main()
{
    int n, d, i, j, lista[100];

    printf("Digite a quantidade de receitas:\n");
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        printf("Digite o 1 tesouro:\n");
        scanf("%d", &d);
        lista[0] = d;
        if (d > 0)
        {
            printf("Digite o %d tesouro:\n", i + 1);
            scanf("%d", &d);
            if (d >= 0)
            {
                lista[i] = d;
            }
        }
        j = i;
    }
    printf("Coordenadas dos tesouros:\n");
    for (int k = 0; k < j; k++)
    {

        if (k != j)
        {

            printf("%d,", lista[k]);
        }
        else
        {
            printf("%d.\n", lista[k]);
        }
    }
    int first=0,tem=0;
    printf("Digte a coordenada:");
    scanf("%d", &d);
    while (d > 0)
    {
        printf("Tesouro %d encontrado na coordenada:", d);
        
        printf("Digte a coordenada:");
        scanf("%d", &d);
    }

    return 1;
}