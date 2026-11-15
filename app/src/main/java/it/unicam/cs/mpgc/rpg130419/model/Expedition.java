package it.unicam.cs.mpgc.rpg130419.model;

import java.util.List;
import java.util.UUID;

public class Expedition {
    private final UUID id;
    private final Dungeon dungeon;
    private final List<Hero> heroes;
    private int remainingTime;
    private ExpeditionState state;

    public Expedition(Dungeon dungeon, List<Hero> heroes) {
        this.id = UUID.randomUUID();
        this.dungeon = dungeon;
        this.heroes = List.copyOf(heroes);
        this.remainingTime = dungeon.getTimeToBeat();
        this.state = ExpeditionState.WAITING;
    }

    public static double calculateSuccessProbability(Dungeon dungeon, List<Hero> heroes) {
        if (heroes.isEmpty()) return 0.0;

        double partyPower = 0;
        for (Hero hero : heroes) {
            partyPower += hero.getTotalPower();
        }
        if (partyPower >= dungeon.getDifficulty()) return 1.0;
        return Math.max(0.0, partyPower / dungeon.getDifficulty());
    }

    public void computeWin() {
        double probability = calculateSuccessProbability(dungeon, heroes);
        state = Math.random() < probability ? ExpeditionState.WON : ExpeditionState.LOST;
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
