// import java.util.*;
// public class Tp1Ex01{
//     public static void main(String[] args){
//         Scanner sc = new Scanner(System.in);
//         String strTemp = "";
//         for (String str = sc.nextLine(); !str.equals("FIM") ; str = sc.nextLine(), strTemp = "") {
//             for (int i = 0; i < str.length(); i++) {
//                 strTemp += (char)(str.charAt(i) + 3);
//             }
//             System.out.println(strTemp);
//         }
//     }
//
// }
#include <stdio.h>

#define TAM 500

char s1[TAM], s2[4] = "FIM", st[TAM];

void ciframento(char s[TAM], int i)
{
    if (s[i] != '\0' && s[i] != '\n' && s[i] != '\r')
    {
        st[i] = s[i] + 3;
        ciframento(s, i + 1);
    }
    else
    {
        st[i] = '\0';
    }
}
int main()
{
    while (fgets(s1, sizeof(s1), stdin) != NULL)
    {
        int condicaoParada = 0;
        for (int i = 0; i < 3; i++)
        {
            if (s1[i] == s2[i])
            {
                condicaoParada++;
            }
        }
        if (condicaoParada == 3 && (s1[3] == '\n' || s1[3] == '\r' || s1[3] == '\0'))
        {
            break;
        }
        else
        {
            ciframento(s1, 0);
            for (int i = 0; st[i] != '\0'; i++)
            {
                printf("%c", st[i]);
            }
            printf("\n");
            for (int i = 0; i < TAM; i++)
            {
                st[i] = '\0';
            }
        }
    }
    return 0;
}