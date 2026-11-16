package it.unicam.cs.mpgc.rpg130419.view;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.ExpeditionState;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

public final class DayEndDialogs {
    private DayEndDialogs() {}

    public static void showExpeditionReport(Window owner, List<Expedition> expeditions) {
        List<String> sections = new ArrayList<>();
        addSection(sections, "Victories", expeditions, ExpeditionState.WON);
        addSection(sections, "Defeats", expeditions, ExpeditionState.LOST);

        Alert alert = createAlert(owner, Alert.AlertType.INFORMATION, "Expedition Report");
        Label report = new Label(String.join("\n\n", sections));
        report.setWrapText(true);
        ScrollPane scroll = new ScrollPane(report);
        scroll.setFitToWidth(true);
        scroll.setMaxHeight(300);
        scroll.getStyleClass().add("expedition-report");
        alert.getDialogPane().setContent(scroll);
        alert.showAndWait();
    }

    public static void showGameOver(Window owner) {
        Alert alert = createAlert(owner, Alert.AlertType.WARNING, "Game Over");
        alert.setContentText("The guild ran out of gold to pay its heroes.");
        alert.showAndWait();
    }

    public static boolean confirmNewGame(Window owner) {
        Alert alert = createAlert(owner, Alert.AlertType.CONFIRMATION, "Game Over");
        ButtonType newGame = new ButtonType("New Game", ButtonType.OK.getButtonData());
        alert.getButtonTypes().setAll(newGame, ButtonType.CANCEL);
        alert.getDialogPane().lookupButton(newGame).getStyleClass().add("button-main");
        alert.setContentText("The saved game has ended. Start a new game?");
        return alert.showAndWait().filter(newGame::equals).isPresent();
    }

    private static Alert createAlert(Window owner, Alert.AlertType type, String title) {
        Alert alert = new Alert(type, "", ButtonType.OK);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setGraphic(null);
        GuildDialogTheme.applyTo(alert.getDialogPane());
        alert.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("button-main");
        return alert;
    }

    private static void addSection(List<String> sections, String title,
                                   List<Expedition> expeditions, ExpeditionState outcome) {
        List<String> dungeonNames = expeditions.stream()
                .filter(expedition -> expedition.getState() == outcome)
                .map(expedition -> "• " + expedition.getDungeonName())
                .toList();
        if (!dungeonNames.isEmpty()) {
            sections.add(title + "\n" + String.join("\n", dungeonNames));
        }
    }
}
