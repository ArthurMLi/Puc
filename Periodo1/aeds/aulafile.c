#include <stdio.h>
#include <stdlib.h>

int main()
{
    FILE *fp = NULL;
    int n, numAlunos, ra;

    fp = fopen("notas.txt", "r");
    fscanf(fp, "%d", &n);
    int r1[n],r2[n],v1[n], v2[n];
    for (int i = 0; i < n; i++)
    {
        fscanf(fp, "%d", &v1[i]);
        fscanf(fp, "%d", &v2[i]);
    }

    printf("Digita ae o numero de alunos");
    scanf("%d", &numAlunos);
    int raDeleta[numAlunos];
    for (int i = 0; i < numAlunos; i++)
    {

        printf("digita o ra ae");
        scanf("%d", &raDeleta[i]);
    }

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < numAlunos; i++)
        {
            if (v1[i]== raDeleta[j])
            {
                /* code */
            }
            
        }
        
    }
    
    return 1;
}