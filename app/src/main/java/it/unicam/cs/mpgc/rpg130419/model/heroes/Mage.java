package it.unicam.cs.mpgc.rpg130419.model.heroes;

import it.unicam.cs.mpgc.rpg130419.model.weapons.WeaponSet;
import it.unicam.cs.mpgc.rpg130419.model.weapons.mageWeapons.MageWeapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.LeftFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.RightFist;

import java.util.UUID;

public class Mage extends Hero<MageWeapon> {
    private static final int initialExpRequirement = 150;
    private static final int expRequirementIncrement = 35;

    public Mage(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement),
                new WeaponSet<>(new LeftFist(), new RightFist()));
    }

    public Mage(UUID id, String name, String iconPath, int level, int currentExp) {
        super(id, name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement, level, currentExp),
                new WeaponSet<>(new LeftFist(), new RightFist()));
    }

    @Override
    public String getHeroClass() {
        return "Mage";
    }
}
