#include <stdio.h>
int main()
{

    int n, qntA = 0, qntD = 0;
    char str[100001];
    scanf("%d %s", &n, &str);
    for (int i = 0; i < n; i++)
    {
        if (str[i] == 'A')
        {
            qntA++;
        }
        else
        {
            if (str[i] == 'D')
            {
                qntD++;
            }
        }
    }
    if (qntA > qntD)
    {
        printf("Anton");
    }
    else
    {
        if (qntA < qntD)
        {
            printf("Danik");
        }
        else
        {
            printf("Friendship");
        }
    }

    return 0;
}