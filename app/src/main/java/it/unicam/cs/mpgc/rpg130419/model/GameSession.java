package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private Guild guild;
    private int currentDay;
    private final List<Dungeon> availableDungeons = new ArrayList<>();
    private final List<Hero> recruitmentCandidates = new ArrayList<>();

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

    public List<Hero> getRecruitmentCandidates() {
        return recruitmentCandidates;
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
