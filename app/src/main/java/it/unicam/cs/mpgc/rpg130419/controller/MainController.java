package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.DayResult;
import it.unicam.cs.mpgc.rpg130419.model.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.GameSession;
import it.unicam.cs.mpgc.rpg130419.model.Hero;
import it.unicam.cs.mpgc.rpg130419.model.RecruitmentCandidate;
import it.unicam.cs.mpgc.rpg130419.persistence.GameSaveRepository;
import it.unicam.cs.mpgc.rpg130419.view.DayEndDialogs;
import it.unicam.cs.mpgc.rpg130419.view.ExpeditionPreparation;
import it.unicam.cs.mpgc.rpg130419.view.SaveDialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;

public class MainController {
    private GameSession session;
    private GameSaveRepository saves;

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
        headerController.setSaveGameAction(this::saveGame);
        heroesController.setFireHero(this::fireHero);
        recruitmentController.setHireHero(this::hireHero);
        overviewController.setShowHeroesAction(this::showHeroesTab);
        overviewController.setPlanExpeditionAction(this::showExpeditionsTab);
        expeditionsController.setPrepareExpedition(this::prepareExpedition);
        footerController.setEndDay(this::endDay);
    }

    public void linkSession(GameSession session, GameSaveRepository saves) {
        this.session = session;
        this.saves = saves;
    }

    public void updateDisplay() {
        setGoldAmountLabel();
        setDayNumberLabel();
        setUnderwayExpeditionsLabel();
        setGuildNameLabel();
        setActiveExpeditionsTable();
        setAvailableHeroesList();
        populateHeroesTable();
        setAvailableDungeonsTable();
        setRecruitmentCandidatesTable();
        mainTabPane.setDisable(session.isGameOver());
        footerController.setEndDayDisabled(session.isGameOver());
    }

    private void showHeroesTab() {
        mainTabPane.getSelectionModel().select(heroesTab);
    }

    private void showExpeditionsTab() {
        mainTabPane.getSelectionModel().select(expeditionsTab);
    }

    private void prepareExpedition(Dungeon dungeon) {
        ExpeditionPreparation.show(mainTabPane.getScene().getWindow(), dungeon,
                session.getAvailableHeroes(), party -> launchExpedition(dungeon, party));
    }

    private boolean launchExpedition(Dungeon dungeon, List<Hero> party) {
        if (!session.launchExpedition(dungeon, party)) return false;
        setAvailableDungeonsTable();
        setUnderwayExpeditionsLabel();
        setActiveExpeditionsTable();
        setAvailableHeroesList();
        populateHeroesTable();
        return true;
    }

    private void endDay() {
        DayResult result = session.endDay();
        updateDisplay();
        Window owner = mainTabPane.getScene().getWindow();
        saveSession(owner, "Could Not Autosave Game");
        if (result.gameOver()) {
            DayEndDialogs.showGameOver(owner);
        } else if (!result.completedExpeditions().isEmpty()) {
            DayEndDialogs.showExpeditionReport(owner, result.completedExpeditions());
        }
    }

    private boolean saveGame(Window owner) {
        return saveSession(owner, "Could Not Save Game");
    }

    private boolean saveSession(Window owner, String errorTitle) {
        try {
            saves.save(session);
            return true;
        } catch (IOException exception) {
            SaveDialogs.showError(owner, errorTitle, exception);
            return false;
        }
    }

    private void fireHero(Hero hero) {
        if (!session.removeHero(hero)) {
            heroesController.showFireFailure(hero);
            return;
        }
        populateHeroesTable();
        setAvailableHeroesList();
    }

    private void hireHero(RecruitmentCandidate candidate) {
        if (!session.hireHero(candidate)) {
            recruitmentController.showHireFailure(candidate);
            return;
        }
        setGoldAmountLabel();
        setRecruitmentCandidatesTable();
        populateHeroesTable();
        setAvailableHeroesList();
    }

    private void populateHeroesTable() {
        heroesController.setGuildHeroes(session.getHeroes(), session.getAvailableHeroes(),
                session.getTotalDailyWages());
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

    private void setAvailableDungeonsTable() {
        List<Dungeon> availableDungeons = session.getAvailableDungeons();
        expeditionsController.setAvailableDungeons(availableDungeons);
    }

    private void setRecruitmentCandidatesTable() {
        List<RecruitmentCandidate> recruitmentCandidates = session.getRecruitmentCandidates();
        recruitmentController.setRecruitmentCandidates(recruitmentCandidates);
    }

    public void setAvailableHeroesList() {
        List<Hero> availableHeroesList = session.getAvailableHeroes();
        overviewController.setAvailableHeroesList(availableHeroesList);
    }


}
