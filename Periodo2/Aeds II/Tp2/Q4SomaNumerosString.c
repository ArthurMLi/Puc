#include <stdio.h>
#include <string.h>
int main()
{
    char s[3] = "tem";
    int resp = 0;
    scanf("%s", &s);
    while (s[0] != 'F')
    {
        for (int i = 0; i < 3; i++)
        {
            resp +=  s[i] - '0' ;
        }
        printf("%d\n", resp);
        resp = 0;
        scanf("%s", &s);
    }
}