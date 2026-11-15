package it.unicam.cs.mpgc.rpg130419.model;

import java.util.List;
import java.util.UUID;

public class Expedition {
    private final UUID id;
    private Dungeon dungeon;
    private List<Hero> heroes; //TODO: might develop a Party class to store party heroes and handle exp-ing.
    private int remainingTime;
    private ExpeditionState state;

    public Expedition(Dungeon dungeon, List<Hero> heroes) {
        this.id = UUID.randomUUID();
        this.dungeon = dungeon;
        this.heroes = heroes;
        this.remainingTime = dungeon.getTimeToBeat();
        this.state = ExpeditionState.WAITING;
    }

    //TODO: this method is not accounting for items in the party, but it should.
    //TODO: create computePartyPower() method for cleaner code.
    public void computeWin() {
        double partyPower = 0;
        for (Hero hero : heroes) {
            partyPower += hero.getTotalPower();
        }
        if (partyPower >= dungeon.getDifficulty()) {
            state = ExpeditionState.WON;
        } else {
            double winPercentage = partyPower / dungeon.getDifficulty();
            double randomNumber = Math.random();
            if (randomNumber > winPercentage) {
                state = ExpeditionState.LOST;
            } else {
                state = ExpeditionState.WON;
            }
        }
    }

    public void dayHasPassed() {
        remainingTime -= 1;
    }

    public boolean isCompleted() {
        return remainingTime <= 0;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setState(ExpeditionState state) {
        this.state = state;
    }

    public List<Hero> getInvolvedHeroes() {
        return heroes;
    }

    public String printInvolvedHeroes() {
        StringBuilder output = new StringBuilder();
        if (heroes == null) return "No heroes in this expedition. Strange...";
        if (heroes.size() == 1) return heroes.getFirst().getName();
        for (int i = 0; i < heroes.size(); i++) {
            Hero h = heroes.get(i);
            if (i == heroes.size() - 1) {
                output.append("%s.".formatted(h.getName()));
            } else {
                output.append("%s, ".formatted(h.getName()));
            }
        }
        return output.toString();
    }

    public String getDungeonName() {
        return dungeon.getName();
    }

    @Override
    public final boolean equals(Object other) {
        return other instanceof Expedition expedition && id.equals(expedition.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
