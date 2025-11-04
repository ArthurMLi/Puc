import java.io.*;

import java.util.*;

public class game {
    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("dados.csv"), "UTF-8"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                ArrayList<String> campos = new ArrayList<>(Arrays.asList(linha.split("\"\\[|\\]\"|,\"|\","))); // separa por vírgula
                ArrayList<String> camposofc = new ArrayList<>();
                String data,linguas;
                for (int i = 0; i < campos.size(); i++) {
                    if (campos.get(i).isEmpty()) {
                        campos.remove(i);
                        i--;
                    }
                }
                data=campos.get(1);    
                campos.remove(1);
                linguas=campos.get(2);
                campos.remove(2);
                
                for (int i = 0; i < campos.size(); i++) {
                    if (campos.get(i).isEmpty()) {
                        campos.remove(i);
                        i--;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class GameObj {

    int appId;
    String name;
    String releaseDate;
    int owners;
    int price;
    String language;
    int metaScore;
    int userScore;
    int achievements;
    String publichers;
    String dev;
    String categories;
    String genres;
    String tags;

    void game() {

    };
}
