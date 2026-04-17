// Importa a classe para representar arquivos e caminhos.
import java.io.File;
// Importa a excecao para arquivo nao encontrado.
import java.io.FileNotFoundException;
// Importa escritor de texto para gerar os arquivos de saida.
import java.io.PrintWriter;
// Importa leitor de texto para ler o arquivo .ula linha por linha.
import java.util.Scanner;

// Classe principal do tradutor de instrucoes da ULA.
public class ula {
    // Limite de instrucoes suportado pelo tradutor.
    public static final int MAX_INSTRUCOES = 200;
    // Limite manual de instrucoes para o arquivo de carga do Arduino.
    // Altere este valor livremente no inicio do arquivo.
    public static int LIMITE_CARGA_ARDUINO = 100;
    // Define arquivo de entrada com o programa fonte.
    public static String origem = "testeula_grande.ula";
    // Define arquivo de saida .hex (uma instrucao por linha).
    public static String destino = "testeula.hex";
    // Define arquivo de saida para colar no serial em blocos.
    public static String destinoSerial = "testeula_serial.txt";
    // Define arquivo de saida limitado ao valor de LIMITE_CARGA_ARDUINO.
    public static String destinoSerial100 = "testeula_serial_100.txt";

    // Metodo de entrada do programa.
    public static void main(String[] args) {
        // Permite informar outro arquivo de entrada via argumento.
        if (args != null && args.length > 0 && args[0] != null && args[0].trim().length() > 0) {
            origem = args[0].trim();
        }

        // Inicia tratamento de erros para toda a execucao principal.
        try {
            // Faz a traducao do .ula para vetor de strings hexadecimais.
            String[] hexa = converterPrograma();
            // Conta quantas instrucoes foram geradas de verdade.
            int tam = quantidadeInstrucoes(hexa);

            // Mostra nome do arquivo fonte usado.
            System.out.println("Arquivo fonte: " + origem);
            // Mostra nome do arquivo hex gerado.
            System.out.println("Arquivo HEX: " + destino);
            // Mostra total de instrucoes traduzidas.
            System.out.println("Total de instrucoes: " + tam);

            // Percorre e imprime cada instrucao traduzida.
            for (int i = 0; i < tam; i++) {
                // Imprime indice e valor da instrucao.
                System.out.println(i + ": " + hexa[i]);
            }

            // Gera arquivo serial com todas as instrucoes disponiveis.
            gerarCargaSerial(hexa, tam, destinoSerial, tam);
            // Gera arquivo serial limitado ao valor configurado no topo do arquivo.
            gerarCargaSerial(hexa, tam, destinoSerial100, LIMITE_CARGA_ARDUINO);

            // Informa que a traducao terminou.
            System.out.println("Traducao concluida.");
            // Informa como usar o arquivo gerado no Arduino.
            System.out.println("Agora envie o arquivo HEX para o Arduino pelo monitor serial.");
            // Mostra caminho/nome do arquivo em blocos.
            System.out.println("Arquivo para colar em blocos: " + destinoSerial);
            // Mostra caminho/nome do arquivo limitado para o valor configurado.
            System.out.println("Arquivo para Arduino (max " + LIMITE_CARGA_ARDUINO + "): " + destinoSerial100);
        // Captura erro quando arquivo de origem nao existe.
        } catch (FileNotFoundException e) {
            // Mostra mensagem amigavel para esse erro especifico.
            System.out.println("Erro: arquivo nao encontrado: " + origem);
        } catch (NumberFormatException e) {
            System.err.println("Erro numerico: " + e.getMessage());
        // Captura qualquer outro erro generico.
        } catch (Exception e) {
            // Mostra mensagem detalhada de erro.
            System.err.println("Erro: " + e.getMessage());
        }
    }

    // Traduz o programa .ula para vetor de instrucoes XYZ em hexadecimal.
    public static String[] converterPrograma() throws Exception {
        // Reserva espaco para ate MAX_INSTRUCOES instrucoes.
        String[] hexa = new String[MAX_INSTRUCOES];
        // Guarda a proxima posicao de escrita no vetor hexa.
        int pos = 0;
        // Valor atual de X durante leitura do fonte.
        int x = 0;
        // Valor atual de Y durante leitura do fonte.
        int y = 0;

        // Tenta abrir arquivo origem no diretorio atual.
        File arquivoOrigem = new File(origem);
        // Se nao existir no diretorio atual...
        if (!arquivoOrigem.exists()) {
            // ...tenta no diretorio Arq2.
            arquivoOrigem = new File("Arq2/" + origem);
        }

        // Se ainda nao existir, interrompe com erro claro.
        if (!arquivoOrigem.exists()) {
            throw new FileNotFoundException("Nao foi possivel localizar o arquivo de entrada.");
        }

        // Se o caminho existir mas nao for arquivo regular, interrompe.
        if (!arquivoOrigem.isFile()) {
            throw new Exception("O caminho de entrada nao e um arquivo valido: " + arquivoOrigem.getPath());
        }

        // Cria leitor para percorrer o arquivo fonte.
        Scanner sc = new Scanner(arquivoOrigem);
        int numeroLinha = 0;
        boolean encontrouFim = false;
        // Enquanto existir linha para ler...
        while (sc.hasNextLine()) {
            numeroLinha++;
            // Le a linha atual.
            String linha = sc.nextLine();
            // Remove comentarios e espacos extras.
            linha = limparLinha(linha);

            // Se a linha ficou vazia apos limpeza...
            if (linha.length() == 0) {
                // ...ignora essa linha.
                continue;
            }

            // Converte para maiusculo para comparar sem depender de caixa.
            String u = linha.toUpperCase();
            // Ignora marcadores de inicio e fim.
            if (u.equals("INICIO:") || u.equals("FIM.")) {
                if (u.equals("FIM.")) {
                    encontrouFim = true;
                }
                // Vai para a proxima linha.
                continue;
            }

            // Se for atribuicao de X...
            if (u.startsWith("X=")) {
                // ...atualiza o valor de X.
                x = lerNibble(u.substring(2), numeroLinha, "X");
            } else {
                // Se nao for X, testa se e atribuicao de Y.
                if (u.startsWith("Y=")) {
                    // Atualiza o valor de Y.
                    y = lerNibble(u.substring(2), numeroLinha, "Y");
                } else {
                    // Se nao for Y, testa se e operacao de W.
                    if (u.startsWith("W=")) {
                        // Extrai o mnemonico de W removendo ';' final.
                        String mnemonico = removerFim(u.substring(2), ';');
                        // Converte mnemonico para opcode.
                        int op = opcode(mnemonico);
                        // Se opcode invalido...
                        if (op < 0) {
                            // Fecha o arquivo antes de abortar.
                            sc.close();
                            // Lanca erro de mnemonico invalido.
                            throw new Exception("Linha " + numeroLinha + ": mnemônico invalido: " + mnemonico);
                        }

                        if (pos >= MAX_INSTRUCOES) {
                            sc.close();
                            throw new Exception("Linha " + numeroLinha + ": limite excedido. Maximo de " + MAX_INSTRUCOES + " instrucoes.");
                        }

                        // Monta instrucao XYZ em hexadecimal e salva no vetor.
                        hexa[pos] = nibbleHex(x) + "" + nibbleHex(y) + "" + nibbleHex(op);
                        // Avanca para proxima posicao do vetor.
                        pos++;
                    } else {
                        // Fecha arquivo antes de abortar por linha invalida.
                        sc.close();
                        // Lanca erro indicando linha nao reconhecida.
                        throw new Exception("Linha " + numeroLinha + " invalida: " + linha);
                    }
                }
            }
        }
        // Fecha scanner no final da leitura.
        sc.close();

        if (pos == 0) {
            throw new Exception("Nenhuma instrucao foi gerada. Verifique o arquivo .ula.");
        }

        if (!encontrouFim) {
            System.out.println("Aviso: o marcador FIM. nao foi encontrado, mas a traducao foi concluida.");
        }

        // Grava arquivo .hex com resultado da traducao.
        gravarHexa(hexa, pos);
        // Retorna vetor com instrucoes traduzidas.
        return hexa;
    }

    // Grava as instrucoes traduzidas no arquivo .hex.
    public static void gravarHexa(String[] hexa, int tam) throws Exception {
        // Cria referencia para arquivo destino no diretorio atual.
        File arqDestino = new File(destino);
        // Se programa nao estiver executando dentro de Arq2...
        if (!new File(".").getCanonicalPath().endsWith("Arq2")) {
            // ...ajusta caminho para Arq2/arquivo.
            arqDestino = new File("Arq2/" + destino);
        }

        // Abre escritor para o arquivo final.
        PrintWriter pw = new PrintWriter(arqDestino);
        // Escreve cada instrucao em uma linha.
        for (int i = 0; i < tam; i++) {
            // Grava instrucao da posicao atual.
            pw.println(hexa[i]);
        }
        // Fecha escritor ao terminar.
        pw.close();
    }

    // Gera arquivo para colar no monitor serial em blocos.
    public static void gerarCargaSerial(String[] hexa, int tam, String nomeArquivo, int limite) throws Exception {
        // Cria referencia para arquivo com nome informado.
        File arq = new File(nomeArquivo);
        // Se nao estiver no diretorio Arq2...
        if (!new File(".").getCanonicalPath().endsWith("Arq2")) {
            // ...ajusta caminho para Arq2/arquivo.
            arq = new File("Arq2/" + nomeArquivo);
        }

        // Inicialmente total recebe toda quantidade traduzida.
        int total = tam;
        // Se ultrapassar limite especificado...
        if (total > limite) {
            // ...corta para o limite.
            total = limite;
        }

        // Abre escritor do arquivo de carga serial.
        PrintWriter pw = new PrintWriter(arq);
        // Primeiro comando: limpar memoria do Arduino.
        pw.println("RESET");

        // Percorre instrucoes em blocos de 20 por linha.
        for (int i = 0; i < total; i += 20) {
            // Acumulador de texto para a linha do bloco.
            String linha = "";
            // Calcula limite superior do bloco.
            int fim = i + 20;
            // Se passou do total, corrige para total.
            if (fim > total) {
                fim = total;
            }

            // Percorre cada instrucao dentro do bloco.
            for (int j = i; j < fim; j++) {
                // Se nao for a primeira, adiciona espaco separador.
                if (j > i) {
                    linha += " ";
                }
                // Concatena instrucao atual na linha.
                linha += hexa[j];
            }

            // Escreve linha pronta no arquivo serial.
            pw.println(linha);
        }

        // Comando final para executar o que foi carregado.
        pw.println("RUN");
        // Fecha escritor ao finalizar arquivo.
        pw.close();
    }

    // Remove comentarios e espacos extras de uma linha.
    public static String limparLinha(String linha) {
        // Procura comentario estilo //.
        int p = linha.indexOf("//");
        // Se encontrou comentario //...
        if (p >= 0) {
            // ...corta tudo a partir do comentario.
            linha = linha.substring(0, p);
        }
        // Procura comentario estilo #.
        p = linha.indexOf('#');
        // Se encontrou comentario #...
        if (p >= 0) {
            // ...corta tudo a partir do comentario.
            linha = linha.substring(0, p);
        }
        // Retorna texto sem espacos nas extremidades.
        return linha.trim();
    }

    // Converte texto para nibble valido (0..15).
    public static int lerNibble(String texto) throws Exception {
        return lerNibble(texto, -1, "valor");
    }

    // Converte texto para nibble valido (0..15) com contexto de erro.
    public static int lerNibble(String texto, int numeroLinha, String campo) throws Exception {
        // Normaliza string e remove ';' do final se houver.
        texto = removerFim(texto.trim().toUpperCase(), ';');

        if (texto.length() == 0) {
            if (numeroLinha > 0) {
                throw new Exception("Linha " + numeroLinha + ": campo " + campo + " vazio.");
            }
            throw new Exception("Campo vazio.");
        }

        // Variavel do valor convertido.
        int valor;
        // Se comecar com 0X, considera hexadecimal completo.
        if (texto.startsWith("0X")) {
            // Converte parte apos 0X em base 16.
            valor = Integer.parseInt(texto.substring(2), 16);
        } else {
            // Se nao for 0X, testa se e decimal puro.
            if (soNumero(texto)) {
                // Converte texto decimal para inteiro.
                valor = Integer.parseInt(texto, 10);
            } else {
                // Se nao for decimal, aceita um unico digito hexadecimal.
                if (texto.length() == 1 && ehHex(texto.charAt(0))) {
                    // Converte esse unico digito em base 16.
                    valor = Integer.parseInt(texto, 16);
                } else {
                    // Formato invalido de entrada.
                    throw new Exception("Valor invalido: " + texto);
                }
            }
        }

        // Valida faixa de 4 bits.
        if (valor < 0 || valor > 15) {
            // Lanca erro quando sair da faixa permitida.
            if (numeroLinha > 0) {
                throw new Exception("Linha " + numeroLinha + ": campo " + campo + " fora da faixa 0..15: " + valor);
            }
            throw new Exception("Valor fora da faixa 0..15: " + valor);
        }
        // Retorna valor validado.
        return valor;
    }

    // Verifica se string contem somente digitos 0..9.
    public static boolean soNumero(String s) {
        // Percorre cada caractere da string.
        for (int i = 0; i < s.length(); i++) {
            // Se qualquer caractere nao for numero...
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                // ...retorna falso imediatamente.
                return false;
            }
        }
        // Retorna verdadeiro somente se string nao estiver vazia.
        return s.length() > 0;
    }

    // Verifica se caractere e hexadecimal valido.
    public static boolean ehHex(char c) {
        // Aceita de 0 a 9 ou de A a F.
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
    }

    // Traduz mnemonico textual para codigo hexadecimal da ULA.
    public static int opcode(String m) {
        // Se for COPIAA, retorna 0.
        if (m.equals("COPIAA")) {
            return 0x0;
        // Se for COPIAB, retorna 1.
        } else if (m.equals("COPIAB")) {
            return 0x1;
        // Se for AXB, retorna 2.
        } else if (m.equals("AXB")) {
            return 0x2;
        // Se for NAXNB, retorna 3.
        } else if (m.equals("NAXNB")) {
            return 0x3;
        // Se for AEBN, retorna 4.
        } else if (m.equals("AEBN")) {
            return 0x4;
        // Se for NB, retorna 5.
        } else if (m.equals("NB")) {
            return 0x5;
        // Se for NAONB, retorna 6.
        } else if (m.equals("NAONB")) {
            return 0x6;
        // Se for NA, retorna 7.
        } else if (m.equals("NA")) {
            return 0x7;
        // Se for AONB, retorna 8.
        } else if (m.equals("AONB")) {
            return 0x8;
        // Se for UML, retorna 9.
        } else if (m.equals("UML")) {
            return 0x9;
        // Se for ZEROL, retorna A.
        } else if (m.equals("ZEROL")) {
            return 0xA;
        // Se for AEB, retorna B.
        } else if (m.equals("AEB")) {
            return 0xB;
        // Se for NAEB, retorna C.
        } else if (m.equals("NAEB")) {
            return 0xC;
        // Se for AENB, retorna D.
        } else if (m.equals("AENB")) {
            return 0xD;
        // Se for AOB, retorna E.
        } else if (m.equals("AOB")) {
            return 0xE;
        // Se for NAENB, retorna F.
        } else if (m.equals("NAENB")) {
            return 0xF;
        } else {
            // Se nao encontrar, retorna -1 (invalido).
            return -1;
        }
    }

    // Converte inteiro para caractere hexadecimal correspondente.
    public static char nibbleHex(int n) {
        // Tabela com os 16 simbolos hexadecimais.
        String h = "0123456789ABCDEF";
        // Usa apenas os 4 bits menos significativos e devolve o caractere.
        return h.charAt(n & 0xF);
    }

    // Remove caractere final especificado (ex.: ';') quando existir.
    public static String removerFim(String s, char c) {
        // Remove espacos extras das pontas.
        s = s.trim();
        // Se string nao vazia e terminar com caractere alvo...
        if (s.length() > 0 && s.charAt(s.length() - 1) == c) {
            // ...retorna sem o ultimo caractere.
            return s.substring(0, s.length() - 1).trim();
        }
        // Se nao terminar com caractere alvo, retorna sem alterar.
        return s;
    }

    // Conta quantas posicoes do vetor estao preenchidas com instrucao.
    public static int quantidadeInstrucoes(String[] hexa) {
        // Comeca contagem no inicio do vetor.
        int i = 0;
        // Anda enquanto nao chegar no fim e houver valor nao nulo.
        while (i < hexa.length && hexa[i] != null) {
            // Incrementa contador/indice.
            i++;
        }
        // Retorna total encontrado.
        return i;
    }
}

