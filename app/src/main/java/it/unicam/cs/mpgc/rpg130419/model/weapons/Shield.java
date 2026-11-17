package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class Shield extends Weapon implements NoClassWeapon {
    private static String NAME = "Shield";
    private static String ICON_PATH = "assets/weapon-icons/shield.png";
    private static int POWER = 7;

    public Shield() {
        super(NAME, ICON_PATH, POWER);
    }
}
