package it.unicam.cs.mpgc.rpg130419.model.weapons;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;

public class Sword extends Weapon implements KnightWeapon {
    private static int power = 10;
    private static String name = "Sword";

    public Sword() {
        super(name, power);
    }
}
