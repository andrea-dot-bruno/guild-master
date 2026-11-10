package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.scene.control.Label;

public class FooterController {
    private Label underwayExpeditionsLabel;

    public FooterController(Label underwayExpeditionsLabel) {
        this.underwayExpeditionsLabel = underwayExpeditionsLabel;
    }

    public void handlerEndDayButton() {}

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
