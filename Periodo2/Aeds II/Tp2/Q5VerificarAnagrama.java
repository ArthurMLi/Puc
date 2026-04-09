import java.util.*;
import java.io.*;

public class Q5VerificarAnagrama {
    static public void main(String args[]) throws Exception {
        Scanner obj = new Scanner(System.in, "UTF-8");
        PrintStream out = new PrintStream(System.out, true, "UTF-8"); // força saída em UTF-8

        String s = obj.nextLine();
        int temp = 0;
        String s2 = "", s1 = "";
        boolean resp = true;
        int[] letras = new int[26];

        while (!s.equals("FIM")) {
            // zerar vetor
            for (int i = 0; i < 26; i++) {
                letras[i] = 0;
            }

            // separar s1 e s2
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ' || s.charAt(i) == '-') {
                    temp = 1;
                } else {
                    if (temp == 0) {
                        s1 += s.charAt(i);
                    } else {
                        s2 += s.charAt(i);
                    }
                }
            }

            if (s1.length() == s2.length()) {
                resp = true;

                // contar letras de s1
                for (int i = 0; i < s1.length(); i++) {
                    char c = s1.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        letras[c - 'A']++;
                    } else {
                        letras[c - 'a']++;
                    }
                }

                // remover letras de s2
                for (int i = 0; i < s2.length(); i++) {
                    char c = s2.charAt(i);
                    if (c >= 'A' && c <= 'Z') {
                        if (letras[c - 'A'] > 0) {
                            letras[c - 'A']--;
                        } else {
                            resp = false;
                            break;
                        }
                    } else {
                        if (letras[c - 'a'] > 0) {
                            letras[c - 'a']--;
                        } else {
                            resp = false;
                            break;
                        }
                    }
                }
            } else {
                resp = false;
            }

            if (resp) {
                out.println("SIM");
            } else {
                out.println("NÃO"); // agora com acento, em UTF-8
            }

            // reset variáveis
            s = obj.nextLine();
            temp = 0; 
            s1 = "";
            s2 = "";
            resp = true;
        }

        obj.close();
        out.close();
    }
}
