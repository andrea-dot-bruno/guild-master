package it.unicam.cs.mpgc.rpg130419.model;

public class RandomHeroBuilder {

    public static Hero buildHero() {
        int heroClass = randomHeroClassPicker();
        return switch (heroClass) {
            case 1 -> new Knight("knight");
            case 2 -> new Mage("mage");
            default -> throw new IllegalArgumentException("Invalid heroClass value");
        };
    }

    private static int randomHeroClassPicker() {
        int numberOfHeroesImplemented = 2;
        return (int) (Math.random() + 1) * numberOfHeroesImplemented;
    }
}
