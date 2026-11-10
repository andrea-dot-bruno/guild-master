package it.unicam.cs.mpgc.rpg130419.model;

public class Wallet {
    private int gold;

    public Wallet() {
        this.gold = 0;
    }

    public int getGold() {
        return gold;
    }

    public void addMoney(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Cannot add negative amount of gold.");
        gold += amount;
    }

    public boolean canPay(int price) {
        return gold > price;
    }

    public boolean takeMoney(int amount) {
        if (!canPay(amount)) return false;
        gold -= amount;
        return true;
    }
}
