package it.unicam.cs.mpgc.rpg130419.model;

public abstract class Weapon {
    private String name;
    private String iconPath;
    private int power;

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
