import java.util.Scanner;
class Q1PalindromoRecursivo {
//n era pra fazer recursivo
static public boolean VerficarPalindromo(String s){
    return VerficarPalindromo(s, 0);
}

static public boolean VerficarPalindromo(String s, int i){
    boolean resp = false;
    if(i == (s.length() -(s.length() % 2) ) /2){
        resp = true;
    }else{
    if(s.charAt(i) == s.charAt(s.length() -1 -i)){
        resp = VerficarPalindromo(s, i +1);
    }
    else{
        resp = false;
    }}

    return resp;
}
static public void main(String[] args){
    
    Scanner myObj = new Scanner(System.in);
    String s = myObj.nextLine();
    while(!s.equals("FIM")){ 
    if(VerficarPalindromo(s))
    {
        System.out.println("SIM");
    }
    else{
        System.out.println("NAO");
    } 
        s = myObj.nextLine();
    }
    myObj.close();

}
}

