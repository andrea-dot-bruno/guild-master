package it.unicam.cs.mpgc.rpg130419.controller.tabs;

import it.unicam.cs.mpgc.rpg130419.model.dungeons.Dungeon;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.function.Consumer;

public class ExpeditionsController {
    private Consumer<Dungeon> prepareExpedition;

    @FXML private TableView<Dungeon> availableDungeonsTable;
    @FXML private Button prepareExpeditionButton;
    @FXML private TableColumn<Dungeon, String> dungeonNameColumn;
    @FXML private TableColumn<Dungeon, Number> dungeonDaysColumn;
    @FXML private TableColumn<Dungeon, Number> dungeonGoldRewardColumn;
    @FXML private TableColumn<Dungeon, Number> dungeonDifficultyColumn;
    @FXML private Label boardDungeonName;
    @FXML private ImageView boardDungeonIcon;
    @FXML private Label boardDungeonDifficulty;
    @FXML private Label boardDungeonDays;
    @FXML private Label boardDungeonExpReward;
    @FXML private Label boardDungeonGoldReward;

    @FXML private void initialize() {
        dungeonNameColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getName()));
        dungeonDaysColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getTimeToBeat()));
        dungeonGoldRewardColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getGoldReward()));
        dungeonDifficultyColumn.setCellValueFactory(cell ->
                new ReadOnlyDoubleWrapper(cell.getValue().getDifficulty()));

        clearInfoFields();
        prepareExpeditionButton.disableProperty().bind(
                availableDungeonsTable.getSelectionModel().selectedItemProperty().isNull());
        availableDungeonsTable.getSelectionModel().selectedItemProperty()
                .addListener((property, oldDungeon, newDungeon) -> {
                    if (newDungeon == null) {
                        clearInfoFields();
                    } else {
                        populateInfoFields(newDungeon);
                    }
                });
    }

    @FXML private void handlePrepareExpeditionButton() {
        Dungeon dungeon = availableDungeonsTable.getSelectionModel().getSelectedItem();
        if (dungeon != null) prepareExpedition.accept(dungeon);
    }

    public void setPrepareExpedition(Consumer<Dungeon> prepareExpedition) {
        this.prepareExpedition = prepareExpedition;
    }

    public void setAvailableDungeons(List<Dungeon> availableDungeons) {
        availableDungeonsTable.getItems().setAll(availableDungeons);
        if (availableDungeonsTable.getSelectionModel().isEmpty()) {
            availableDungeonsTable.getSelectionModel().selectFirst();
        }
    }

    private void clearInfoFields() {
        boardDungeonName.setText("Select a Dungeon to display its details.");
        boardDungeonIcon.setImage(null);
        boardDungeonDifficulty.setText("");
        boardDungeonDays.setText("");
        boardDungeonExpReward.setText("");
        boardDungeonGoldReward.setText("");
    }

    private void populateInfoFields(Dungeon dungeon) {
        boardDungeonName.setText(dungeon.getName());
        boardDungeonIcon.setImage(new Image(dungeon.getIconPath()));
        boardDungeonDifficulty.setText("Difficulty: %s".formatted(dungeon.getDifficulty()));
        boardDungeonDays.setText("Days: %d".formatted(dungeon.getTimeToBeat()));
        boardDungeonExpReward.setText("XP Reward: %d".formatted(dungeon.getExpReward()));
        boardDungeonGoldReward.setText("Gold Reward: %d G".formatted(dungeon.getGoldReward()));
    }
}
