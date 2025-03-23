#include <stdio.h>
int main(void){
    int pesoBolo,numeroPessoas,diferenca;
    scanf("%d %d", &pesoBolo,&numeroPessoas);
    
    if (pesoBolo%numeroPessoas == 0){
        printf("0\n");
    } 
    else {
    diferenca = numeroPessoas-pesoBolo%numeroPessoas;
    printf("%d\n",diferenca);
    }
    
    return 1;
}