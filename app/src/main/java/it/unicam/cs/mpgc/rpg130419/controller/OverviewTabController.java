package it.unicam.cs.mpgc.rpg130419.controller;


import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class OverviewTabController {
    private TableView activeExpeditionsTable;
    private ListView availableHeroesList;

    public OverviewTabController(TableView activeExpeditionsTable, ListView availableHeroesList) {
        this.activeExpeditionsTable = activeExpeditionsTable;
        this.availableHeroesList = availableHeroesList;
    }

    public void handlerPlanExpeditionButton() {}
    public void handlerViewHeroesButton() {}
}
