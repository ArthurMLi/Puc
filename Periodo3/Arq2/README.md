# EP04 - ULA 4 bits (Java + Arduino)

Projeto da atividade **EP04**: simulacao de uma ULA de 4 bits com dois programas separados.

- **Java**: traduz um programa fonte (`.ula`) para arquivo hexadecimal (`.hex`).
- **Arduino**: carrega/executa as instrucoes hexadecimais e mostra os resultados nos LEDs e no monitor serial.

## Estrutura do projeto

- `ula.java` -> tradutor `.ula` -> `.hex`
- `ula_arduino.ino` -> simulador da ULA no Arduino
- `testeula.ula` -> programa fonte de exemplo
- `testeula.hex` -> saida gerada pelo tradutor
- `EP04_2026_1.pdf` -> enunciado da atividade

## Requisitos

### Java

- JDK 8+ instalado
- Terminal (PowerShell/CMD)

### Arduino

- Arduino IDE
- Placa Arduino (ex.: Uno)
- 4 LEDs + resistores
- Ligacao dos LEDs:
  - Pino 13 -> F3 (bit mais significativo)
  - Pino 12 -> F2
  - Pino 11 -> F1
  - Pino 10 -> F0 (bit menos significativo)

## Como executar (Java - tradutor)

Na pasta do projeto (`Arq2`):

```bash
javac ula.java
java ula
```

Saida esperada:

- Gera `testeula.hex`
- Mostra no console as instrucoes convertidas

## Formato do arquivo `.ula`

Exemplo:

```text
inicio:
X=12;
Y=6;
W=AeB;
X=10;
Y=3;
W=AoB;
W=AeBn;
X=13;
W=nB;
fim.
```

## Mnemônicos suportados

- CopiaA
- CopiaB
- AxB
- nAxnB
- AeBn
- nB
- nAonB
- nA
- AonB
- UmL
- ZeroL
- AeB
- nAeB
- AenB
- AoB
- nAenB

## Como executar (Arduino)

1. Abra `ula_arduino.ino` na Arduino IDE.
2. Grave o sketch na placa.
3. Abra o **Monitor Serial** em **9600 baud**.
4. Envie cada linha do arquivo `testeula.hex` (uma por linha).
5. Envie `RUN` para iniciar a execucao.

### Comandos seriais do Arduino

- `RUN` -> executa o programa carregado
- `DUMP` -> mostra memoria e registradores
- `RESET` -> limpa memoria e registradores
- `XYZ` (3 hexadecimais) -> carrega 1 instrucao na memoria

## Funcionamento interno (Arduino)

- Memoria com 100 posicoes para instrucoes
- Banco de registradores:
  - `reg[0]` = PC
  - `reg[1]` = W
  - `reg[2]` = X
  - `reg[3]` = Y
- Intervalo de 4 segundos entre instrucoes
- Dump exibido apos carga e apos cada execucao

## Exemplo de traducao

Entrada (`testeula.ula`):

- `X=12; Y=6; W=AeB;`
- `X=10; Y=3; W=AoB;`
- `W=AeBn;`
- `X=13; W=nB;`

Saida (`testeula.hex`):

```text
C6B
A3E
A34
D35
```

## Publicando no GitHub

Sugestao de arquivos para versionar:

- `ula.java`
- `ula_arduino.ino`
- `testeula.ula`
- `README.md`
- `EP04_2026_1.pdf` (opcional)

Sugestao de arquivos para **nao** versionar:

- `*.class`

## Autor

Projeto desenvolvido para a disciplina de Arquitetura/Organizacao de Computadores (EP04).
