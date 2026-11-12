package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML private Label guildNameLabel;
    @FXML private Label dayNumberLabel;
    @FXML private Label goldAmountLabel;

    @FXML private void handleMenuButton() {} //TODO: implement

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
