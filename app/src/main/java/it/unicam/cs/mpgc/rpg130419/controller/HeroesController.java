package it.unicam.cs.mpgc.rpg130419.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class HeroesController {

    @FXML private TableView guildHeroesTable;
    @FXML private ImageView guildHeroIcon;
    @FXML private Label guildHeroName;
    @FXML private Label guildHeroLevel;
    @FXML private Label guildHeroClass;
    @FXML private Label guildHeroPay;
    @FXML private ProgressBar guildHeroExpBar;
    @FXML private ImageView guildHeroMainItemIcon;
    @FXML private Label guildHeroMainItemDamage;
    @FXML private ImageView guildHeroSecondItemIcon;
    @FXML private Label guildHeroSecondItemDamage;

    @FXML private void handleFireHeroButton(ActionEvent actionEvent) {} //TODO: implement
}
