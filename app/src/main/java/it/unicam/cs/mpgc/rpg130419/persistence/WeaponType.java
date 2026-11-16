package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.Weapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.LeftFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.RightFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Sword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.Wand;

enum WeaponType {
    LEFT_FIST, RIGHT_FIST, SWORD, WAND;

    static WeaponType from(Weapon weapon) {
        return switch (weapon) {
            case LeftFist fist -> LEFT_FIST;
            case RightFist fist -> RIGHT_FIST;
            case Sword sword -> SWORD;
            case Wand wand -> WAND;
            default -> throw new IllegalArgumentException("Unsupported weapon: " + weapon.getClass().getName());
        };
    }

    Weapon create() {
        return switch (this) {
            case LEFT_FIST -> new LeftFist();
            case RIGHT_FIST -> new RightFist();
            case SWORD -> new Sword();
            case WAND -> new Wand();
        };
    }
}
