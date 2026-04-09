#include <stdio.h>
/*Autor: Arthur Mendes Lima*/

int main(void)
{
    /* iniciação da variavel policias e eventos sem caso como 0 */
    int i, eventos, policiais = 0, eventosSemCaso = 0, eventoAtual;
    scanf("%d", &eventos);
    for (i = 1; i <= eventos; i++)
    {
        scanf("%d", &eventoAtual);
        /*verificar se o evento foi um crime ou uma contratacao*/
        if (eventoAtual < 0)
        {
            /*no caso de ser um crime verificar se possui policial suficiente para resolver o caso e alterando as variaveis em cada caso*/
            if (policiais <= 0)
            {
                eventosSemCaso++;
            }
            else
            {
                policiais--;
            }
        }
        else
        {
            /*no caso de se uma contratação, aumenta a quantidade de policial*/
            policiais += eventoAtual;
        }
    }
    printf("%d\n", eventosSemCaso);
    return 0;
}