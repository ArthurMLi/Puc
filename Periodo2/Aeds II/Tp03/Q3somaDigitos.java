public class Q3somaDigitos {

    // Método recursivo que soma os dígitos de um número inteiro
    public static int somaDigitos(int n) {
        if (n < 0) n = -n; // se for negativo, ignora o sinal
        if (n == 0) return 0;
        return (n % 10) + somaDigitos(n / 10);
    }

    public static void main(String[] args) throws Exception {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String linha;

        while ((linha = br.readLine()) != null) {
            if (linha.equals("FIM")) break;

            // transformar string em inteiro (sem usar parseInt direto em char)
            int numero = 0;
            int sinal = 1;
            int i = 0;

            if (linha.length() > 0 && (linha.charAt(0) == '-' || linha.charAt(0) == '+')) {
                if (linha.charAt(0) == '-') sinal = -1;
                i = 1;
            }

            for (; i < linha.length(); i++) {
                char c = linha.charAt(i);
                if (c >= '0' && c <= '9') {
                    numero = numero * 10 + (c - '0');
                }
            }
            numero *= sinal;

            System.out.println(somaDigitos(numero));
        }
    }
}
