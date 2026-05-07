
public class Arvore {

    No raiz;

    public Arvore() {
        raiz = null;
    }

    public void inserir(int x) {
        if (raiz == null) {
            raiz = new No(x);
        }else{
            inserir(x, raiz);
        }
    }

    private void inserir(int x, No no) {
        if (noDiferente(no.elemento, x)) {
            if (no.elemento > x) {
                //elemento menor vai pra esquerda
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
                if (pai.elemento > x) {
                    pai.esq = new No(x);
                }
            }
        } else {
            if (noDiferente(filho.elemento, x)) {
                if (filho.elemento > x) {
                    //elemento menor vai pra esquerda
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

    public void caminharPre(){
        caminharPre(raiz);
    }

    private void caminharPre(No no){
        if(no != null){
            System.out.println(no.elemento);
            caminharPre(no.esq);
            caminharPre(no.dir);
        }
    }

    public void caminharPos(){
        caminharPos(raiz);
    }

    private void caminharPos(No no){
        if(no != null){
            caminharPos(no.esq);
            caminharPos(no.dir);
            System.out.println(no.elemento);
        }
    }

    public void caminharCentral(){
        caminharCentral(raiz);
    }

    private void caminharCentral(No no){
        if(no != null){
            caminharCentral(no.esq);
            System.out.println(no.elemento);
            caminharCentral(no.dir);
        }
    }
}

class No {

    int elemento;
    No esq;
    No dir;

    No() {
        elemento = -1;
        esq = null;
        dir = null;
    }

    No(int x) {
        elemento = x;
        esq = null;
        dir = null;
    }
}
