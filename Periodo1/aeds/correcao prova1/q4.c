#include <stdio.h>
int potencia5(int n); // fechei parenteses e um ponto e virgula
int main(void)
{
    int  n;
    scanf("%d", &n);
    if (potencia5(n)== 1)
    {
        printf("SIM");
    }else{
        printf("NAO");
    }
    return 1;
}
int potencia5(int n){
    if (n%5 == 0 )
    {
        if (n/5 ==5)
        {
            return 1;
        }else{
            if (n/5 == 1)
            {
                return 0;
            }
            
        }
        return potencia5(n/5);
    }else{return 0;}} // coloquei essas 2 fecha chave
