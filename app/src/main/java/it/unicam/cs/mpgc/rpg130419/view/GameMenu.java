package it.unicam.cs.mpgc.rpg130419.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

/**
 * Caricamento e setup della finestra di menu.
 * */
public final class GameMenu {
    private GameMenu() {}

    public static void show(Window owner) throws IOException {
        String fxmlPath = "/view/game-menu-dialog.fxml";
        URL location = GameMenu.class.getResource(fxmlPath);
        if (location == null) {
            throw new IOException("FXML resource not found: " + fxmlPath);
        }
        Parent content = FXMLLoader.load(location);

        Stage menu = new Stage(StageStyle.UNDECORATED);
        menu.setTitle("Game Menu");
        menu.initOwner(owner);
        menu.initModality(Modality.WINDOW_MODAL);
        menu.setScene(new Scene(content));
        menu.setOnShown(event -> {
            centerOnOwner(menu, owner);
            content.requestFocus();
        });
        menu.show();
    }

    private static void centerOnOwner(Stage menu, Window owner) {
        menu.setX(owner.getX() + (owner.getWidth() - menu.getWidth()) / 2);
        menu.setY(owner.getY() + (owner.getHeight() - menu.getHeight()) / 2);
    }
}
