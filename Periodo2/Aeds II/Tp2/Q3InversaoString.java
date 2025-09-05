import java.util.*;

public class Q3InversaoString {

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        String s = obj.nextLine();
        String resp = "";
        while (!s.equals("FIM")) {

            for (int i = s.length() - 1; i >= 0; i--) {
                resp += s.charAt(i);
            }
            System.out.println(resp);
            resp = "";
            s = obj.nextLine();
        }

        obj.close();
    }
}