package it.unicam.cs.mpgc.rpg130419.model;

import java.util.List;

public class GameSession {
    private Guild guild;
    private int currentDay;

    public GameSession() {
        //TODO: implement real name and day
        this.guild = new Guild("Test Guild");
        this.currentDay = 12;
    }

    public int getGold() {
        return guild.getGold();
    }

    public int getCurrentDay() {
        return currentDay;
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
}
