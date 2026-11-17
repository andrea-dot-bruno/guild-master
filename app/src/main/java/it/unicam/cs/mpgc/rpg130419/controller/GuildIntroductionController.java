package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.Predicate;

public class GuildIntroductionController {
    private Predicate<String> confirmName;

    @FXML private TextField guildNameField;
    @FXML private Button confirmButton;
    @FXML private Label saveError;

    @FXML private void initialize() {
        confirmButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> guildNameField.getText().isBlank(), guildNameField.textProperty()));
    }

    public void setConfirmName(Predicate<String> confirmName) {
        this.confirmName = confirmName;
    }

    @FXML private void handleConfirm() {
        if (confirmName.test(guildNameField.getText())) {
            confirmButton.getScene().getWindow().hide();
        } else {
            saveError.setVisible(true);
        }
    }
}
