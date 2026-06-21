import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TP04Q6 {
    public static int comparacoes = 0;

    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            try {
                base.lerCsv("/tmp/restaurantes.csv");
            } catch (RuntimeException e) {
                base.lerCsv("../restaurantes.csv");
            }

            ArvoreBinaria arvore = new ArvoreBinaria();

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
        }
        long fim = System.currentTimeMillis();

        try {
            FileWriter fw = new FileWriter("885134_hibrida_arvore_arvore.txt");
            fw.write("885134\t" + (fim - inicio) + "\t" + comparacoes);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class NoAVL {
        Restaurante r;
        NoAVL esq, dir;
        int altura;

        NoAVL(Restaurante r) {
            this.r = r;
            this.esq = this.dir = null;
            this.altura = 1;
        }
    }

    static class ArvoreAVL {
        private NoAVL raiz;
        public String caminho;

        ArvoreAVL() {
            raiz = null;
        }

        void inserir(Restaurante r) {
            raiz = inserir(r, raiz);
        }

        private int altura(NoAVL no) {
            if (no == null) return 0;
            return no.altura;
        }

        private int getFatorBalanceamento(NoAVL no) {
            if (no == null) return 0;
            return altura(no.esq) - altura(no.dir);
        }

        private NoAVL rotacionarDireita(NoAVL y) {
            NoAVL x = y.esq;
            NoAVL T2 = x.dir;

            x.dir = y;
            y.esq = T2;

            y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;
            x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;

            return x;
        }

        private NoAVL rotacionarEsquerda(NoAVL x) {
            NoAVL y = x.dir;
            NoAVL T2 = y.esq;

            y.esq = x;
            x.dir = T2;

            x.altura = Math.max(altura(x.esq), altura(x.dir)) + 1;
            y.altura = Math.max(altura(y.esq), altura(y.dir)) + 1;

            return y;
        }

        private NoAVL inserir(Restaurante r, NoAVL no) {
            if (no == null) {
                return new NoAVL(r);
            }
            comparacoes++;
            int cmp = r.nome.compareTo(no.r.nome);
            if (cmp < 0) {
                no.esq = inserir(r, no.esq);
            } else if (cmp > 0) {
                comparacoes++;
                no.dir = inserir(r, no.dir);
            } else {
                comparacoes++;
                return no;
            }

            no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));

            int fb = getFatorBalanceamento(no);

            if (fb > 1 && r.nome.compareTo(no.esq.r.nome) < 0) {
                return rotacionarDireita(no);
            }

            if (fb < -1 && r.nome.compareTo(no.dir.r.nome) > 0) {
                return rotacionarEsquerda(no);
            }

            if (fb > 1 && r.nome.compareTo(no.esq.r.nome) > 0) {
                no.esq = rotacionarEsquerda(no.esq);
                return rotacionarDireita(no);
            }

            if (fb < -1 && r.nome.compareTo(no.dir.r.nome) < 0) {
                no.dir = rotacionarDireita(no.dir);
                return rotacionarEsquerda(no);
            }

            return no;
        }

        Restaurante pesquisar(String nome) {
            return pesquisar(nome, raiz);
        }

        private Restaurante pesquisar(String nome, NoAVL no) {
            if (no == null) {
                return null;
            }
            comparacoes++;
            int cmp = nome.compareTo(no.r.nome);
            if (cmp < 0) {
                caminho += " esq";
                return pesquisar(nome, no.esq);
            } else if (cmp > 0) {
                comparacoes++;
                caminho += " dir";
                return pesquisar(nome, no.dir);
            } else {
                comparacoes++;
                return no.r;
            }
        }
    }

    static class NoBST {
        int chave;
        ArvoreAVL arvSec;
        NoBST esq, dir;

        NoBST(int chave) {
            this.chave = chave;
            this.arvSec = new ArvoreAVL();
            this.esq = this.dir = null;
        }
    }

    static class ArvoreBinaria {
        private NoBST raiz;

        ArvoreBinaria() {
            raiz = null;
        }

        void inserir(Restaurante r) {
            int chave = r.capacidade % 15;
            raiz = inserir(chave, r, raiz);
        }

        private NoBST inserir(int chave, Restaurante r, NoBST no) {
            if (no == null) {
                no = new NoBST(chave);
                no.arvSec.inserir(r);
            } else if (chave < no.chave) {
                no.esq = inserir(chave, r, no.esq);
            } else if (chave > no.chave) {
                no.dir = inserir(chave, r, no.dir);
            } else {
                no.arvSec.inserir(r);
            }
            return no;
        }

        String pesquisar(String nome) {
            String caminho = "RAIZ";
            return pesquisar(nome, raiz, caminho);
        }

        private String pesquisar(String nome, NoBST no, String caminho) {
            if (no == null) {
                return caminho + " NAO";
            }

            no.arvSec.caminho = " raiz";
            Restaurante res = no.arvSec.pesquisar(nome);
            String pathSec = no.arvSec.caminho;

            if (res != null) {
                return caminho + pathSec + " SIM " + res.formatar();
            } else {
                String caminhoAnterior = caminho + pathSec;

                String resultadoEsq = pesquisar(nome, no.esq, caminhoAnterior + " ESQ");
                if (resultadoEsq.contains("SIM")) {
                    return resultadoEsq;
                }

                // Extrai a parte inicial que termina em ESQ ... NAO para tirar o ESQ e colocar DIR
                String baseSemEsq = resultadoEsq.substring(0, resultadoEsq.lastIndexOf(" NAO"));

                String resultadoDir = pesquisar(nome, no.dir, baseSemEsq + " DIR");
                if (resultadoDir.contains("SIM")) {
                    return resultadoDir;
                }

                return resultadoDir;
            }
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
