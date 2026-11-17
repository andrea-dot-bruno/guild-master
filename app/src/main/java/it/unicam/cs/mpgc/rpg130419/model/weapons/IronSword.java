package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class IronSword extends Weapon implements KnightWeapon {
    private static String NAME = "Iron Sword";
    private static String ICON_PATH = "assets/weapon-icons/iron-sword.png";
    private static int POWER = 15;

    public IronSword() {
        super(NAME, ICON_PATH, POWER);
    }
}
