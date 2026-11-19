package it.unicam.cs.mpgc.rpg130419.model.dungeons;

import java.util.Objects;
import java.util.UUID;

public class Dungeon {
    private final UUID id;
    private final String name;
    private final String iconPath;
    private final double difficulty;
    private final int timeToBeat;
    private final int expReward;
    private final int goldReward;

    public Dungeon(String name, String iconPath, double difficulty, int timeToBeat, int expReward, int goldReward) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.iconPath = iconPath;
        this.difficulty = difficulty;
        this.timeToBeat = timeToBeat;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public Dungeon(UUID id, String name, String iconPath, double difficulty, int timeToBeat,
                   int expReward, int goldReward) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.iconPath = iconPath;
        this.difficulty = difficulty;
        this.timeToBeat = timeToBeat;
        this.expReward = expReward;
        this.goldReward = goldReward;
    }

    public UUID getId() {
        return id;
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

    public int getGoldReward() {
        return goldReward;
    }

    public String getIconPath() {
        return iconPath;
    }

    @Override
    public final boolean equals(Object other) {
        return other instanceof Dungeon dungeon && id.equals(dungeon.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
