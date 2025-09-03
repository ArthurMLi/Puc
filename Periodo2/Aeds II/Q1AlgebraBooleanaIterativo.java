import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q1AlgebraBooleanaIterativo {
    public static boolean Verificar(List<String> lista, int inicial, int finalP) {
        for (int i = 0; i < finalP; i++) {

            if (lista.get(i).equals("not")) {

                // System.out.println("not errado");

                if (i + 1 < lista.size() && (lista.get(i + 1).equals("0") || lista.get(i + 1).equals("1"))) {

                    String resultado = lista.get(i + 1).equals("1") ? "0" : "1";
                    System.out.println("not");
                    System.out.println(lista);

                    lista.set(i, resultado);

                    lista.remove(i + 1);
                    System.out.println(lista);
                }
            } else {

                if (lista.get(i).equals("and")) {

                    int cont = 0, resultado = 1;
                    for (int j = i + 1; j < lista.size(); j++) {

                        if (lista.get(j).equals("0")) {
                            resultado *= 0;
                            cont++;
                        } else {
                            if (lista.get(j).equals("1")) {
                                resultado *= 1;
                                cont++;

                            } else {
                                break;
                            }
                        }
                    }
                    if (cont > 1) {
                        System.out.println("and");
                        System.out.println(lista);

                        for (int j = 0; j < cont; j++) {

                            lista.remove(i + 1);
                        }
                        System.out.println(lista);

                        lista.set(i, resultado + "");
                    }

                }

            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        int n = obj.nextInt();
        int parenteseAberto = 0, parenteseFechado = 0;
        while (n != 0) {

            int[] valores = new int[n];
            for (int i = 0; i < n; i++) {
                valores[i] = obj.nextInt();
            }

            String expressao = obj.nextLine();
            System.out.println(expressao);
            // String[] separado = expressao.split("[( ,]+");

            List<String> lista = new ArrayList<>();
            // Expressão regular que encontra tokens: palavras, números, ou símbolos
            // individuais
            Pattern pattern = Pattern.compile("[a-zA-Z]+|\\d+|\\(|\\)");
            Matcher matcher = pattern.matcher(expressao);

            while (matcher.find()) {
                lista.add(matcher.group());
            }
            System.out.println(lista);

            // lista.remove(0); // Remove o primeiro elemento vazio

            // troca as variaveis da expressao pelos seus valores
            for (int i = 0; i < n; i++) {
                Collections.replaceAll(lista, Character.toString((char) (i + 65)), "" + valores[i]);
            }

            // Apaga vazias
            lista.removeIf(s -> s.trim().isEmpty());
            for (int i = 0; i < lista.size(); i++) {
                lista.set(i, lista.get(i).trim());
            }
            System.out.println(lista);

            while (lista.size() > 1) {

                // Process NOT operations first (highest precedence)
                for (int i = 0; i < lista.size(); i++) {

                    if (lista.get(i).equals("(")) {
                        parenteseAberto = i;
                    } else {
                        if (lista.get(i).equals(")")) {
                            parenteseFechado = i;
                           boolean resultado =  Verificar(lista, parenteseAberto, parenteseFechado);
                           //tem q apagaros parenteses
                        }
                    }

                }

            }
            System.out.println(lista.get(0));
            n = obj.nextInt();

        }

        obj.close();
    }
}