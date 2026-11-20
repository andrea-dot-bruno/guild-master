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
Quando il giocatore ritiene di aver terminato tutte le azioni per la giornata, può cliccare il pulsante "End Day", che farà terminare la giornata in corso. Le spedizioni in corso avanzeranno di un giorno, e quelle terminate verranno "calcolate", assegnando, in caso di vittoria, oro al giocatore ed esperienza agli eroi ingaggiati. Infine, vengono sottratte al totale dell'oro della gilda le paghe giornaliere degli eroi. Prima dell'inizio della giornata successiva vengono anche generati casualmente nuovi dungeon, rimuovendo quelli non utilizzati in precedenza e ripopolando quelli giornalieri, fino a un totale di tre. Gli eroi reclutabili, invece, vengono mantenuti se non ingaggiati, generandone di nuovi fino a raggiungere un totale di quattro eroi reclutabili a inizio giornata. 

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
Classe che raccoglie e gestisce i dati della sessione, come la gilda, il giorno corrente, i dungeon da affrontare, gli eroi disponibili, e lo stato della partita.<br>
#### .model.Guild
Classe che raccoglie e gestisce gli elementi della gilda, come l'oro, i suoi eroi e le sue spedizioni attive. 
#### .model.Wallet
Classe che regola i movimenti effettuati sul saldo dell'oro.
### .model.dungeons
#### .model.dungeons.Dungeon
Classe che identifica un dungeon. Dato che il dungeon è generato automaticamente dalla classe `RandomDungeonBuilder` (`.model.dungeons.RandomDungeonBuilder`), e non ci sono logiche intrinseche al dungeon stesso, ma i suoi dati vengono elaborati al di fuori, questa classe espone solo i suoi valori tramite metodi getter.
#### .model.dungeons.RandomDungeonBuilder
Classe che genera dungeon in maniera casuale.<br>
Costruisce un record interno `DungeonData`, nel quale inserisce un catalogo di stringhe contenenti nomi per i dungeon e i percorsi alle icone, per poi estrarre un elemento ciascuno randomicamente, da assegnare al dungeon finale.
### .model.heroes
#### .model.heroes.Hero
Classe astratta che implementa tutti i campi e i metodi in comune che un eroe dovrebbe avere. Contenendo la classe WeaponSet, anche questa deve specificare il tipo `W` di equipaggiamento permesso. 
#### .model.heroes.HeroLevel
Classe che amministra l'esperienza e il leveling degli eroi.
#### .model.heroes.RandomHeroBuilder
Classe che genera RecruitmentCandidate (ossia il record "eroe + prezzo di ingaggio") in maniera casuale.<br>
Per prima cosa, presenta un record interno `HeroData`, nel quale inserisce un catalogo di stringhe contenenti nomi per gli eroi e i percorsi alle icone, suddivise per classi, per poi estrarre un elemento ciascuno randomicamente, da assegnare all'eroe finale.<br>
Per la creazione effettiva dell'oggetto, invece, estrae in maniera casuale uno dei profili registrati in PROFILES e sceglie un nome dal catalogo comune. Il profilo selezionato associa il costruttore del suo eroe ai ritratti e ai costruttori delle tre armi. Il builder usa poi il profilo per creare l'eroe e assegnare l'arma destra, sorteggiando poi per la presenza dello scudo in quella sinistra, e infine calcola il prezzo.
#### .model.heroes.RecruitmentCandidate
Record che raccoglie gli eroi generati e il loro prezzo di reclutamento, calcolato automaticamente in base alla potenza dell'eroe. Dato che il prezzo di reclutamento, una volta inserito l'eroe nella gilda, non ha più utilità, è stato inserito al di fuori della classe Hero, ma collegato ad essa tramite questo record.
### .model.weapons
I package `.model.weapons.knightWeapons`, `.model.weapons.mageWeapons` e `.model.weapons.noClassWeapons` contengono le classi che definiscono le armi delle varie classi di eroi, oltre a una marker interface per permettere alla rispettiva classe di utilizzare le armi che implementano questa interfaccia.  
#### .model.weapons.Equippable
Interfaccia richiesta da ogni arma del gioco.
#### .model.weapons.Weapon
Classe astratta che implementa l'interfaccia `Equippable`. Le armi concrete estendono questa classe e dichiarano quali eroi possono utilizzarle tramite l'uso delle marker interface `KnightWeapon` e `MageWeapon`. Gli oggetti che invece usano `NoClassWeapon` possono essere assegnati a qualsiasi eroe.
#### .model.weapons.WeaponSet
Classe che raccoglie l'arma destra e sinistra di un eroe, andando a formare il suo equipaggiamento. Il parametro `W` determina quali oggetti possono essere assegnati. 
## .persistence
Comprende tutte le classi, interfacce, record ed enum che si occupano di salvare e recuperare i dati di salvataggio.
#### .persistence.GameSaveRepository 
È l'interfaccia principale di tutto il package. Ogni classe che implementa un tipo diverso di persistenza di questo progetto deve utilizzarla.
#### .persistence.JsonGameSaveRepository
Classe che implementa il salvataggio e il caricamento dei dati in un file JSON, sfruttando i metodi della libreria Gson.
## .view
Racchiude tutte le classi che si occupano di mostrare e gestire le finestre di pop-up.

---

# Persistenza dei dati
La persistenza dei dati è stata realizzata tramite file JSON. Serializzazione e deserializzazione sono svolte dalla libreria Gson. Il file di salvataggio `save.json` è contenuto nella cartella `saves`.<br>
L'implementazione futura di metodi differenti di persistenza è resa possibile grazie all'interfaccia `.persistence.GameSaveRepository`. È possibile trovare un esempio più dettagliato nel capitolo *Estensioni Future*.

## Avvio della partita
All'avvio dell'applicazione, al giocatore vengono presentate due alternative per iniziare la partita: *New Game* e *Load Game*.

### New Game
Permette di creare un nuovo salvataggio, sovrascrivendo quello precedente (se presente).<br>
Il click del pulsante lancia il metodo `startNewGame` di `MainApplication`, che a sua volta chiama il metodo `newSession()`. Quest'ultimo restituisce una istanza di `GameSession` (`.model.GameSession`) con il setup iniziale del gioco. Viene poi caricata la vista principale, collegato il controller, e caricata la finestra di introduzione per il giocatore. In quest'ultima, quando il giocatore inserisce e infine invia il nome della nuova Gilda, il salvataggio viene sovrascritto. In questo modo, nel caso in cui il giocatore avviasse un nuovo salvataggio, ma uscisse dal gioco prima di inviare il nome, il salvataggio precedente (se presente) non verrebbe toccato.
### Load Game
Permette di caricare l'ultimo salvataggio effettuato.<br>
Nel caso in cui il file di salvataggio non sia presente o sia vuoto, il metodo `load` di `JsonGameSaveRepository` restituisce un oggetto `Optional` vuoto, e quindi il salvataggio viene creato da zero, riutilizzando la funzione `startNewGame`.<br>
Nel caso in cui il salvataggio precedente sia terminato in un Game Over, verrà avviata una nuova partita su conferma dell'utente, sempre grazie al riutilizzo del metodo `startNewGame`.<br>
La deserializzazione avviene ricreando l'istanza di `GameSave` (`.persistence.GameSave`) tramite il file di salvataggio.

## Salvataggio della partita
Il salvataggio avviene automaticamente alla fine di ogni giornata, a seguito del click del pulsante *End Day*. Può però essere effettuato anche manualmente tramite il pulsante *Save Game* situato all'interno del *Menu*, accessibile dal pulsante nell'header.
### Salvataggio a fine giornata
Dopo l'aggiornamento delle statistiche della partita scaturito dal click del pulsante *End Day*, viene lanciato il metodo `save` della classe `JsonGameSaveRepository`. Il metodo `GameSave.from(session)` raccoglie i dati da conservare in un record GameSave, che il metodo `toJson` della libreria Gson converte in stringa JSON. Il repository scrive la stringa in un file temporaneo nella stessa cartella di `save.json`. Una volta completata la scrittura, tenta la sostituzione del file di salvataggio con il file temporaneo, tramite `Files.move`. Questa operazione è effettuata in maniera atomica con l'opzione `ATOMIC_MOVE`, se supportata. In questo modo, se la scrittura del file temporaneo si interrompe, il salvataggio precedente non viene sovrascritto.
### Salvataggio manuale
Il salvataggio sfrutta lo stesso metodo `save` utilizzato dal salvataggio automatico.

## Game Over
Al termine della giornata il metodo `payDailyWages` di `Guild` (`.model.Guild`) calcola l'ammontare giornaliero dei pagamenti per gli eroi. Se la Gilda non può pagare tutti gli eroi, il gioco termina con un Game Over.

---

# Estensioni future
A seguire sono illustrati alcuni esempi di modifica o estensione del codice. 
## Nuova classe di eroi
Una delle più ovvie estensioni di Guild Master sta nell'aggiungere una nuova classe di eroi, come per esempio il *Rogue*. È immediato pensare che la nuova classe abbia anche delle armi ad essa dedicate, perciò iniziamo creando, nel package `.model.weapons.rogueWeapons`, l'interfaccia `RogueWeapon`, che deve estendere quella `Equippable`. Aggiungiamo inoltre questa interfaccia tra quelle estese da `NoClassWeapon`, in modo tale che anche il Rogue possa sfruttare quelle armi. Creiamo quindi alcune armi dedicate alla nuova classe. Tutte queste devono estendere la classe `Weapon` e implementare `RogueWeapon`. A questo punto, è dunque necessario creare la classe Java del nuovo eroe, `Rogue.java`, da inserire nel package `.model.heroes`, nel seguente modo: 
```java
public class Rogue extends Hero<RogueWeapon> {...}
```
Possiamo quindi aggiungere gli sprite per il Rogue in `assets/rogue-icons` e registrarli nel catalogo `catalogs/heroes.json`.
Possiamo ora modificare la classe RandomHeroBuilder, aggiungendo alla lista `PROFILES` il nuovo `HeroProfile` dedicato alla nuova classe, che comprende il puntatore al costruttore della classe, il gruppo di icone corrispondenti e i costruttori delle nuove armi dedicate (in ordine di rarità).
Infine, per adeguare anche la persistenza, aggiungiamo `ROGUE` a `HeroType` e aggiungiamo i casi dedicati negli switch di `from` e `restore` in `HeroSave`. Lo stesso vale per le nuove armi nelle classi, aggiornando i metodi `from` e `createFor` in `WeaponType`.
## Interfaccia grafica
Se per caso vi fosse necessità di pubblicare il gioco su una pagina web, invece che un pacchetto scaricabile ed eseguibile, la modularità del pattern MVC permette di mantenere tutte le classi che gestiscono la logica interna dell'app, come eroi, armi dungeon e spedizioni, dovendo quindi sviluppare semplicemente il mezzo di fruizione grafico del progetto. Tutti i metodi per recuperare i dati necessari, al fine di mostrarli nell'interfaccia utente, sono esposti dalle classi del package `.model`.
## Persistenza dati
Nel momento in cui si volesse modificare il sistema di salvataggio e caricamento dei dati, sostituendo quello JSON con, per esempio, un file XML, basta semplicemente implementare la nuova logica in una classe, come `XMLGameSaveRepository`, sempre utilizzando la stessa interfaccia `.persistence.GameSaveRepository`. È necessario quindi modificare solo la creazione dell'oggetto nel metodo `start` di `.app.MainApplication`.<br>
Per esempio:
```java
it.unicam.cs.mpgc.rpg130419.app.MainApplication

public void start(Stage stage) throws IOException {
    ...
    GameSaveRepository saves = new XMLGameSaveRepository(saveFile);
    ...
}
```

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
$$p(\text{W}) = \begin{cases}
1 & \text{se } P_{\text{gruppo}} \geq D\\
\frac{P_{\text{gruppo}}}{D} & \text{altrimenti}
\end{cases}$$
### Determinare la vittoria di una spedizione
Viene estratto un numero casuale $r$ con `Math.random()`. Se il numero è minore della probabilità di successo, allora la spedizione tornerà alla gilda vittoriosa:
$$
\begin{cases}
r < p(\text{W}) & \text{Vittoria} \\
r \geq p(\text{W}) & \text{Sconfitta}
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
Utilizzato ChatGPT per:
* brainstorming sulle feature del gioco;
* comprendere concetti teorici, come l'uso delle interfacce Consumer, Runnable, Supplier e altre, usate nel progetto;
* identificazione di bug o errori di compilazione particolarmente tediosi;
* migliorare la resa estetica dei componenti dell'interfaccia grafica;
* generazione degli asset di gioco;
* scrittura di codice ripetitivo, come getter e setter;

Utilizzato ChatGPT per generare una prima versione di una funzione, poi:
* compresa e controllata l'implementazione, ed eventualmente discusse le alternative;
* corretta e modificata personalmente per rispettare i principi SOLID e di Clean Code.

Ogni utilizzo dell'Intelligenza Artificiale è stato effettuato coscenziosamente, e sempre supervisionato. Inoltre, ogni azione sul codice è sempre stata pianificata e discussa in anticipo.
