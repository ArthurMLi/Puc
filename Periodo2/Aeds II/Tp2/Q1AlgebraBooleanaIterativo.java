import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q1AlgebraBooleanaIterativo {
    public static List<String> Verificar(List<String> lista, int inicial, int finalP, int i) {

        if (lista.size() == 1) {
            return lista;
        }
        if (i > 0 && lista.get(i - 1).equals("not")) {

            if (i + 1 < lista.size() && (lista.get(i + 1).equals("0") || lista.get(i + 1).equals("1"))) {

                String resultado = lista.get(i + 1).equals("1") ? "0" : "1";

                lista.set(i, resultado);

                lista.remove(i + 1);
                lista.remove(i + 1);
                lista.remove(i - 1);
            }
        } else {

            if (i > 0 && lista.get(i - 1).equals("and")) {

                int cont = 0, resultado = 1;

                for (int j = i + 1; j < finalP; j++) {

                    if (lista.get(j).equals("0")) {
                        resultado *= 0;
                        cont++;

                    } else {
                        if (lista.get(j).equals("1")) {
                            resultado *= 1;
                            cont++;

                        }
                    }
                }
                if (cont > 1) {

                    lista.set(i - 1, resultado + "");

                    for (int j = finalP; j >= inicial; j--) {

                        lista.remove(j);

                    }

                }

            } else {
                if (i > 0 && lista.get(i - 1).equals("or")) {

                    int cont = 0, resultado = 0;
                    for (int j = i + 1; j < finalP; j++) {

                        if (lista.get(j).equals("0")) {
                            cont++;
                        } else {
                            if (lista.get(j).equals("1")) {
                                resultado = 1;
                                cont++;

                            } else {
                                break;
                            }
                        }
                    }
                    if (cont > 1) {

                        lista.set(i - 1, resultado + "");
                        for (int j = finalP; j >= inicial; j--) {

                            lista.remove(j);
                        }

                    }
                }

            }

        }

        return lista;
    }

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        int n = obj.nextInt();
        while (n != 0) {
            int parenteseAberto = 0, parenteseFechado = 0;

            int[] valores = new int[n];
            for (int i = 0; i < n; i++) {
                valores[i] = obj.nextInt();
            }

            String expressao = obj.nextLine();

            List<String> lista = new ArrayList<>();

            Pattern pattern = Pattern.compile("[a-zA-Z]+|\\d+|\\(|\\)");
            Matcher matcher = pattern.matcher(expressao);

            while (matcher.find()) {
                lista.add(matcher.group());
            }

            for (int i = 0; i < n; i++) {
                Collections.replaceAll(lista, Character.toString((char) (i + 65)), "" + valores[i]);
            }

            lista.removeIf(s -> s.trim().isEmpty());
            for (int i = 0; i < lista.size(); i++) {
                lista.set(i, lista.get(i).trim());
            }
            //

            while (lista.size() > 1) {

                for (int i = 0; i < lista.size(); i++) {

                    if (lista.get(i).equals("(")) {
                        parenteseAberto = i;
                    } else {
                        if (lista.get(i).equals(")")) {
                            parenteseFechado = i;

                            lista = Verificar(lista, parenteseAberto, parenteseFechado, parenteseAberto);

                            break;

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