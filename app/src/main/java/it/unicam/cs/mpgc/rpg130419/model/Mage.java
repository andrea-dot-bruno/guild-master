package it.unicam.cs.mpgc.rpg130419.model;

public class Mage extends Hero {
    private static final int initialExpRequirement = 150;
    private static final int expRequirementIncrement = 35;
    private final double powerMultiplier = 1.1;

    public Mage(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement), 10);
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
