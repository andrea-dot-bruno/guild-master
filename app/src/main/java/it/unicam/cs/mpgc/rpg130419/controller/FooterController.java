package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FooterController {
    @FXML private Label underwayExpeditionsLabel;

    @FXML private void handleEndDayButton(ActionEvent actionEvent) {} //TODO: implement

    public void setUnderwayExpeditionsLabel(int underwayExpeditions) {
        if (underwayExpeditions < 0) {
            throw new  IllegalArgumentException("Underway expeditions cannot be negative.");
        }
        if (underwayExpeditions == 0) {
            underwayExpeditionsLabel.setText("There are no expeditions underway at the moment.");
        } else if (underwayExpeditions == 1) {
            underwayExpeditionsLabel.setText("There is " + underwayExpeditions + " expedition underway at the moment.");
        } else {
            underwayExpeditionsLabel.setText("There are " + underwayExpeditions + " expeditions underway at the moment.");
        }
    }
}
