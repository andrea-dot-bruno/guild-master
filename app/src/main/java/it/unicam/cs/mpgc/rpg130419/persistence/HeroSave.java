package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Knight;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Mage;

import java.util.Objects;
import java.util.UUID;

record HeroSave(UUID id, HeroType type, String name, String iconPath, int level, int currentExp,
                WeaponType leftWeapon, WeaponType rightWeapon) {
    static HeroSave from(Hero hero) {
        HeroType type = switch (hero) {
            case Knight knight -> HeroType.KNIGHT;
            case Mage mage -> HeroType.MAGE;
            default -> throw new IllegalArgumentException("Unsupported hero: " + hero.getClass().getName());
        };
        return new HeroSave(hero.getId(), type, hero.getName(), hero.getIconPath(), hero.getLevel(),
                hero.getCurrentExp(), WeaponType.from(hero.getLeftWeapon()), WeaponType.from(hero.getRightWeapon()));
    }

    Hero restore() {
        Objects.requireNonNull(name);
        Hero hero = switch (type) {
            case KNIGHT -> new Knight(id, name, iconPath, level, currentExp);
            case MAGE -> new Mage(id, name, iconPath, level, currentExp);
        };
        hero.setLeftWeapon(leftWeapon.create());
        hero.setRightWeapon(rightWeapon.create());
        return hero;
    }
}
