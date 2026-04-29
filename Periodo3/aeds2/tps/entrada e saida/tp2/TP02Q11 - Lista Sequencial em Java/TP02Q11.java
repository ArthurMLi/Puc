import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP02Q11 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            base.lerCsv("/tmp/restaurantes.csv");

            ListaSequencial lista = new ListaSequencial();

            int id = sc.nextInt();
            while (id != -1) {
                Restaurante r = base.pesquisarRestaurante(id);
                if (r != null) {
                    lista.inserirFim(r);
                }
                id = sc.nextInt();
            }

            int n = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < n; i++) {
                String linha = sc.nextLine();
                processarComando(linha, base, lista);
            }

            lista.mostrar();
        }
    }

    private static void processarComando(String linha, ColecaoRestaurantes base, ListaSequencial lista) {
        String[] partes = linha.split(" ");
        String cmd = partes[0];

        if (cmd.compareTo("II") == 0) {
            int id = Integer.parseInt(partes[1]);
            Restaurante r = base.pesquisarRestaurante(id);
            if (r != null) {
                lista.inserirInicio(r);
            }
        } else if (cmd.compareTo("IF") == 0) {
            int id = Integer.parseInt(partes[1]);
            Restaurante r = base.pesquisarRestaurante(id);
            if (r != null) {
                lista.inserirFim(r);
            }
        } else if (cmd.compareTo("I*") == 0) {
            int pos = Integer.parseInt(partes[1]);
            int id = Integer.parseInt(partes[2]);
            Restaurante r = base.pesquisarRestaurante(id);
            if (r != null) {
                lista.inserir(r, pos);
            }
        } else if (cmd.compareTo("RI") == 0) {
            Restaurante r = lista.removerInicio();
            if (r != null) {
                System.out.println("(R)" + r.nome);
            }
        } else if (cmd.compareTo("RF") == 0) {
            Restaurante r = lista.removerFim();
            if (r != null) {
                System.out.println("(R)" + r.nome);
            }
        } else if (cmd.compareTo("R*") == 0) {
            int pos = Integer.parseInt(partes[1]);
            Restaurante r = lista.remover(pos);
            if (r != null) {
                System.out.println("(R)" + r.nome);
            }
        }
    }

    static class ListaSequencial {
        private Restaurante[] array;
        private int n;

        ListaSequencial() {
            this.array = new Restaurante[16];
            this.n = 0;
        }

        private void garantirCapacidade() {
            if (n >= array.length) {
                Restaurante[] novo = new Restaurante[array.length * 2];
                for (int i = 0; i < array.length; i++) {
                    novo[i] = array[i];
                }
                array = novo;
            }
        }

        void inserirInicio(Restaurante x) {
            garantirCapacidade();
            for (int i = n; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = x;
            n++;
        }

        void inserirFim(Restaurante x) {
            garantirCapacidade();
            array[n++] = x;
        }

        void inserir(Restaurante x, int pos) {
            if (pos < 0 || pos > n) {
                return;
            }
            garantirCapacidade();
            for (int i = n; i > pos; i--) {
                array[i] = array[i - 1];
            }
            array[pos] = x;
            n++;
        }

        Restaurante removerInicio() {
            if (n == 0) {
                return null;
            }
            Restaurante resp = array[0];
            n--;
            for (int i = 0; i < n; i++) {
                array[i] = array[i + 1];
            }
            return resp;
        }

        Restaurante removerFim() {
            if (n == 0) {
                return null;
            }
            return array[--n];
        }

        Restaurante remover(int pos) {
            if (n == 0 || pos < 0 || pos >= n) {
                return null;
            }
            Restaurante resp = array[pos];
            n--;
            for (int i = pos; i < n; i++) {
                array[i] = array[i + 1];
            }
            return resp;
        }

        void mostrar() {
            for (int i = 0; i < n; i++) {
                System.out.println(array[i].formatar());
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
