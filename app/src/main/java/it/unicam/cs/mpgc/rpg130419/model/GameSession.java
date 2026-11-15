package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private static final int MIN_RECRUITMENT_CANDIDATES = 4;
    private static final int MIN_AVAILABLE_DUNGEONS = 3;

    private Guild guild;
    private int currentDay;
    private boolean gameOver;
    private final List<Dungeon> availableDungeons = new ArrayList<>();
    private final List<RecruitmentCandidate> recruitmentCandidates = new ArrayList<>();

    public GameSession() {
        //TODO: implement real name and day
        this.guild = new Guild("Test Guild");
        this.currentDay = 12;
    }

    public int getGold() {
        return guild.getGold();
    }

    public int getActiveExpeditionsNumber() {
        return guild.getActiveExpeditionsNumber();
    }

    public String getGuildName() {
        return guild.getName();
    }

    public void addMoney(int amount) {
        guild.addMoney(amount);
    }

    public boolean canPay(int price) {
        return guild.canPay(price);
    }

    public boolean takeMoney(int amount) {
        return guild.takeMoney(amount);
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
        if (!gameOver) replenishBoards();

        return new DayResult(completedExpeditions, gameOver);
    }

    public int getCurrentDay() {
        return currentDay;
    }

    private void replenishBoards() {
        while (recruitmentCandidates.size() < MIN_RECRUITMENT_CANDIDATES) {
            addRecruitmentCandidate(RandomHeroBuilder.buildCandidate());
        }
        while (availableDungeons.size() < MIN_AVAILABLE_DUNGEONS) {
            addAvailableDungeon(RandomDungeonBuilder.buildDungeon());
        }
    }
}
