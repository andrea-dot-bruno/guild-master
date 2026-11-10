package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class Guild {
    private String name;
    private GuildInventory inventory;
    private Wallet wallet;
    private List<Hero> heroes;
    private List<Expedition> activeExpeditions;

    public Guild(String name) {
        this.name = name;
        this.inventory = new GuildInventory();
        this.heroes = new ArrayList<>();
        this.activeExpeditions = new ArrayList<>();
        this.wallet = new Wallet();
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return wallet.getGold();
    }

    public int getActiveExpeditionsNumber() {
        return activeExpeditions.size();
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

    public void addHero(Hero hero) {
        if (hero == null) throw new IllegalArgumentException("Hero can't be null.");
        if (heroes.contains(hero)) throw new IllegalArgumentException("Hero already added to the guild.");
        heroes.add(hero);
    }

    public List<Expedition> getActiveExpeditions() {
        return activeExpeditions;
    }

    public void launchExpedition(Expedition expedition) {
        if (expedition == null) throw new IllegalArgumentException("Expedition can't be null.");
        activeExpeditions.add(expedition);
    }

    public boolean removeExpedition(Expedition expedition) {
        if (expedition == null) throw new IllegalArgumentException("Expedition can't be null.");
        if (!expedition.isCompleted()) return false;
        return activeExpeditions.remove(expedition);
    }

    public void endDay() {
        for (Expedition e : activeExpeditions) {
            e.dayHasPassed();
            if (e.isCompleted()) {
                e.computeWin();
            }
        }
    }

    //TODO: make this method better, maybe by making heroes a HashSet with K=Hero V=available.
    public List<Hero> getAvailableHeroes() {
        List<Hero> availableHeroes = new ArrayList<>();
        for (Hero h : heroes) {
            boolean available = true;
            for (Expedition e : activeExpeditions) {
                if (e.getInvolvedHeroes().contains(h)) {
                    available = false;
                }
            }
            if (available) availableHeroes.add(h);
        }
        return availableHeroes;
    }
}
