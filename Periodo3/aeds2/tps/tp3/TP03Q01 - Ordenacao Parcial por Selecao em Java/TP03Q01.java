
// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class TP03Q01 {
   private static final String MATRICULA = "885134";

   public TP03Q01() {
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);

      try {
         ColecaoRestaurantes var2 = new ColecaoRestaurantes();
         var2.lerCsv("/tmp/restaurantes.csv");
         ColecaoRestaurantes var3 = lerEntradas(var2, var1);
         if (var1.hasNextLine()) {
            var1.nextLine();
         }

         long var4 = System.nanoTime();
         var3.selecaoColecaoRestaurantes(10);
         long var6 = System.nanoTime();
         double var8 = (double)(var6 - var4) / (double)1000000.0F;

         for(int var10 = 0; var10 < var3.tamanho; ++var10) {
            System.out.println(var3.restaurantes[var10].formatar());
         }

         escreverLog(var3.comparacoes, var3.movimentacoes, var8, "_sequencial_parcial.txt");
      } catch (Throwable var12) {
         try {
            var1.close();
         } catch (Throwable var11) {
            var12.addSuppressed(var11);
         }

         throw var12;
      }

      var1.close();
   }

   private static ColecaoRestaurantes lerEntradas(ColecaoRestaurantes var0, Scanner var1) {
      ColecaoRestaurantes var2 = new ColecaoRestaurantes();

      for(int var3 = var1.nextInt(); var3 != -1; var3 = var1.nextInt()) {
         Restaurante var4 = var0.pesquisarRestaurante(var3);
         if (var4 != null) {
            var2.inserirRestaurante(var4);
         }
      }

      return var2;
   }

   private static void escreverLog(long var0, long var2, double var4, String var6) {
      String var7 = MATRICULA + var6;

      try {
         PrintWriter var8 = new PrintWriter(var7);

         try {
            if (var2 == 0L) {
               var8.printf("%s\t%d\t%.3f%n", "885134", var0, var4);
            } else {
               var8.printf("%s\t%d\t%d\t%.3f%n", "885134", var0, var2, var4);
            }
         } catch (Throwable var12) {
            try {
               var8.close();
            } catch (Throwable var11) {
               var12.addSuppressed(var11);
            }

            throw var12;
         }

         var8.close();
      } catch (FileNotFoundException var13) {
         throw new RuntimeException("Nao foi possivel criar o arquivo de log", var13);
      }
   }

   static class ColecaoRestaurantes {
      private int tamanho = 0;
      private Restaurante[] restaurantes = new Restaurante[0];
      long comparacoes = 0L;
      long movimentacoes = 0L;

      public ColecaoRestaurantes() {
      }

      public int getTamanho() {
         return this.tamanho;
      }

      public Restaurante[] getRestaurantes() {
         return this.restaurantes;
      }

      public void selecaoColecaoRestaurantes(int var1) {
         for(int var2 = 0; var2 < var1; ++var2) {
            int var3 = var2;

            for(int var4 = var2 + 1; var4 < this.tamanho; ++var4) {
               ++this.comparacoes;
               if (this.restaurantes[var4].nome.compareTo(this.restaurantes[var3].nome) < 0) {
                  var3 = var4;
               }
            }

            this.swap(var3, var2);
         }

      }

      private void swap(int var1, int var2) {
         Restaurante var3 = this.restaurantes[var1];
         this.restaurantes[var1] = this.restaurantes[var2];
         this.restaurantes[var2] = var3;
         this.movimentacoes += 3L;
      }

      void inserirRestaurante(Restaurante var1) {
         Restaurante[] var2 = new Restaurante[this.tamanho + 1];

         for(int var3 = 0; var3 < this.tamanho; ++var3) {
            var2[var3] = this.restaurantes[var3];
         }

         var2[this.tamanho] = var1;
         this.restaurantes = var2;
         ++this.tamanho;
      }

      Restaurante pesquisarRestaurante(int var1) {
         for(int var2 = 0; var2 < this.tamanho; ++var2) {
            if (this.restaurantes[var2].id == var1) {
               return this.restaurantes[var2];
            }
         }

         return null;
      }

      public void lerCsv(String var1) {
         try {
            int var2 = 0;
            Scanner var3 = new Scanner(new File(var1));

            try {
               if (var3.hasNextLine()) {
                  var3.nextLine();
               }

               while(var3.hasNextLine()) {
                  String var4 = var3.nextLine();
                  if (var4 != null && var4.trim().length() > 0) {
                     ++var2;
                  }
               }
            } catch (Throwable var9) {
               try {
                  var3.close();
               } catch (Throwable var7) {
                  var9.addSuppressed(var7);
               }

               throw var9;
            }

            var3.close();
            this.restaurantes = new Restaurante[var2];
            this.tamanho = var2;
            var3 = new Scanner(new File(var1));

            try {
               if (var3.hasNextLine()) {
                  var3.nextLine();
               }

               int var12 = 0;

               while(var3.hasNextLine() && var12 < this.tamanho) {
                  String var5 = var3.nextLine();
                  if (var5 != null && var5.trim().length() > 0) {
                     this.restaurantes[var12] = TP03Q01.Restaurante.parseRestaurante(var5);
                     ++var12;
                  }
               }
            } catch (Throwable var8) {
               try {
                  var3.close();
               } catch (Throwable var6) {
                  var8.addSuppressed(var6);
               }

               throw var8;
            }

            var3.close();
         } catch (FileNotFoundException var10) {
            throw new RuntimeException("Arquivo CSV nao encontrado: " + var1, var10);
         }
      }
   }

   static class Hora {
      private int hora;
      private int minuto;

      public Hora(int var1, int var2) {
         this.hora = var1;
         this.minuto = var2;
      }

      public static Hora parseHora(String var0) {
         Scanner var1 = new Scanner(var0);
         var1.useDelimiter("[:\\-]");
         int var2 = var1.nextInt();
         int var3 = var1.nextInt();
         var1.close();
         return new Hora(var2, var3);
      }

      public String formatar() {
         String var10000 = this.hora > 9 ? "" + this.hora : "0" + this.hora;
         return var10000 + ":" + (this.minuto > 9 ? "" + this.minuto : "0" + this.minuto);
      }

      public int getHora() {
         return this.hora;
      }

      public void setHora(int var1) {
         this.hora = var1;
      }

      public int getMinuto() {
         return this.minuto;
      }

      public void setMinuto(int var1) {
         this.minuto = var1;
      }
   }

   static class Data {
      private int ano;
      private int mes;
      private int dia;

      public Data(int var1, int var2, int var3) {
         this.ano = var1;
         this.mes = var2;
         this.dia = var3;
      }

      public static Data parseData(String var0) {
         Scanner var1 = new Scanner(var0);
         var1.useDelimiter("-");
         int var2 = var1.nextInt();
         int var3 = var1.nextInt();
         int var4 = var1.nextInt();
         var1.close();
         return new Data(var2, var3, var4);
      }

      public String formatar() {
         String var10000 = this.dia > 9 ? "" + this.dia : "0" + this.dia;
         String var1 = var10000 + "/" + (this.mes > 9 ? "" + this.mes : "0" + this.mes) + "/";
         if (this.ano > 999) {
            var1 = var1 + this.ano;
         } else if (this.ano > 99) {
            var1 = var1 + "0" + this.ano;
         } else if (this.ano > 9) {
            var1 = var1 + "00" + this.ano;
         } else {
            var1 = var1 + "000" + this.ano;
         }

         return var1;
      }

      public int getAno() {
         return this.ano;
      }

      public void setAno(int var1) {
         this.ano = var1;
      }

      public int getMes() {
         return this.mes;
      }

      public void setMes(int var1) {
         this.mes = var1;
      }

      public int getDia() {
         return this.dia;
      }

      public void setDia(int var1) {
         this.dia = var1;
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

      public Restaurante(int var1, String var2, String var3, int var4, double var5, String[] var7, int var8, Hora var9, Hora var10, Data var11, boolean var12) {
         this.id = var1;
         this.nome = var2;
         this.cidade = var3;
         this.capacidade = var4;
         this.avaliacao = var5;
         this.tiposCozinha = var7;
         this.faixaPreco = var8;
         this.horarioAbertura = var9;
         this.horarioFechamento = var10;
         this.dataAbertura = var11;
         this.aberto = var12;
      }

      public static Restaurante parseRestaurante(String var0) {
         Scanner var1 = new Scanner(var0);
         var1.useDelimiter(",");
         var1.useLocale(Locale.US);
         int var2 = var1.nextInt();
         String var3 = var1.next();
         String var4 = var1.next();
         int var5 = var1.nextInt();
         double var6 = var1.nextDouble();
         String[] var8 = var1.next().split(";");
         String var9 = var1.next().trim();
         int var10 = var9.length();
         String[] var11 = var1.next().split("-");
         Hora var12 = TP03Q01.Hora.parseHora(var11[0]);
         Hora var13 = TP03Q01.Hora.parseHora(var11[1]);
         Data var14 = TP03Q01.Data.parseData(var1.next());
         boolean var15 = var1.next().trim().compareTo("true") == 0;
         var1.close();
         return new Restaurante(var2, var3, var4, var5, var6, var8, var10, var12, var13, var14, var15);
      }

      public int getId() {
         return this.id;
      }

      public void setId(int var1) {
         this.id = var1;
      }

      public String getNome() {
         return this.nome;
      }

      public void setNome(String var1) {
         this.nome = var1;
      }

      public String getCidade() {
         return this.cidade;
      }

      public void setCidade(String var1) {
         this.cidade = var1;
      }

      public int getCapacidade() {
         return this.capacidade;
      }

      public void setCapacidade(int var1) {
         this.capacidade = var1;
      }

      public double getAvaliacao() {
         return this.avaliacao;
      }

      public void setAvaliacao(double var1) {
         this.avaliacao = var1;
      }

      public String[] getTiposCozinha() {
         return this.tiposCozinha;
      }

      public void setTiposCozinha(String[] var1) {
         this.tiposCozinha = var1;
      }

      public int getFaixaPreco() {
         return this.faixaPreco;
      }

      public void setFaixaPreco(int var1) {
         this.faixaPreco = var1;
      }

      public Hora getHorarioAbertura() {
         return this.horarioAbertura;
      }

      public void setHorarioAbertura(Hora var1) {
         this.horarioAbertura = var1;
      }

      public Hora getHorarioFechamento() {
         return this.horarioFechamento;
      }

      public void setHorarioFechamento(Hora var1) {
         this.horarioFechamento = var1;
      }

      public Data getDataAbertura() {
         return this.dataAbertura;
      }

      public void setDataAbertura(Data var1) {
         this.dataAbertura = var1;
      }

      public boolean isAberto() {
         return this.aberto;
      }

      public void setAberto(boolean var1) {
         this.aberto = var1;
      }

      public String formatar() {
         String var1 = "[" + this.id + " ## " + this.nome + " ## " + this.cidade + " ## " + this.capacidade + " ## " + this.avaliacao + " ## [";

         for(int var2 = 0; var2 < this.tiposCozinha.length; ++var2) {
            var1 = var1 + this.tiposCozinha[var2];
            if (var2 < this.tiposCozinha.length - 1) {
               var1 = var1 + ",";
            }
         }

         var1 = var1 + "] ## ";

         for(int var5 = 0; var5 < this.faixaPreco; ++var5) {
            var1 = var1 + "$";
         }

         var1 = var1 + " ## " + this.horarioAbertura.formatar() + "-" + this.horarioFechamento.formatar() + " ## " + this.dataAbertura.formatar() + " ## " + this.aberto + "]";
         return var1;
      }
   }
}
