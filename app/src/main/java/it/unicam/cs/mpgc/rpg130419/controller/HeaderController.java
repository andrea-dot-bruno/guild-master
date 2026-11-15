package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.view.GameMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HeaderController {
    @FXML private Label guildNameLabel;
    @FXML private Label dayNumberLabel;
    @FXML private Label goldAmountLabel;
    @FXML private Button menuButton;

    @FXML private void handleMenuButton() throws IOException {
        GameMenu.show(menuButton.getScene().getWindow());
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
