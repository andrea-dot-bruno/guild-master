package it.unicam.cs.mpgc.rpg130419.model.weapons;

public abstract class Weapon {
    private final String name;
    private final String iconPath;
    private final int power;

    public Weapon(String name, String iconPath, int power) {
        this.name = name;
        this.iconPath = iconPath;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getName() {
        return name;
    }
}
