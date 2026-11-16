package it.unicam.cs.mpgc.rpg130419.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.io.IOException;

public final class SaveDialogs {
    private SaveDialogs() {}

    public static void showError(Window owner, String title, IOException exception) {
        Alert alert = createAlert(owner, Alert.AlertType.ERROR, title, exception.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }

    public static boolean confirmOverwrite(Window owner) {
        ButtonType newGame = new ButtonType("New Game", ButtonType.OK.getButtonData());
        Alert alert = createAlert(owner, Alert.AlertType.CONFIRMATION, "New Game",
                "Start a new game? Any existing save will be overwritten.", newGame, ButtonType.CANCEL);
        return alert.showAndWait().filter(newGame::equals).isPresent();
    }

    private static Alert createAlert(Window owner, Alert.AlertType type, String title, String message,
                                     ButtonType primaryButton, ButtonType... otherButtons) {
        Alert alert = new Alert(type, message, primaryButton);
        alert.getButtonTypes().addAll(otherButtons);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setGraphic(null);
        GuildDialogTheme.applyTo(alert.getDialogPane());
        alert.getDialogPane().lookupButton(primaryButton).getStyleClass().add("button-main");
        return alert;
    }
}
