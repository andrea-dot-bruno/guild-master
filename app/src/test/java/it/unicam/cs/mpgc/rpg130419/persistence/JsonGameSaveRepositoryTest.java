package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import it.unicam.cs.mpgc.rpg130419.model.Knight;
import it.unicam.cs.mpgc.rpg130419.model.Mage;
import it.unicam.cs.mpgc.rpg130419.model.RecruitmentCandidate;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Sword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Wand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class JsonGameSaveRepositoryTest {
    @TempDir Path directory;

    @Test
    void resumesExpeditionAndAwardsExperienceToTheGuildHero() throws IOException {
        GameSaveRepository saves = new JsonGameSaveRepository(directory.resolve("save.json"));
        GameSession session = new GameSession();
        session.addMoney(1000);
        Hero hero = new Knight("Bram", "bram.png");
        hero.setRightWeapon(new Sword());
        hero.addExp(170);
        hero.levelUp();
        session.addHero(hero);
        Hero candidate = new Mage("Iris", "iris.png");
        candidate.setLeftWeapon(new Wand());
        session.addRecruitmentCandidate(new RecruitmentCandidate(candidate, 75));
        Dungeon dungeon = new Dungeon("Ruins", "ruins.png", 1, 2, 90, 150);
        session.addAvailableDungeon(dungeon);
        assertTrue(session.launchExpedition(dungeon, List.of(hero)));
        Expedition expedition = session.getActiveExpeditions().getFirst();

        saves.save(session);
        session.endDay();
        saves.save(session);

        GameSession loaded = saves.loadOrCreate(() -> fail("Expected the existing save"));
        assertEquals(session.getCurrentDay(), loaded.getCurrentDay());
        assertEquals(session.getGold(), loaded.getGold());
        assertEquals(session.getAvailableDungeons(), loaded.getAvailableDungeons());
        RecruitmentCandidate loadedCandidate = loaded.getRecruitmentCandidates().getFirst();
        assertEquals(75, loadedCandidate.hireFee());
        assertEquals(candidate.getId(), loadedCandidate.hero().getId());
        assertInstanceOf(Mage.class, loadedCandidate.hero());
        assertInstanceOf(Wand.class, loadedCandidate.hero().getLeftWeapon());
        Hero loadedHero = loaded.getHeroes().getFirst();
        assertEquals(hero.getId(), loadedHero.getId());
        assertEquals(2, loadedHero.getLevel());
        assertEquals(70, loadedHero.getCurrentExp());
        assertInstanceOf(Sword.class, loadedHero.getRightWeapon());
        Expedition loadedExpedition = loaded.getActiveExpeditions().getFirst();
        assertEquals(expedition.getId(), loadedExpedition.getId());
        assertEquals(dungeon.getId(), loadedExpedition.getDungeon().getId());
        assertEquals(1, loadedExpedition.getRemainingTime());
        assertSame(loadedHero, loadedExpedition.getInvolvedHeroes().getFirst());
        assertTrue(loaded.getAvailableHeroes().isEmpty());

        loaded.endDay();
        assertTrue(loaded.getActiveExpeditions().isEmpty());
        assertSame(loadedHero, loaded.getAvailableHeroes().getFirst());
        assertEquals(3, loadedHero.getLevel());
        assertEquals(10, loadedHero.getCurrentExp());
        assertEquals(session.getGold() + 150 - hero.getDailyPay(), loaded.getGold());
    }

    @Test
    void preservesGameOverWithAnExpeditionStillActive() throws IOException {
        GameSaveRepository saves = new JsonGameSaveRepository(directory.resolve("save.json"));
        GameSession session = new GameSession();
        Hero hero = new Knight("Bram", "bram.png");
        session.addHero(hero);
        Dungeon dungeon = new Dungeon("Ruins", "ruins.png", 1, 3, 90, 150);
        session.addAvailableDungeon(dungeon);
        assertTrue(session.launchExpedition(dungeon, List.of(hero)));
        assertTrue(session.endDay().gameOver());
        saves.save(session);

        GameSession loaded = saves.loadOrCreate(() -> fail("Expected the existing save"));
        assertTrue(loaded.isGameOver());
        assertEquals(2, loaded.getActiveExpeditions().getFirst().getRemainingTime());
        assertThrows(IllegalStateException.class, loaded::endDay);
    }

    @Test
    void createsAndPersistsSessionWhenSaveIsMissing() throws IOException {
        Path saveFile = directory.resolve("new-game").resolve("save.json");
        GameSaveRepository saves = new JsonGameSaveRepository(saveFile);
        GameSession initialSession = new GameSession();
        initialSession.addMoney(150);

        assertSame(initialSession, saves.loadOrCreate(() -> initialSession));
        assertTrue(Files.isRegularFile(saveFile));
        GameSession loaded = saves.loadOrCreate(() -> fail("Expected the newly written save"));
        assertEquals(150, loaded.getGold());
    }

    @Test
    void createsAndPersistsSessionWhenSaveIsBlank() throws IOException {
        Path saveFile = directory.resolve("save.json");
        GameSaveRepository saves = new JsonGameSaveRepository(saveFile);
        GameSession initialSession = new GameSession();
        initialSession.addMoney(150);

        for (String blank : List.of("", " \n\t")) {
            Files.writeString(saveFile, blank);
            assertSame(initialSession, saves.loadOrCreate(() -> initialSession));
            GameSession loaded = saves.loadOrCreate(() -> fail("Expected the newly written save"));
            assertEquals(150, loaded.getGold());
        }
    }

    @Test
    void preservesInvalidSaveAndReportsError() throws IOException {
        Path saveFile = directory.resolve("save.json");
        GameSaveRepository saves = new JsonGameSaveRepository(saveFile);

        for (String invalidSave : List.of("{", "null", "{\"version\":1}")) {
            Files.writeString(saveFile, invalidSave);
            assertThrows(IOException.class, () -> saves.loadOrCreate(() -> fail("Invalid save must be preserved")));
            assertEquals(invalidSave, Files.readString(saveFile));
        }
    }
}
