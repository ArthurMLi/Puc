void verificar(int *a, int *b, int *c, int *maior)
{

    if (*a > *b)
    {
        *maior = *a;
    }else{
        *maior = *b; 
    }
    if (*maior < *c)
    {
        *maior = *c;
    }

}

int main(void)
{

    int a, b, c, maior;
    scanf("%d %d %d", &a, &b, &c);
    verificar(&a, &b, &c, &maior);
    printf("%d",maior);
    return 1;
}