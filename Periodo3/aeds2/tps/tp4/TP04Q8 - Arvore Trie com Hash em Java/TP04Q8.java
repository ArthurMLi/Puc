import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TP04Q8 {
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

            ArvoreTrie arvore = new ArvoreTrie();

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
            FileWriter fw = new FileWriter("885134_arvore_trie_hash.txt");
            fw.write("885134\t" + (fim - inicio) + "\t" + comparacoes);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class NoTrie {
        char elemento;
        int tamanhoHash = 255;
        NoTrie[] hash;
        boolean isFim;
        Restaurante r;

        NoTrie(char elemento) {
            this.elemento = elemento;
            this.hash = new NoTrie[tamanhoHash];
            this.isFim = false;
            this.r = null;
        }

        int getHash(char c) {
            return c % tamanhoHash;
        }

        NoTrie getFilho(char c) {
            return hash[getHash(c)];
        }

        void setFilho(char c, NoTrie no) {
            hash[getHash(c)] = no;
        }
    }

    static class ArvoreTrie {
        private NoTrie raiz;

        ArvoreTrie() {
            raiz = new NoTrie(' ');
        }

        void inserir(Restaurante r) {
            String s = r.nome;
            NoTrie no = raiz;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (no.getFilho(c) == null) {
                    no.setFilho(c, new NoTrie(c));
                }
                no = no.getFilho(c);
            }
            no.isFim = true;
            no.r = r;
        }

        String pesquisar(String nome) {
            NoTrie no = raiz;
            StringBuilder caminho = new StringBuilder();

            for (int i = 0; i < nome.length(); i++) {
                char c = nome.charAt(i);
                comparacoes++;
                if (no.getFilho(c) != null) {
                    no = no.getFilho(c);
                    caminho.append(nome.charAt(i)).append(" ");
                } else {
                    return caminho.toString() + "NAO";
                }
            }
            if (no.isFim) {
                return caminho.toString() + "SIM " + no.r.formatar();
            }
            return caminho.toString() + "NAO";
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
