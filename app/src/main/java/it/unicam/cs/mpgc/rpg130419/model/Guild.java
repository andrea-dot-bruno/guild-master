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
}
