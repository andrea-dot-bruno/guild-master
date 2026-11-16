package it.unicam.cs.mpgc.rpg130419.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.io.IOException;

public final class SaveDialogs {
    private SaveDialogs() {}

    public static void showError(Window owner, String title, IOException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setGraphic(null);
        GuildDialogTheme.applyTo(alert.getDialogPane());
        alert.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("button-main");
        alert.showAndWait();
    }
}
