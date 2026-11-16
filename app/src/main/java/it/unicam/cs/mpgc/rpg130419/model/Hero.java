package it.unicam.cs.mpgc.rpg130419.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Hero {
    protected final UUID id;
    protected final String name;
    protected final String iconPath;
    protected final int dailyPay;
    protected final HeroLevel heroLevel;
    protected WeaponSet weaponSet;

    public Hero(String name, String iconPath, HeroLevel heroLevel, int dailyPay) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.iconPath = iconPath;
        this.dailyPay = dailyPay;
        this.heroLevel = heroLevel;
        this.weaponSet = new WeaponSet();
    }

    protected Hero(UUID id, String name, String iconPath, HeroLevel heroLevel, int dailyPay) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.iconPath = iconPath;
        this.dailyPay = dailyPay;
        this.heroLevel = heroLevel;
        this.weaponSet = new WeaponSet();
    }

    public void addExp(int exp) {
        heroLevel.addExp(exp);
    }

    public void levelUp() {
        heroLevel.levelUp();
    }

    public void setRightWeapon(Weapon rightWeapon) {
        weaponSet.setRightWeapon(rightWeapon);
    }
    public void setLeftWeapon(Weapon leftWeapon) {
        weaponSet.setLeftWeapon(leftWeapon);
    }

    public double getTotalPower() {
        return calculateTotalPower();
    }

    public abstract double calculateTotalPower();

    public double getExpPercentage() {
        return heroLevel.getExpPercentage();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public abstract String getHeroClass();

    public int getDailyPay() {
        return dailyPay;
    }

    public int getLevel() {
        return heroLevel.getLevel();
    }

    public int getCurrentExp() {
        return heroLevel.getCurrentExp();
    }

    public int getRequiredExpToLevelUp() {
        return heroLevel.getRequiredExpToLevelUp();
    }

    public Weapon getLeftWeapon() {
        return weaponSet.getLeftWeapon();
    }

    public Weapon getRightWeapon() {
        return weaponSet.getRightWeapon();
    }

    public String getLeftWeaponIconPath() {
        return weaponSet.getLeftWeaponIconPath();
    }
    public String getRightWeaponIconPath() {
        return weaponSet.getRightWeaponIconPath();
    }
    public int getLeftWeaponPower() {
        return weaponSet.getLeftWeaponPower();
    }
    public int getRightWeaponPower() {
        return weaponSet.getRightWeaponPower();
    }
    public String getLeftWeaponName() {
        return weaponSet.getLeftWeaponName();
    }
    public String getRightWeaponName() {
        return weaponSet.getRightWeaponName();
    }

    @Override
    public final boolean equals(Object other) {
        return other instanceof Hero hero && id.equals(hero.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
