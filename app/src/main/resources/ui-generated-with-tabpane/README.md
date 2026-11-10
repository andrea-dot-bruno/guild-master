# Guild Master — versione con TabPane

Apri **`guild-main.fxml`** in Scene Builder. Contiene tutta la finestra di gioco, in un unico albero FXML e in un solo ambito di controller:

- **Overview**: spedizioni attive, eroi disponibili e riepilogo.
- **Expeditions**: sottoschede Available, Underway e Prepare party. La preparazione si abilita dopo la scelta di un contratto.
- **Heroes**: elenco, scheda eroe ed equipaggiamento.
- **Recruitment**: candidati, dettagli e assunzione.
- **Inventory**: filtro, elenco e dettagli dell'oggetto.
- **Shop**: una sola tabella e un solo riepilogo, condivisi dalle modalità Buy e Sell.

I titoli e l'accesso al gioco sono esclusi. Il pulsante Main menu nel dialogo è soltanto il punto di aggancio per il futuro ritorno al menu.

## Struttura dei file

| File | Uso |
| --- | --- |
| `guild-main.fxml` | Finestra principale con le sei schede |
| `game-menu-dialog.fxml` | Menu di gioco: salvataggio, caricamento e uscita al menu |
| `end-day-confirmation.fxml` | Conferma avanzamento giornata |
| `load-confirmation.fxml` | Conferma caricamento partita |
| `weapon-selection-dialog.fxml` | Selezione arma compatibile e confronto potenza |
| `expedition-report-dialog.fxml` | Un unico resoconto per successo o fallimento |
| `guild-theme.css` | Tema pergamena, noce e ocra per controlli JavaFX standard |
| `assets/` | Illustrazioni locali, disponibili anche per le celle create dal controller |
| `CONTROLLER-FIELDS.md` | Tutti gli `fx:id`, raggruppati per file, con il tipo JavaFX |
| `controller-fields.json` | Lo stesso elenco in formato leggibile da strumenti |
| `CONTROLLER-INTEGRATION.md` | Caricamento, collegamento dati, navigazione e dialoghi |

Mantieni i file e `assets/` nella stessa cartella: i riferimenti a CSS e immagini sono relativi. La cartella è autonoma e non dipende da `ui-generated`.

## Preparazione per il controller

Le informazioni di gioco sono inizialmente **vuote o indicate con `—`**. Non sono precompilati nomi, eroi, prezzi, quantità, probabilità o partite salvate. Le `TableView` e `ListView` sono vere liste JavaFX, senza righe dimostrative. I loro placeholder gestiscono naturalmente gli elenchi vuoti nella stessa vista.

Ogni informazione dinamica ha un `fx:id`. Le colonne hanno identificatori propri per impostare le `cellValueFactory` dal controller. Non sono configurate `PropertyValueFactory` con nomi di proprietà presunti, né chiamate a metodi di dominio mancanti. I filtri e gli `Spinner` ricevono opzioni e limiti dal controller.

Non sono impostati `fx:controller` o `onAction`: puoi passare i tuoi controller a `FXMLLoader.setController(...)` prima di caricare ciascun file. In alternativa, aggiungi il tuo `fx:controller` al nodo radice; scegli una sola delle due modalità.

La navigazione tra le sei schede funziona già tramite il `TabPane`. Gli acquisti, la selezione degli oggetti, la preparazione, i filtri e le altre operazioni richiedono i collegamenti al controller descritti nella guida. Le azioni che dipendono da una selezione sono inizialmente disabilitate.

## Finestre minori

I cinque FXML secondari hanno **`DialogPane` come radice**. Caricali e assegnali a `Dialog.setDialogPane(...)` o `Alert.setDialogPane(...)`. Non contengono copie della schermata principale né finte coperture: modalità, focus, finestra proprietaria, ESC e pulsanti di conferma sono gestiti dal dialogo JavaFX.

I pulsanti nativi di chiusura/conferma sono rappresentati da `ButtonType` con `fx:id`. Li recuperi nel controller del dialogo oppure dal namespace del suo loader, e ottieni il pulsante effettivo con `pane.lookupButton(buttonType)`.

Successo e fallimento aggiornano lo stesso resoconto; oro insufficiente aggiorna il messaggio e l'abilitazione dell'unica vista Shop. Non servono FXML duplicati per questi stati.

## Verifica

Dimensione di riferimento: **1120 × 700**. Namespace JavaFX 25, controlli standard compatibili con Scene Builder 26. I contenuti di dettaglio usano contenitori adattabili e, dove necessario, scorrimento.

Sono stati verificati il caricamento FXML, l'iniezione dei campi in un controller di prova, le sei schede principali, le tre sottoschede di Expeditions, il gruppo Buy/Sell e l'assegnazione dei cinque `DialogPane` a dialoghi JavaFX con pulsanti nativi. Le anteprime sono state controllate a 1120 × 700; questa verifica riguarda le viste, non l'integrazione della logica di gioco.
