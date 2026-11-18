package it.unicam.cs.mpgc.rpg130419.model;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.dungeons.RandomDungeonBuilder;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130419.model.heroes.RandomHeroBuilder;
import it.unicam.cs.mpgc.rpg130419.model.heroes.RecruitmentCandidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameSession {
    public static final int RECRUITMENT_REFRESH_COST = 10;
    private static final int MIN_RECRUITMENT_CANDIDATES = 4;
    private static final int DAILY_DUNGEON_COUNT = 3;

    private final Guild guild;
    private int currentDay;
    private boolean gameOver;
    private final List<Dungeon> availableDungeons;
    private final List<RecruitmentCandidate> recruitmentCandidates;

    public GameSession(Guild guild, int currentDay, boolean gameOver, List<Dungeon> availableDungeons,
                       List<RecruitmentCandidate> recruitmentCandidates) {
        if (currentDay < 1) throw new IllegalArgumentException("Invalid game day.");
        this.guild = Objects.requireNonNull(guild);
        this.currentDay = currentDay;
        this.gameOver = gameOver;
        this.availableDungeons = new ArrayList<>(availableDungeons);
        this.recruitmentCandidates = new ArrayList<>(recruitmentCandidates);
    }

    public int getGold() {
        return guild.getGold();
    }

    public int getTotalDailyWages() {
        return guild.getTotalDailyWages();
    }

    public int getActiveExpeditionsNumber() {
        return guild.getActiveExpeditionsNumber();
    }

    public String getGuildName() {
        return guild.getName();
    }

    public void renameGuild(String name) {
        guild.rename(name);
    }

    public void addMoney(int amount) {
        guild.addMoney(amount);
    }

    public List<Expedition> getActiveExpeditions() {
        return guild.getActiveExpeditions();
    }

    public boolean launchExpedition(Expedition expedition) {
        return !gameOver && guild.launchExpedition(expedition);
    }

    public boolean launchExpedition(Dungeon dungeon, List<Hero> party) {
        if (!availableDungeons.contains(dungeon)) return false;
        if (!launchExpedition(new Expedition(dungeon, party))) return false;

        availableDungeons.remove(dungeon);
        return true;
    }

    public List<Dungeon> getAvailableDungeons() {
        return availableDungeons;
    }

    public void addAvailableDungeon(Dungeon newDungeon) {
        if (newDungeon == null) throw new IllegalArgumentException("Dungeon can't be null.");
        if (availableDungeons.contains(newDungeon)) throw new IllegalArgumentException("Dungeon already present in the list.");
        availableDungeons.add(newDungeon);
    }

    public List<RecruitmentCandidate> getRecruitmentCandidates() {
        return recruitmentCandidates;
    }

    public void addRecruitmentCandidate(RecruitmentCandidate candidate) {
        if (candidate == null) throw new IllegalArgumentException("Candidate can't be null.");
        if (recruitmentCandidates.contains(candidate)) throw new IllegalArgumentException("Candidate already in recruitment list.");
        recruitmentCandidates.add(candidate);
    }

    public boolean hireHero(RecruitmentCandidate candidate) {
        if (gameOver || !recruitmentCandidates.contains(candidate)) return false;
        Hero hero = candidate.hero();
        if (guild.getHeroes().contains(hero)) return false;
        if (!guild.takeMoney(candidate.hireFee())) return false;

        guild.addHero(hero);
        recruitmentCandidates.remove(candidate);
        return true;
    }

    public boolean canRefreshRecruitment() {
        return !gameOver && guild.canPay(RECRUITMENT_REFRESH_COST);
    }

    public boolean refreshRecruitment() {
        if (!canRefreshRecruitment()) return false;
        guild.takeMoney(RECRUITMENT_REFRESH_COST);
        recruitmentCandidates.clear();
        for (int i = 0; i < MIN_RECRUITMENT_CANDIDATES; i++) {
            recruitmentCandidates.add(RandomHeroBuilder.buildCandidate());
        }
        return true;
    }

    public void addHero(Hero hero) {
        guild.addHero(hero);
    }

    public List<Hero> getAvailableHeroes() {
        return guild.getAvailableHeroes();
    }

    public boolean removeHero(Hero hero) {
        return !gameOver && guild.removeHero(hero);
    }

    public List<Hero> getHeroes() {
        return guild.getHeroes();
    }

    public DayResult endDay() {
        if (gameOver) throw new IllegalStateException("The game has ended.");
        currentDay += 1;
        List<Expedition> completedExpeditions = guild.advanceExpeditions();
        guild.awardExpeditionRewards(completedExpeditions);
        gameOver = !guild.payDailyWages();
        if (!gameOver) {
            replenishRecruitment();
            refreshDungeonBoard();
        }

        return new DayResult(completedExpeditions, gameOver);
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void replenishRecruitment() {
        while (recruitmentCandidates.size() < MIN_RECRUITMENT_CANDIDATES) {
            addRecruitmentCandidate(RandomHeroBuilder.buildCandidate());
        }
    }

    private void refreshDungeonBoard() {
        availableDungeons.clear();
        for (int i = 0; i < DAILY_DUNGEON_COUNT; i++) {
            availableDungeons.add(RandomDungeonBuilder.buildDungeon());
        }
    }
}
