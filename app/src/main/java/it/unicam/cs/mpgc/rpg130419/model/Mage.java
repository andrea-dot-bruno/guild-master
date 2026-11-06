package it.unicam.cs.mpgc.rpg130419.model;

public class Mage extends Hero {
    private static final int initialExpRequirement = 150;
    private static final int expRequirementIncrement = 35;
    private final double powerMultiplier = 1.1;

    public Mage(String name) {
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
