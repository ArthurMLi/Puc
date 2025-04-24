int main(void)
{
    int n;
    printf("Digite um numero:\n");
    scanf("%d", &n);
    while (n>=0)
    {
        /* code */
    
    
    if (n % 3 == 0 && n % 5 == 0)
    {
        printf("O numero %d e divisivel por 5 e 3 ao mesmo tempo\n", n);
    }
    else if (n % 3 == 0)
    {
        printf("O numero %d e divisivel por 3 mas nao por 5\n", n);
    }
    else if (n % 5 == 0)
    {
        printf("O numero %d e divisivel por 5 mas nao por 3\n");
    }
    else
    {
        printf("O numero %d nao e divisivel nem por 5 nem por 3\n");
    }
    printf("Digite um numero(flag valor negativo):\n");
    scanf("%d", &n);
    }
    return 1;
}