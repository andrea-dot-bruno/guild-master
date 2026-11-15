package it.unicam.cs.mpgc.rpg130419.model;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Sword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Wand;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomHeroBuilder {
    private static final int HIRE_FEE_DAYS = 5;
    private static final HeroData DATA = loadHeroData();

    private RandomHeroBuilder() {}

    public static Hero buildHero() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String name = DATA.names().get(random.nextInt(DATA.names().size()));

        if (random.nextBoolean()) {
            Knight knight = new Knight(name, DATA.knightIcons().randomPath(random));
            knight.setRightWeapon(new Sword());
            return knight;
        }

        Mage mage = new Mage(name, DATA.mageIcons().randomPath(random));
        mage.setRightWeapon(new Wand());
        return mage;
    }

    public static RecruitmentCandidate buildCandidate() {
        Hero hero = buildHero();
        return new RecruitmentCandidate(hero, hero.getDailyPay() * HIRE_FEE_DAYS);
    }

    private static HeroData loadHeroData() {
        String path = "/catalogs/heroes.json";
        InputStream stream = RandomHeroBuilder.class.getResourceAsStream(path);
        if (stream == null) throw new IllegalStateException("Missing resource: " + path);

        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, HeroData.class);
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read resource: " + path, exception);
        }
    }

    private record HeroData(List<String> names, IconGroup knightIcons, IconGroup mageIcons) {}

    private record IconGroup(String directory, List<String> files) {
        String randomPath(ThreadLocalRandom random) {
            String filename = files.get(random.nextInt(files.size()));
            return directory + "/" + filename;
        }
    }
}
