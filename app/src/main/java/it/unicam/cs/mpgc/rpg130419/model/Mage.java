package it.unicam.cs.mpgc.rpg130419.model;

import java.util.UUID;

public class Mage extends Hero {
    private static final int initialExpRequirement = 150;
    private static final int expRequirementIncrement = 35;
    private final double powerMultiplier = 1.1;

    public Mage(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement), 10);
    }

    public Mage(UUID id, String name, String iconPath, int level, int currentExp) {
        super(id, name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement, level, currentExp), 10);
    }

    @Override
    public String getHeroClass() {
        return "Mage";
    }

    @Override
    public double calculateTotalPower() {
        return weaponSet.getWeaponSetPower();
    }
}
