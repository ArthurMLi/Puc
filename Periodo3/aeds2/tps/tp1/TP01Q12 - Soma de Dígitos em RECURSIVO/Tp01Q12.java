import java.util.*;

public class Tp01Q12 {

    public static int somar(String s, int i) {
        if (i == s.length()) {
            return 0;
        }
        return (s.charAt(i) - '0') + somar(s, i + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1;

        while (sc.hasNextLine()) {
            s1 = sc.nextLine();
            System.out.println(somar(s1, 0));
        }

        sc.close();
    }
}
