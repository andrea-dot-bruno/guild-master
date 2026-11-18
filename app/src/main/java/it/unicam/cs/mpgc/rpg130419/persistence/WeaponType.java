package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.weapons.Weapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.BronzeSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.IronSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.SteelSword;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.BasicWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.EmberWand;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.MoonstoneScepter;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.LeftFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.RightFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.Shield;

enum WeaponType {
    LEFT_FIST, RIGHT_FIST, SWORD, WAND, IRON_SWORD, STEEL_SWORD, EMBER_WAND, MOONSTONE_SCEPTER, SHIELD;

    static WeaponType from(Weapon weapon) {
        return switch (weapon) {
            case LeftFist fist -> LEFT_FIST;
            case RightFist fist -> RIGHT_FIST;
            case BronzeSword bronzeSword -> SWORD;
            case BasicWand basicWand -> WAND;
            case IronSword ironSword -> IRON_SWORD;
            case SteelSword steelSword -> STEEL_SWORD;
            case EmberWand emberWand -> EMBER_WAND;
            case MoonstoneScepter moonstoneScepter -> MOONSTONE_SCEPTER;
            case Shield shield -> SHIELD;
            default -> throw new IllegalArgumentException("Unsupported weapon: " + weapon.getClass().getName());
        };
    }

    Weapon create() {
        return switch (this) {
            case LEFT_FIST -> new LeftFist();
            case RIGHT_FIST -> new RightFist();
            case SWORD -> new BronzeSword();
            case WAND -> new BasicWand();
            case IRON_SWORD -> new IronSword();
            case STEEL_SWORD -> new SteelSword();
            case EMBER_WAND -> new EmberWand();
            case MOONSTONE_SCEPTER -> new MoonstoneScepter();
            case SHIELD -> new Shield();
        };
    }
}
