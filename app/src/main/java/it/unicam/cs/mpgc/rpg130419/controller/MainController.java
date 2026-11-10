package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

import java.util.List;

public class MainController {
    private HeaderController headerController;
    private OverviewTabController overviewTabController;
    private FooterController footerController;
    private GameSession session;

    public TableView<Expedition> activeExpeditionsTable;
    public ListView<Hero> availableHeroesList;
    public Label guildNameLabel;
    public Label goldAmountLabel;
    public Label dayNumberLabel;
    public Label underwayExpeditionsLabel;

    @FXML public void initialize() {
        headerController = new HeaderController(guildNameLabel, goldAmountLabel, dayNumberLabel);
        overviewTabController = new OverviewTabController(activeExpeditionsTable, availableHeroesList);
        footerController = new FooterController(underwayExpeditionsLabel);
    }

    @FXML private void handlerMenuButton() {headerController.handlerMenuButton();}
    @FXML private void handlerPlanExpeditionButton() {overviewTabController.handlerPlanExpeditionButton();}
    @FXML private void handlerViewHeroesButton() {overviewTabController.handlerViewHeroesButton();}
    @FXML private void handlerEndDayButton() {footerController.handlerEndDayButton();}

    public void linkSession(GameSession session) {
        this.session = session;
    }
    public void setGoldAmountLabel() {
        int goldAmount = session.getGold();
        headerController.setGoldAmountLabel(goldAmount);
    }

    public void setDayNumberLabel() {
        int currentDay = session.getCurrentDay();
        headerController.setDayNumberLabel(currentDay);
    }

    public void setUnderwayExpeditionsLabel() {
        int activeExpeditions = session.getActiveExpeditionsNumber();
        footerController.setUnderwayExpeditionsLabel(activeExpeditions);
    }

    public void setGuildNameLabel() {
        String guildName = session.getGuildName();
        headerController.setGuildNameLabel(guildName);
    }

    public void setActiveExpeditionsTable() {
        List<Expedition> activeExpeditionsList = session.getActiveExpeditions();
        overviewTabController.setActiveExpeditionsTable(activeExpeditionsList);
    }

    public void setAvailableHeroesList() {
        List<Hero> availableHeroesList = session.getAvailableHeroes();
        overviewTabController.setAvailableHeroesList(availableHeroesList);
    }

    public void updateDisplay() {
        setGoldAmountLabel();
        setDayNumberLabel();
        setUnderwayExpeditionsLabel();
        setGuildNameLabel();
        setActiveExpeditionsTable();
    }
}
