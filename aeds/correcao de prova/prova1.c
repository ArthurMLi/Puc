#include <stdio.h>
int main(void)
{
    int n, i = 1, par = 0, impar = 0;
    scanf("%d", &n);
    while (n != 0)
    {
        while ((n - i) % 10 != 0)
        {
            i++;
        }
        if (i % 2 == 0)
        {
            par += i;
        }
        else
        {
            impar += i;
        }
        n = (n-i)/10;
        i=1;
    }
    par = par * impar;
    printf("%d", par);
}
