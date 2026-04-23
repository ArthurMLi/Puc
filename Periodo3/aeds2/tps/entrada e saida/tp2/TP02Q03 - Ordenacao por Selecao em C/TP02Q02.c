#include <stdio.h>
#include <string.h>

typedef struct
{
    int ano;
    int mes;
    int dia;
} Data;

Data parsedata(Data* data ,char *s)
{
    sscanf(s, "%d %d %d",data->ano)
}

int main()
{

    return 1; 
}
