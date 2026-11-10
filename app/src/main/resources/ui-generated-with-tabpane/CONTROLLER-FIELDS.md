# Campi disponibili per i controller

Ogni ID corrisponde a un campo `@FXML` nel controller del relativo file. Tutti i campi delle sei schede appartengono al controller di `guild-main.fxml`: non ci sono `fx:include` o controller annidati. I dialoghi hanno un caricamento separato e un proprio controller.

I tipi generici di `TableView`, `TableColumn`, `ListView` e `ComboBox` vanno specializzati con i tuoi modelli o modelli di presentazione. Il file non impone tipi di riga o nomi di proprietà del dominio.

## end-day-confirmation.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `DialogPane` | `endDayDialogPane` |
| `Label` | `endDayMessageLabel` |
| `Label` | `currentDayLabel` |
| `Label` | `nextDayLabel` |
| `ListView` | `returningExpeditionsList` |
| `ButtonType` | `keepPlanningButtonType` |
| `ButtonType` | `confirmEndDayButtonType` |

## expedition-report-dialog.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `DialogPane` | `expeditionReportDialogPane` |
| `Label` | `reportDungeonNameLabel` |
| `Label` | `reportOutcomeLabel` |
| `Label` | `reportGoldRewardLabel` |
| `Label` | `reportWagesLabel` |
| `TableView` | `reportItemsTable` |
| `TableColumn` | `reportItemNameColumn` |
| `TableColumn` | `reportItemOutcomeColumn` |
| `TableView` | `reportHeroesTable` |
| `TableColumn` | `reportHeroNameColumn` |
| `TableColumn` | `reportHeroLevelColumn` |
| `TableColumn` | `reportHeroExperienceColumn` |
| `Label` | `reportSummaryLabel` |
| `ButtonType` | `closeReportButtonType` |

## game-menu-dialog.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `DialogPane` | `gameMenuDialogPane` |
| `Button` | `saveGameButton` |
| `Button` | `loadGameButton` |
| `Button` | `returnToMainMenuButton` |
| `Label` | `lastSaveSummaryLabel` |
| `Label` | `saveFeedbackLabel` |
| `ButtonType` | `resumeButtonType` |

## guild-main.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `BorderPane` | `guildRoot` |
| `ToggleGroup` | `shopModeToggleGroup` |
| `Label` | `guildNameLabel` |
| `Label` | `dayLabel` |
| `Label` | `goldLabel` |
| `Button` | `openMenuButton` |
| `TabPane` | `mainTabPane` |
| `Tab` | `overviewTab` |
| `Button` | `planExpeditionButton` |
| `TableView` | `overviewExpeditionsTable` |
| `TableColumn` | `overviewExpeditionNameColumn` |
| `TableColumn` | `overviewPartyColumn` |
| `TableColumn` | `overviewRemainingColumn` |
| `TableColumn` | `overviewProgressColumn` |
| `ListView` | `availableHeroesList` |
| `Button` | `viewHeroesButton` |
| `Label` | `overviewSummaryLabel` |
| `Tab` | `expeditionsTab` |
| `TabPane` | `expeditionsTabPane` |
| `Tab` | `availableExpeditionsTab` |
| `TableView` | `contractsTable` |
| `TableColumn` | `contractNameColumn` |
| `TableColumn` | `contractDurationColumn` |
| `TableColumn` | `contractRewardColumn` |
| `ScrollPane` | `contractDetailScroll` |
| `Label` | `dungeonNameLabel` |
| `ImageView` | `dungeonImageView` |
| `Label` | `dungeonDescriptionLabel` |
| `Label` | `dungeonDifficultyLabel` |
| `Label` | `dungeonDurationLabel` |
| `Label` | `dungeonRewardLabel` |
| `Label` | `dungeonRequirementsLabel` |
| `Label` | `dungeonPossibleLootLabel` |
| `Button` | `preparePartyButton` |
| `Tab` | `underwayExpeditionsTab` |
| `TableView` | `underwayExpeditionsTable` |
| `TableColumn` | `underwayNameColumn` |
| `TableColumn` | `underwayPartyColumn` |
| `TableColumn` | `underwayDaysColumn` |
| `TableColumn` | `underwayProgressColumn` |
| `TableColumn` | `underwayRewardColumn` |
| `Tab` | `preparePartyTab` |
| `TableView` | `partyHeroesTable` |
| `TableColumn` | `partyHeroNameColumn` |
| `TableColumn` | `partyHeroClassColumn` |
| `TableColumn` | `partyHeroWageColumn` |
| `ListView` | `selectedPartyList` |
| `TableView` | `partySuppliesTable` |
| `TableColumn` | `partySupplyNameColumn` |
| `TableColumn` | `partySupplyStockColumn` |
| `TableColumn` | `partySupplyQuantityColumn` |
| `Spinner` | `partySupplyQuantitySpinner` |
| `ScrollPane` | `partySummaryScroll` |
| `Label` | `preparedDungeonNameLabel` |
| `Label` | `partySuccessChanceLabel` |
| `ProgressBar` | `partySuccessChanceProgressBar` |
| `Label` | `partySizeLabel` |
| `Label` | `partyWagesLabel` |
| `Label` | `partyGoldAfterDepartureLabel` |
| `Label` | `partyDurationLabel` |
| `Label` | `partyValidationLabel` |
| `Button` | `cancelPreparationButton` |
| `Button` | `launchExpeditionButton` |
| `Tab` | `heroesTab` |
| `ComboBox` | `heroFilterComboBox` |
| `TableView` | `heroesTable` |
| `TableColumn` | `heroNameColumn` |
| `TableColumn` | `heroClassColumn` |
| `TableColumn` | `heroLevelColumn` |
| `TableColumn` | `heroStatusColumn` |
| `ScrollPane` | `heroDetailScroll` |
| `ImageView` | `heroPortraitImageView` |
| `Label` | `selectedHeroNameLabel` |
| `Label` | `selectedHeroClassLabel` |
| `Label` | `selectedHeroStatusLabel` |
| `Label` | `selectedHeroLevelLabel` |
| `Label` | `selectedHeroWageLabel` |
| `ProgressBar` | `heroExperienceProgressBar` |
| `Label` | `heroExperienceLabel` |
| `ImageView` | `offenseWeaponImageView` |
| `Label` | `offenseWeaponNameLabel` |
| `Label` | `offenseWeaponPowerLabel` |
| `Button` | `offenseChangeButton` |
| `Button` | `offenseUnequipButton` |
| `ImageView` | `defenseWeaponImageView` |
| `Label` | `defenseWeaponNameLabel` |
| `Label` | `defenseWeaponPowerLabel` |
| `Button` | `defenseChangeButton` |
| `Button` | `defenseUnequipButton` |
| `Label` | `heroFeedbackLabel` |
| `Tab` | `recruitmentTab` |
| `TableView` | `recruitmentTable` |
| `TableColumn` | `candidateNameColumn` |
| `TableColumn` | `candidateClassColumn` |
| `TableColumn` | `candidateLevelColumn` |
| `TableColumn` | `candidateFeeColumn` |
| `ScrollPane` | `recruitmentDetailScroll` |
| `ImageView` | `candidatePortraitImageView` |
| `Label` | `selectedCandidateNameLabel` |
| `Label` | `selectedCandidateClassLabel` |
| `Label` | `candidateLevelLabel` |
| `Label` | `candidateExperienceLabel` |
| `Label` | `candidateWageLabel` |
| `Label` | `candidateOffenseWeaponLabel` |
| `Label` | `candidateDefenseWeaponLabel` |
| `Label` | `candidateHiringFeeLabel` |
| `Label` | `candidateGoldAfterHiringLabel` |
| `Label` | `recruitmentFeedbackLabel` |
| `Button` | `recruitButton` |
| `Tab` | `inventoryTab` |
| `ComboBox` | `inventoryCategoryComboBox` |
| `TableView` | `inventoryTable` |
| `TableColumn` | `inventoryItemColumn` |
| `TableColumn` | `inventoryQuantityColumn` |
| `TableColumn` | `inventoryStatusColumn` |
| `ScrollPane` | `inventoryDetailScroll` |
| `ImageView` | `inventoryItemImageView` |
| `Label` | `inventoryItemNameLabel` |
| `Label` | `inventoryItemTypeLabel` |
| `Label` | `inventoryItemPowerLabel` |
| `Label` | `inventoryOwnedLabel` |
| `Label` | `inventoryUnassignedLabel` |
| `Label` | `inventoryItemDescriptionLabel` |
| `Button` | `manageEquipmentButton` |
| `Tab` | `shopTab` |
| `ToggleButton` | `shopBuyToggle` |
| `ToggleButton` | `shopSellToggle` |
| `ComboBox` | `shopCategoryComboBox` |
| `TableView` | `shopItemsTable` |
| `TableColumn` | `shopItemColumn` |
| `TableColumn` | `shopQuantityColumn` |
| `TableColumn` | `shopPriceColumn` |
| `ScrollPane` | `shopDetailScroll` |
| `ImageView` | `shopItemImageView` |
| `Label` | `shopItemNameLabel` |
| `Label` | `shopItemTypeLabel` |
| `Label` | `shopItemPowerLabel` |
| `Label` | `shopUnitPriceLabel` |
| `Spinner` | `shopQuantitySpinner` |
| `Label` | `shopTotalLabel` |
| `Label` | `shopGoldAfterTransactionLabel` |
| `Label` | `shopFeedbackLabel` |
| `Button` | `shopTransactionButton` |
| `Label` | `statusLabel` |
| `Button` | `endDayButton` |

## load-confirmation.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `DialogPane` | `loadConfirmationDialogPane` |
| `Label` | `savedGuildNameLabel` |
| `Label` | `savedDayLabel` |
| `Label` | `loadFeedbackLabel` |
| `ButtonType` | `cancelLoadButtonType` |
| `ButtonType` | `confirmLoadButtonType` |

## weapon-selection-dialog.fxml

| Tipo JavaFX | fx:id |
| --- | --- |
| `DialogPane` | `weaponSelectionDialogPane` |
| `Label` | `weaponTargetHeroLabel` |
| `ListView` | `compatibleWeaponsList` |
| `ImageView` | `weaponPreviewImageView` |
| `Label` | `weaponNameLabel` |
| `Label` | `weaponTypeLabel` |
| `Label` | `currentWeaponPowerLabel` |
| `Label` | `newWeaponPowerLabel` |
| `Label` | `weaponPowerDifferenceLabel` |
| `Label` | `equipmentFeedbackLabel` |
| `ButtonType` | `cancelEquipButtonType` |
| `ButtonType` | `confirmEquipButtonType` |

