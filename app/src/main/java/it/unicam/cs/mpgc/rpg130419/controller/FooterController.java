package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.view.EndDayConfirmation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FooterController {
    private Runnable endDay;
    @FXML private Label underwayExpeditionsLabel;
    @FXML private Button endDayButton;

    @FXML private void handleEndDayButton() {
        if (EndDayConfirmation.show(endDayButton.getScene().getWindow())) {
            endDay.run();
        }
    }

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

    public void setEndDay(Runnable endDay) {
        this.endDay = endDay;
    }

    public void disableEndDay() {
        endDayButton.setDisable(true);
    }
}
