package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Hero;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class HeroesController {
    @FXML private TableView<Hero> guildHeroesTable;
    @FXML private TableColumn<Hero, String> heroNameColumn;
    @FXML private TableColumn<Hero, String> heroClassColumn;
    @FXML private TableColumn<Hero, Number> heroLevelColumn;
    @FXML private TableColumn<Hero, Number> heroPayColumn;
    @FXML private TableColumn<Hero, String> heroStatusColumn;
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
    private Consumer<Hero> fireHero;

    private Set<Hero> availableHeroesSet = Set.of();

    @FXML private void initialize() {
        heroNameColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getName()));
        heroClassColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getClass().getSimpleName()));
        heroLevelColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getLevel()));
        heroPayColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().getDailyPay()));
        heroStatusColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(availableHeroesSet.contains(cell.getValue())
                        ? "Available" : "On expedition"));

        clearInfoFields();
        guildHeroesTable.getSelectionModel().selectedItemProperty()
                .addListener((property, oldHero, newHero) -> {
                    if (newHero == null) {
                        clearInfoFields();
                    } else {
                        populateInfoFields(newHero);
                    }
                });

    }

    public void handleFireHeroButton(ActionEvent actionEvent) {
        Hero hero = guildHeroesTable.getSelectionModel().getSelectedItem();
        fireHero.accept(hero);
    }

    public void setFireHero(Consumer<Hero> fireHero) {
        this.fireHero = fireHero;
    }

    public void setGuildHeroes(List<Hero> heroes, List<Hero> availableHeroes) {
        availableHeroesSet = Set.copyOf(availableHeroes);
        guildHeroesTable.getItems().setAll(heroes);
    }

    public void clearInfoFields() {
        guildHeroIcon.setImage(null);
        guildHeroName.setText("Select a Hero to display its details.");
        guildHeroLevel.setText("");
        guildHeroClass.setText("");
        guildHeroPay.setText("");
        guildHeroExpBar.setProgress(0.0);
        guildHeroLeftWeaponIcon.setImage(null);
        guildHeroLeftWeaponDamage.setText("");
        guildHeroRightWeaponIcon.setImage(null);
        guildHeroRightWeaponDamage.setText("");
    }

    public void populateInfoFields(Hero hero) {
        guildHeroIcon.setImage(new Image(hero.getIconPath()));
        guildHeroName.setText(hero.getName());
        guildHeroLevel.setText("Level: %d".formatted(hero.getLevel()));
        guildHeroClass.setText("Class: %s".formatted(hero.getHeroClass()));
        guildHeroPay.setText("Daily Pay: %d".formatted(hero.getDailyPay()));
        guildHeroExpBar.setProgress(hero.getExpPercentage());
        guildHeroLeftWeaponIcon.setImage(new Image(hero.getLeftWeaponIconPath()));
        guildHeroLeftWeaponDamage.setText("Power: %d".formatted(hero.getLeftWeaponPower()));
        guildHeroRightWeaponIcon.setImage(new Image(hero.getRightWeaponIconPath()));
        guildHeroRightWeaponDamage.setText("Power: %d".formatted(hero.getRightWeaponPower()));
    }
}
