import java.util.*;
public class Tp1Ex01{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String strTemp = "";
        for (String str = sc.nextLine(); !str.equals("FIM") ; str = sc.nextLine(), strTemp = "") {   
            for (int i = 0; i < str.length(); i++) {
                strTemp += (char)(str.charAt(i) + 3); 
            }
            System.out.println(strTemp);
        }
    }

}