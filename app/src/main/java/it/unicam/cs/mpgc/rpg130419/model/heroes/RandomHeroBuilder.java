package it.unicam.cs.mpgc.rpg130419.model.heroes;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Equippable;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.BronzeSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.IronSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.KnightWeapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.SteelSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.BasicWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.EmberWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.MageWeapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.MoonstoneScepter;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.NoClassWeapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.Shield;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public final class RandomHeroBuilder {
    private static final int BASIC_WEAPON_CHANCE = 60;
    private static final int INTERMEDIATE_WEAPON_CHANCE = 30;
    private static final int SHIELD_CHANCE = 20;
    private static final HeroData DATA = loadHeroData();
    private static final List<HeroProfile<? super NoClassWeapon>> PROFILES = List.of(
            new HeroProfile<KnightWeapon>(Knight::new, DATA.iconsFor("knight"),
                    BronzeSword::new, IronSword::new, SteelSword::new),
            new HeroProfile<MageWeapon>(Mage::new, DATA.iconsFor("mage"),
                    BasicWand::new, EmberWand::new, MoonstoneScepter::new));

    private RandomHeroBuilder() {}

    public static RecruitmentCandidate buildCandidate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        var profile = PROFILES.get(random.nextInt(PROFILES.size()));
        String name = DATA.names().get(random.nextInt(DATA.names().size()));
        Hero<? super NoClassWeapon> hero = profile.buildHero(name, random);
        if (random.nextInt(100) < SHIELD_CHANCE) {
            hero.setLeftWeapon(new Shield());
        }
        return new RecruitmentCandidate(hero);
    }

    private record HeroProfile<W extends Equippable>(
            BiFunction<String, String, Hero<W>> constructor, IconGroup icons,
            Supplier<W> basicWeapon, Supplier<W> intermediateWeapon, Supplier<W> advancedWeapon) {

        Hero<W> buildHero(String name, ThreadLocalRandom random) {
            Hero<W> hero = constructor.apply(name, icons.randomPath(random));
            hero.setRightWeapon(rollRightWeapon(random));
            return hero;
        }

        private W rollRightWeapon(ThreadLocalRandom random) {
            int roll = random.nextInt(100);
            if (roll < BASIC_WEAPON_CHANCE) return basicWeapon.get();
            if (roll < BASIC_WEAPON_CHANCE + INTERMEDIATE_WEAPON_CHANCE) return intermediateWeapon.get();
            return advancedWeapon.get();
        }
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

    private record HeroData(List<String> names, Map<String, IconGroup> iconsByClass) {
        IconGroup iconsFor(String heroClass) {
            return Objects.requireNonNull(iconsByClass.get(heroClass), "Missing icons for " + heroClass);
        }
    }

    private record IconGroup(String directory, List<String> files) {
        String randomPath(ThreadLocalRandom random) {
            String filename = files.get(random.nextInt(files.size()));
            return directory + "/" + filename;
        }
    }
}
