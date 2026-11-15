package it.unicam.cs.mpgc.rpg130419.model;

import java.util.UUID;

public class Dungeon {
    private final UUID id;
    private final String name;
    private double difficulty;
    private int timeToBeat;
    private int expReward;

    public Dungeon(String name, double difficulty, int timeToBeat, int expReward) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.difficulty = difficulty;
        this.timeToBeat = timeToBeat;
        this.expReward = expReward;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public int getTimeToBeat() {
        return timeToBeat;
    }

    public int getExpReward() {
        return expReward;
    }

    public String getName() {
        return name;
    }
}
