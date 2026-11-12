package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.function.Consumer;

public class HeroesController {
    private Consumer<Hero> fireHero;

    @FXML private TableView<Hero> guildHeroesTable;
    @FXML private ImageView guildHeroIcon;
    @FXML private Label guildHeroName;
    @FXML private Label guildHeroLevel;
    @FXML private Label guildHeroClass;
    @FXML private Label guildHeroPay;
    @FXML private ProgressBar guildHeroExpBar;
    @FXML private ImageView guildHeroLeftWeaponIcon;
    @FXML private Label guildHeroLeftWeaponDamage;
    @FXML private ImageView guildHeroRightWeaponIcon;
    @FXML private Label guildHeroRightWeaponDamage;

    public void handleFireHeroButton(ActionEvent actionEvent) {
        Hero hero = guildHeroesTable.getSelectionModel().getSelectedItem();
        fireHero.accept(hero);
    }

    public void setFireHero(Consumer<Hero> fireHero) {
        this.fireHero = fireHero;
    }

    public void populateInfoFields() {
        Hero hero = guildHeroesTable.getSelectionModel().getSelectedItem();
        guildHeroIcon.setImage(new Image(hero.getIconPath()));
        guildHeroName.setText(hero.getName());
        guildHeroLevel.setText("Level: %d".formatted(hero.getLevel()));
        guildHeroClass.setText("Class: %s".formatted(hero.getClass()));
        guildHeroPay.setText("Daily Pay: %d".formatted(hero.getDailyPay()));
        guildHeroExpBar.setProgress(hero.getExpPercentage());
        guildHeroLeftWeaponIcon.setImage(new Image(hero.getLeftWeaponIconPath()));
        guildHeroLeftWeaponDamage.setText("Power: %d".formatted(hero.getLeftWeaponPower()));
        guildHeroRightWeaponIcon.setImage(new Image(hero.getRightWeaponIconPath()));
        guildHeroRightWeaponDamage.setText("Power: %d".formatted(hero.getRightWeaponPower()));
    }
}
