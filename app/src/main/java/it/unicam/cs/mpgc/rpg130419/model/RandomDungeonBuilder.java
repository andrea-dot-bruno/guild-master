package it.unicam.cs.mpgc.rpg130419.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomDungeonBuilder {
    private static final DungeonData DATA = loadDungeonData();

    private RandomDungeonBuilder() {}

    public static Dungeon buildDungeon() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String name = DATA.names().get(random.nextInt(DATA.names().size()));
        IconGroup icons = DATA.icons();
        String filename = icons.files().get(random.nextInt(icons.files().size()));
        String iconPath = icons.directory() + "/" + filename;
        DungeonProfile profile = DATA.profiles().get(random.nextInt(DATA.profiles().size()));
        return new Dungeon(name, iconPath, profile.difficulty(), profile.days(), profile.xp(), profile.gold());
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

    private record DungeonData(List<String> names, IconGroup icons, List<DungeonProfile> profiles) {}

    private record IconGroup(String directory, List<String> files) {}

    private record DungeonProfile(double difficulty, int days, int xp, int gold) {}
}
