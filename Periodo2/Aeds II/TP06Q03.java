import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

public class TP06Q03 {

    private static final String CSV_FILE_PATH = "/tmp/games.csv";

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Fila filaDeGames = new Fila();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String idInput;
        while ((idInput = br.readLine()) != null && !idInput.equalsIgnoreCase("FIM")) {
            Game game = findGameById(idInput);
            if (game != null) {
                filaDeGames.inserirFim(game);
            }
        }
        int n = Integer.parseInt(br.readLine()); // Lê a quantidade de comandos

        for (int i = 0; i < n; i++) {
            String commandLine = br.readLine();
            String[] parts = commandLine.split(" ");
            String command = parts[0];
            Game game;
            Game removido;

            try {
                switch (command) {
                    case "I":
                        game = findGameById(parts[1]);
                        filaDeGames.inserirFim(game);
                        break;
                    case "R":
                        removido = filaDeGames.removerFim();
                        System.out.println("(R) " + removido.getName());
                        break;
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filaDeGames.mostrar();

        br.close();
    }

    private static Game findGameById(String id) throws IOException {
        try (BufferedReader brCsv = new BufferedReader(new FileReader(CSV_FILE_PATH, StandardCharsets.UTF_8))) {
            brCsv.readLine();
            String line;

            while ((line = brCsv.readLine()) != null) {
                String currentId = line.substring(0, line.indexOf(','));

                if (currentId.equals(id)) {
                    Game game = new Game();
                    game.parseLine(line);
                    return game;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERRO: Arquivo CSV não encontrado no caminho: " + CSV_FILE_PATH);
        }
        return null;
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

    public void mostrar() {
        int j = 0;
        Game i = this.primeiro;
        while (i != null) {
            System.out.println("[" + j + "] => " + i.toString());
            i = i.prox;
            j++;
        }
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
            ultimo.prox = game;
            this.ultimo = game; // Atualiza o ponteiro 'ultimo'
            game.prox = null;
        }
        this.tamanho++;
    }

    
    public Game removerInicio() {
        if (this.primeiro == null) {
            return null;
        }
        Game tmp = this.primeiro;
        primeiro = primeiro.prox;
        if (primeiro == null) {
            ultimo = null;
        }
        this.tamanho--;
        tmp.prox = null;
        return tmp;
    }
    public Game removerFim() {
        if (this.primeiro == null) {
            return null;
        }

        // Caso de 1 elemento
        if (this.primeiro == this.ultimo) {
            Game tmp = primeiro;
            primeiro = null;
            ultimo = null;
            tamanho = 0;
            return tmp;
        }

        // Caso recursivo (mais de 1 elemento)
        return removerFimRecursivo(primeiro, primeiro);
    }

    private Game removerFimRecursivo(Game i, Game anterior) {
        if (i.prox == null) {
            Game tmp = i;
            anterior.prox = null;
            ultimo = anterior;
            tamanho--;
            return tmp;
        } else {
            return removerFimRecursivo(i.prox, i);
        }
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

    public String getName() {
        return this.name;
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
            if (c == '\"')
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
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "01";
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