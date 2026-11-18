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
Dato che tutte le classi sono racchiuse nel package `it.unicam.cs.mpgc.rpg130419`, d'ora in poi questa parte verrà omessa, inserendo solamente i nomi dei sub-package preceduti da un punto.<br>
Per esempio:<br>
`it.unicam.cs.mpgc.rpg130419.persistence` --> `.persistence`<br>
`it.unicam.cs.mpgc.rpg130419.model.heroes` --> `.model.heroes`<br>
## Pattern MVC
Per l'organizzazione del progetto è stato scelto di suddividere le classi secondo la struttura Model-View-Controller. Questa separazione di responsabilità, insieme alle classi per la persistenza, permette la modularità dell'intero progetto. È possibile, per esempio, implementare un sistema diverso di persistenza senza andare a intaccare le classi degli altri package, oppure è possibile implementare una GUI differente, lasciando la persistenza dei dati già presente. 
## .app
Package che ospita la classe di partenza del gioco. La classe `MainApplication` si occupa infatti di caricare il primo pannello della vista di JavaFX e collegare il suo controller.
## .controller
Package che racchiude i controller usati dalle viste di JavaFX. Di questi, i controller delle finestre di pop-up sono raggruppati in `controller.dialogs`, mentre i controller delle tab della finestra principale in `controller.tabs`.<br>
Tutti questi controller hanno il compito di far reagire dinamicamente gli elementi dell'interfaccia in base ai dati presenti in `model` e rendere interattivi i pulsanti della GUI. 
## .model
Comprende l'insieme di classi che amministrano i dati di gioco, suddivise internamente in ulteriori package.
#### .model.Expedition
Classe che definisce una spedizione. Contiene campi privati che descrivono il dungeon affrontato, la lista degli eroi di cui è composta, il numero di giorni rimanenti e lo stato della spedizione.<br>
Se il numero di giorni rimanenti è zero, calcola automaticamente se la spedizione è stata vinta o persa, aggiornando lo stato della spedizione.
#### .model.GameSession
#### .model.Guild
#### .model.Wallet
### .model.dungeons
### .model.heroes
### .model.weapons

## .persistence
## .view


---

# Persistenza dei dati
La persistenza dei dati è stata realizzata tramite file JSON. Serializzazione e deserializzazione sono svolte dalla libreria Gson. Il file di salvataggio `save.json` è contenuto nella cartella `/saves`.<br>
L'implementazione futura di metodi differenti di persistenza è resa possibile grazie all'interfaccia `.persistence.GameSaveRepository`. Ogni nuova classe che utilizza un nuovo metodo di persistenza deve semplicemente implementare questa interfaccia, estendere i suoi metodi e modificare solo la creazione dell'oggetto nel metodo `start` di `.app.MainApplication`.<br>
Per esempio:
```java
it.unicam.cs.mpgc.rpg130419.app.MainApplication

public void start(Stage stage) throws IOException {
    ...
    GameSaveRepository saves = new XMLGameSaveRepository(saveFile);
    ...
}
```

## Avvio della partita
All'avvio dell'applicazione, al giocatore vengono presentate due alternative per iniziare la partita: *New Game* e *Load Game*.

### New Game
Permette di creare un nuovo salvataggio, sovrascrivendo quello precedente (se presente).<br>
Il click del pulsante lancia il metodo `startNewGame` di `MainApplication`, che a sua volta chiama il metodo `newSession()`. Quest'ultimo restituisce una istanza di `GameSession` (`.model.GameSession`) con il setup iniziale del gioco. Viene poi caricata la vista principale, collegato il controller, e caricata la finestra di introduzione per il giocatore. In quest'ultima, quando il giocatore inserisce e infine invia il nome della nuova Gilda, il salvataggio viene sovrascritto. In questo modo, nel caso in cui il giocatore avviasse un nuovo salvataggio, ma uscisse dal gioco prima di inviare il nome, il salvataggio precedente (se presente) non verrebbe toccato.
### Load Game
Permette di caricare l'ultimo salvataggio effettuato.<br>
Nel caso in cui il file di salvataggio non sia presente o sia vuoto, il metodo `load` di `GameSaveRepository` restituisce un oggetto `Optional` vuoto, e quindi il salvataggio viene creato da zero, riutilizzando la funzione `startNewGame`.<br>
Nel caso in cui il salvataggio precedente sia terminato in un Game Over, verrà avviata una nuova partita, sempre grazie al riutilizzo del metodo `startNewGame`.<br>
La deserializzazione avviene ricreando la classe `GameSave`(`.persistence.GameSave`) tramite il file di salvataggio.

## Salvataggio della partita
Il salvataggio avviene automaticamente alla fine di ogni giornata, a seguito del click del pulsante *End Day*. Può però essere effettuato anche manualmente tramite il pulsante *Save Game* situato all'interno del *Menu*, accessibile dal pulsante nell'header.
### Salvataggio a fine giornata
Dopo l'aggiornamento delle statistiche della partita scaturito dal click del pulsante *End Day*, viene lanciato il metodo `save` dell'interfaccia `GameSaveRepository`. Questo, tramite il metodo `toJson` della liberia Gson, converte l'oggetto `session`, passato come parametro della funzione, in una stringa con il contenuto dell'oggetto in sintassi Json. Crea poi un file temporaneo con prefisso `guild-master-`, al cui interno salva la stringa Json precedentemente creata. Poi procede a tentare la copia del file temporaneo sul file di salvataggio in una azione atomica, se supportata. In questo modo non si rischiano interruzioni durante la sovrascrittura del file di salvataggio, evitando di perdere la precedente sessione di gioco. Infine, il file temporaneo viene rimosso.
### Salvataggio manuale
Il salvataggio sfrutta lo stesso metodo `save` utilizzato dal salvataggio automatico.

## Game Over
Al termine della giornata il metodo `payDailyWages` di `Guild`(`.model.Guild`) calcola l'ammontare giornaliero dei pagamenti per gli eroi. Se la Gilda non può pagare tutti gli eroi, il gioco termina con un Game Over.

---

# Estensioni future
INDIPENDENZA MVC, INTERFACE GAMESAVEREPOSITORY, MARKER INTERFACE PER ARMI, NUOVO TIPO DI EROE.

---

# Formule utilizzate
> [!NOTE]
> Tutte le formule riportate rispettano gli arrotondamenti dei tipi di dato utilizzati, anche se in questa appendice non sono riportati.
## Parametri di un dungeon
### Difficoltà
La difficoltà $D$ è un numero intero casuale tra $10$ e $80$.
### Durata
La durata in giorni del dungeon dipende dalla difficoltà:
$$T = 1 + \frac{D - 10}{20}$$
### Ricompensa
La ricompensa del dungeon dipende dalla difficoltà:
$$R_{\text{oro}} = 60T + 2D$$
La ricompensa in esperienza dipende dalla difficoltà.
$$R_{\text{exp}} = 20 + 3D$$
## Generazione casuale di un eroe
### Armi
Le probabilità nell'assegnamento dell'arma destra sono uguali per entrambe le classi:
| Fascia | Knight | Mage | Probabilità |
| --- | --- | --- | --- |
| Base | Bronze Sword | Basic Wand | 60% |
| Intermedia | Iron Sword | Ember Wand | 30% |
| Avanzata | Steel Sword | Moonstone Scepter | 10% |
La mano sinistra invece ha una probabilità del $20\%$ di ricevere uno scudo, altrimenti rimane vuota. 
### Potenza degli equipaggiamenti
| Equipaggiamento | Potenza |
| --- | --- |
| Left Fist | 1 |
| Right Fist | 1 |
| Bronze Sword | 10 |
| Iron Sword | 15 |
| Steel Sword | 20 |
| Basic Wand | 10 |
| Ember Wand | 17 |
| Moonstone Scepter | 25 |
| Shield | 7 |
## Curve di livello degli eroi
### Knight
$$E_{\text{Knight}}(L) = 100 + 50(L - 1)$$
### Mage
$$E_{\text{Mage}}(L) = 150 + 35(L - 1)$$
## Esito di una spedizione
### Calcolo della potenza dell'equipaggiamento di un eroe
La potenza dell'equipaggiamento di un eroe è la somma della potenza dell'oggetto nella mano sinistra e nella mano destra:
$$P_{\text{equip}} = P_{\text{sinistra}} + P_{\text{destra}}$$
### Calcolo della potenza di un eroe
La potenza di un eroe è determinata dalla somma della potenza del suo equipaggiamento, più un bonus di due punti per ogni livello successivo al primo:
$$P_{\text{eroe}} = P_{\text{equip}} + 2(L - 1)$$
### Calcolo della potenza del party
La potenza del gruppo è la somma delle singole potenze degli eroi:
$$P_{\text{gruppo}} = \sum P_{\text{eroe}}$$
### Calcolo della probabilità di vittoria
La probabilità di successo $p(\text{W})$ è calcolata semplicemente come il rapporto tra la potenza totale del gruppo e la difficoltà del dungeon:
$$p(\text{W}) = \frac{P_{\text{gruppo}}}{D}$$
### Determinare la vittoria di una spedizione
Viene estratto un numero casuale $r$ con `Math.random()`. Se il numero è minore della probabilità di successo, allora la spedizione tornerà alla gilda vittoriosa:
$$
\begin{cases}
r < p(\text{W}) & \text{Vittoria} \\
r >= p(\text{W}) & \text{Sconfitta}
\end{cases}
$$
## Costo di un eroe
### Stipendio
Lo stipendio di ciascun eroe dipende dalla potenza del suo equipaggiamento:
$$
S_{\text{eroe}} = 5 + \frac{P_{\text{equip}}}{2}
$$
### Reclutamento
Il costo di reclutamento è pari a cinque volte la potenza dell'equipaggiamento dell'eroe:
$$
C_{\text{rec}} = 5 \cdot P_{\text{equip}}
$$

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
Ogni utilizzo dell'Intelligenza Artificiale è stato effettuato coscenziosamente, e sempre supervisionato. Inoltre, ogni azione sul codice è sempre stata pianificata e discussa in anticipo.
