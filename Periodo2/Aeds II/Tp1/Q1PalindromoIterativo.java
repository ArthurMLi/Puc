import java.util.Scanner;
class Q1PalindromoIterativo {

static public boolean VerficarPalindromo(String s){
    boolean resp = true;
    for(int i =0; i <(s.length() -(s.length() % 2 ) /2);i++){
    if(!(s.charAt(i) == s.charAt(s.length() -1 -i))){
        resp = false ;
        
    }
    }
        

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
}
}


