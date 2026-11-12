package it.unicam.cs.mpgc.rpg130419.controller;


import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class OverviewController {
    @FXML private TableView<Expedition> activeExpeditionsTable;
    @FXML private ListView<String> availableHeroesList;

    @FXML private void handlePlanExpeditionButton(ActionEvent actionEvent) {} //TODO: implement
    @FXML private void handleViewHeroesButton(ActionEvent actionEvent) {} //TODO: implement

    public void setActiveExpeditionsTable(List<Expedition> activeExpeditionsList) {
        activeExpeditionsTable.getItems().setAll(activeExpeditionsList);
    }

    public void setAvailableHeroesList(List<Hero> availableHeroes) {
        List<String> heroesNames = new ArrayList<>();
        for (Hero h : availableHeroes) {
            heroesNames.add(h.getName());
        }
        availableHeroesList.getItems().addAll(heroesNames);
    }
}
