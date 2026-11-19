package it.unicam.cs.mpgc.rpg130419.model.heroes;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.WeaponSet;

import java.util.Objects;
import java.util.UUID;

public abstract class Hero {
    private static final int BASE_DAILY_PAY = 5;
    private static final int POWER_PER_EXTRA_GOLD = 2;
    private static final int POWER_PER_LEVEL = 2;

    protected final UUID id;
    protected final String name;
    protected final String iconPath;
    protected final HeroLevel heroLevel;
    protected WeaponSet weaponSet;

    public Hero(String name, String iconPath, HeroLevel heroLevel) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.iconPath = iconPath;
        this.heroLevel = heroLevel;
        this.weaponSet = new WeaponSet();
    }

    protected Hero(UUID id, String name, String iconPath, HeroLevel heroLevel) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.iconPath = iconPath;
        this.heroLevel = heroLevel;
        this.weaponSet = new WeaponSet();
    }

    public abstract String getHeroClass();

    public int getDailyPay() {
        return BASE_DAILY_PAY + getEquipmentPower() / POWER_PER_EXTRA_GOLD;
    }

    public void addExp(int exp) {
        heroLevel.addExp(exp);
    }

    public void levelUp() {
        heroLevel.levelUp();
    }

    public int getLevel() {
        return heroLevel.getLevel();
    }

    public int getCurrentExp() {
        return heroLevel.getCurrentExp();
    }

    public double getExpPercentage() {
        return heroLevel.getExpPercentage();
    }

    public double getTotalPower() {
        return getEquipmentPower() + (getLevel() - 1) * (double) POWER_PER_LEVEL;
    }

    public int getEquipmentPower() {
        return weaponSet.getWeaponSetPower();
    }

    public Weapon getLeftWeapon() {
        return weaponSet.getLeftWeapon();
    }

    public Weapon getRightWeapon() {
        return weaponSet.getRightWeapon();
    }

    public void setRightWeapon(Weapon rightWeapon) {
        weaponSet.setRightWeapon(rightWeapon);
    }

    public void setLeftWeapon(Weapon leftWeapon) {
        weaponSet.setLeftWeapon(leftWeapon);
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

    @Override
    public final boolean equals(Object other) {
        return other instanceof Hero hero && id.equals(hero.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
