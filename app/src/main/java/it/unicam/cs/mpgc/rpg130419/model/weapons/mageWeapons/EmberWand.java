package it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;

public class EmberWand extends Weapon implements MageWeapon {
    private static String NAME = "Ember Wand";
    private static String ICON_PATH = "assets/weapon-icons/ember-wand.png";
    private static int POWER = 17;

    public EmberWand() {
        super(NAME, ICON_PATH, POWER);
    }
}
