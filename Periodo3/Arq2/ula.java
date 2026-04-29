import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ula {
    // Configuracoes gerais do tradutor.
    public static final int MAX_INSTRUCOES = 200;
    public static String origem = "TESTEULA.ula";
    public static String destino = "ula.hex";

    // Fluxo principal: traduz o .ula, gera arquivos de saida e imprime resumo.
    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0] != null && args[0].trim().length() > 0) {
            origem = args[0].trim();
        }

        try {
            String[] hexa = converterPrograma();
            int tam = quantidadeInstrucoes(hexa);

            System.out.println("Arquivo fonte: " + origem);
            System.out.println("Arquivo HEX: " + destino);
            System.out.println("Total de instrucoes: " + tam);

            // Exibe no terminal o indice e a instrucao gerada, para facilitar conferencia.
            for (int i = 0; i < tam; i++) {
                System.out.println(i + ": " + hexa[i]);
            }

            System.out.println("Traducao concluida.");
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo nao encontrado: " + origem);
        } catch (NumberFormatException e) {
            System.err.println("Erro numerico: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    // Le o arquivo .ula, valida comandos e converte para instrucoes XYZ em hexa.
    public static String[] converterPrograma() throws Exception {
        String[] hexa = new String[MAX_INSTRUCOES];
        int pos = 0;
        int x = 0;
        int y = 0;
        int totalErros = 0;
        StringBuilder erros = new StringBuilder();

        File arquivoOrigem = new File(origem);
        if (!arquivoOrigem.exists()) {
            arquivoOrigem = new File("Arq2/" + origem);
        }

        if (!arquivoOrigem.exists()) {
            throw new FileNotFoundException("Nao foi possivel localizar o arquivo de entrada.");
        }

        if (!arquivoOrigem.isFile()) {
            throw new Exception("O caminho de entrada nao e um arquivo valido: " + arquivoOrigem.getPath());
        }

        Scanner sc = new Scanner(arquivoOrigem);
        int numeroLinha = 0;
        boolean encontrouFim = false;

        while (sc.hasNextLine()) {
            numeroLinha++;
            String linha = limparLinha(sc.nextLine());
            String u = linha.toUpperCase();

            try {
                if (linha.length() == 0) {
                    throw new Exception("Linha " + numeroLinha + ": linha vazia.");
                }

                if (u.equals("INICIO:") || u.equals("FIM.")) {
                    if (u.equals("FIM.")) {
                        encontrouFim = true;
                    }
                    continue;
                }

                if (u.startsWith("X=")) {
                    exigirPontoVirgula(u, numeroLinha, "X");
                    x = lerNibble(u.substring(2), numeroLinha, "X");
                } else if (u.startsWith("Y=")) {
                    exigirPontoVirgula(u, numeroLinha, "Y");
                    y = lerNibble(u.substring(2), numeroLinha, "Y");
                } else if (u.startsWith("W=")) {
                    exigirPontoVirgula(u, numeroLinha, "W");
                    String mnemonico = removerFim(u.substring(2), ';');
                    int op = opcode(mnemonico);

                    if (op < 0) {
                        throw new Exception("Linha " + numeroLinha + ": mnem?nico invalido: " + mnemonico);
                    }

                    if (pos >= MAX_INSTRUCOES) {
                        throw new Exception("Linha " + numeroLinha + ": limite excedido. Maximo de " + MAX_INSTRUCOES + " instrucoes.");
                    }

                    hexa[pos] = "" + nibbleHex(x) + nibbleHex(y) + nibbleHex(op);
                    pos++;
                } else {
                    throw new Exception("Linha " + numeroLinha + " invalida: " + linha);
                }
            } catch (Exception e) {
                totalErros++;
                erros.append(e.getMessage()).append("\n");
            }
        }

        sc.close();

        if (totalErros > 0) {
            throw new Exception("Foram encontrados " + totalErros + " erro(s):\n" + erros.toString().trim());
        }

        if (pos == 0) {
            throw new Exception("Nenhuma instrucao foi gerada. Verifique o arquivo .ula.");
        }

        if (!encontrouFim) {
            System.out.println("Aviso: o marcador FIM. nao foi encontrado, mas a traducao foi concluida.");
        }

        gravarHexa(hexa, pos);
        return hexa;
    }

    // Grava o arquivo .hex com uma instrucao por linha.
    public static void gravarHexa(String[] hexa, int tam) throws Exception {
        File arqDestino = new File(destino);

        PrintWriter pw = new PrintWriter(arqDestino);
        for (int i = 0; i < tam; i++) {
            pw.println(hexa[i]);
        }
        pw.close();
    }

    // Remove comentarios (// e #) e espacos extras de uma linha.
    public static String limparLinha(String linha) {
        int p = linha.indexOf("//");
        if (p >= 0) {
            linha = linha.substring(0, p);
        }

        p = linha.indexOf('#');
        if (p >= 0) {
            linha = linha.substring(0, p);
        }

        return linha.trim();
    }

    // Sobrecarga simples para converter texto em nibble (0..15).
    public static int lerNibble(String texto) throws Exception {
        return lerNibble(texto, -1, "valor");
    }

    // Converte texto para nibble (0..15), validando formato e faixa com contexto de erro.
    public static int lerNibble(String texto, int numeroLinha, String campo) throws Exception {
        texto = removerFim(texto.trim().toUpperCase(), ';');

        if (texto.length() == 0) {
            if (numeroLinha > 0) {
                throw new Exception("Linha " + numeroLinha + ": campo " + campo + " vazio.");
            }
            throw new Exception("Campo vazio.");
        }

        int valor;
       if (soNumero(texto)) {
            valor = Integer.parseInt(texto, 10);
        } else if (texto.length() == 1 && ehHex(texto.charAt(0))) {
            valor = Integer.parseInt(texto, 16);
        } else {
            throw new Exception("Valor invalido: " + texto);
        }

        if (valor < 0 || valor > 15) {
            if (numeroLinha > 0) {
                throw new Exception("Linha " + numeroLinha + ": campo " + campo + " fora da faixa 0..15: " + valor);
            }
            throw new Exception("Valor fora da faixa 0..15: " + valor);
        }

        return valor;
    }

    // Verifica se uma string e decimal pura (somente 0..9 e nao vazia).
    public static boolean soNumero(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                return false;
            }
        }
        return s.length() > 0;
    }

    // Verifica se caractere pertence ao conjunto hexadecimal (0..9, A..F).
    public static boolean ehHex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
    }

    // Mapeia cada mnem?nico da ULA para seu opcode hexadecimal.
    public static int opcode(String m) {
        if (m.equals("COPIAA")) return 0x0;
        else if (m.equals("COPIAB")) return 0x1;
        else if (m.equals("AXB")) return 0x2;
        else if (m.equals("NAXNB")) return 0x3;
        else if (m.equals("AEBN")) return 0x4;
        else if (m.equals("NB")) return 0x5;
        else if (m.equals("NAONB")) return 0x6;
        else if (m.equals("NA")) return 0x7;
        else if (m.equals("AONB")) return 0x8;
        else if (m.equals("UML")) return 0x9;
        else if (m.equals("ZEROL")) return 0xA;
        else if (m.equals("AEB")) return 0xB;
        else if (m.equals("NAEB")) return 0xC;
        else if (m.equals("AENB")) return 0xD;
        else if (m.equals("AOB")) return 0xE;
        else if (m.equals("NAENB")) return 0xF;
        else return -1;
    }

    // Converte inteiro para um caractere hexadecimal usando apenas 4 bits.
    public static char nibbleHex(int n) {
        String h = "0123456789ABCDEF";
        return h.charAt(n & 0xF);
    }

    // Remove um caractere final especifico (como ';') quando presente.
    public static String removerFim(String s, char c) {
        s = s.trim();
        if (s.length() > 0 && s.charAt(s.length() - 1) == c) {
            return s.substring(0, s.length() - 1).trim();
        }
        return s;
    }

    // Exige ';' ao final de comandos X, Y e W para manter sintaxe consistente.
    public static void exigirPontoVirgula(String linha, int numeroLinha, String campo) throws Exception {
        if (!linha.endsWith(";")) {
            throw new Exception("Linha " + numeroLinha + ": comando " + campo + " sem ';' no final.");
        }
    }

    // Conta quantas instrucoes validas existem no vetor gerado.
    public static int quantidadeInstrucoes(String[] hexa) {
        int i = 0;
        while (i < hexa.length && hexa[i] != null) {
            i++;
        }
        return i;
    }
}

