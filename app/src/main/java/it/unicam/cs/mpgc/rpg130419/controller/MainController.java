package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.fxml.FXML;
import java.util.List;

public class MainController {
    private GameSession session;

    @FXML private HeaderController headerController;
    @FXML private OverviewController overviewController;
    @FXML private ExpeditionsController expeditionsController;
    @FXML private HeroesController heroesController;
    @FXML private RecruitmentController recruitmentController;
    @FXML private FooterController footerController;

    public void initialize() {
        heroesController.setFireHero(this::fireHero);
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

    private void fireHero(Hero hero) {
        session.removeHero(hero);
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
