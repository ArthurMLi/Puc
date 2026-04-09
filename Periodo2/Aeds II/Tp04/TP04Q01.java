import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TP04Q01 {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Fila filaDeGames = new Fila();
        String csvFilePath = "/tmp/games.csv";

        try (BufferedReader brCsv = new BufferedReader(new FileReader(csvFilePath, StandardCharsets.UTF_8))) {
            String line = brCsv.readLine();

            while ((line = brCsv.readLine()) != null) {
                Game game = new Game();
                game.parseLine(line);
                filaDeGames.inserirFim(game);
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERRO: Arquivo CSV não encontrado no caminho: " + csvFilePath);
            return;
        }

        try (BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String idInput;
            while ((idInput = brIn.readLine()) != null && !idInput.equalsIgnoreCase("FIM")) {
                try {
                    int idParaBuscar = Integer.parseInt(idInput.trim());
                    Game jogoEncontrado = filaDeGames.pesquisar(idParaBuscar);
                    if (jogoEncontrado != null) {
                        System.out.println(jogoEncontrado.toString());
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
    }
}

class Fila {

    private Game primeiro = null;
    private Game ultimo = null;
    private int tamanho;

    public Fila() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    public Game pesquisar(int id) {
        Game i = this.primeiro;
        while (i != null) {
            if (i.getId() == id) {
                return i;
            }
            i = i.prox;
        }
        return null;
    }

    public void inserirInicio(Game game) {
        if (this.primeiro == null) {
            this.primeiro = game;
            this.ultimo = game;
        } else {
            game.prox = primeiro;
            primeiro = game;
        }
        this.tamanho++;
    }

    public void inserirFim(Game game) {
        if (this.primeiro == null) {
            this.primeiro = game;
            this.ultimo = game;
        } else {
            this.ultimo.prox = game;
            this.ultimo = game;
            game.prox = null;
        }
        this.tamanho++;
    }

    public void inserir(Game game, int p) {
        if (p < 0 || p > this.tamanho) {
            return;
        }

        if (p == 0) {
            inserirInicio(game);
        } else if (p == this.tamanho) {
            inserirFim(game);
        } else {
            Game tmp = primeiro;
            for (int i = 0; i < p - 1; i++) {
                tmp = tmp.prox;
            }
            game.prox = tmp.prox;
            tmp.prox = game;
            this.tamanho++;
        }
    }

    public Game removerInicio() {
        if (this.primeiro == null) {
            return null;
        }

        Game tmp = this.primeiro;
        this.primeiro = this.primeiro.prox;

        if (this.primeiro == null) {
            this.ultimo = null;
        }

        this.tamanho--;
        tmp.prox = null;
        return tmp;
    }

    public Game removerFim() {
        if (this.primeiro == null) {
            return null;
        }

        if (this.primeiro == this.ultimo) {
            Game tmp = this.primeiro;
            this.primeiro = null;
            this.ultimo = null;
            this.tamanho = 0;
            return tmp;
        }

        return removerFimRecursivo(primeiro, primeiro);
    }

    private Game removerFimRecursivo(Game i, Game anterior) {
        if (i.prox == null) {
            Game tmp = i;
            anterior.prox = null;
            this.ultimo = anterior;
            this.tamanho--;
            return tmp;
        } else {
            return removerFimRecursivo(i.prox, i);
        }
    }

    public Game remover(int p) {
        if (this.primeiro == null || p < 0 || p >= this.tamanho) {
            return null;
        }

        Game removido = null;

        if (p == 0) {
            removido = removerInicio();
        } else if (p == this.tamanho - 1) {
            removido = removerFim();
        } else {
            Game tmp = primeiro;
            for (int i = 0; i < p - 1; i++) {
                tmp = tmp.prox;
            }
            removido = tmp.prox;
            tmp.prox = removido.prox;
            this.tamanho--;
            removido.prox = null;
        }

        return removido;
    }
}

class Game {
    private int id;
    private String name;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private String[] supportedLanguages;
    private int metacriticScore;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;
    public Game prox = null;

    public int getId() {
        return this.id;
    }

    public Game() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "01/01/1970";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.supportedLanguages = new String[0];
        this.publishers = new String[0];
        this.developers = new String[0];
        this.categories = new String[0];
        this.genres = new String[0];
        this.tags = new String[0];
        this.prox = null;
    }

    public void parseLine(String csvLine) {
        String[] fields = new String[14];
        int fieldIndex = 0;
        int start = 0;
        boolean inQuotes = false;

        for (int i = 0; i < csvLine.length(); i++) {
            char c = csvLine.charAt(i);
            if (c == '"')
                inQuotes = !inQuotes;
            if (c == ',' && !inQuotes) {
                fields[fieldIndex++] = csvLine.substring(start, i).trim();
                start = i + 1;
            }
        }
        fields[fieldIndex] = csvLine.substring(start).trim();

        this.id = normalizeId(fields[0]);
        this.name = normalizeName(fields[1]);
        this.releaseDate = normalizeDate(fields[2]);
        this.estimatedOwners = normalizeOwners(fields[3]);
        this.price = normalizePrice(fields[4]);
        this.supportedLanguages = normalizeBracketList(fields[5]);
        this.metacriticScore = normalizeMetacritic(fields[6]);
        this.userScore = normalizeUserScore(fields[7]);
        this.achievements = normalizeAchievements(fields[8]);
        this.publishers = normalizeSimpleList(fields[9]);
        this.developers = normalizeSimpleList(fields[10]);
        this.categories = normalizeSimpleList(fields[11]);
        this.genres = normalizeSimpleList(fields[12]);
        this.tags = normalizeSimpleList(fields[13]);
    }

    private int normalizeId(String s) {
        return Integer.parseInt(s.trim());
    }

    private String normalizeName(String s) {
        return s.trim().replace("\"", "");
    }

    private String normalizeDate(String s) {
        s = s.replace("\"", "");
        String day = "01";
        String month;
        String year;

        try {
            if (s.contains(",")) {
                String[] parts = s.split(" ");
                month = mapMonth(parts[0]);
                day = parts[1].replace(",", "");
                year = parts[2];
            } else {
                String[] parts = s.split(" ");
                month = mapMonth(parts[0]);
                year = parts[1];
            }
            if (day.length() == 1)
                day = "0" + day;
            return day + "/" + month + "/" + year;
        } catch (Exception e) {
            if (s.matches("\\d{4}"))
                return "01/01/" + s;
            return "01/01/1970";
        }
    }

    private String mapMonth(String monthName) {
        switch (monthName) {
            case "Jan": return "01";
            case "Feb": return "02";
            case "Mar": return "03";
            case "Apr": return "04";
            case "May": return "05";
            case "Jun": return "06";
            case "Jul": return "07";
            case "Aug": return "08";
            case "Sep": return "09";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default:    return "01";
        }
    }

    private int normalizeOwners(String s) {
        String firstNum = s.split(" ")[0].replace(",", "");
        if (firstNum.isEmpty())
            return 0;
        return Integer.parseInt(firstNum);
    }

    private float normalizePrice(String s) {
        s = s.trim().replace("\"", "");
        if (s.equalsIgnoreCase("Free to Play"))
            return 0.0f;
        return Float.parseFloat(s);
    }

    private String[] normalizeBracketList(String s) {
        s = s.replace("\"", "").replace("[", "").replace("]", "").replace("'", "");
        if (s.isEmpty())
            return new String[0];
        return s.split(", ?");
    }

    private String[] normalizeSimpleList(String s) {
        s = s.replace("\"", "");
        if (s.isEmpty())
            return new String[0];
        return s.split(", ?");
    }

    private int normalizeMetacritic(String s) {
        s = s.trim();
        if (s.isEmpty())
            return -1;
        return Integer.parseInt(s);
    }

    private float normalizeUserScore(String s) {
        s = s.trim();
        if (s.isEmpty() || s.equalsIgnoreCase("tbd"))
            return -1.0f;
        return Float.parseFloat(s);
    }

    private int normalizeAchievements(String s) {
        s = s.trim();
        if (s.isEmpty())
            return 0;
        return Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%d ## %s ## %s ## %d ## %.2f ## %s ## %d ## %.1f ## %d ## %s ## %s ## %s ## %s ## %s ##",
                id,
                name,
                releaseDate,
                estimatedOwners,
                price,
                Arrays.toString(supportedLanguages),
                metacriticScore,
                userScore,
                achievements,
                Arrays.toString(publishers),
                Arrays.toString(developers),
                Arrays.toString(categories),
                Arrays.toString(genres),
                Arrays.toString(tags));
    }
}