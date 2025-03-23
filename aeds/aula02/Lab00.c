#include <stdio.h>

    int main(void){
    
        float p1,p2,e,m,a1,a2,ada,p3,nota;
        scanf("%f %f %f %f %f %f %f",&p1,&p2,&e,&m,&a1,&a2,&ada);
        
        nota = (p1 + p2) * 2.5 + e * 2 + (m + a1 + a2) * 1.5 + ada;
        p3 = (60 *15 - nota) /2.5;
        printf("%.2f", p3);

        return 0;
    }