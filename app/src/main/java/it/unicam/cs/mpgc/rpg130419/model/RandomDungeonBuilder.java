package it.unicam.cs.mpgc.rpg130419.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomDungeonBuilder {
    private static final int MIN_DIFFICULTY = 10;
    private static final int MAX_DIFFICULTY = 80;
    private static final int DIFFICULTY_PER_EXTRA_DAY = 20;
    private static final int GOLD_PER_DAY = 60;
    private static final int GOLD_PER_DIFFICULTY = 2;
    private static final int BASE_EXP_REWARD = 20;
    private static final int EXP_PER_DIFFICULTY = 3;
    private static final DungeonData DATA = loadDungeonData();

    private RandomDungeonBuilder() {}

    public static Dungeon buildDungeon() {
        //Estraendo nome e icona casuali.
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String name = DATA.names().get(random.nextInt(DATA.names().size()));
        IconGroup icons = DATA.icons();
        String filename = icons.files().get(random.nextInt(icons.files().size()));
        String iconPath = icons.directory() + "/" + filename;
        //Estraendo la difficoltà casuale del dungeon.
        int difficulty = random.nextInt(MIN_DIFFICULTY, MAX_DIFFICULTY + 1);
        //Calibrando il dungeon sulla sua difficoltà.
        int days = 1 + (difficulty - MIN_DIFFICULTY) / DIFFICULTY_PER_EXTRA_DAY;
        int goldReward = GOLD_PER_DAY * days + GOLD_PER_DIFFICULTY * difficulty;
        int expReward = BASE_EXP_REWARD + EXP_PER_DIFFICULTY * difficulty;
        return new Dungeon(name, iconPath, difficulty, days, expReward, goldReward);
    }

    private static DungeonData loadDungeonData() {
        String path = "/catalogs/dungeons.json";
        InputStream stream = RandomDungeonBuilder.class.getResourceAsStream(path);
        if (stream == null) throw new IllegalStateException("Missing resource: " + path);
        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, DungeonData.class);
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read resource: " + path, exception);
        }
    }

    private record DungeonData(List<String> names, IconGroup icons) {}

    private record IconGroup(String directory, List<String> files) {}
}
