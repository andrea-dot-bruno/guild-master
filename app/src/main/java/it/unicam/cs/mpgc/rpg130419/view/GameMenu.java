package it.unicam.cs.mpgc.rpg130419.view;

import it.unicam.cs.mpgc.rpg130419.controller.dialogs.GameMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.function.Predicate;

/**
 * Caricamento e setup della finestra di menu.
 * */
public final class GameMenu {
    private GameMenu() {}

    public static void show(Window owner, Predicate<Window> saveGameAction) throws IOException {
        String fxmlPath = "/view/game-menu-dialog.fxml";
        URL location = GameMenu.class.getResource(fxmlPath);
        if (location == null) {
            throw new IOException("FXML resource not found: " + fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(location);
        Parent content = loader.load();
        GameMenuController controller = loader.getController();
        controller.setSaveGameAction(saveGameAction);

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
