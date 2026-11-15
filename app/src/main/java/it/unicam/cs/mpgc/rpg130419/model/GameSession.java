package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private Guild guild;
    private int currentDay;
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

    public void launchExpedition(Expedition expedition) {
        guild.launchExpedition(expedition);
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
        if (!recruitmentCandidates.contains(candidate)) return false;

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

    public void removeHero(Hero hero) {
        guild.removeHero(hero);
    }

    public List<Hero> getHeroes() {
        return guild.getHeroes();
    }

    public void newDay() {
        currentDay += 1;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void updateExpeditions() {
        guild.updateExpeditions();
    }
}
