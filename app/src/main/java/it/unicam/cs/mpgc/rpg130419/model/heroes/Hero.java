package it.unicam.cs.mpgc.rpg130419.model.heroes;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Equippable;
import it.unicam.cs.mpgc.rpg130419.model.weapons.WeaponSet;

import java.util.Objects;
import java.util.UUID;

public abstract class Hero<W extends Equippable> {
    private static final int BASE_DAILY_PAY = 5;
    private static final int POWER_PER_EXTRA_GOLD = 2;
    private static final int POWER_PER_LEVEL = 2;

    protected final UUID id;
    protected final String name;
    protected final String iconPath;
    protected final HeroLevel heroLevel;
    private final WeaponSet<W> weaponSet;

    protected Hero(String name, String iconPath, HeroLevel heroLevel, WeaponSet<W> weaponSet) {
        this(UUID.randomUUID(), name, iconPath, heroLevel, weaponSet);
    }

    protected Hero(UUID id, String name, String iconPath, HeroLevel heroLevel, WeaponSet<W> weaponSet) {
        this.id = Objects.requireNonNull(id);
        this.name = name;
        this.iconPath = iconPath;
        this.heroLevel = heroLevel;
        this.weaponSet = Objects.requireNonNull(weaponSet);
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

    public W getLeftWeapon() {
        return weaponSet.getLeftWeapon();
    }

    public W getRightWeapon() {
        return weaponSet.getRightWeapon();
    }

    public void setRightWeapon(W rightWeapon) {
        weaponSet.setRightWeapon(rightWeapon);
    }

    public void setLeftWeapon(W leftWeapon) {
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
        return other instanceof Hero<?> hero && id.equals(hero.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
