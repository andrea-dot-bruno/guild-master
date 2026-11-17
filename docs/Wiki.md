# Guild Master - Wiki del progetto

---

# Scopo del gioco e overview generale
Guild Master è un gioco di ruolo interattivo in cui il giocatore interpreta il capo di una gilda. Il suo compito è quello di amministrare le risorse a disposizione in modo strategico ed effettuare spedizioni nei dungeon, al fine di accrescere l'importanza e la forza della gilda.<br>
Il giocatore ha la possibilità di assumere nuovi eroi, licenziare quelli già presenti nella gilda (a patto che non siano impegnati in una spedizione) e lanciare nuove spedizioni.<br>
Il gioco non ha una condizione di vittoria, ma può concludersi con un Game Over se, al termine della giornata, il giocatore non ha abbastanza oro per poter pagare tutti gli eroi assunti nella gilda.

## Lancio di una spedizione
Per preparare una nuova spedizione, selezionare quella desiderata dalla pagina *Expeditions*, poi cliccare il pulsante *Prepare Expedition*. Verrà visualizzata una finestra di pop-up che permette di scegliere gli eroi da inviare nella spedizione. La quantità e la potenza di questi ultimi determina la probabilità di successo della spedizione, visibile sempre nell'interfaccia.
> [!NOTE]
> Una spedizione può richiedere più di un giorno per essere completata, e durante tutta la sua durata gli eroi richiederanno comunque che la loro paga venga saldata, quindi attenzione a calcolare bene le proprie risorse!

## Miglioramento degli eroi
Gli eroi appartenenti alla gilda possono salire di livello facendo esperienza durante le spedizioni. Le spedizioni di successo, infatti, comportano un guadagno di exp da parte degli eroi che ne hanno preso parte.

## Fine della giornata
Quando il giocatore ritiene di aver terminato tutte le azioni per la giornata, può cliccare il pulsante "End Day", che farà terminare la giornata in corso. Le spedizioni in corso avanzeranno di un giorno, e quelle terminate verranno "calcolate", assegnando, in caso di vittoria, oro al giocatore ed esperienza agli eroi ingaggiati. Infine, vengono sottratte al totale dell'oro della gilda le paghe giornaliere degli eroi. Prima dell'inizio della giornata successiva vengono anche generati casualmente nuovi dungeon ed eroi da assumere, nel caso in cui il giorno precedente erano stati consumati, fino ad un massimo di tre dungeon e quattro eroi per una singola giornata.

---

# Struttura del progetto
## Pattern Model-View-Controller
## it.unicam.cs.mpgc.rpg130419.app
## it.unicam.cs.mpgc.rpg130419.controller
## it.unicam.cs.mpgc.rpg130419.model
### it.unicam.cs.mpgc.rpg130419.model.weapons
## it.unicam.cs.mpgc.rpg130419.persistence
## it.unicam.cs.mpgc.rpg130419.view


---

# Formule utilizzate 
## Level-up degli eroi
## Determinare la vittoria di una spedizione
### Calcolo della "potenza" del party

---


# Persistenza dei dati
La persistenza dei dati è stata realizzata tramite file JSON. Serializzazione e deserializzazione sono svolte dalla libreria Gson. Il file di salvataggio `save.json` è contenuto nella cartella `/saves`.
L'implementazione futura di metodi differenti di persistenza è resa possibile grazie all'interfaccia `it.unicam.cs.mpgc.rpg130419.persistence.GameSaveRepository`. Ogni nuova classe che utilizza un nuovo metodo di persistenza deve semplicemente implementare questa interfaccia, estendere i suoi metodi e modificare solo la creazione dell'oggetto nel metodo `start` di `it.unicam.cs.mpgc.rpg130419.app.MainApplication`. 
> Per esempio:
> ```java
> it.unicam.cs.mpgc.rpg130419.app.MainApplication
> 
> public void start(Stage stage) throws IOException {
>     ...
>     GameSaveRepository saves = new XMLGameSaveRepository(saveFile);
>     ...
> }
> ```

## Avvio della partita
All'avvio dell'applicazione, al giocatore vengono presentate due alternative per iniziare la partita: *New Game* e *Load Game*.

### New Game
Permette di creare un nuovo salvataggio, sovrascrivendo quello precedente (se presente).
Il click del pulsante lancia il metodo `startNewGame` di `MainApplication`, che a sua volta chiama il metodo `newSession()`. Quest'ultimo restituisce una istanza di `GameSession` (`it.unicam.cs.mpgc.rpg130419.model.GameSession`) con il setup iniziale del gioco.
Viene poi caricata la vista principale, collegato il controller, e caricata la finestra di introduzione per il giocatore. In quest'ultima, quando il giocatore inserisce e infine invia il nome della nuova Gilda, il salvataggio viene sovrascritto. In questo modo, nel caso in cui il giocatore avviasse un nuovo salvataggio, ma uscisse dal gioco prima di inviare il nome, il salvataggio precedente (se presente) non verrebbe toccato.
### Load Game
Permette di caricare l'ultimo salvataggio effettuato.
Nel caso in cui il file di salvataggio non sia presente o sia vuoto, il metodo `load` di `GameSaveRepository` restituisce un oggetto `Optional` vuoto, e quindi il salvataggio viene creato da zero, riutilizzando la funzione `startNewGame`.
Nel caso in cui il salvataggio precedente sia terminato in un Game Over, verrà avviata una nuova partita, sempre grazie al riutilizzo del metodo `startNewGame`.
La deserializzazione avviene ricreando la classe `GameSave`(`it.unicam.cs.mpgc.rpg130419.persistence.GameSave`) tramite il file di salvataggio.

## Salvataggio della partita
Il salvataggio avviene automaticamente alla fine di ogni giornata, a seguito del click del pulsante *End Day*. Può però essere effettuato anche manualmente tramite il pulsante *Save Game* situato all'interno del *Menu*, accessibile dal pulsante nell'header.
### Salvataggio a fine giornata
Dopo l'aggiornamento delle statistiche della partita scaturito dal click del pulsante *End Day*, viene lanciato il metodo `save` dell'interfaccia `GameSaveRepository`. Questo, tramite il metodo `toJson` della liberia Gson, converte l'oggetto `session`, passato come parametro della funzione, in una stringa con il contenuto dell'oggetto in sintassi Json.
Crea poi un file temporaneo con prefisso `guild-master-`, al cui interno salva la stringa Json precedentemente creata. Poi procede a tentare la copia del file temporaneo sul file di salvataggio in una azione atomica, se supportata. In questo modo non si rischiano interruzioni durante la sovrascrittura del file di salvataggio, evitando di perdere la precedente sessione di gioco.
Infine, il file temporaneo viene rimosso.
### Salvataggio manuale
Il salvataggio sfrutta lo stesso metodo `save` utilizzato dal salvataggio automatico.

## Game Over
Al termine della giornata il metodo `payDailyWages` di `Guild`(`it.unicam.cs.mpgc.rpg130419.model.Guild`) calcola l'ammontare giornaliero dei pagamenti per gli eroi. Se la Gilda non può pagare tutti gli eroi, il gioco termina con un Game Over.

---

# Estensioni future

---

# Utilizzo dell'AI
* Utilizzato ChatGPT per:
    * brainstorming sulle feature del gioco;
    * comprendere concetti teorici, come l'uso delle interfacce Consumer, Runnable, Supplier e altre usate nel progetto;
    * identificazione di bug o errori di compilazione particolarmente tediosi;
    * migliorare la resa estetica dei componenti dell'interfaccia grafica;
    * generazione degli asset di gioco;
    * scrittura di codice ripetitivo, come getter e setter;
* Utilizzato ChatGPT per generare una prima versione di una funzione, poi:
    * controllata e compresa l'implementazione, e discusse le alternative;
    * corretta e modificata personalmente per rispettare i principi SOLID e di Clean Code.
