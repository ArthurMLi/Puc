#include <stdio.h>

int main() {
    int n, a[100];
    int neg[100], pos[100], zero[100];
    int n_neg = 0, n_pos = 0, n_zero = 0;

    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%d", &a[i]);
        if (a[i] < 0)
            neg[n_neg++] = a[i];
        else if (a[i] > 0)
            pos[n_pos++] = a[i];
        else
            zero[n_zero++] = a[i];
    }

    // Primeiro conjunto: 1 número negativo
    printf("1 %d\n", neg[0]);

    // Segundo conjunto: positivos, ou dois negativos se não houver positivos
    if (n_pos > 0) {
        printf("%d", n_pos);
        for (int i = 0; i < n_pos; i++)
            printf(" %d", pos[i]);
        printf("\n");
    } else {
        printf("2 %d %d\n", neg[1], neg[2]);
    }

    // Terceiro conjunto: o resto (zeros e negativos que sobraram)
    int rest = n_zero + n_neg - (n_pos > 0 ? 1 : 3);
    printf("%d", rest);
    for (int i = (n_pos > 0 ? 1 : 3); i < n_neg; i++)
        printf(" %d", neg[i]);
    for (int i = 0; i < n_zero; i++)
        printf(" %d", zero[i]);
    printf("\n");

    return 0;
}