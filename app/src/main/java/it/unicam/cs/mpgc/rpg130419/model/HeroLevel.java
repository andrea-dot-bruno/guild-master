package it.unicam.cs.mpgc.rpg130419.model;

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

    //TODO: change and balance this formula. It should favour more heroes with smaller expRequiredIncrement value.
    public void calculateRequiredExpToLevelUp() {
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

    public int getLevel() {
        return level;
    }
}
