package it.unicam.cs.mpgc.rpg130419.model;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;

import java.util.List;
import java.util.Objects;
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

    private Expedition(UUID id, Dungeon dungeon, List<Hero> heroes, int remainingTime) {
        this.id = Objects.requireNonNull(id);
        this.dungeon = dungeon;
        this.heroes = List.copyOf(heroes);
        this.remainingTime = remainingTime;
        this.state = ExpeditionState.WAITING;
    }

    public static Expedition restore(UUID id, Dungeon dungeon, List<Hero> heroes, int remainingTime) {
        if (remainingTime < 1 || remainingTime > dungeon.getTimeToBeat()) {
            throw new IllegalArgumentException("Invalid remaining expedition time.");
        }
        return new Expedition(id, dungeon, heroes, remainingTime);
    }

    public UUID getId() {
        return id;
    }

    public double getSuccessProbability() {
        return calculateSuccessProbability(dungeon, heroes);
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

    boolean start() {
        if (state != ExpeditionState.WAITING) return false;
        state = ExpeditionState.ACTIVE;
        return true;
    }

    void advanceDay() {
        if (state != ExpeditionState.ACTIVE) return;

        remainingTime = Math.max(0, remainingTime - 1);
        if (remainingTime == 0) {
            double probability = getSuccessProbability();
            state = Math.random() < probability ? ExpeditionState.WON : ExpeditionState.LOST;
        }
    }

    public boolean isCompleted() {
        return state == ExpeditionState.WON || state == ExpeditionState.LOST;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public ExpeditionState getState() {
        return state;
    }

    public Dungeon getDungeon() {
        return dungeon;
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
