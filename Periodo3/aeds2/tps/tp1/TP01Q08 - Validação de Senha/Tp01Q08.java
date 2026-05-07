
import java.util.*;

public class Tp01Q08 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s;
        s = sc.nextLine();
        while (!s.equals("FIM")) {
            System.out.println(verificarSenha(s) + "");
            s = sc.nextLine();
        }

    }

    public static String verificarSenha(String s) {
        boolean minuscula = false, maiuscula = false, numero = false, caractere = false;
        int cont=0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numero = true;
            } else {
                if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                    minuscula = true;
                } else {
                    if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                        maiuscula = true;
                    } else {
                        caractere = true;
                    }
                }
            }
            cont++;
        }
        if (minuscula && maiuscula && numero && caractere && cont >=8 ) {
            return "SIM";
        } else {
            return "NAO";
        }
    }
    
}
