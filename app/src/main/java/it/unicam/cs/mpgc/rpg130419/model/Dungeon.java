package it.unicam.cs.mpgc.rpg130419.model;

import java.util.UUID;

public class Dungeon {
    private final UUID id;
    private double difficulty;
    private int expReward;

    public Dungeon(double difficulty, int expReward) {
        this.id = UUID.randomUUID();
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
