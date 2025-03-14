#include <stdio.h>

int main()
{
    int temp, x1, x2, x3, x4,a,b,c;
    int sorted = 0;

    scanf("%d %d %d %d", &x1, &x2, &x3, &x4);

    while (!sorted)
    {
        sorted = 1;
        if (x1 > x2)
        {
            temp = x1;
            x1 = x2;
            x2 = temp;
            sorted = 0;
        }
        if (x2 > x3)
        {
            temp = x2;
            x2 = x3;
            x3 = temp;
            sorted = 0;
        }
        if (x3 > x4)
        {
            temp = x3;
            x3 = x4;
            x4 = temp;
            sorted = 0;
        }
    }

    a = x4 - x3;
    b = x4 - x2;
    c = x4 - x1;


    printf("%d %d %d\n", a, b, c);

    return 0;
}
