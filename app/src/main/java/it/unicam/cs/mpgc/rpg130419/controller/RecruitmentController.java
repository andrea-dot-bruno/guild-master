package it.unicam.cs.mpgc.rpg130419.controller;

import it.unicam.cs.mpgc.rpg130419.model.Hero;
import it.unicam.cs.mpgc.rpg130419.model.RecruitmentCandidate;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.function.Consumer;

public class RecruitmentController {
    private Consumer<RecruitmentCandidate> hireHero;

    @FXML private TableView<RecruitmentCandidate> recruitmentCandidatesTable;
    @FXML private Button hireHeroButton;
    @FXML private TableColumn<RecruitmentCandidate, String> candidateNameColumn;
    @FXML private TableColumn<RecruitmentCandidate, String> candidateClassColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidateLevelColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidatePayColumn;
    @FXML private TableColumn<RecruitmentCandidate, Number> candidateHireFeeColumn;
    @FXML private ImageView recruitHeroIcon;
    @FXML private Label recruitHeroName;
    @FXML private Label recruitHeroLevelAndClass;
    @FXML private Label recruitHeroPay;
    @FXML private Label recruitHeroHireFee;
    @FXML private ImageView recruitHeroLeftItemIcon;
    @FXML private Label recruitHeroLeftItemDamage;
    @FXML private ImageView recruitHeroRightItemIcon;
    @FXML private Label recruitHeroRightItemDamage;

    @FXML private void initialize() {
        candidateNameColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().hero().getName()));
        candidateClassColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().hero().getHeroClass()));
        candidateLevelColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hero().getLevel()));
        candidatePayColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hero().getDailyPay()));
        candidateHireFeeColumn.setCellValueFactory(cell ->
                new ReadOnlyIntegerWrapper(cell.getValue().hireFee()));

        clearInfoFields();
        hireHeroButton.disableProperty().bind(
                recruitmentCandidatesTable.getSelectionModel().selectedItemProperty().isNull());
        recruitmentCandidatesTable.getSelectionModel().selectedItemProperty()
                .addListener((property, oldCandidate, newCandidate) -> {
                    if (newCandidate == null) {
                        clearInfoFields();
                    } else {
                        populateInfoFields(newCandidate);
                    }
                });
    }

    @FXML private void handleHireHeroButton() {
        RecruitmentCandidate candidate = recruitmentCandidatesTable.getSelectionModel().getSelectedItem();
        if (candidate != null) hireHero.accept(candidate);
    }

    public void setHireHero(Consumer<RecruitmentCandidate> hireHero) {
        this.hireHero = hireHero;
    }

    public void showHireFailure(RecruitmentCandidate candidate) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(hireHeroButton.getScene().getWindow());
        alert.setTitle("Recruitment");
        alert.setHeaderText("Unable to hire %s".formatted(candidate.hero().getName()));
        alert.setContentText("Check that the hero is still available and that you have %d gold."
                .formatted(candidate.hireFee()));
        alert.show();
    }

    public void setRecruitmentCandidates(List<RecruitmentCandidate> recruitmentCandidates) {
        recruitmentCandidatesTable.getItems().setAll(recruitmentCandidates);
        if (recruitmentCandidatesTable.getSelectionModel().isEmpty()) {
            recruitmentCandidatesTable.getSelectionModel().selectFirst();
        }
    }

    private void clearInfoFields() {
        recruitHeroName.setText("Select a Hero to display its details.");
        recruitHeroIcon.setImage(null);
        recruitHeroLevelAndClass.setText("");
        recruitHeroPay.setText("");
        recruitHeroHireFee.setText("");
        recruitHeroLeftItemIcon.setImage(null);
        recruitHeroLeftItemDamage.setText("");
        recruitHeroRightItemIcon.setImage(null);
        recruitHeroRightItemDamage.setText("");
    }

    private void populateInfoFields(RecruitmentCandidate candidate) {
        Hero hero = candidate.hero();
        recruitHeroName.setText(hero.getName());
        recruitHeroIcon.setImage(new Image(hero.getIconPath()));
        recruitHeroLevelAndClass.setText("Level %d · %s".formatted(hero.getLevel(), hero.getHeroClass()));
        recruitHeroPay.setText("Daily Pay: %d G".formatted(hero.getDailyPay()));
        recruitHeroHireFee.setText("Hire Fee: %d G".formatted(candidate.hireFee()));
        recruitHeroLeftItemIcon.setImage(new Image(hero.getLeftWeaponIconPath()));
        recruitHeroLeftItemDamage.setText("Power: %d".formatted(hero.getLeftWeaponPower()));
        recruitHeroRightItemIcon.setImage(new Image(hero.getRightWeaponIconPath()));
        recruitHeroRightItemDamage.setText("Power: %d".formatted(hero.getRightWeaponPower()));
    }
}
