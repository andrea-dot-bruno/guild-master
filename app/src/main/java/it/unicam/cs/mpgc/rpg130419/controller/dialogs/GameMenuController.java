package it.unicam.cs.mpgc.rpg130419.controller.dialogs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.util.function.Predicate;

public class GameMenuController {
    private Predicate<Window> saveGameAction;

    @FXML private Label saveStatusLabel;
    @FXML private Button closeMenuButton;

    public void setSaveGameAction(Predicate<Window> saveGameAction) {
        this.saveGameAction = saveGameAction;
    }

    @FXML private void handleSaveGameButton() {
        saveStatusLabel.setVisible(false);
        boolean saved = saveGameAction.test(saveStatusLabel.getScene().getWindow());
        saveStatusLabel.setVisible(saved);
    }

    @FXML private void handleQuitGameButton() {
        Platform.exit();
    }

    @FXML private void handleCloseMenuButton() {
        closeMenuButton.getScene().getWindow().hide();
    }
}
