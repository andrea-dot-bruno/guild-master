package it.unicam.cs.mpgc.rpg130419.controller.tabs;


import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

public class OverviewController {
    private Runnable showHeroesAction;
    private Runnable planExpeditionAction;
    @FXML private TableColumn<Expedition, String> aeTableExpeditionColumn;
    @FXML private TableColumn<Expedition, String> aeTableHeroesColumn;
    @FXML private TableColumn<Expedition, Number> aeTableRemainingColumn;
    @FXML private TableColumn<Expedition, Number> aeTableGoldRewardColumn;
    @FXML private TableColumn<Expedition, Number> aeTableSuccessChanceColumn;
    @FXML private TableView<Expedition> activeExpeditionsTable;
    @FXML private ListView<Hero> availableHeroesList;

    @FXML private void initialize() {
        aeTableExpeditionColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getDungeonName()));
        aeTableHeroesColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().printInvolvedHeroes()));
        aeTableRemainingColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getRemainingTime()));
        aeTableGoldRewardColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getDungeon().getGoldReward()));
        aeTableSuccessChanceColumn.setCellValueFactory(cell ->
                new ReadOnlyDoubleWrapper(cell.getValue().getSuccessProbability()));

        NumberFormat probabilityFormat = NumberFormat.getPercentInstance();
        probabilityFormat.setMaximumFractionDigits(1);
        probabilityFormat.setRoundingMode(RoundingMode.DOWN);
        aeTableSuccessChanceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number probability, boolean empty) {
                super.updateItem(probability, empty);
                setText(empty || probability == null ? null : probabilityFormat.format(probability));
            }
        });

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
        if (activeExpeditionsTable.getSelectionModel().isEmpty()) {
            activeExpeditionsTable.getSelectionModel().selectFirst();
        }
    }

    public void setAvailableHeroesList(List<Hero> availableHeroes) {
        availableHeroesList.getItems().setAll(availableHeroes);
    }
}
