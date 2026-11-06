package it.unicam.cs.mpgc.rpg130419.model;

public class Knight extends Hero {
    private static final int initialExpRequirement = 100;
    private static final int expRequirementIncrement = 50;
    private final double powerMultiplier = 1.2;

    public Knight(String name) {
        super(name, new HeroLevel(initialExpRequirement, expRequirementIncrement));
    }

    @Override
    public double calculateTotalPower() {
        if (weaponSet != null) {
            return weaponSet.calculateWeaponSetPower() + (heroLevel.getLevel() * powerMultiplier);
        } else {
            return heroLevel.getLevel() * powerMultiplier;
        }
    }
}
