package it.unicam.cs.mpgc.rpg130419.model.heroes;

import it.unicam.cs.mpgc.rpg130419.model.weapons.WeaponSet;
import it.unicam.cs.mpgc.rpg130419.model.weapons.knightWeapons.KnightWeapon;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.LeftFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.noClassWeapons.RightFist;

import java.util.UUID;

public class Knight extends Hero<KnightWeapon> {
    private static final int initialExpRequirement = 100;
    private static final int expRequirementIncrement = 50;

    public Knight(String name, String iconPath) {
        super(name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement),
                new WeaponSet<>(new LeftFist(), new RightFist()));
    }

    public Knight(UUID id, String name, String iconPath, int level, int currentExp) {
        super(id, name, iconPath, new HeroLevel(initialExpRequirement, expRequirementIncrement, level, currentExp),
                new WeaponSet<>(new LeftFist(), new RightFist()));
    }

    @Override
    public String getHeroClass() {
        return "Knight";
    }
}
