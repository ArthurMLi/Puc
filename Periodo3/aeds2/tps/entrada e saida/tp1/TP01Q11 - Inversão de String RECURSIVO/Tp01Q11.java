import java.util.*;

public class Tp01Q11 {

    public static int ehFim(String s) {
        if (s.length() != 3) {
            return 0;
        }
        if (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
            return 1;
        }
        return 0;
    }

    public static String inverter(String s, int i) {
        if (i == s.length()) {
            return "";
        }
        return inverter(s, i + 1) + s.charAt(i);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1;

        while (sc.hasNextLine()) {
            s1 = sc.nextLine();

            if (ehFim(s1) == 1) {
                break;
            }

            System.out.println(inverter(s1, 0));
        }

        sc.close();
    }
}
