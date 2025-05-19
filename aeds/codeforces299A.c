#include <stdio.h>
int main(void)
{
    int n, menor, divi = 1, resultado = -1;
    scanf("%d", &n);
    int array[n];
    for (int i = 0; i < n; i++)
    {
        
        int temp;
        scanf("%d", &temp);
        array[i] = temp;
        if (i == 0)
        {
            menor = temp;
        }
        
        if (temp < menor)
        {
            menor = temp;
        }
    }

    for (int j = 0; j < n; j++)
    {
        if (array[j] % menor != 0)
        {

            menor = -1;
            break;
        }
    }
    
        printf("%d", menor);
}