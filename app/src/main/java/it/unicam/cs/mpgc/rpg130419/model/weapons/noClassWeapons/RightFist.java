package it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class RightFist extends Weapon implements NoClassWeapon {
    private static final String NAME = "Right Fist";
    private static final String ICON_PATH = "assets/weapon-icons/fist-right.png";
    private static final int POWER = 1;

    public RightFist() {
        super(NAME, ICON_PATH, POWER);
    }
}
