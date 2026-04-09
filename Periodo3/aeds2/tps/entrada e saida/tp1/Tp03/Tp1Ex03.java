import java.util.*;

public class Tp1Ex03 {
    public final static char vogais[] = { 'a', 'e', 'i', 'o', 'u' };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String strTemp = "", str;
        for (str = sc.nextLine(); !str.equals("FIM"); str = sc.nextLine(), strTemp = "") {

            strTemp += (soVogal(str) ? "SIM " : "NAO ") + (soConsoante(str) ? "SIM " : "NAO ")
                    + (soNumero(str) ? "SIM " : "NAO ") + (soNumeroReal(str) ? "SIM" : "NAO");
            System.out.println(strTemp);
        }
    }

    public static int forLetra(char c) {
        if (c >= 'a' & c <= 'z') {
            return 1;
        } else {
            if (c >= 'A' & c <= 'Z') {
                return 2;
            } else {
                return 0;
            }
        }
    }

    public static boolean soConsoante(String s) {
        int cont = 0;
        Boolean resp = true;
        for (int i = 0; i < s.length(); i++, cont = 0) {
            for (int j = 0; j < 5; j++) {
                if (forLetra(s.charAt(i)) == 1 & s.charAt(i) == vogais[j]) {
                    cont++;
                }

            }
            if (cont != 0 || forLetra(s.charAt(i)) == 0) {
                resp = false;
            }
        }
        return resp;
    }

    public static boolean soVogal(String s) {
        int cont = 0;
        Boolean resp = true;
        for (int i = 0; i < s.length(); i++, cont = 0) {
            for (int j = 0; j < 5; j++) {
                if (forLetra(s.charAt(i)) == 1 & s.charAt(i) == vogais[j]) {
                    cont++;
                }

            }
            if (cont == 0 || forLetra(s.charAt(i)) == 0) {
                resp = false;
            }
        }
        return resp;
    }

    public static boolean soNumero(String s) {
        Boolean resp = true;
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' & s.charAt(i) <= '9')) {
                resp = false;
            }

        }
        return resp;
    }

    public static boolean soNumeroReal(String s) {
        Boolean resp = true;
        boolean ponto = false;
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' & s.charAt(i) <= '9')) {

                if (ponto) {
                    resp = false;
                } else {
                    if (s.charAt(i) == '.' || s.charAt(i) == ',') {
                        ponto = true;
                    } else {
                        resp = false;
                    }
                }

            }

        }
        return resp;
    }
}
