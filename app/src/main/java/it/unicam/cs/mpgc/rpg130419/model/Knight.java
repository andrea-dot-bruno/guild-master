package it.unicam.cs.mpgc.rpg130419.model;

public class Knight extends Hero {
    private static final int initialExpRequirement = 100;
    private static final int expRequirementIncrement = 50;
    private final double powerMultiplier = 1.2;

    public Knight(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement), 12);
    }

    @Override
    public String getHeroClass() {
        return "Knight";
    }

    @Override
    public double calculateTotalPower() {
        return weaponSet.getWeaponSetPower();
    }
}
