package it.unicam.cs.mpgc.rpg130419.model;

import java.util.ArrayList;
import java.util.List;

public class Guild {
    private GuildInventory inventory;
    private List<Hero> heroes;

    public Guild() {
        this.inventory = new GuildInventory();
        this.heroes = new ArrayList<>();
    }
}
