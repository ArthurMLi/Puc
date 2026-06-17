
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        Scanner sc = new Scanner(System.in);

        for (String linha = parseComando(sc.next()); !linha.equals("-1"); linha = parseComando(sc.next())) {
            lerComando(sc, linha, arvore);
        }
        sc.close();
    }

    public static void lerComando(Scanner sc, String comando, Arvore arvore) {
        switch (comando) {
            case "i" ->
                inserir(sc.nextInt(), arvore);
            case "inserir" ->
                inserir(sc.nextInt(), arvore);
            case "inserirrandom" ->
                inserirRand(sc.nextInt(),arvore);
            case "ir" ->
                inserirRand(sc.nextInt(),arvore);
            case "p" ->
                pesquisar(sc.nextInt(), arvore);
            case "pesquisar" ->
                pesquisar(sc.nextInt(), arvore);
            case "caminharpre" ->
                caminharPre(arvore);
            case "caminharprenivel" ->
                caminharPreNivel(arvore);
            case "caminharpos" ->
                caminharPos(arvore);
            case "caminharcentral" ->
                caminharCentral(arvore);
            case "maior" ->
                getMaior(arvore);
            case "menor" ->
                getMenor(arvore);
            case "remover" ->
                remover(sc.nextInt(),arvore);
            case "r" ->
                remover(sc.nextInt(),arvore);
            default ->
                System.out.println("Comando não aceito");
        }
    }

    private static void inserir(int x, Arvore arvore) {
        arvore.inserir(x);
    }



    private static void inserirRand(int n, Arvore arvore) {
        Random r = new Random(4);
        int valor;
        for (int i = 0; i < n;) {
            valor = r.nextInt() % n;
            if (!(arvore.pesquisar(valor))) {
                arvore.inserir(valor);
                i++;
            }
        }
    }

    private static void remover(int x,Arvore arvore){
        arvore.remover(x);
    }

    private static void pesquisar(int x, Arvore arvore) {
        System.out.println("Elemento " + x + (arvore.pesquisar(x) ? "" : " não") + " está na arvore");
    }

    private static void caminharPre(Arvore arvore) {
        arvore.caminharPre();
    }

    private static void caminharPreNivel(Arvore arvore) {
        arvore.caminharPreNivel();
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
            } else {
                if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                    resp += (s.charAt(i) - 'A' + 'a');
                }
            }
        }
        return resp.trim();
    }

    private static void getMaior(Arvore arvore) {
        System.out.println(arvore.getMaior());
    }
    
    private static void getMenor(Arvore arvore) {
        System.out.println(arvore.getMenor());
    }
}
