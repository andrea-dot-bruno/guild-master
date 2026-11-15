package it.unicam.cs.mpgc.rpg130419.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public final class EndDayConfirmation {
    private EndDayConfirmation() {}

    public static boolean show(Window owner) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to end the day?", ButtonType.YES, ButtonType.NO);
        confirmation.initOwner(owner);
        confirmation.setTitle("End Day");
        confirmation.setHeaderText("End Day");

        ImageView moon = new ImageView("assets/moon.png");
        moon.setFitWidth(30);
        moon.setFitHeight(30);
        moon.setPreserveRatio(true);
        confirmation.setGraphic(moon);

        applyTheme(confirmation.getDialogPane());
        return confirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private static void applyTheme(DialogPane pane) {
        GuildDialogTheme.applyTo(pane);
        pane.lookupButton(ButtonType.YES).getStyleClass().add("button-main");
        pane.lookupButton(ButtonType.NO).getStyleClass().add("button-secondary");
    }
}
