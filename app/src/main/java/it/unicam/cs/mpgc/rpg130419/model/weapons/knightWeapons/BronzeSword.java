package it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class BronzeSword extends Weapon implements KnightWeapon {
    private static String NAME = "Bronze Sword";
    private static String ICON_PATH = "assets/weapon-icons/bronze-sword.png";
    private static int POWER = 10;

    public BronzeSword() {
        super(NAME, ICON_PATH, POWER);
    }
}
