
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TP02Q04 {

    private static final String MATRICULA = "885134";

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        ColecaoRestaurantes base = new ColecaoRestaurantes();
        base.lerCsv("/tmp/restaurantes.csv");

        ColecaoRestaurantes selecionados = lerEntradas(base, sc);

        long inicio = System.nanoTime();
        selecionados.insercaoPorCidade();
        long fim = System.nanoTime();
        double tempoMs = (fim - inicio) / 1_000_000.0;

        escreverLog(selecionados.comparacoes, selecionados.movimentacoes, tempoMs);

        Restaurante[] restaurantes = selecionados.getRestaurantes();
        for (int i = 0; i < selecionados.getTamanho(); i++) {
            System.out.println(restaurantes[i].formatar());
        }

        sc.close();
    }

    private static ColecaoRestaurantes lerEntradas(ColecaoRestaurantes base, Scanner sc) {
        ColecaoRestaurantes selecionados = new ColecaoRestaurantes();
        int id = sc.nextInt();
        while (id != -1) {
            Restaurante encontrado = base.pesquisarRestaurante(id);
            if (encontrado != null) {
                selecionados.inserirRestaurante(encontrado);
            }
            id = sc.nextInt();
        }

        return selecionados;
    }

    private static void escreverLog(long comparacoes, long movimentacoes, double tempoMs) {
        String nomeArquivo = MATRICULA + "_insercao.txt";
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

        public ColecaoRestaurantes() {
            this.tamanho = 0;
            this.restaurantes = new Restaurante[0];
        }

        public int getTamanho() {
            return tamanho;
        }

        public Restaurante[] getRestaurantes() {
            return restaurantes;
        }

        void inserirRestaurante(Restaurante r){
            Restaurante[] novo = new Restaurante[tamanho + 1];
            for(int i = 0; i < tamanho; i++) {
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

        public void insercaoPorCidade() {
            comparacoes = 0;
            movimentacoes = 0;

            for (int i = 1; i < tamanho; i++) {
                Restaurante tmp = restaurantes[i];
                int j = i - 1;

                while (j >= 0) {
                    comparacoes++;
                    if (restaurantes[j].getCidade().compareTo(tmp.getCidade()) > 0) {
                        restaurantes[j + 1] = restaurantes[j];
                        movimentacoes++;
                        j--;
                    } else {
                        break;
                    }
                }

                restaurantes[j + 1] = tmp;
                movimentacoes++;
            }

        }

        public void lerCsv(String path) {
            try {
                int count = 0;

                try (Scanner sc = new Scanner(new File(path))) {
                    if (sc.hasNextLine()) {
                        sc.nextLine(); // cabecalho
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
                        sc.nextLine(); // cabecalho
                    }
                    int i = 0;
                    while (sc.hasNextLine() && i < tamanho) {
                        String linha = sc.nextLine();
                        if (linha != null && linha.trim().length() > 0) {
                            restaurantes[i] = Restaurante.parseRestaurante(linha);
                            i++;
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

        public Hora(int hora, int minuto) {
            this.hora = hora;
            this.minuto = minuto;
        }

        public static Hora parseHora(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter("[:\\-]");
            int hora = sc.nextInt();
            int minuto = sc.nextInt();
            sc.close();
            return new Hora(hora, minuto);
        }

        public String formatar() {
            return (hora > 9 ? (hora + "") : ("0" + hora)) + ":" + (minuto > 9 ? (minuto + "") : ("0" + minuto));
        }

        public int getHora() {
            return hora;
        }

        public void setHora(int hora) {
            this.hora = hora;
        }

        public int getMinuto() {
            return minuto;
        }

        public void setMinuto(int minuto) {
            this.minuto = minuto;
        }
    }

    static class Data {

        private int ano;
        private int mes;
        private int dia;

        public Data(int ano, int mes, int dia) {
            this.ano = ano;
            this.mes = mes;
            this.dia = dia;
        }

        public static Data parseData(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter("-");
            int ano = sc.nextInt();
            int mes = sc.nextInt();
            int dia = sc.nextInt();
            sc.close();
            return new Data(ano, mes, dia);
        }

        public String formatar() {
            String temp = (dia > 9 ? (dia + "") : ("0" + dia)) + "/" + (mes > 9 ? (mes + "") : ("0" + mes)) + "/";
            if (ano > 999) {
                temp += "" + ano;
            } else {
                if (ano > 99) {
                    temp += "0" + ano;
                } else {
                    if (ano > 9) {
                        temp += "00" + ano;
                    } else {
                        temp += "000" + ano;
                    }
                }
            }
            return temp;
        }

        public int getAno() {
            return ano;
        }

        public void setAno(int ano) {
            this.ano = ano;
        }

        public int getMes() {
            return mes;
        }

        public void setMes(int mes) {
            this.mes = mes;
        }

        public int getDia() {
            return dia;
        }

        public void setDia(int dia) {
            this.dia = dia;
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

        public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
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

        public static Restaurante parseRestaurante(String s) {
            Scanner sc = new Scanner(s);
            sc.useDelimiter(",");
            sc.useLocale(java.util.Locale.US);
            int id = sc.nextInt();
            String nome = sc.next();
            String cidade = sc.next();
            int capacidade = sc.nextInt();
            double avaliacao = sc.nextDouble();
            String[] tiposCozinha = sc.next().split(";");
            String preco = sc.next().trim();
            int faixaPreco = preco.length();
            String[] temp = sc.next().split("-");
            Hora horarioAbertura = Hora.parseHora(temp[0]);
            Hora horarioFechamento = Hora.parseHora(temp[1]);
            Data dataAbertura = Data.parseData(sc.next());
            boolean aberto = sc.next().trim().compareTo("true") == 0;
            sc.close();

            return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco,
                    horarioAbertura, horarioFechamento, dataAbertura, aberto);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public int getCapacidade() {
            return capacidade;
        }

        public void setCapacidade(int capacidade) {
            this.capacidade = capacidade;
        }

        public double getAvaliacao() {
            return avaliacao;
        }

        public void setAvaliacao(double avaliacao) {
            this.avaliacao = avaliacao;
        }

        public String[] getTiposCozinha() {
            return tiposCozinha;
        }

        public void setTiposCozinha(String[] tiposCozinha) {
            this.tiposCozinha = tiposCozinha;
        }

        public int getFaixaPreco() {
            return faixaPreco;
        }

        public void setFaixaPreco(int faixaPreco) {
            this.faixaPreco = faixaPreco;
        }

        public Hora getHorarioAbertura() {
            return horarioAbertura;
        }

        public void setHorarioAbertura(Hora horarioAbertura) {
            this.horarioAbertura = horarioAbertura;
        }

        public Hora getHorarioFechamento() {
            return horarioFechamento;
        }

        public void setHorarioFechamento(Hora horarioFechamento) {
            this.horarioFechamento = horarioFechamento;
        }

        public Data getDataAbertura() {
            return dataAbertura;
        }

        public void setDataAbertura(Data dataAbertura) {
            this.dataAbertura = dataAbertura;
        }

        public boolean isAberto() {
            return aberto;
        }

        public void setAberto(boolean aberto) {
            this.aberto = aberto;
        }

        public String formatar() {
            String temp = "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + avaliacao
                    + " ## [";

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
                    + dataAbertura.formatar()
                    + " ## " + aberto + "]";

            return temp;
        }
    }

}
