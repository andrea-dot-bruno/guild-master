package it.unicam.cs.mpgc.rpg130419.model;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Guild {
    private String name;
    private final Wallet wallet;
    private final List<Hero<?>> heroes;
    private final List<Expedition> activeExpeditions;

    public Guild(String name) {
        this.name = name;
        this.heroes = new ArrayList<>();
        this.activeExpeditions = new ArrayList<>();
        this.wallet = new Wallet();
    }

    public boolean launchExpedition(Expedition expedition) {
        if (expedition == null) throw new IllegalArgumentException("Expedition can't be null.");
        List<Hero<?>> party = expedition.getInvolvedHeroes();
        if (party.isEmpty() || new HashSet<>(party).size() != party.size()) return false;
        if (!getAvailableHeroes().containsAll(party)) return false;
        if (!expedition.start()) return false;
        activeExpeditions.add(expedition);
        return true;
    }

    List<Expedition> advanceExpeditions() {
        List<Expedition> completedExpeditions = new ArrayList<>();
        Iterator<Expedition> expeditions = activeExpeditions.iterator();
        while (expeditions.hasNext()) {
            Expedition expedition = expeditions.next();
            expedition.advanceDay();
            if (expedition.isCompleted()) {
                completedExpeditions.add(expedition);
                expeditions.remove();
            }
        }
        return completedExpeditions;
    }

    void awardExpeditionRewards(List<Expedition> completedExpeditions) {
        for (Expedition expedition : completedExpeditions) {
            if (expedition.getState() != ExpeditionState.WON) continue;
            Dungeon dungeon = expedition.getDungeon();
            addMoney(dungeon.getGoldReward());
            for (Hero<?> hero : expedition.getInvolvedHeroes()) {
                hero.addExp(dungeon.getExpReward());
                hero.levelUp();
            }
        }
    }

    public String getName() {
        return name;
    }

    void rename(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Guild name cannot be blank.");
        this.name = name.strip();
    }

    public int getGold() {
        return wallet.getGold();
    }

    public void addMoney(int amount) {
        wallet.addMoney(amount);
    }

    public boolean canPay(int price) {
        return wallet.canPay(price);
    }

    public boolean takeMoney(int amount) {
        return wallet.takeMoney(amount);
    }

    boolean payDailyWages() {
        return takeMoney(getTotalDailyWages());
    }

    int getTotalDailyWages() {
        int totalWages = 0;
        for (Hero<?> hero : heroes) {
            totalWages += hero.getDailyPay();
        }
        return totalWages;
    }

    public List<Hero<?>> getHeroes() {
        return List.copyOf(heroes);
    }

    public void addHero(Hero<?> hero) {
        if (hero == null) throw new IllegalArgumentException("Hero can't be null.");
        if (heroes.contains(hero)) throw new IllegalArgumentException("Hero already added to the guild.");
        heroes.add(hero);
    }

    public boolean removeHero(Hero<?> hero) {
        if (!getAvailableHeroes().contains(hero)) return false;
        return heroes.remove(hero);
    }

    public List<Expedition> getActiveExpeditions() {
        return List.copyOf(activeExpeditions);
    }

    public int getActiveExpeditionsNumber() {
        return activeExpeditions.size();
    }

    public List<Hero<?>> getAvailableHeroes() {
        List<Hero<?>> availableHeroes = new ArrayList<>();
        for (Hero<?> h : heroes) {
            boolean available = true;
            for (Expedition e : activeExpeditions) {
                if (e.getInvolvedHeroes().contains(h)) {
                    available = false;
                    break;
                }
            }
            if (available) availableHeroes.add(h);
        }
        return availableHeroes;
    }
}
