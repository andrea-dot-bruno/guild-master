package it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class BasicWand extends Weapon implements MageWeapon {
    private static String NAME = "Basic Wand";
    private static String ICON_PATH = "assets/weapon-icons/basic-wand.png";
    private static int POWER = 10;

    public BasicWand() {
        super(NAME, ICON_PATH, POWER);
    }
}
