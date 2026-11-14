package it.unicam.cs.mpgc.rpg130419.controller;


import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import java.util.List;

public class OverviewController {
    private Runnable showHeroesAction;
    @FXML private TableView<Expedition> activeExpeditionsTable;
    @FXML private ListView<Hero> availableHeroesList;

    @FXML private void handlePlanExpeditionButton(ActionEvent actionEvent) {} //TODO: implement

    @FXML private void handleViewHeroesButton(ActionEvent actionEvent) {
        showHeroesAction.run();
    }

    public void setShowHeroesAction(Runnable showHeroesAction) {
        this.showHeroesAction = showHeroesAction;
    }

    public void setActiveExpeditionsTable(List<Expedition> activeExpeditionsList) {
        activeExpeditionsTable.getItems().setAll(activeExpeditionsList);
    }

    public void setAvailableHeroesList(List<Hero> availableHeroes) {
//        TODO: review method
//        List<String> heroesNames = new ArrayList<>();
//        for (Hero h : availableHeroes) {
//            heroesNames.add(h.getName());
//        }
//        availableHeroesList.getItems().addAll(heroesNames);
    }
}
