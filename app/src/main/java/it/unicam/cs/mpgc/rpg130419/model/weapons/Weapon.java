package it.unicam.cs.mpgc.rpg130419.model.weapons;

public abstract class Weapon implements Equippable {
    private final String name;
    private final String iconPath;
    private final int power;

    public Weapon(String name, String iconPath, int power) {
        this.name = name;
        this.iconPath = iconPath;
        this.power = power;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String getName() {
        return name;
    }
}
