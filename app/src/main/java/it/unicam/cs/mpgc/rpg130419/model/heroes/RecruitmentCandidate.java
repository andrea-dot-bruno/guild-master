package it.unicam.cs.mpgc.rpg130419.model.heroes;

import java.util.Objects;

public record RecruitmentCandidate(Hero hero, int hireFee) {
    private static final int GOLD_PER_POWER = 5;

    public RecruitmentCandidate(Hero hero) {
        this(hero, Objects.requireNonNull(hero, "Hero can't be null.").getEquipmentPower() * GOLD_PER_POWER);
    }

    public RecruitmentCandidate {
        if (hero == null) throw new IllegalArgumentException("Hero can't be null.");
        if (hireFee < 0) throw new IllegalArgumentException("Hire fee can't be negative.");
    }
}
