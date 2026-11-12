package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class LeftFist extends Weapon implements NoClassWeapon {
    private static final String NAME = "Left Fist";
    private static final String ICON_PATH = "assets/weapon-icons/fist-left.png";
    private static final int POWER = 1;

    public LeftFist() {
        super(NAME, ICON_PATH, POWER);
    }
}