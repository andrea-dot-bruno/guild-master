package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;

import java.util.Objects;
import java.util.UUID;

record DungeonSave(UUID id, String name, String iconPath, double difficulty, int timeToBeat,
                   int expReward, int goldReward) {
    static DungeonSave from(Dungeon dungeon) {
        return new DungeonSave(dungeon.getId(), dungeon.getName(), dungeon.getIconPath(),
                dungeon.getDifficulty(), dungeon.getTimeToBeat(), dungeon.getExpReward(), dungeon.getGoldReward());
    }

    Dungeon restore() {
        return new Dungeon(id, Objects.requireNonNull(name), iconPath, difficulty, timeToBeat, expReward, goldReward);
    }
}
