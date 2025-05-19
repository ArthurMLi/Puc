#include <stdio.h>

int main()
{
    int n, r;
    char str[11], code[11] = "codeforces";
    scanf("%d", &n);

    for (int i = 0; i < n; i++)
    {
        scanf("%s", str);
        r = 0;
        for (int j = 0; j < 10; j++)
        {
            if (str[j] != code[j])
            {
                r++;
            }
        }
        printf("%d\n", r);
    }
    return 0;
}
