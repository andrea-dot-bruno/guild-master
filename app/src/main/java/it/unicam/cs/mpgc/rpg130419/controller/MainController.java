package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.fxml.FXML;
import java.util.List;

public class MainController {
    private GameSession session;
    private HeaderController headerController;
    private OverviewController overviewController;
    private ExpeditionsController expeditionsController;
    private HeroesController heroesController;
    private RecruitmentController recruitmentController;
    private FooterController footerController;

    public MainController() {
        this.headerController = new HeaderController();
        this.overviewController = new OverviewController();
        this.expeditionsController = new ExpeditionsController();
        this.heroesController = new HeroesController();
        this.recruitmentController = new RecruitmentController();
        this.footerController = new FooterController();
    }

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
        setAvailableHeroesList();
    }
}
