package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public class MainController {
    private GameSession session;

    @FXML private HeaderController headerController;
    @FXML private OverviewController overviewController;
    @FXML private ExpeditionsController expeditionsController;
    @FXML private HeroesController heroesController;
    @FXML private RecruitmentController recruitmentController;
    @FXML private FooterController footerController;
    @FXML private TabPane mainTabPane;
    @FXML private Tab heroesTab;
    @FXML private Tab expeditionsTab;

    public void initialize() {
        heroesController.setFireHero(this::fireHero);
        overviewController.setShowHeroesAction(this::showHeroesTab);
        overviewController.setPlanExpeditionAction(this::showExpeditionsTab);
    }

    public void linkSession(GameSession session) {
        this.session = session;
    }

    public void updateDisplay() {
        setGoldAmountLabel();
        setDayNumberLabel();
        setUnderwayExpeditionsLabel();
        setGuildNameLabel();
        setActiveExpeditionsTable();
        setAvailableHeroesList();
    }

    private void showHeroesTab() {
        mainTabPane.getSelectionModel().select(heroesTab);
    }

    private void showExpeditionsTab() {
        mainTabPane.getSelectionModel().select(expeditionsTab);
    }

    private void fireHero(Hero hero) {
        session.removeHero(hero);
        populateHeroesTable();
    }

    public void populateHeroesTable() {
        heroesController.setGuildHeroes(session.getHeroes(), session.getAvailableHeroes());
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
        overviewController.setActiveExpeditionsTable(activeExpeditionsList);
    }

    public void setAvailableHeroesList() {
        List<Hero> availableHeroesList = session.getAvailableHeroes();
        overviewController.setAvailableHeroesList(availableHeroesList);
    }


}
