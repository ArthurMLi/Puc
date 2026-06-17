
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP04Q1 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            try {
                base.lerCsv("/tmp/restaurantes.csv");
            } catch (RuntimeException e) {
                base.lerCsv("../restaurantes.csv");
            }

            ArvoreAVL arvore = new ArvoreAVL();

            int id = sc.nextInt();
            while (id != -1) {
                Restaurante r = base.pesquisarRestaurante(id);
                if (r != null) {
                    arvore.inserir(r);
                }
                id = sc.nextInt();
            }

            sc.nextLine();
            String nome = sc.nextLine();
            while (!nome.equals("FIM")) {
                System.out.println(arvore.pesquisar(nome));
                nome = sc.nextLine();
            }

            arvore.mostrarInOrder();
        }
    }

    static class No {

        Restaurante r;
        No esq, dir;
        private int nivel;

        No(Restaurante r) {
            this.r = r;
            esq = dir = null;
            nivel = 0;
        }

        public int getNivel() {
            return nivel;
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

    static class ArvoreAVL {

        private No raiz;
        private String caminho;

        ArvoreAVL() {
            raiz = null;
        }

        void inserir(Restaurante r) {
            if (raiz == null) {
                raiz = new No(r);
            } else {
                inserir(r, raiz);
            }
        }

        private void inserir(Restaurante r, No no) {
            int cmp = r.nome.compareTo(no.r.nome);
            if (cmp < 0) {
                if (no.esq == null) {
                    no.esq = new No(r);
                } else {
                    inserir(r, no.esq);
                }
            } else if (cmp > 0) {
                if (no.dir == null) {
                    no.dir = new No(r);
                } else {
                    inserir(r, no.dir);
                }
            }
        }

        String pesquisar(String nome) {
            caminho = "raiz";
            return pesquisar(nome, raiz);
        }

        private String pesquisar(String nome, No no) {
            if (no == null) {
                return caminho + " NAO";
            }
            int cmp = nome.compareTo(no.r.nome);
            if (cmp < 0) {
                caminho += " esq";
                return pesquisar(nome, no.esq);
            } else if (cmp > 0) {
                caminho += " dir";
                return pesquisar(nome, no.dir);
            } else {
                return caminho + " SIM";
            }
        }

        private No balancear(No no) {
            if (no == null) {
                return null;
            }
            // 1. Atualiza a altura do nó atual
            no.nivel = Math.max(obterAltura(no.esq), obterAltura(no.dir)) + 1;

            // 2. Obtém o fator de balanceamento
            int fb = obterFatorBalanceamento(no);

            // CASO 1: Desbalanceamento à Esquerda (Pendente para a esquerda)
            if (fb > 1) {
                // Zigue-zague (Esquerda-Direita): precisa de rotação dupla
                if (obterFatorBalanceamento(no.esq) < 0) {
                    no.esq = rotacionarEsq(no.esq);
                }
                // Rotação simples à direita (o seu método anterior)
                return rotacionarDir(no);
            }

            // CASO 2: Desbalanceamento à Direita (Pendente para a direita)
            if (fb < -1) {
                // Zigue-zague (Direita-Esquerda): precisa de rotação dupla
                if (obterFatorBalanceamento(no.dir) > 0) {
                    no.dir = rotacionarDir(no.dir);
                }
                // Rotação simples à esquerda
                return rotacionarEsq(no);
            }

            return no; // Retorna o nó sem alterações se já estiver balanceado
        }

        private int obterAltura(No no) {
            return no == null ? -1 : no.nivel;
        }

        private int obterFatorBalanceamento(No no) {
            if (no == null) {
                return 0;
            }
            return obterAltura(no.esq) - obterAltura(no.dir);
        }

        private No rotacionarDir(No no) {
            // no tem um filho a esquerda e um neto a esquerda
            // filho deve virar raiz e o no vira filho a direita desse no
            //sem perder os outros filhos
            No noEsq = no.esq;
            No noEsqDir = noEsq.dir;

            no.esq = noEsqDir;
            noEsq.dir = no;
            return noEsq;
        }

        private No rotacionarEsq(No no) {
            // no tem um filho a direita e um neto a direita
            // filho deve virar raiz e o no vira filho a esquerda desse no
            //sem perder os outros filhos
            No noDir = no.dir;
            No noDirEsq = noDir.esq;

            no.dir = noDirEsq;
            noDir.esq = no;
            return noDir;
        }

        int getFB(No no) {
            int resp = 0;
            if (no.esq != null) {
                if (no.dir != null) {
                    resp = no.esq.nivel - no.dir.nivel;
                } else {
                    resp = no.esq.nivel;
                }
            } else {
                if (no.dir != null) {
                    resp = 0 - no.dir.nivel;
                }
            }
            return resp;

        }

        void mostrarInOrder() {
            mostrarInOrder(raiz);
        }

        private void mostrarInOrder(No no) {
            if (no == null) {
                return;
            }
            mostrarInOrder(no.esq);
            System.out.println(no.r.formatar());
            mostrarInOrder(no.dir);
        }
    }

    static class ColecaoRestaurantes {

        private int tamanho;
        private Restaurante[] restaurantes;

        ColecaoRestaurantes() {
            tamanho = 0;
            restaurantes = new Restaurante[0];
        }

        Restaurante pesquisarRestaurante(int id) {
            for (int i = 0; i < tamanho; i++) {
                if (restaurantes[i].id == id) {
                    return restaurantes[i];
                }
            }
            return null;
        }

        void lerCsv(String path) {
            try {
                int count = 0;
                try (Scanner sc = new Scanner(new File(path))) {
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    while (sc.hasNextLine()) {
                        String linha = sc.nextLine();
                        if (linha != null && linha.trim().length() > 0) {
                            count++;
                        }
                    }
                }

                restaurantes = new Restaurante[count];
                tamanho = count;

                try (Scanner sc = new Scanner(new File(path))) {
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    int i = 0;
                    while (sc.hasNextLine() && i < tamanho) {
                        String linha = sc.nextLine();
                        if (linha != null && linha.trim().length() > 0) {
                            restaurantes[i++] = Restaurante.parseRestaurante(linha);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Arquivo CSV nao encontrado: " + path, e);
            }
        }
    }

    static class Restaurante {

        int id;
        String nome;
        String cidade;
        int capacidade;
        double avaliacao;
        String[] tiposCozinha;
        int faixaPreco;
        Hora horarioAbertura;
        Hora horarioFechamento;
        Data dataAbertura;
        boolean aberto;

        Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                String[] tiposCozinha, int faixaPreco, Hora horarioAbertura, Hora horarioFechamento,
                Data dataAbertura, boolean aberto) {
            this.id = id;
            this.nome = nome;
            this.cidade = cidade;
            this.capacidade = capacidade;
            this.avaliacao = avaliacao;
            this.tiposCozinha = tiposCozinha;
            this.faixaPreco = faixaPreco;
            this.horarioAbertura = horarioAbertura;
            this.horarioFechamento = horarioFechamento;
            this.dataAbertura = dataAbertura;
            this.aberto = aberto;
        }

        static Restaurante parseRestaurante(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter(",");
            sc.useLocale(java.util.Locale.US);
            int id = sc.nextInt();
            String nome = sc.next();
            String cidade = sc.next();
            int capacidade = sc.nextInt();
            double avaliacao = sc.nextDouble();
            String[] tiposCozinha = sc.next().split(";");
            int faixaPreco = sc.next().trim().length();
            String[] temp = sc.next().split("-");
            Hora horarioAbertura = Hora.parseHora(temp[0]);
            Hora horarioFechamento = Hora.parseHora(temp[1]);
            Data dataAbertura = Data.parseData(sc.next());
            boolean aberto = sc.next().trim().compareTo("true") == 0;
            sc.close();

            return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco,
                    horarioAbertura, horarioFechamento, dataAbertura, aberto);
        }

        String formatar() {
            String temp = "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + avaliacao + " ## [";

            for (int i = 0; i < tiposCozinha.length; i++) {
                temp += tiposCozinha[i];
                if (i < tiposCozinha.length - 1) {
                    temp += ",";
                }
            }

            temp += "] ## ";

            for (int i = 0; i < faixaPreco; i++) {
                temp += "$";
            }

            temp += " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar() + " ## "
                    + dataAbertura.formatar() + " ## " + aberto + "]";

            return temp;
        }
    }

    static class Hora {

        private int hora;
        private int minuto;

        Hora(int hora, int minuto) {
            this.hora = hora;
            this.minuto = minuto;
        }

        static Hora parseHora(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter("[:\\-]");
            int h = sc.nextInt();
            int m = sc.nextInt();
            sc.close();
            return new Hora(h, m);
        }

        String formatar() {
            return (hora > 9 ? (hora + "") : ("0" + hora)) + ":" + (minuto > 9 ? (minuto + "") : ("0" + minuto));
        }
    }

    static class Data {

        private int ano;
        private int mes;
        private int dia;

        Data(int ano, int mes, int dia) {
            this.ano = ano;
            this.mes = mes;
            this.dia = dia;
        }

        static Data parseData(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter("-");
            int a = sc.nextInt();
            int m = sc.nextInt();
            int d = sc.nextInt();
            sc.close();
            return new Data(a, m, d);
        }

        String formatar() {
            String temp = (dia > 9 ? (dia + "") : ("0" + dia)) + "/" + (mes > 9 ? (mes + "") : ("0" + mes)) + "/";
            if (ano > 999) {
                temp += "" + ano;
            } else if (ano > 99) {
                temp += "0" + ano;
            } else if (ano > 9) {
                temp += "00" + ano;
            } else {
                temp += "000" + ano;
            }
            return temp;
        }
    }
}
