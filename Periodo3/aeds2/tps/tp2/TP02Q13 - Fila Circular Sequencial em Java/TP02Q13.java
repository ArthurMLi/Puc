import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP02Q13 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            base.lerCsv("/tmp/restaurantes.csv");

            FilaCircular fila = new FilaCircular(5);

            int id = sc.nextInt();
            while (id != -1) {
                Restaurante r = base.pesquisarRestaurante(id);
                if (r != null) {
                    fila.inserir(r);
                }
                id = sc.nextInt();
            }

            int n = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < n; i++) {
                String linha = sc.nextLine();
                String[] partes = linha.split(" ");

                if (partes[0].compareTo("I") == 0) {
                    int novoId = Integer.parseInt(partes[1]);
                    Restaurante r = base.pesquisarRestaurante(novoId);
                    if (r != null) {
                        fila.inserir(r);
                    }
                } else if (partes[0].compareTo("R") == 0) {
                    Restaurante removido = fila.remover();
                    if (removido != null) {
                        System.out.println("(R)" + removido.nome);
                    }
                }
            }

            fila.mostrar();
        }
    }

    static class FilaCircular {
        private Restaurante[] array;
        private int primeiro;
        private int ultimo;
        private int capacidade;

        FilaCircular(int tamanhoMaximo) {
            this.capacidade = tamanhoMaximo + 1;
            this.array = new Restaurante[this.capacidade];
            this.primeiro = 0;
            this.ultimo = 0;
        }

        private boolean cheia() {
            return ((ultimo + 1) % capacidade) == primeiro;
        }

        private boolean vazia() {
            return primeiro == ultimo;
        }

        void inserir(Restaurante x) {
            if (cheia()) {
                Restaurante removido = removerInterno();
                if (removido != null) {
                    System.out.println("(R)" + removido.nome);
                }
            }

            array[ultimo] = x;
            ultimo = (ultimo + 1) % capacidade;

            long media = Math.round(mediaAnoAbertura());
            System.out.println("(I)" + media);
        }

        Restaurante remover() {
            return removerInterno();
        }

        private Restaurante removerInterno() {
            if (vazia()) {
                return null;
            }

            Restaurante resp = array[primeiro];
            primeiro = (primeiro + 1) % capacidade;
            return resp;
        }

        private double mediaAnoAbertura() {
            if (vazia()) {
                return 0.0;
            }

            int i = primeiro;
            int qtd = 0;
            long soma = 0;

            while (i != ultimo) {
                soma += array[i].dataAbertura.ano;
                qtd++;
                i = (i + 1) % capacidade;
            }

            return (double) soma / qtd;
        }

        void mostrar() {
            int i = primeiro;
            while (i != ultimo) {
                System.out.println(array[i].formatar());
                i = (i + 1) % capacidade;
            }
        }
    }

    static class ColecaoRestaurantes {
        private int tamanho;
        private Restaurante[] restaurantes;

        ColecaoRestaurantes() {
            this.tamanho = 0;
            this.restaurantes = new Restaurante[0];
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

    static class Restaurante {
        private int id;
        private String nome;
        private String cidade;
        private int capacidade;
        private double avaliacao;
        private String[] tiposCozinha;
        private int faixaPreco;
        private Hora horarioAbertura;
        private Hora horarioFechamento;
        private Data dataAbertura;
        private boolean aberto;

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
}
