#include <stdio.h>
int main(void)
{
    int a[100], n, m, soma = 0, qttneg = 0, mudanca = 0;
    scanf("%d", &m);
    
    for (int i = 0; i < m; i++)
    {

        scanf("%d", &n);
        for (int i = 0; i < n; i++)
        {
            int temp;
            scanf("%d", &temp);
            a[i] = temp;
            soma += temp;
            if (temp < 0)
            {
                qttneg++;
            }
        }
        if (qttneg % 2 != 0)
        {
            mudanca++;
            soma += 2;
        }
        while (soma < 0)
        {
            soma += 2;
            mudanca++;
        }
        printf("%d", mudanca);
        soma = 0; qttneg = 0; mudanca = 0;
    }
    return 1;
}