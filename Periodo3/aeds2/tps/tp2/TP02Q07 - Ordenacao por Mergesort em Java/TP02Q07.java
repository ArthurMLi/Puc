import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TP02Q07 {

    private static final String MATRICULA = "885134";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            base.lerCsv("/tmp/restaurantes.csv");

            ColecaoRestaurantes selecionados = lerEntradas(base, sc);

            long inicio = System.nanoTime();
            selecionados.mergesortPorCidadeNome();
            long fim = System.nanoTime();
            double tempoMs = (fim - inicio) / 1_000_000.0;

            escreverLog(selecionados.comparacoes, selecionados.movimentacoes, tempoMs, "_mergesort.txt");

            for (int i = 0; i < selecionados.getTamanho(); i++) {
                System.out.println(selecionados.getRestaurantes()[i].formatar());
            }
        }
    }

    private static ColecaoRestaurantes lerEntradas(ColecaoRestaurantes base, Scanner sc) {
        ColecaoRestaurantes selecionados = new ColecaoRestaurantes();
        int id = sc.nextInt();
        while (id != -1) {
            Restaurante r = base.pesquisarRestaurante(id);
            if (r != null) {
                selecionados.inserirRestaurante(r);
            }
            id = sc.nextInt();
        }
        return selecionados;
    }

    private static void escreverLog(long comparacoes, long movimentacoes, double tempoMs, String sufixo) {
        String nomeArquivo = MATRICULA + sufixo;
        try (PrintWriter pw = new PrintWriter(nomeArquivo)) {
            pw.printf("%s\t%d\t%d\t%.3f%n", MATRICULA, comparacoes, movimentacoes, tempoMs);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Nao foi possivel criar o arquivo de log", e);
        }
    }

    static class ColecaoRestaurantes {
        private int tamanho;
        private Restaurante[] restaurantes;
        long comparacoes;
        long movimentacoes;

        ColecaoRestaurantes() {
            this.tamanho = 0;
            this.restaurantes = new Restaurante[0];
            this.comparacoes = 0;
            this.movimentacoes = 0;
        }

        public int getTamanho() {
            return tamanho;
        }

        public Restaurante[] getRestaurantes() {
            return restaurantes;
        }

        void inserirRestaurante(Restaurante r) {
            Restaurante[] novo = new Restaurante[tamanho + 1];
            for (int i = 0; i < tamanho; i++) {
                novo[i] = restaurantes[i];
            }
            novo[tamanho] = r;
            restaurantes = novo;
            tamanho++;
        }

        Restaurante pesquisarRestaurante(int id) {
            for (int i = 0; i < tamanho; i++) {
                if (restaurantes[i].id == id) {
                    return restaurantes[i];
                }
            }
            return null;
        }

        private int compararCidadeNome(Restaurante a, Restaurante b) {
            comparacoes++;
            int cmp = a.cidade.compareTo(b.cidade);
            if (cmp != 0) {
                return cmp;
            }

            comparacoes++;
            return a.nome.compareTo(b.nome);
        }

        void mergesortPorCidadeNome() {
            comparacoes = 0;
            movimentacoes = 0;
            if (tamanho > 1) {
                Restaurante[] tmp = new Restaurante[tamanho];
                mergesort(0, tamanho - 1, tmp);
            }
        }

        private void mergesort(int esq, int dir, Restaurante[] tmp) {
            if (esq < dir) {
                int meio = (esq + dir) / 2;
                mergesort(esq, meio, tmp);
                mergesort(meio + 1, dir, tmp);
                intercalar(esq, meio, dir, tmp);
            }
        }

        private void intercalar(int esq, int meio, int dir, Restaurante[] tmp) {
            int i = esq;
            int j = meio + 1;
            int k = esq;

            while (i <= meio && j <= dir) {
                if (compararCidadeNome(restaurantes[i], restaurantes[j]) <= 0) {
                    tmp[k++] = restaurantes[i++];
                } else {
                    tmp[k++] = restaurantes[j++];
                }
                movimentacoes++;
            }

            while (i <= meio) {
                tmp[k++] = restaurantes[i++];
                movimentacoes++;
            }

            while (j <= dir) {
                tmp[k++] = restaurantes[j++];
                movimentacoes++;
            }

            for (int x = esq; x <= dir; x++) {
                restaurantes[x] = tmp[x];
                movimentacoes++;
            }
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
        int id;
        String nome;
        String cidade;
        private int capacidade;
        private double avaliacao;
        String[] tiposCozinha;
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