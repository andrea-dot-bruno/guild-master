package it.unicam.cs.mpgc.rpg130419.view;

import javafx.scene.control.DialogPane;

import java.net.URL;
import java.util.Objects;

public final class GuildDialogTheme {
    private GuildDialogTheme() {}

    public static void applyTo(DialogPane pane) {
        URL stylesheet = Objects.requireNonNull(GuildDialogTheme.class.getResource("/view/guild-theme.css"),
                "Missing guild-theme.css");
        pane.getStylesheets().add(stylesheet.toExternalForm());
        pane.getStyleClass().add("guild-dialog");
    }
}
