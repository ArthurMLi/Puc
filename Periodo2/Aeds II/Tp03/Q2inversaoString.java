public class Q2inversaoString {


    public static String inverter(String s, int i) {

        if (i < 0) {
            return "";
        }

        return s.charAt(i) + inverter(s, i - 1);
    }

    public static void main(String[] args) throws Exception {
        //precisei fazer isso pra funcionar o charset
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String linha;

        while ((linha = br.readLine()) != null) {
            if (linha.equals("FIM")) break;

            String invertida = inverter(linha, linha.length() - 1);
            System.out.println(invertida);
        }
    }
}
