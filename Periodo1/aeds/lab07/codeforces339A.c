#include <stdio.h>
#include <string.h>
//Nome: Arthur Mendes Lima

int main() {
    char s[101];
    int nums[100]; // máximo de 100 números
    int n = 0;

    scanf("%s", s);

    // Extrair os números (ignorando os '+')
    for (int i = 0; s[i]; i++) {
        if (s[i] >= '1' && s[i] <= '3') {
            nums[n++] = s[i] - '0';
        }
    }

    // Ordenar os números (algoritmo simples: bubble sort)
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (nums[j] > nums[j + 1]) {
                int temp = nums[j];
                nums[j] = nums[j + 1];
                nums[j + 1] = temp;
            }
        }
    }

    // Imprimir os números com '+'
    for (int i = 0; i < n; i++) {
        if (i > 0) {
            printf("+");
        }
        printf("%d", nums[i]);
    }

    printf("\n");
    return 0;
}
