package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;

public class MainController {
    public TableView activeExpeditionsTable;
    public ListView availableHeroesList;
    private HeaderController headerController;
    private OverviewTabController overviewTabController;
    private FooterController footerController;

    public Label guildNameLabel;
    public Label goldAmountLabel;
    public Label dayNumberLabel;
    public Label underwayExpeditionsLabel;

    public MainController() {
        headerController = new HeaderController(guildNameLabel, goldAmountLabel, dayNumberLabel);
        overviewTabController = new OverviewTabController(activeExpeditionsTable, availableHeroesList);
        footerController = new FooterController(underwayExpeditionsLabel);
    }

    @FXML private void handlerMenuButton() {headerController.handlerMenuButton();}
    @FXML private void handlerPlanExpeditionButton() {overviewTabController.handlerPlanExpeditionButton();}
    @FXML private void handlerViewHeroesButton() {overviewTabController.handlerViewHeroesButton();}
    @FXML private void handlerEndDayButton() {footerController.handlerEndDayButton();}

    public void setGoldAmountLabel(int goldAmount) {
        headerController.setGoldAmountLabel(goldAmount);
    }

    public void setDayNumberLabel(int dayNumber) {
        headerController.setDayNumberLabel(dayNumber);
    }

    public void setUnderwayExpeditionsLabel(int underwayExpeditions) {
        footerController.setUnderwayExpeditionsLabel(underwayExpeditions);
    }

    public void  setGuildNameLabel(String guildName) {
        headerController.setGuildNameLabel(guildName);
    }
}
