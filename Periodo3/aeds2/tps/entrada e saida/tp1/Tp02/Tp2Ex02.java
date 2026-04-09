import java.util.*;
public class Tp2Ex02{
    public static void main(String[] args) {
        Random generator = new Random();
        generator.setSeed(4);
        
        Scanner sc = new Scanner(System.in);
        String strTemp = "";
        for (String str = sc.nextLine();!str.equals("FIM"); str = sc.nextLine() ,strTemp = "") {
            char p1 = (char) ('a' + (Math.abs(generator.nextInt()) % 26));
                char p2 = (char) ('a' + (Math.abs(generator.nextInt()) % 26));
            for (int i = 0; i < str.length(); i++) {
                if(str.charAt(i) == p1){
                    strTemp += p2;
                } else{
                    strTemp += str.charAt(i);
                }
            }
            System.out.println(strTemp);
        }       
    }    
}
