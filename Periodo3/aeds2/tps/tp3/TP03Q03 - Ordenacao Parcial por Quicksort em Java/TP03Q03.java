// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class TP03Q03 {
   private static final String MATRICULA = "885134";

   public TP03Q03() {
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      Throwable var2 = null;

      try {
         ColecaoRestaurantes var3 = new ColecaoRestaurantes();
         var3.lerCsv("../../tp2/restaurantes.csv");
         ColecaoRestaurantes var4 = lerEntradas(var3, var1);
         if (var1.hasNextLine()) {
            var1.nextLine();
         }

         long var5 = System.nanoTime();
         var4.quicksort(10);
         long var7 = System.nanoTime();
         double var9 = (double)(var7 - var5) / (double)1000000.0F;

         for(int var11 = 0; var11 < var4.tamanho; ++var11) {
            System.out.println(var4.restaurantes[var11].formatar());
         }

         escreverLog(var4.comparacoes, var4.movimentacoes, var9, "_sequencial_parcial.txt");
      } catch (Throwable var19) {
         var2 = var19;
         throw var19;
      } finally {
         if (var1 != null) {
            if (var2 != null) {
               try {
                  var1.close();
               } catch (Throwable var18) {
                  var2.addSuppressed(var18);
               }
            } else {
               var1.close();
            }
         }

      }

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
      String var7 = "885134" + var6;

      try {
         PrintWriter var8 = new PrintWriter(var7);
         Throwable var9 = null;

         try {
            if (var2 == 0L) {
               var8.printf("%s\t%d\t%.3f%n", "885134", var0, var4);
            } else {
               var8.printf("%s\t%d\t%d\t%.3f%n", "885134", var0, var2, var4);
            }
         } catch (Throwable var19) {
            var9 = var19;
            throw var19;
         } finally {
            if (var8 != null) {
               if (var9 != null) {
                  try {
                     var8.close();
                  } catch (Throwable var18) {
                     var9.addSuppressed(var18);
                  }
               } else {
                  var8.close();
               }
            }

         }

      } catch (FileNotFoundException var21) {
         throw new RuntimeException("Nao foi possivel criar o arquivo de log", var21);
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

      public void quicksort(int var1) {
         this.quicksort(0, this.tamanho - 1, var1);
      }

      private void quicksort(int var1, int var2, int var3) {
         int var4 = var1;
         int var5 = var2;
         String var6 = this.restaurantes[(var1 + var2) / 2].nome;

         while(var4 <= var5) {
            while(this.restaurantes[var4].nome.compareTo(var6) > 0) {
               ++this.comparacoes;
               ++var4;
            }

            while(this.restaurantes[var5].nome.compareTo(var6) < 0) {
               ++this.comparacoes;
               --var5;
            }

            if (var4 <= var5) {
               this.swap(var4, var5);
               ++var4;
               --var5;
            }
         }

         if (var1 < var5 && var5 < var3) {
            ++this.comparacoes;
            this.quicksort(var1, var5, var3);
         }

         if (var4 < var2) {
            ++this.comparacoes;
         }

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
            Throwable var4 = null;

            try {
               if (var3.hasNextLine()) {
                  var3.nextLine();
               }

               while(var3.hasNextLine()) {
                  String var5 = var3.nextLine();
                  if (var5 != null && var5.trim().length() > 0) {
                     ++var2;
                  }
               }
            } catch (Throwable var31) {
               var4 = var31;
               throw var31;
            } finally {
               if (var3 != null) {
                  if (var4 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var28) {
                        var4.addSuppressed(var28);
                     }
                  } else {
                     var3.close();
                  }
               }

            }

            this.restaurantes = new Restaurante[var2];
            this.tamanho = var2;
            var3 = new Scanner(new File(var1));
            var4 = null;

            try {
               if (var3.hasNextLine()) {
                  var3.nextLine();
               }

               int var36 = 0;

               while(var3.hasNextLine() && var36 < this.tamanho) {
                  String var6 = var3.nextLine();
                  if (var6 != null && var6.trim().length() > 0) {
                     this.restaurantes[var36] = TP03Q03.Restaurante.parseRestaurante(var6);
                     ++var36;
                  }
               }
            } catch (Throwable var29) {
               var4 = var29;
               throw var29;
            } finally {
               if (var3 != null) {
                  if (var4 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var27) {
                        var4.addSuppressed(var27);
                     }
                  } else {
                     var3.close();
                  }
               }

            }

         } catch (FileNotFoundException var33) {
            throw new RuntimeException("Arquivo CSV nao encontrado: " + var1, var33);
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
         return (this.hora > 9 ? this.hora + "" : "0" + this.hora) + ":" + (this.minuto > 9 ? this.minuto + "" : "0" + this.minuto);
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
         String var1 = (this.dia > 9 ? this.dia + "" : "0" + this.dia) + "/" + (this.mes > 9 ? this.mes + "" : "0" + this.mes) + "/";
         if (this.ano > 999) {
            var1 = var1 + "" + this.ano;
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
         Hora var12 = TP03Q03.Hora.parseHora(var11[0]);
         Hora var13 = TP03Q03.Hora.parseHora(var11[1]);
         Data var14 = TP03Q03.Data.parseData(var1.next());
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
