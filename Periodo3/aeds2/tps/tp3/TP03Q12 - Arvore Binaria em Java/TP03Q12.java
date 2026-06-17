
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP03Q12 {

    public static void main(String[] args) {
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

            arvore.mostrarInOrder();
        }
    }

    static class No {

        Restaurante r;
        No esq, dir;

        No(Restaurante r) {
            this.r = r;
            esq = dir = null;
        }
    }

    static class ArvoreBinaria {

        private No raiz;
        private String caminho;

        ArvoreBinaria() {
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
