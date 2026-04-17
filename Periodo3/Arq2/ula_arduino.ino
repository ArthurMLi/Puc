// Pinos de saida dos 4 bits do resultado (F3..F0).
int pinos[4] = {13, 12, 11, 10};

// Memoria de instrucoes da maquina (ate 100 instrucoes XYZ).
int memoria[100];

// Quantidade atual de instrucoes carregadas na memoria.
int tamMemoria = 0;

// Evita repetir o mesmo aviso de memoria cheia varias vezes.
bool memoriaCheiaAvisada = false;

// Banco de registradores:
// reg[0] = PC (program counter)
// reg[1] = W  (resultado da ULA)
// reg[2] = X  (operando A)
// reg[3] = Y  (operando B)
int reg[4] = {0, 0, 0, 0};

// Inicializacao do Arduino: serial, pinos e estado inicial.
void setup() {
  Serial.begin(9600);

  for (int i = 0; i < 4; i++) {
    pinMode(pinos[i], OUTPUT);
    digitalWrite(pinos[i], LOW);
  }

  Serial.println("ULA pronta. Envie XYZ e depois RUN.");
  dump();
}

// Loop principal: quando chegar texto na serial, processa a entrada.
void loop() {
  if (Serial.available() <= 0) {
    return;
  }

  String bloco = Serial.readString();
  processarEntrada(bloco);
}

// Quebra a entrada recebida em tokens separados por espacos, quebra de linha,
// virgula ou ponto-e-virgula. Isso permite colar em varios formatos.
void processarEntrada(String entrada) {
  entrada.toUpperCase();

  String token = "";
  for (int i = 0; i < entrada.length(); i++) {
    char c = entrada.charAt(i);

    if (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == ';' || c == ',') {
      processarToken(token);
      token = "";
    } else {
      token += c;
    }
  }

  processarToken(token);
}

// Interpreta cada token recebido:
// - comandos (RUN, DUMP, RESET)
// - uma instrucao XYZ
// - bloco continuo de hex com tamanho multiplo de 3
void processarToken(String token) {
  token.trim();
  if (token.length() == 0) {
    return;
  }

  if (token == "RUN") {
    executar();
    return;
  }

  if (token == "DUMP") {
    dump();
    return;
  }

  if (token == "RESET") {
    limpar();
    dump();
    return;
  }

  if (instrucaoValida(token)) {
    carregarInstrucao(token);
    return;
  }

  // Permite colar tudo junto: ex. C6BA3EA34D35
  if (soHex(token) && token.length() % 3 == 0) {
    for (int i = 0; i < token.length(); i += 3) {
      if (tamMemoria >= 100) {
        avisarMemoriaCheia();
        return;
      }

      String parte = token.substring(i, i + 3);
      carregarInstrucao(parte);
    }
    return;
  }

  Serial.print("Comando invalido: ");
  Serial.println(token);
}

// Carrega uma unica instrucao XYZ na memoria.
// Se lotar, mostra aviso e ignora novas instrucoes.
void carregarInstrucao(String s) {
  if (tamMemoria < 100) {
    memoriaCheiaAvisada = false;
    memoria[tamMemoria] = parseInstrucao(s);
    tamMemoria++;
    reg[0] = 0;
    dump();
  } else {
    avisarMemoriaCheia();
  }
}

// Mostra aviso de memoria cheia uma unica vez por ciclo de carga.
void avisarMemoriaCheia() {
  if (!memoriaCheiaAvisada) {
    Serial.println("Memoria cheia. Apenas as 100 primeiras instrucoes foram carregadas.");
    memoriaCheiaAvisada = true;
  }
}

// Limpa memoria, registradores e LEDs (estado inicial da maquina).
void limpar() {
  for (int i = 0; i < 100; i++) {
    memoria[i] = 0;
  }
  reg[0] = 0;
  reg[1] = 0;
  reg[2] = 0;
  reg[3] = 0;
  tamMemoria = 0;
  memoriaCheiaAvisada = false;
  escreverLeds(0);
}

// Executa o programa carregado de acordo com o PC.
// Para cada instrucao: decodifica XYZ, executa ULA, atualiza registradores,
// escreve nos LEDs, faz dump e espera 4 segundos.
void executar() {
  if (tamMemoria == 0) {
    Serial.println("Nenhuma instrucao carregada.");
    return;
  }

  while (reg[0] < tamMemoria) {
    int instr = memoria[reg[0]];
    int x = (instr >> 8) & 0xF;
    int y = (instr >> 4) & 0xF;
    int op = instr & 0xF;

    reg[2] = x;
    reg[3] = y;
    reg[1] = ula(x, y, op);

    escreverLeds(reg[1]);

    reg[0] = reg[0] + 1;
    dump();
    delay(4000);
  }

  Serial.println("Programa concluido!");
}

// Implementacao da ULA 4 bits conforme tabela de opcodes (0x0..0xF).
int ula(int x, int y, int s) {
  int nx = (~x) & 0xF;
  int ny = (~y) & 0xF;

  if (s == 0x0) return x;
  else if (s == 0x1) return y;
  else if (s == 0x2) return (x ^ y) & 0xF;
  else if (s == 0x3) return (nx ^ ny) & 0xF;
  else if (s == 0x4) return (~(x & y)) & 0xF;
  else if (s == 0x5) return ny;
  else if (s == 0x6) return (nx | ny) & 0xF;
  else if (s == 0x7) return nx;
  else if (s == 0x8) return (x | ny) & 0xF;
  else if (s == 0x9) return 0xF;
  else if (s == 0xA) return 0x0;
  else if (s == 0xB) return (x & y) & 0xF;
  else if (s == 0xC) return (nx & y) & 0xF;
  else if (s == 0xD) return (x & ny) & 0xF;
  else if (s == 0xE) return (x | y) & 0xF;
  else if (s == 0xF) return (nx & ny) & 0xF;
  else return 0;
}

// Escreve o resultado W nos 4 LEDs: pino 13 = bit 3 ... pino 10 = bit 0.
void escreverLeds(int valor) {
  digitalWrite(13, ((valor >> 3) & 1) ? HIGH : LOW);
  digitalWrite(12, ((valor >> 2) & 1) ? HIGH : LOW);
  digitalWrite(11, ((valor >> 1) & 1) ? HIGH : LOW);
  digitalWrite(10, ((valor >> 0) & 1) ? HIGH : LOW);
}

// Mostra estado atual da memoria e dos registradores no monitor serial.
// O marcador "->" indica a proxima instrucao apontada pelo PC.
void dump() {
  Serial.println("--------------------------------");
  Serial.print("Memoria:       | ");
  for (int i = 0; i < tamMemoria; i++) {
    if (i == reg[0]) {
      Serial.print("->");
    }
    printInstrucao(memoria[i]);
    Serial.print(" | ");
  }
  Serial.println();

  Serial.print("Registradores: | ");
  printHex(reg[0]);
  Serial.print(" | ");
  printHex(reg[1]);
  Serial.print(" | ");
  printHex(reg[2]);
  Serial.print(" | ");
  printHex(reg[3]);
  Serial.println(" |");
}

// Valida se token e uma instrucao no formato XYZ (3 chars hex).
bool instrucaoValida(String s) {
  if (s.length() != 3) {
    return false;
  }

  for (int i = 0; i < 3; i++) {
    if (!ehHex(s.charAt(i))) {
      return false;
    }
  }

  return true;
}

// Retorna true se caractere for hexadecimal valido (0-9, A-F).
bool ehHex(char c) {
  return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
}

// Retorna true se toda a string for composta por caracteres hexadecimais.
bool soHex(String s) {
  if (s.length() == 0) {
    return false;
  }

  for (int i = 0; i < s.length(); i++) {
    if (!ehHex(s.charAt(i))) {
      return false;
    }
  }

  return true;
}

// Converte um caractere hexadecimal para inteiro (0..15).
int hexParaInt(char c) {
  if (c >= '0' && c <= '9') {
    return c - '0';
  }
  return 10 + (c - 'A');
}

// Converte string XYZ para inteiro compacto: X no nibble alto, Y no meio e op no baixo.
int parseInstrucao(String s) {
  int x = hexParaInt(s.charAt(0));
  int y = hexParaInt(s.charAt(1));
  int op = hexParaInt(s.charAt(2));
  return (x << 8) | (y << 4) | op;
}

// Imprime inteiro como um unico digito hexadecimal (0..F).
void printHex(int n) {
  n = n & 0xF;
  if (n < 10) {
    Serial.print((char)('0' + n));
  } else {
    Serial.print((char)('A' + (n - 10)));
  }
}

// Imprime instrucao no formato XYZ para facilitar leitura da memoria.
void printInstrucao(int instr) {
  int x = (instr >> 8) & 0xF;
  int y = (instr >> 4) & 0xF;
  int op = instr & 0xF;
  printHex(x);
  printHex(y);
  printHex(op);
}
