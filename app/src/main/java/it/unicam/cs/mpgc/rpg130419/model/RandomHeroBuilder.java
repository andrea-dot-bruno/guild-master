package it.unicam.cs.mpgc.rpg130419.model;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130419.model.weapons.BasicWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.BronzeSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.EmberWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.IronSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.MoonstoneScepter;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Shield;
import it.unicam.cs.mpgc.rpg130419.model.weapons.SteelSword;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public final class RandomHeroBuilder {
    private static final int BASIC_WEAPON_CHANCE = 60;
    private static final int INTERMEDIATE_WEAPON_CHANCE = 30;
    private static final int SHIELD_CHANCE = 20;
    private static final List<Supplier<Weapon>> KNIGHT_WEAPONS = List.of(
            BronzeSword::new, IronSword::new, SteelSword::new);
    private static final List<Supplier<Weapon>> MAGE_WEAPONS = List.of(
            BasicWand::new, EmberWand::new, MoonstoneScepter::new);
    private static final HeroData DATA = loadHeroData();

    private RandomHeroBuilder() {}

    public static Hero buildHero() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String name = DATA.names().get(random.nextInt(DATA.names().size()));
        Hero hero;
        if (random.nextBoolean()) {
            hero = new Knight(name, DATA.knightIcons().randomPath(random));
            hero.setRightWeapon(rollRightWeapon(KNIGHT_WEAPONS, random));
        } else {
            hero = new Mage(name, DATA.mageIcons().randomPath(random));
            hero.setRightWeapon(rollRightWeapon(MAGE_WEAPONS, random));
        }
        if (random.nextInt(100) < SHIELD_CHANCE) {
            hero.setLeftWeapon(new Shield());
        }
        return hero;
    }

    public static RecruitmentCandidate buildCandidate() {
        return new RecruitmentCandidate(buildHero());
    }

    private static Weapon rollRightWeapon(List<Supplier<Weapon>> weapons, ThreadLocalRandom random) {
        int roll = random.nextInt(100);
        if (roll < BASIC_WEAPON_CHANCE) return weapons.get(0).get();
        if (roll < BASIC_WEAPON_CHANCE + INTERMEDIATE_WEAPON_CHANCE) return weapons.get(1).get();
        return weapons.get(2).get();
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
