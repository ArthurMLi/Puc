
import java.util.*;

class Arvore {

    No raiz;

    public Arvore() {
        raiz = null;
    }

    public void inserir(int x) {
        if (raiz == null) {
            raiz = new No(x);
        } else {
            inserir(x, raiz);
        }
    }

    private void inserir(int x, No no) {
        if (noDiferente(no.elemento, x)) {
            if (no.elemento > x) {
                // elemento menor vai pra esquerda
                if (no.esq == null) {
                    no.esq = new No(x);
                } else {
                    inserir(x, no.esq);
                }
            } else {
                if (no.dir == null) {
                    no.dir = new No(x);
                } else {
                    inserir(x, no.dir);
                }
            }

        } else {
            System.err.println("Elemento não foi adicionada\nNumero " + x + " ja existe na arvore");
        }
    }

    public void inserirPai(int x) {
        if (x > raiz.elemento) {
            inserirPai(x, raiz, raiz.dir);
        } else {
            inserirPai(x, raiz, raiz.esq);
        }
    }

    private void inserirPai(int x, No pai, No filho) {
        if (filho == null) {
            if (pai.elemento < x) {
                pai.dir = new No(x);
            } else {
                pai.esq = new No(x);
            }
        } else {
            if (noDiferente(filho.elemento, x)) {
                if (filho.elemento > x) {
                    inserirPai(x, filho, filho.esq);
                } else {
                    inserirPai(x, filho, filho.dir);
                }
            } else {
                System.err.println("Elemento não foi adicionada\nNumero " + x + " ja existe na arvore");

            }
        }
    }

    private boolean noDiferente(int x, int y) {
        return (x != y);
    }

    public boolean pesquisar(int x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(int x, No no) {
        boolean resp = false;
        if (no != null) {
            System.out.printf("%d ", no.elemento);
            if (no.elemento == x) {
                resp = true;
            } else {
                if (no.elemento < x) {
                    resp = pesquisar(x, no.dir);
                } else {
                    resp = pesquisar(x, no.esq);
                }
            }
        }
        return resp;
    }

    public void caminharPre() {
        if (raiz == null) {
         System.out.println("V");  
         return; 
        }
        caminharPre(raiz);
        System.out.println("");  

    }

    private void caminharPre(No no) {
        if (no != null) {
            System.out.printf("%d ", no.elemento);
            caminharPre(no.esq);
            caminharPre(no.dir);
        }
    }

    public void caminharPos() {
        if (raiz == null) {
         System.out.println("V");  
         return; 
        }
        caminharPos(raiz);
        System.out.println("");  

    }

    private void caminharPos(No no) {
        if (no != null) {
            caminharPos(no.esq);
            caminharPos(no.dir);
            System.out.printf("%d ", no.elemento);

        }
    }

    public void caminharCentral() {
        if (raiz == null) {
         System.out.println("V");  
         return; 
        }
        caminharCentral(raiz);
        System.out.println("");  
    
    }

    private void caminharCentral(No no) {
        if (no != null) {
            caminharCentral(no.esq);
            System.out.printf("%d ", no.elemento);
            caminharCentral(no.dir);
        }
    }
}

class No {

    int elemento;
    No esq;
    No dir;

    No(int x) {
        elemento = x;
        esq = null;
        dir = null;
    }
}

public class Main {

    public static void main(String[] args) {
        Arvore arvore = new Arvore();
        Scanner sc = new Scanner(System.in);

        for (String linha = sc.next(); !linha.equals("-1"); linha = sc.next()) {
            lerComando(sc, linha, arvore);
            if (!sc.hasNext()) {
                break;
            }
        }
        sc.close();
    }

    public static void lerComando(Scanner sc, String comando, Arvore arvore) {
        switch (comando) {
            case "I":

                inserir(sc.nextInt(), arvore);
                break;
            case "P":
                pesquisar(sc.nextInt(), arvore);
                break;
            case "PRE":
                caminharPre(arvore);
                break;
            case "POS":
                caminharPos(arvore);
                break;
            case "EM":
                caminharCentral(arvore);
                break;
            default:
                System.out.println("Comando não aceito");
        }
    }

    private static void inserir(int x, Arvore arvore) {
        arvore.inserir(x);
    }

    private static void pesquisar(int x, Arvore arvore) {
        System.out.println((arvore.pesquisar(x) ? "S" : "N"));
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

}
