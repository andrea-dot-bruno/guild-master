package it.unicam.cs.mpgc.rpg130419.view;

import it.unicam.cs.mpgc.rpg130419.controller.dialogs.ExpeditionPreparationController;
import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class ExpeditionPreparation {
    private ExpeditionPreparation() {}

    public static void show(Window owner, Dungeon dungeon, List<Hero> availableHeroes,
                            Predicate<List<Hero>> launchExpedition) {
        URL location = Objects.requireNonNull(
                ExpeditionPreparation.class.getResource("/view/expedition-preparation.fxml"),
                "Missing expedition-preparation.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        Parent content;
        try {
            content = loader.load();
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not load expedition preparation.", exception);
        }
        ExpeditionPreparationController controller = loader.getController();
        controller.prepare(dungeon, availableHeroes, launchExpedition);

        Stage preparation = new Stage(StageStyle.UNDECORATED);
        preparation.setTitle("Preparing Expedition");
        preparation.initOwner(owner);
        preparation.initModality(Modality.WINDOW_MODAL);
        preparation.setResizable(false);
        preparation.setScene(new Scene(content));
        preparation.setOnShown(event -> {
            preparation.setX(owner.getX() + (owner.getWidth() - preparation.getWidth()) / 2);
            preparation.setY(owner.getY() + (owner.getHeight() - preparation.getHeight()) / 2);
            content.requestFocus();
        });
        preparation.show();
    }
}
