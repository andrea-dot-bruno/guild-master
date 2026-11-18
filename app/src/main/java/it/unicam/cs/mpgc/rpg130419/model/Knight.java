package it.unicam.cs.mpgc.rpg130419.model;

import java.util.UUID;

public class Knight extends Hero {
    private static final int initialExpRequirement = 100;
    private static final int expRequirementIncrement = 50;

    public Knight(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement));
    }

    public Knight(UUID id, String name, String iconPath, int level, int currentExp) {
        super(id, name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement, level, currentExp));
    }

    @Override
    public String getHeroClass() {
        return "Knight";
    }
}
