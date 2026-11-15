package it.unicam.cs.mpgc.rpg130419.model;

public record RecruitmentCandidate(Hero hero, int hireFee) {
    public RecruitmentCandidate {
        if (hero == null) throw new IllegalArgumentException("Hero can't be null.");
        if (hireFee < 0) throw new IllegalArgumentException("Hire fee can't be negative.");
    }
}
