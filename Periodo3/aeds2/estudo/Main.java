
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        Scanner sc = new Scanner(System.in);

        for (String linha = parseComando(sc.next()); !linha.equals("-1"); linha = parseComando(sc.next())) {
            System.out.println("(" + linha + ")");
            lerComando(sc, linha, arvore);
        }
        sc.close();
    }

    public static void lerComando(Scanner sc,String comando, Arvore arvore) {
        switch (comando) {
            case "I" ->
                inserir(sc.nextInt(), arvore);
            case "IP" ->
                inserirPai(sc.nextInt(), arvore);
            case "P" ->
                pesquisar(sc.nextInt(), arvore);
            case "CPRE" ->
                caminharPre(arvore);
            case "CPOS" ->
                caminharPos(arvore);
            case "CCENTRAL" ->
                caminharCentral(arvore);
            default ->
                System.out.println("Comando não aceito");
        }
    }

    private static void inserir(int x, Arvore arvore) {
        arvore.inserir(x);
    }

    private static void inserirPai(int x, Arvore arvore) {
        arvore.inserir(x);
    }

    private static void pesquisar(int x, Arvore arvore) {
        System.out.println("Elemento " + x + (arvore.pesquisar(x) ? "" : " não") + " está na arvore");
    }

    private static void caminharPre(Arvore arvore) {
        arvore.caminharPre();
    }

    private static void caminharPos(Arvore arvore) {
        arvore.caminharPos();
    }

    private static void caminharCentral(Arvore arvore) {
        arvore.caminharCentral();
    }

    private static String parseComando(String s) {
        String resp = "";
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                resp += s.charAt(i) + "";
            }
        }
        return resp;
    }
}
