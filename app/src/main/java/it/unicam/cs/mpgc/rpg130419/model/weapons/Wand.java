package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class Wand extends Weapon implements MageWeapon {
    private static int power = 10;
    private static String name = "Wand";

    public Wand() {
        super(name, power);
    }
}
