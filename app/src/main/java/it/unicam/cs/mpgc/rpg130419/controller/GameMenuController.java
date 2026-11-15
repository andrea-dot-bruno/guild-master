package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameMenuController {
    @FXML private Button closeMenuButton;

    @FXML private void handleQuitGameButton() {
        Platform.exit();
    }

    @FXML private void handleCloseMenuButton() {
        closeMenuButton.getScene().getWindow().hide();
    }
}
