package it.unicam.cs.mpgc.rpg130419.model;

public class Dungeon {
    private double difficulty;
    private int expReward;

    public Dungeon(double difficulty, int expReward) {
        this.difficulty = difficulty;
        this.expReward = expReward;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public int getExpReward() {
        return expReward;
    }
}
