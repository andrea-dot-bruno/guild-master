package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class RecruitmentController {

    @FXML private TableView<Hero> recruitHeroTable;
    @FXML private ImageView recruitHeroIcon;
    @FXML private Label recruitHeroName;
    @FXML private Label recruitHeroLevel;
    @FXML private Label recruitHeroClass;
    @FXML private Label recruitHeroPay;
    @FXML private Label recruitHeroHireFee;
    @FXML private ImageView recruitHeroLeftItemIcon;
    @FXML private Label recruitHeroLeftItemDamage;
    @FXML private ImageView recruitHeroRightItemIcon;
    @FXML private Label recruitHeroRightItemDamage;

    @FXML private void handleHireHeroButton(ActionEvent actionEvent) {

    }
}
