package it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class SteelSword extends Weapon implements KnightWeapon {
    private static String NAME = "Steel Sword";
    private static String ICON_PATH = "assets/weapon-icons/steel-sword.png";
    private static int POWER = 20;

    public SteelSword() {
        super(NAME, ICON_PATH, POWER);
    }
}
