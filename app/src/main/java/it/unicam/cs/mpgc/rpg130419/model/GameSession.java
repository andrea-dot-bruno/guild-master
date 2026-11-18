package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameSession {
    public static final int RECRUITMENT_REFRESH_COST = 10;
    private static final int MIN_RECRUITMENT_CANDIDATES = 4;
    private static final int DAILY_DUNGEON_COUNT = 3;

    private final Guild GUILD;
    private int currentDay;
    private boolean gameOver;
    private final List<Dungeon> availableDungeons;
    private final List<RecruitmentCandidate> recruitmentCandidates;

    public GameSession(Guild guild, int currentDay, boolean gameOver, List<Dungeon> availableDungeons,
                       List<RecruitmentCandidate> recruitmentCandidates) {
        if (currentDay < 1) throw new IllegalArgumentException("Invalid game day.");
        this.GUILD = Objects.requireNonNull(guild);
        this.currentDay = currentDay;
        this.gameOver = gameOver;
        this.availableDungeons = new ArrayList<>(availableDungeons);
        this.recruitmentCandidates = new ArrayList<>(recruitmentCandidates);
    }

    public int getGold() {
        return GUILD.getGold();
    }

    public int getTotalDailyWages() {
        return GUILD.getTotalDailyWages();
    }

    public int getActiveExpeditionsNumber() {
        return GUILD.getActiveExpeditionsNumber();
    }

    public String getGuildName() {
        return GUILD.getName();
    }

    public void renameGuild(String name) {
        GUILD.rename(name);
    }

    public void addMoney(int amount) {
        GUILD.addMoney(amount);
    }

    public boolean canPay(int price) {
        return GUILD.canPay(price);
    }

    public boolean takeMoney(int amount) {
        return GUILD.takeMoney(amount);
    }

    public List<Expedition> getActiveExpeditions() {
        return GUILD.getActiveExpeditions();
    }

    public boolean launchExpedition(Expedition expedition) {
        return !gameOver && GUILD.launchExpedition(expedition);
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
        if (GUILD.getHeroes().contains(hero)) return false;
        if (!GUILD.takeMoney(candidate.hireFee())) return false;

        GUILD.addHero(hero);
        recruitmentCandidates.remove(candidate);
        return true;
    }

    public boolean canRefreshRecruitment() {
        return !gameOver && GUILD.canPay(RECRUITMENT_REFRESH_COST);
    }

    public boolean refreshRecruitment() {
        if (!canRefreshRecruitment()) return false;
        GUILD.takeMoney(RECRUITMENT_REFRESH_COST);
        recruitmentCandidates.clear();
        for (int i = 0; i < MIN_RECRUITMENT_CANDIDATES; i++) {
            recruitmentCandidates.add(RandomHeroBuilder.buildCandidate());
        }
        return true;
    }

    public void addHero(Hero hero) {
        GUILD.addHero(hero);
    }

    public List<Hero> getAvailableHeroes() {
        return GUILD.getAvailableHeroes();
    }

    public boolean removeHero(Hero hero) {
        return !gameOver && GUILD.removeHero(hero);
    }

    public List<Hero> getHeroes() {
        return GUILD.getHeroes();
    }

    public DayResult endDay() {
        if (gameOver) throw new IllegalStateException("The game has ended.");
        currentDay += 1;
        List<Expedition> completedExpeditions = GUILD.advanceExpeditions();
        GUILD.awardExpeditionRewards(completedExpeditions);
        gameOver = !GUILD.payDailyWages();
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
