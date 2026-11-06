package it.unicam.cs.mpgc.rpg130419.model;

import java.util.List;

public class Expedition {
    private Dungeon dungeon;
    private List<Hero> heroes;

    public Expedition(Dungeon dungeon, List<Hero> heroes) {
        this.dungeon = dungeon;
        this.heroes = heroes;
    }

    //TODO: this method is not accounting for items in the party, but it should.
    public boolean win() {
        double partyPower = 0;
        for (Hero hero : heroes) {
            partyPower += hero.getTotalPower();
        }
        if (partyPower >= dungeon.getDifficulty()) {
            return true;
        } else {
            double winPercentage = partyPower / dungeon.getDifficulty();
            double randomNumber = Math.random();
            return winPercentage >= randomNumber;
        }
    }
}
