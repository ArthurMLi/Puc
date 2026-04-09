import java.util.Random;
import java.util.Scanner;

public class Q4AlteracaoIterativa {
    public static void main(String[] args) {
        Random gerador = new Random();
        gerador.setSeed(4);

        Scanner obj = new Scanner(System.in, "UTF-8");
        String s = obj.nextLine();
        String resposta = "";
        while (!s.equals("FIM")) {
            char Letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            char Letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == Letra1) {
                    resposta += Letra2;
                } else {
                    resposta += s.charAt(i);
                }
            }
            System.out.println(resposta);
            s = obj.nextLine();
            resposta = "";
        }
        obj.close();

    }

}