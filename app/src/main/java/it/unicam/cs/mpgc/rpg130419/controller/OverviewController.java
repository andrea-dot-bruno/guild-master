package it.unicam.cs.mpgc.rpg130419.controller;


import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;

public class OverviewController {
    private Runnable showHeroesAction;
    private Runnable planExpeditionAction;
    @FXML private TableColumn<Expedition, String> aeTableExpeditionColumn;
    @FXML private TableColumn<Expedition, String> aeTableHeroesColumn;
    @FXML private TableColumn<Expedition, Number> aeTableRemainingColumn;
    @FXML private TableView<Expedition> activeExpeditionsTable;
    @FXML private ListView<Hero> availableHeroesList;

    @FXML private void initialize() {
        aeTableExpeditionColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getDungeonName()));
        aeTableHeroesColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().printInvolvedHeroes()));
        aeTableRemainingColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getRemainingTime()));

        availableHeroesList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Hero hero, boolean empty) {
                super.updateItem(hero, empty);
                setText(empty || hero == null ? null : hero.getName());
            }
        });
    }

    @FXML private void handlePlanExpeditionButton(ActionEvent actionEvent) {
        planExpeditionAction.run();
    }

    @FXML private void handleViewHeroesButton(ActionEvent actionEvent) {
        showHeroesAction.run();
    }

    public void setShowHeroesAction(Runnable showHeroesAction) {
        this.showHeroesAction = showHeroesAction;
    }

    public void setPlanExpeditionAction(Runnable planExpeditionAction) {
        this.planExpeditionAction = planExpeditionAction;
    }

    public void setActiveExpeditionsTable(List<Expedition> activeExpeditionsList) {
        activeExpeditionsTable.getItems().setAll(activeExpeditionsList);
    }

    public void setAvailableHeroesList(List<Hero> availableHeroes) {
        availableHeroesList.getItems().setAll(availableHeroes);
    }
}
