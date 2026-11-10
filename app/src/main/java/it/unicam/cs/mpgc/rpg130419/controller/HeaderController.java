package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.scene.control.Label;

public class HeaderController {
    private Label goldAmountLabel;
    private Label guildNameLabel;
    private Label dayNumberLabel;

    public HeaderController(Label goldAmountLabel, Label guildNameLabel, Label dayNumberLabel) {
        this.goldAmountLabel =  goldAmountLabel;
        this.guildNameLabel = guildNameLabel;
        this.dayNumberLabel = dayNumberLabel;
    }

    public void handlerMenuButton() {}

    public void setGoldAmountLabel(int goldAmount) {
        goldAmountLabel.setText("" + goldAmount);
    }

    public void setDayNumberLabel(int dayNumber) {
        dayNumberLabel.setText("" + dayNumber);
    }

    public void setGuildNameLabel(String guildName) {
        guildNameLabel.setText(guildName);
    }
}
