#include <stdio.h>
#include <string.h>

int VerficarPalindromoRec(char s[], int i)
{
    int resp = 1;
    if (i != (strlen(s) - (strlen(s) % 2)) / 2)
    {

        if (s[i] == s[strlen(s) - 1 - i])
        {
            resp = VerficarPalindromoRec(s, i + 1);
        }
        else
        {
            resp *= 0;
        }
    }
    else
    {
    }
    printf("%d", resp);

    return resp;
}

int VerficarPalindromo(char s[])
{
    return VerficarPalindromoRec(s, 0);
}
int main()
{

    char s[] = "";
    scanf("%s", &s);
    while (!strcmp("FIM", s))
    {
        if (VerficarPalindromo(s))
        {
            printf("SIM");
        }
        else
        {
            printf("NAO");
        }
        scanf("%s", &s);
    }
}

//
//
//
//
//
// import java.util.Scanner;
// class Q1PalindromoRecursivo {
////n era pra fazer recursivo

// static public void main(String[] args){
//
//     Scanner myObj = new Scanner(System.in);
//     String s = myObj.nextLine();
//     while(!s.equals("FIM")){
//     if(VerficarPalindromo(s))
//     {
//         System.out.println("SIM");
//     }
//     else{
//         System.out.println("NAO");
//     }
//         s = myObj.nextLine();
//     }
// }
// }
//
//