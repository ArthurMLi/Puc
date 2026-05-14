
public class Arvore {

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

    public void remover(int x) {
        raiz = remover(x, raiz);
    }

    private No remover(int x, No no) {
        if (no.elemento < x) {
            no.dir = remover(x, no.dir);
        } else if (no.elemento > x) {
            no.esq = remover(x, no.esq);
        } else {
            if (no.esq == null && no.dir == null) {
                no.esq = maiorEsq(no, no.esq);
            }else{
                if (no.esq == null){
                    no = no.dir;
                }else{
                    no = no.esq;
                }
            }
        }
        return no;
    }

    private No maiorEsq(No i, No j) {
        if (j.dir == null) {
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
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

    public void caminharPre() {
        caminharPre(raiz);
    }

    private void caminharPre(No no) {
        if (no != null) {
            System.out.println(no.elemento);
            caminharPre(no.esq);
            caminharPre(no.dir);
        }
    }

    public void caminharPos() {
        caminharPos(raiz);
    }

    private void caminharPos(No no) {
        if (no != null) {
            caminharPos(no.esq);
            caminharPos(no.dir);
            System.out.println(no.elemento);
        }
    }

    public void caminharCentral() {
        caminharCentral(raiz);
    }

    private void caminharCentral(No no) {
        if (no != null) {
            caminharCentral(no.esq);
            System.out.println(no.elemento);
            caminharCentral(no.dir);
        }
    }

    public int getMaior() {
        if (raiz != null) {
            return getMaior(raiz);
        }
        return -1;
    }

    private int getMaior(No no) {

        if (no.dir != null) {
            return getMaior(no.dir);
        }
        return no.elemento;
    }

    public int getMenor() {
        if (raiz != null) {
            return getMenor(raiz);
        }
        return -1;
    }

    private int getMenor(No no) {

        if (no.esq != null) {
            return getMenor(no.esq);
        }
        return no.elemento;
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
