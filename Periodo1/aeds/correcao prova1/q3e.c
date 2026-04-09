#include <stdio.h>
int main(void)
{
    int n;
    scanf("%d", &n);
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            if (j==(n/2)-1 & (i==(n/2)-1 || i ==(n/2)-1 ||i ==(n/2)||i ==(n/2)+1) || (j==(n/2) || j==(n/2)+1) &(i ==(n/2)-1 ||i ==(n/2)||i ==(n/2)+1)) 
            {
                printf(" ");
            }else{
                printf("*");

            }
        } //adicionei esse fim de chave
        printf("\n"); 
        
    }//adicionei esse fim de chave
    
    return 1;
}
