package it.unicam.cs.mpgc.rpg130419.model;

public class GameSession {
    private Guild guild;
    private int currentDay;

    public GameSession() {}

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
}
