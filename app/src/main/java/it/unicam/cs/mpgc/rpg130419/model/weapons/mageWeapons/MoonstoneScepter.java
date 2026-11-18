package it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class MoonstoneScepter extends Weapon implements MageWeapon {
    private static String NAME = "Moonstone Scepter";
    private static String ICON_PATH = "assets/weapon-icons/moonstone-scepter.png";
    private static int POWER = 25;

    public MoonstoneScepter() {
        super(NAME, ICON_PATH, POWER);
    }
}
