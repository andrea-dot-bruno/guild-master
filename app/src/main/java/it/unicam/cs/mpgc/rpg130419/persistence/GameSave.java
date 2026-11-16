package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Guild;
import it.unicam.cs.mpgc.rpg130419.model.Hero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

record GameSave(int version, String guildName, int currentDay, int gold, boolean gameOver,
                List<HeroSave> heroes, List<CandidateSave> recruitmentCandidates,
                List<DungeonSave> availableDungeons, List<ExpeditionSave> activeExpeditions) {
    private static final int FORMAT_VERSION = 1;

    static GameSave from(GameSession session) {
        return new GameSave(FORMAT_VERSION, session.getGuildName(), session.getCurrentDay(),
                session.getGold(), session.isGameOver(),
                session.getHeroes().stream().map(HeroSave::from).toList(),
                session.getRecruitmentCandidates().stream().map(CandidateSave::from).toList(),
                session.getAvailableDungeons().stream().map(DungeonSave::from).toList(),
                session.getActiveExpeditions().stream().map(ExpeditionSave::from).toList());
    }

    GameSession restore() {
        if (version != FORMAT_VERSION) throw new IllegalArgumentException("Unsupported save version: " + version);

        Guild guild = new Guild(Objects.requireNonNull(guildName));
        guild.addMoney(gold);
        Map<UUID, Hero> heroesById = new HashMap<>();
        for (HeroSave savedHero : heroes) {
            Hero hero = savedHero.restore();
            guild.addHero(hero);
            heroesById.put(hero.getId(), hero);
        }

        for (ExpeditionSave savedExpedition : activeExpeditions) {
            if (!guild.launchExpedition(savedExpedition.restore(heroesById))) {
                throw new IllegalArgumentException("Invalid saved expedition party.");
            }
        }

        return new GameSession(guild, currentDay, gameOver,
                availableDungeons.stream().map(DungeonSave::restore).toList(),
                recruitmentCandidates.stream().map(CandidateSave::restore).toList());
    }
}
