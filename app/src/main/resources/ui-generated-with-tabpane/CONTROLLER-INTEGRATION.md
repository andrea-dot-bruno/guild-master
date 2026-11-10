# Collegamento ai controller

## 1. Caricamento della finestra principale

Esempio da adattare al nome e al costruttore del tuo controller:

```java
FXMLLoader loader = new FXMLLoader(
    getClass().getResource("/ui-generated-with-tabpane/guild-main.fxml")
);
loader.setController(controller); // la tua istanza, creata dall'applicazione
Parent root = loader.load();
stage.setScene(new Scene(root, 1120, 700));
stage.show();
```

Il controller assegnato riceve i campi di **tutte e sei le schede**. Nessuna scheda utilizza `fx:include`, quindi non occorre cercare campi in controller figli. Il modello può essere passato al costruttore del controller prima di `load()`; JavaFX inietta poi i campi e chiama `initialize()`.

Il modello attuale offre, per esempio, `Hero.getName()`, `Hero.getLevel()` e `Weapon.getPower()`. Nome della gilda, denaro, paghe, giorni, inventario e calendario delle spedizioni non hanno ancora tutte le API necessarie. Per questo gli FXML non impostano collegamenti presunti a queste informazioni. Quando il modello sarà completo potrai usare aggiornamenti espliciti oppure proprietà di un modello di presentazione osservabile.

## 2. Liste e dettagli

Esempio minimo di campi e colonne, usando i getter che esistono già:

```java
@FXML private TableView<Hero> heroesTable;
@FXML private TableColumn<Hero, String> heroNameColumn;
@FXML private TableColumn<Hero, Number> heroLevelColumn;
@FXML private Label selectedHeroNameLabel;

@FXML
private void initialize() {
    heroNameColumn.setCellValueFactory(cell ->
        new ReadOnlyStringWrapper(cell.getValue().getName()));
    heroLevelColumn.setCellValueFactory(cell ->
        new ReadOnlyIntegerWrapper(cell.getValue().getLevel()));

    heroesTable.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldHero, hero) ->
            selectedHeroNameLabel.setText(hero == null ? "—" : hero.getName()));
}
```

Import: `javafx.fxml.FXML`, `javafx.scene.control.*`, `javafx.beans.property.ReadOnlyStringWrapper`, `javafx.beans.property.ReadOnlyIntegerWrapper` e la classe `Hero` del progetto. Completa l'inizializzazione con `heroesTable.setItems(...)` e con le altre colonne e informazioni del profilo.

Le normali liste Java del modello non aggiornano automaticamente le celle quando cambia un campo. Usa proprietà osservabili nel modello di presentazione oppure aggiorna le righe e richiama `refresh()` dopo una modifica non osservabile. Tutte le modifiche alla vista vanno eseguite sul thread JavaFX.

Per le immagini dinamiche usa `ImageView.setImage(...)`; per ritratti e miniature nelle liste imposta una `cellFactory`. Gli `ImageView` di dettaglio iniziano senza un'immagine di eroe o oggetto fittizio. Le illustrazioni in `assets/` sono disponibili come risorse locali.

Le colonne Progress di Overview e Underway possono ricevere valori tra 0 e 1 e una `ProgressBarTableCell`. La logica che calcola avanzamento e probabilità rimane nel modello o nei servizi del gioco.

Quando una selezione viene cancellata, azzera **tutti** i relativi dettagli e disabilita le azioni dipendenti. Prima di riutilizzare un dialogo aggiorna tutte le sue informazioni.

## 3. Navigazione e preparazione

```java
planExpeditionButton.setOnAction(event -> {
    mainTabPane.getSelectionModel().select(expeditionsTab);
    expeditionsTabPane.getSelectionModel().select(availableExpeditionsTab);
});
viewHeroesButton.setOnAction(event ->
    mainTabPane.getSelectionModel().select(heroesTab));
```

Gestisci questi passaggi nel controller:

1. Una selezione valida in `contractsTable` aggiorna i campi `dungeon...` e abilita `preparePartyButton`.
2. Prepare party imposta il contratto, popola gli eroi disponibili e i consumabili, abilita `preparePartyTab` e la seleziona.
3. Imposta `partyHeroesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE)`. Sincronizza le righe selezionate con `selectedPartyList`, paghe e probabilità; escludi gli eroi già impegnati.
4. Quando cambia la riga selezionata in `partySuppliesTable`, configura `partySupplyQuantitySpinner` con una `IntegerSpinnerValueFactory(0, disponibilita, assegnati)`. Salva la quantità della singola riga prima di cambiarne selezione. Non assegnare i consumabili al singolo eroe.
5. Abilita Launch expedition solo dopo la validazione del gruppo e delle disponibilità. Gli importi e la probabilità sono forniti dal gioco. Le paghe possono portare l'oro sotto zero secondo le regole concordate; non applicare qui il vincolo degli acquisti.
6. Cancel annulla la preparazione e torna ad Available; Launch esegue l'operazione nel modello e passa a Underway. In entrambi i casi ripulisci lo stato e disabilita nuovamente `preparePartyTab`.

Collega il pulsante End day allo stato del controller: durante una preparazione aperta puoi disabilitarlo finché il gruppo non viene annullato o avviato, evitando di invalidare silenziosamente la selezione. Il pulsante globale è un solo nodo, condiviso da tutte le schede.

Le azioni sugli eroi devono considerare anche disponibilità e compatibilità, oltre all'esistenza di una selezione. Gli eroi impegnati in una spedizione sono consultabili ma non modificabili.

## 4. Shop, filtri e quantità

`shopBuyToggle` e `shopSellToggle` condividono già `shopModeToggleGroup`. Ascolta `selectedToggleProperty()` per aggiornare gli articoli, i titoli delle colonne, la quantità massima e il testo di `shopTransactionButton`. Un `ToggleButton` può essere deselezionato cliccandolo di nuovo: se la nuova selezione è `null`, ripristina il toggle precedente o Buy.

Buy mostra lo stock del negozio; Sell mostra solo oggetti vendibili e consumabili disponibili. La stessa `TableView` e gli stessi campi servono entrambe le modalità. Popola `heroFilterComboBox`, `inventoryCategoryComboBox` e `shopCategoryComboBox` con i tipi che preferisci e applica i filtri alle rispettive liste.

Inizializza `shopQuantitySpinner` quando esiste una selezione valida, per esempio:

```java
shopQuantitySpinner.setValueFactory(
    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maximumQuantity, 1)
);
```

Usa questa configurazione solo se `maximumQuantity >= 1`; altrimenti lascia il controllo e la transazione disabilitati. Aggiorna totale e saldo previsto quando cambia la quantità. Per un acquisto senza fondi disabilita il pulsante e mostra il motivo in `shopFeedbackLabel`; un saldo negativo non deve disabilitare la vendita né la normale prosecuzione del gioco.

Prima di confermare una transazione ricontrolla saldo, stock e disponibilità nel modello. Non affidarti soltanto allo stato di abilitazione del pulsante. Non calcolare prezzi o paghe all'interno dell'FXML.

I messaggi sono inizialmente vuoti, invisibili e non occupano spazio. Un collegamento utile, per ciascun messaggio, è:

```java
shopFeedbackLabel.visibleProperty().bind(
    shopFeedbackLabel.textProperty().isNotEmpty());
shopFeedbackLabel.managedProperty().bind(shopFeedbackLabel.visibleProperty());
```

Puoi usare lo stesso schema con `partyValidationLabel`, `heroFeedbackLabel`, `recruitmentFeedbackLabel` e i messaggi dei dialoghi.

## 5. Dialoghi e Alert nativi

Esempio di caricamento di una conferma con un controller dedicato:

```java
FXMLLoader loader = new FXMLLoader(getClass().getResource(
    "/ui-generated-with-tabpane/end-day-confirmation.fxml"));
loader.setController(confirmationController);
DialogPane pane = loader.load();

Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setDialogPane(pane);
alert.initOwner(stage);
alert.initModality(Modality.WINDOW_MODAL);
alert.setTitle("End day");
alert.setResizable(true);

ButtonType confirm = (ButtonType) loader.getNamespace()
    .get("confirmEndDayButtonType");
if (alert.showAndWait().filter(confirm::equals).isPresent()) {
    // Avanza il giorno nel modello, poi aggiorna le schede e mostra i resoconti.
}
```

Import aggiuntivi: `javafx.stage.Modality` e `javafx.stage.Stage`. `stage` è la finestra principale; `confirmationController` è l'istanza che prepara i dati della conferma prima di mostrarla. Ogni FXML ha pulsanti nativi con `ButtonData.CANCEL_CLOSE`, così ESC e la chiusura della finestra hanno un risultato di annullamento.

Per menu, cambio arma e resoconto puoi usare invece `Dialog<ButtonType>` e assegnare allo stesso modo il `DialogPane`. I dialoghi utilizzano il proprio controller, separato da quello principale: passa il contesto necessario nel costruttore o attraverso metodi chiamati dopo `load()` e prima di `showAndWait()`.

Il controller del menu collega i pulsanti Save, Load e Main menu nel contenuto. Save non chiude automaticamente il dialogo: aggiorna `lastSaveSummaryLabel` e mostra `saveFeedbackLabel` solo dopo un salvataggio riuscito. Prima di Load o Main menu puoi riutilizzare lo schema di conferma, adattandone testo e azione.

La conferma di cambio arma deve restare disabilitata quando manca una selezione valida. I pulsanti generati dai `ButtonType` non esistono come nodi nel testo FXML; dopo `load()` puoi recuperarli così:

```java
ButtonType equipType = (ButtonType) loader.getNamespace()
    .get("confirmEquipButtonType");
Button equipButton = (Button) pane.lookupButton(equipType);
equipButton.disableProperty().bind(
    compatibleWeaponsList.getSelectionModel().selectedItemProperty().isNull());
```

In questo esempio `loader` e `pane` sono quelli di `weapon-selection-dialog.fxml`, e `compatibleWeaponsList` appartiene al suo controller. Popola l'elenco con sole armi compatibili e ricontrolla disponibilità e stato dell'eroe al momento dell'operazione. Puoi aggiungere un filtro su `ActionEvent.ACTION` e consumare l'evento se una validazione finale fallisce, mantenendo aperto il dialogo.

`expedition-report-dialog.fxml` serve per entrambi gli esiti: aggiorna titolo del dungeon, esito, ricompense, consumabili ed eroi restituiti. I premi sono già applicati nel modello quando presenti il resoconto; il pulsante Back to guild chiude soltanto il dialogo. Non accreditare nuovamente i premi alla chiusura e non mostrare XP guadagnati in caso di fallimento.

## 6. Stati e file

| Stato | Aggiornamento della vista |
| --- | --- |
| Nessuna spedizione | `overviewExpeditionsTable` vuota: placeholder nella stessa scheda |
| Eroe selezionato | Campi `selectedHero...`, immagini, XP e slot Offense/Defense |
| Nessun eroe selezionato | Dettagli a `—`, immagini rimosse, azioni disabilitate |
| Compra / vendi | Cambio del toggle e dei dati della medesima vista Shop |
| Oro insufficiente | `shopFeedbackLabel` visibile e `shopTransactionButton` disabilitato |
| Partita salvata | Riepilogo e messaggio nel dialogo menu |
| Spedizione conclusa | Dati aggiornati nel solo resoconto, con esito diverso |

Il progetto non viene avviato o modificato automaticamente da questi file. `MainApplication.java`, modello e controller dell'app restano da collegare durante l'implementazione del gioco.
