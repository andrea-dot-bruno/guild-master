package it.unicam.cs.mpgc.rpg130419.controller;


import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.List;

public class OverviewTabController {
    private TableView<Expedition> activeExpeditionsTable;
    private ListView<Hero> availableHeroesList;

    public OverviewTabController(TableView activeExpeditionsTable, ListView availableHeroesList) {
        this.activeExpeditionsTable = activeExpeditionsTable;
        this.availableHeroesList = availableHeroesList;
    }

    public void handlerPlanExpeditionButton() {} //TODO: implement
    public void handlerViewHeroesButton() {} //TODO: implement

    public void setActiveExpeditionsTable(List<Expedition> activeExpeditionsList) {
        activeExpeditionsTable.getItems().setAll(activeExpeditionsList);
    }

    public void setAvailableHeroesList(List<Hero> availableHeroes) {
        availableHeroesList.getItems().addAll(availableHeroes);
    }
}
