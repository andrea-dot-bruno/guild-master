package it.unicam.cs.mpgc.rpg130419.model.heroes;

public class HeroLevel {
    private int level;
    private final int initialExpRequirement;
    private final int expRequirementIncrement;
    private int currentExp;
    private int requiredExpToLevelUp;

    public HeroLevel(int initialExpRequirement, int expRequirementIncrement) {
        this.initialExpRequirement = initialExpRequirement;
        this.expRequirementIncrement = expRequirementIncrement;
        this.requiredExpToLevelUp = initialExpRequirement;
        this.level = 1;
        this.currentExp = 0;
    }

    HeroLevel(int initialExpRequirement, int expRequirementIncrement, int level, int currentExp) {
        if (level < 1 || currentExp < 0) throw new IllegalArgumentException("Invalid hero progress.");
        this.initialExpRequirement = initialExpRequirement;
        this.expRequirementIncrement = expRequirementIncrement;
        this.level = level;
        this.currentExp = currentExp;
        calculateRequiredExpToLevelUp();
    }

    private void calculateRequiredExpToLevelUp() {
        requiredExpToLevelUp = initialExpRequirement + (expRequirementIncrement * (level - 1));
    }

    public void addExp(int exp) {
        if (exp < 0) throw new IllegalArgumentException("Cannot add a negative number of exp.");
        currentExp += exp;
    }

    public void levelUp() {
        while (currentExp >= requiredExpToLevelUp) {
            level += 1;
            currentExp = currentExp - requiredExpToLevelUp;
            calculateRequiredExpToLevelUp();
        }
    }

    public double getExpPercentage() {
        return (currentExp / (double) requiredExpToLevelUp);
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentExp() {
        return currentExp;
    }
}
