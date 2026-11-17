package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.view.GameMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.io.IOException;
import java.util.function.Predicate;

public class HeaderController {
    private Predicate<Window> saveGameAction;

    @FXML private Label guildNameLabel;
    @FXML private Label dayNumberLabel;
    @FXML private Label goldAmountLabel;
    @FXML private Button menuButton;

    @FXML private void handleMenuButton() throws IOException {
        GameMenu.show(menuButton.getScene().getWindow(), saveGameAction);
    }

    public void setSaveGameAction(Predicate<Window> saveGameAction) {
        this.saveGameAction = saveGameAction;
    }

    public void setGuildNameLabel(String guildName) {
        guildNameLabel.setText(guildName);
    }

    public void setDayNumberLabel(int dayNumber) {
        dayNumberLabel.setText("Day %d".formatted(dayNumber));
    }

    public void setGoldAmountLabel(int goldAmount) {
        goldAmountLabel.setText("%d G".formatted(goldAmount));
    }
}
