package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ExpeditionsController {

    @FXML private TableView expeditionBoardTable;
    @FXML private Label boardDungeonName;
    @FXML private ImageView boardDungeonIcon;
    @FXML private Label boardDungeonDifficulty;
    @FXML private Label boardDungeonDays;
    @FXML private Label boardDungeonReward;

    @FXML private void handlePreparePartyButton(ActionEvent actionEvent) {} //TODO: implement
}
