#include <stdio.h>

int somar(char *x, int i,int len)
{
    
    if (x == NULL)
    {
        return 0;
    }
    else
    {
        if (i == len - 1)
        {
            return (int) x[i]-'0';
        }else{
            return somar(x, i+1, len) + (int) x[i]-'0';
        }
    }
}
int main()
{
    char s1[50];
    while(fgets(s1, sizeof(s1), stdin)!= NULL)
    {
        int len = 0;
        int resp = 0;
        while (s1[len] != '\n' && s1[len] != '\0')
        {
            len++;
        }
        if (s1[len] == '\n')
        {
            s1[len] = '\0';
        }
        resp = somar(s1,0,len);      
        printf("%d\n",resp);

    }
    return 0;
}
