import java.util.ArrayList;
import java.util.List;

public class teste {
    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        lista.add("and");
        lista.add("0");
        lista.add("not");
        lista.add("0");

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals("not")) {
                // Verifica se há um próximo elemento
                if (i + 1 < lista.size() && (lista.get(i + 1).equals("0") || lista.get(i + 1).equals("1"))) {
                    String resultado = lista.get(i + 1).equals("1") ? "0" : "1";

                    System.out.println("not identificado e processado:");
                    System.out.println("Antes: " + lista);

                    // Substitui "not" pelo resultado
                    lista.set(i, resultado);

                    // Remove o valor após "not"
                    lista.remove(i + 1);

                    System.out.println("Depois: " + lista);
                } else {
                    System.out.println("Erro: 'not' não seguido por 0 ou 1");
                }
            }
        }
    }
}
