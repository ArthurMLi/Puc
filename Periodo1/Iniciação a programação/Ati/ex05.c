#include <stdio.h>

int main() {
    int numero_escolhido;
    
    printf("Pense em um numero de 1 a 1023\n");
    while (1) {
        int menor = 1, maior = 1023, tentativa, tentativas = 0;
        
        printf("Neste caso para testar o programa me diga o numero que voce escolheu:\n");
        scanf("%d", &numero_escolhido);
        
        if (numero_escolhido == 0) {
            return 0;
        }
        
        while (menor <= maior) {
            tentativa = (menor + maior) / 2;
            tentativas++;
            
            printf("O numerio >,< ou = %d\n", tentativa);
            
            if (numero_escolhido == tentativa) {
                printf("Advinhei o numero %d em %d tentativas!\n", tentativa, tentativas);
                break;
            } else if (numero_escolhido > tentativa) {
                menor = tentativa + 1;
            } else {
                maior = tentativa - 1;
            }
        }
        
        printf("Pense em um numero de 1 a 1023(flag valor <1)\n");
    }
    
    return 0;
}
