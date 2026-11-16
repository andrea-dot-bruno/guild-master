package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class StartScreenController {
    private Runnable newGameAction;
    private Runnable loadGameAction;

    public void setNewGameAction(Runnable newGameAction) {
        this.newGameAction = newGameAction;
    }

    public void setLoadGameAction(Runnable loadGameAction) {
        this.loadGameAction = loadGameAction;
    }

    @FXML
    private void handleNewGame() {
        newGameAction.run();
    }

    @FXML
    private void handleLoadGame() {
        loadGameAction.run();
    }

    @FXML
    private void handleQuit() {
        Platform.exit();
    }
}
