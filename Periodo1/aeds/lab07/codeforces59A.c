#include <stdio.h>
//Nome: Arthur Mendes Lima

int main() {
    char s[101];
    int lower = 0, upper = 0;

    scanf("%s", s);

    // Contar maiúsculas e minúsculas
    for (int i = 0; s[i]; i++) {
        if (s[i] >= 'A' && s[i] <= 'Z') {
            upper++;
        } else if (s[i] >= 'a' && s[i] <= 'z') {
            lower++;
        }
    }

    // Converter conforme a contagem
    for (int i = 0; s[i]; i++) {
        if (upper > lower) {
            // Converter para maiúscula
            if (s[i] >= 'a' && s[i] <= 'z') {
                s[i] = s[i] - ('a' - 'A'); // ou: s[i] -= 32;
            }
        } else {
            // Converter para minúscula
            if (s[i] >= 'A' && s[i] <= 'Z') {
                s[i] = s[i] + ('a' - 'A'); // ou: s[i] += 32;
            }
        }
    }

    printf("%s\n", s);
    return 0;
}
