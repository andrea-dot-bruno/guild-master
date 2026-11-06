package it.unicam.cs.mpgc.rpg130419.model;

public abstract class Hero {
    protected final String name;
    protected final HeroLevel heroLevel;
    protected WeaponSet weaponSet;

    public Hero(String name, HeroLevel heroLevel) {
        this.name = name;
        this.heroLevel = heroLevel;
        this.weaponSet = new WeaponSet();
    }

    public void addExp(int exp) {
        heroLevel.addExp(exp);
    }

    public void levelUp() {
        heroLevel.levelUp();
    }

    public boolean addWeapon(Weapon weapon) {
        return weaponSet.addWeapon(weapon);
    }

    public double getTotalPower() {
        return calculateTotalPower();
    }

    public abstract double calculateTotalPower();

    public int getLevel() {
        return heroLevel.getLevel();
    }

    public String getName() {
        return name;
    }
}
