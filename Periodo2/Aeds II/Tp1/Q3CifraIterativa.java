import java.util.Scanner;
import java.io.PrintStream;

public class Q3CifraIterativa {
    public static void main(String[] args) throws Exception {
        // Scanner configurado para ISO-8859-1
        Scanner obj = new Scanner(System.in, "ISO-8859-1");

        // Saída configurada para ISO-8859-1
        System.setOut(new PrintStream(System.out, true, "ISO-8859-1"));

        String s = obj.nextLine();
        String resposta = "";
        while (!s.equals("FIM")) {
            resposta = "";
            for (int i = 0; i < s.length(); i++) {
                resposta += (char)(((int)s.charAt(i))+ 3);
            }
            System.out.println(resposta);
            s = obj.nextLine();
        }
        obj.close();
    }
}