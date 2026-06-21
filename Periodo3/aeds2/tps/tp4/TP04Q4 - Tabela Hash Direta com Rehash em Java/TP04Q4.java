
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TP04Q4 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ColecaoRestaurantes base = new ColecaoRestaurantes();
            try {
                base.lerCsv("/tmp/restaurantes.csv");
            } catch (RuntimeException e) {
                base.lerCsv("../restaurantes.csv");
            }
            listaHash lista = new listaHash(83);
            int id = sc.nextInt();

            while (id != -1) {
                Restaurante r = base.pesquisarRestaurante(id);
                if (r != null){
                    lista.inserir(r);
                }
                id = sc.nextInt();
            }

            sc.nextLine();
            String nome = sc.nextLine();
            while (!nome.equals("FIM")) {
                Restaurante r = lista.pesquisar(nome);

                System.out.println((r != null) ? r.formatar(): "-1");
                nome = sc.nextLine();
            }

        }
    }

    static class listaHash {
        Restaurante lista[];
        int tamanho;

        public listaHash(int tam) {
            lista = new Restaurante[tam];
            tamanho = tam;
        }

        public int hash(int x) {
            return x % tamanho;
        }

        public int rehash(int x) {
            return (x + 1) % tamanho;
        }

        private int somarASCII(String s) {
            int soma = 0;
            for (int i = 0; i < s.length(); i++) {
                soma += s.charAt(i);
            }
            return soma;
        }

        public boolean inserir(Restaurante r) {
            int pos = hash(somarASCII(r.nome));
            boolean resp = true;
            if (lista[pos] == null) {
                lista[pos] = r;
            } else {
                pos = rehash(somarASCII(r.nome));
                if (lista[pos] == null) {
                    lista[pos] = r;
                } else {
                    System.out.println(r.nome);
                    resp = false;
                }
            }
            return resp;
        }

        public Restaurante pesquisar(String s) {
            int pos = hash(somarASCII(s));
            Restaurante resp = null;

            if (lista[pos] != null && lista[pos].nome.equals(s)) {
                resp = lista[pos];
            } else {
                pos = rehash(somarASCII(s));
                if (lista[pos] != null && lista[pos].nome.equals(s)) {
                    resp = lista[pos];
                }
            }
            return resp;
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
