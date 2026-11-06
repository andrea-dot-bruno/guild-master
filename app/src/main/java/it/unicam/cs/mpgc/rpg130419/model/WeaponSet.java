package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class WeaponSet {
    private List<Weapon> weapons;

    public WeaponSet() {
        weapons = new ArrayList<>();
    }

    public boolean addWeapon(Weapon weapon) {
        if (weapon == null) throw new IllegalArgumentException("Weapon can't be null");
        if (weapons.size() >= 2) {
            return false;
        } else {
            weapons.add(weapon);
            return true;
        }
    }

    public int calculateWeaponSetPower() {
        int power = 0;
        for (Weapon w : weapons) {
            power += w.getPower();
        }
        return power;
    }
}
