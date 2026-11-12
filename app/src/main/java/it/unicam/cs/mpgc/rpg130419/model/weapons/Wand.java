package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class Wand extends Weapon implements MageWeapon {
    private static String NAME = "Basic Wand";
    private static String ICON_PATH = "assets/weapon-icons/basic-wand.png";
    private static int POWER = 10;

    public Wand() {
        super(NAME, ICON_PATH, POWER);
    }
}
