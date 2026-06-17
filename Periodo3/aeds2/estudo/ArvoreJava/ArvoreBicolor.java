
public class ArvoreBicolor {

    No raiz;

    public ArvoreBicolor() {
        raiz = null;
    }

    public void inserir(int x) {

        raiz = inserir(x, raiz);

    }

    private No inserir(int x, No no) {
        if (no != null) {
            if (noDiferente(no.elemento, x)) {
                if (no.elemento > x) {
                    no.esq = inserir(x, no.esq);
                } else {
                    no.dir = inserir(x, no.dir);
                }
            } else {
                System.err.println("Elemento não foi adicionada\nNumero " + x + " ja existe na arvore");
            }
        } else {
            no = new No(x);
        }
        return balancear(no);
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
            } else {
                if (no.esq == null) {
                    no = no.dir;
                } else {
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
            System.out.println(no.elemento + "\n");
            caminharPre(no.esq);
            caminharPre(no.dir);
        }
    }

    public void caminharPreNivel() {
        caminharPreNivel(raiz);
    }

    private void caminharPreNivel(No no) {
        if (no != null) {
            System.out.println(no.elemento + " nivel = " + no.nivel + "Fator de balancemento = " + getFatorBalanceamento(no) + "\n");
            caminharPreNivel(no.esq);
            caminharPreNivel(no.dir);
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

    public No balancear(No no) {

        if (no != null) {
            int fb = getFatorBalanceamento(no);
            if (fb < -1) {
                int fbEsq = getFatorBalanceamento(no.esq);
                if (fbEsq == 1) {
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarEsq(no);
            } else {
                if (fb > 1) {
                    int fbDir = getFatorBalanceamento(no.dir);
                    if (fbDir == -1) {
                        no.dir = rotacionarDir(no.dir);
                    }
                    no = rotacionarDir(no);
                } else {
                    no.setNivel();
                }
            }
        }
        return no;
    }

    private No rotacionarEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel();
        noDir.setNivel();
        return noDir;
    }

    private No rotacionarDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        no.setNivel();
        noEsq.setNivel();
        return noEsq;
    }

    private int getFatorBalanceamento(No no) {
        if (no.esq != null) {
            if (no.dir != null) {

                return no.dir.nivel - no.esq.nivel;
            } else {
                return 0 - no.esq.nivel;
            }
        } else {
            if (no.dir != null) {
                return no.dir.nivel;
            } else {
                return 0;
            }
        }
    }

}

class No {

    int elemento;
    No esq;
    No dir;
    int nivel;

    No(int x) {
        elemento = x;
        esq = null;
        dir = null;
        nivel = 0;
    }

    public void setNivel() {
        int nivelEsq = setNivel(esq);
        int nivelDir = setNivel(dir);
        if (nivelEsq >= nivelDir) {
            nivel = 1 + nivelEsq;
        } else {
            nivel = 1 + nivelDir;
        }
    }

    public int setNivel(No no) {
        if (no == null) {
            return 0;
        }

        int nivelEsq = setNivel(no.esq);
        int nivelDir = setNivel(no.dir);
        if (nivelEsq >= nivelDir) {
            no.nivel = 1 + nivelEsq;
        } else {
            no.nivel = 1 + nivelDir;
        }
        return no.nivel;
    }
}
