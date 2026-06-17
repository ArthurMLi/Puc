#include <stdio.h>
void swap( int*vetor, int temp,int menor){
    int receba = vetor[temp];
    vetor[temp] = vetor[menor];
    vetor[menor] = receba;
}
int main(){

    int v[7] = {0,7,2,3,4,5,6};
    int n = 7;
    int menor;
    for (int i = 0; i  < n; i++)
    {
        menor = i;
        for (int j = i+1; j < n; j++)
        {
            if (v[menor] > v[j])
            {
                menor = j ; 
            }
        }
        if (menor != i)
        {
            swap(v,i,menor);
        }
    }
    for (int i = 0; i < n; i++)
    {
        printf("%d",v[i]);
    }
    
}

