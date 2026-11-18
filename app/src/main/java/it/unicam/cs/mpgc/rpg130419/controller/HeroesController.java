package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130419.view.GuildDialogTheme;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Consumer<Hero> fireHero;
    private Set<Hero> availableHeroesSet = Set.of();
    @FXML private TableView<Hero> guildHeroesTable;
    @FXML private Label totalDailyWagesLabel;
    @FXML private Button fireHeroButton;
    @FXML private TableColumn<Hero, String> heroNameColumn;
    @FXML private TableColumn<Hero, String> heroClassColumn;
    @FXML private TableColumn<Hero, Number> heroLevelColumn;
    @FXML private TableColumn<Hero, Number> heroPayColumn;
    @FXML private TableColumn<Hero, String> heroStatusColumn;
    @FXML private ImageView guildHeroIcon;
    @FXML private Label guildHeroName;
    @FXML private Label guildHeroLevelAndClass;
    @FXML private Label guildHeroPay;
    @FXML private ProgressBar guildHeroExpBar;
    @FXML private ImageView guildHeroLeftWeaponIcon;
    @FXML private Label guildHeroLeftWeaponDamage;
    @FXML private ImageView guildHeroRightWeaponIcon;
    @FXML private Label guildHeroRightWeaponDamage;

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
        fireHeroButton.disableProperty().bind(
                guildHeroesTable.getSelectionModel().selectedItemProperty().isNull());
        guildHeroesTable.getSelectionModel().selectedItemProperty()
                .addListener((property, oldHero, newHero) -> {
                    if (newHero == null) {
                        clearInfoFields();
                    } else {
                        populateInfoFields(newHero);
                    }
                });

    }

    @FXML private void handleFireHeroButton() {
        Hero hero = guildHeroesTable.getSelectionModel().getSelectedItem();
        if (hero != null) fireHero.accept(hero);
    }

    public void setFireHero(Consumer<Hero> fireHero) {
        this.fireHero = fireHero;
    }

    public void showFireFailure(Hero hero) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "You can't remove %s while on an expedition."
                        .formatted(hero.getName()),
                ButtonType.OK);
        alert.initOwner(fireHeroButton.getScene().getWindow());
        alert.setTitle("Fire Hero");
        alert.setHeaderText("Hero unavailable");
        alert.setGraphic(null);

        GuildDialogTheme.applyTo(alert.getDialogPane());
        alert.getDialogPane().lookupButton(ButtonType.OK).getStyleClass().add("button-main");
        alert.show();
    }

    public void setGuildHeroes(List<Hero> heroes, List<Hero> availableHeroes, int totalDailyWages) {
        availableHeroesSet = Set.copyOf(availableHeroes);
        guildHeroesTable.getItems().setAll(heroes);
        totalDailyWagesLabel.setText("Total Daily Wages: %d G".formatted(totalDailyWages));
        if (guildHeroesTable.getSelectionModel().isEmpty()) {
            guildHeroesTable.getSelectionModel().selectFirst();
        }
    }

    public void clearInfoFields() {
        guildHeroIcon.setImage(null);
        guildHeroName.setText("Select a Hero to display its details.");
        guildHeroLevelAndClass.setText("");
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
        guildHeroLevelAndClass.setText("Level %d · %s".formatted(hero.getLevel(), hero.getHeroClass()));
        guildHeroPay.setText("Daily Pay: %d G".formatted(hero.getDailyPay()));
        guildHeroExpBar.setProgress(hero.getExpPercentage());
        guildHeroLeftWeaponIcon.setImage(new Image(hero.getLeftWeaponIconPath()));
        guildHeroLeftWeaponDamage.setText("Power: %d".formatted(hero.getLeftWeaponPower()));
        guildHeroRightWeaponIcon.setImage(new Image(hero.getRightWeaponIconPath()));
        guildHeroRightWeaponDamage.setText("Power: %d".formatted(hero.getRightWeaponPower()));
    }
}
