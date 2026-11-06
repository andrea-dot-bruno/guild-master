# Guild Master - Note per lo sviluppo
Appunti e idee per la fase preliminare della produzione del codice

## 1. Il Concept
Il gioco di ruolo in questione consiste nella gestione di una gilda. Il giocatore interpreta, di fatto, il generale della gilda, con il compito di amministrare le risorse, sia umane che materiali, al fine di portare la gilda a espandersi, tramite il conseguimento di sfide sotto forma di dungeon.



## 2. Le Risorse
### Eroi
La gilda è composta in primo luogo dagli eroi, ossia i personaggi che sfideranno i dungeon.
> [!NOTE]
> Durante la fase iniziale del gioco, gli eroi sono suddivisi solamente in due classi: il Cavaliere e il Mago.

### Armi
Ciascun eroe può equipaggiare un'arma di offesa e un'arma di difesa. Una volta equipaggiate possono essere rimosse o spostate su altri eroi. 

### Oggetti
La gilda possiede una scorta di oggetti di vario tipo, come pozioni, lanciabili di difesa o di offesa ecc..<br>
Questi oggetti possono essere assegnati alla spedizione prima della partenza, e potrebbero essere consumati nel mentre dagli eroi.

### Soldi
Attualmente il sistema monetario del gioco serve principalmente per l'acquisto di armi e oggetti nel negozio.<br>
Gli eroi, in quanto membri della gilda, necessitano di una paga per poter prendere parte alla spedizione.
- La paga potrebbe crescere, al crescere del livello del personaggio.<br>
    > [!NOTE]  
    > Implementazioni future possono prevedere nuove classi, con costi maggiori o minori.

Le alternative per ulteriori implementazioni sono:
1. Le spedizioni potrebbero beneficiare nel caso di una quantità di oro fornita (LORE: per permettere agli eroi di acquistare oggetti nei negozi interni al dungeon, come avviene quando il giocatore interpreta gli stessi eroi);
    - Eventualmente, le spedizioni con l'oro possono riportare al giocatore gli oggetti che hanno acquistato.



## 3. Le Spedizioni
Una spedizione richiede che un insieme di eroi venga inviato nel dungeon. La scelta riguradante la quantità di eroi da inviare spetta al giocatore, che dovrà quindi amministrare le sue risorse. 
> [!NOTE]
> È possibile che alcune spedizioni richiedano un numero limitato (o esatto) di eroi, come difficoltà aggiuntiva.

### Determinare il successo di una spedizione
Il successo delle spedizioni è determinato da un calcolo matematico probabilistico (**da definire**).<br>
Migliore è l'equipaggiamento della spedizione, più alta sarà la percentuale di successo.

#### Accortezze di gameplay
Per evitare che il giocatore si limiti ad assegnare il più alto numero possibile di risorse a una spedizione, evadendo il divertimento del gioco, ci saranno questi fattori a influenzare le sue decisioni:
1. Gli eroi inviati nella spedizione richiedono una paga in oro;
2. Gli oggetti assegnati alla spedizione potrebbero non ritornare indietro;
3. Le armi si usurano con l'uso;
4. L'oro potrebbe essere speso;
5. Il numero e l'esperienza degli eroi in spedizione influisce sulla durata di esse.

    > [!NOTE]
    > Tutti questi campi richiedono accortezza per l'implementazione delle loro formule matematiche, al fine di ottenere un gioco bilanciato.

In più, la presenza in bacheca di spedizioni con vincoli sugli eroi dovrebbe portare il giocatore a riflettere sulle proprie scelte.

### Reward delle spedizioni
Le spedizioni in bacheca, oltre a offrire un compenso in oro, danno la possibilità di riportare a casa alcuni oggetti che gli eroi possono trovare nei dungeon. Per esempio, potrebbe essere indicato nell'annuncio che nel determinato dungeon è stata smarrita la seguente arma, e quindi potrebbe diventare di proprietà della gilda, se ritrovata.<br>
È possibile inoltre che gli eroi, in alcuni dungeon, incontrino altri personaggi, che saranno reclutati e diventeranno parte della gilda.

## Spedizioni simultanee
Avanzando nel gioco, la bacheca potrebbe riempirsi di spedizioni, permettendo al giocatore di suddividere gli eroi in più spedizioni contemporaneamente.



## 4. Sistema di leveling
### Eroi
Gli eroi salgono di livello acquisendo esperienza durante le spedizioni.
### Armi
Le armi salgono di livello se portate a migliorare dal **fabbro** della gilda.
### Negozio
Il negozio offre articoli sempre migliori, man mano che il giocatore spende soldi con il mercante.



## 5. Gameplay loop
Alcune azioni possono richiedere del tempo. Per questo, gli eventi potrebbero essere scanditi in base al passare dei giorni.<br>
Eventi istantanei:
- Miglioramento delle armi
- Acquisto di oggetti

Eventi non istantanei:
- Spedizioni (di diversa durata)
- Miglioramento del negozio (al raggiungimento della quota di denaro necessaria, il negozio migliorerà a partire dal giorno seguente)



## 6. Aumento di difficoltà del gameplay
Mentre il giocatore diventa più esperto nell'amministrare le sue risorse, il gameplay potrebbe farsi sempre più impegnativo. Le spedizioni in bacheca diventano più impegnative, e di conseguenza la percentuale di successo si abbassa. Contemporaneamente però, offrono una ricompensa maggiore. Inoltre lo shop offre prodotti migliori (in relazione all'oro speso), gli eroi salgono di livello (se sono stati inviati nelle spedizioni), le armi nei dungeon salgono di valore ecc..


## 7. Autogeneramento del gioco
Si suppone che le spedizioni debbano essere generate automaticamente, con randomizzazioni come armi disponibili, personaggi da trovare, reward basato sulla difficoltà ecc..<br>
Il negozio anche deve offrire automaticamente un set di oggetti in base al livello raggiunto.
> [!NOTE]
> Considerare possibile rotazione giornaliera randomica estratta da un pool di oggetti del livello.

I personaggi da includere nella gilda devono essere generati, su base randomica delle caratteristiche.

### Scripted Events
Si contempla la possibilità di inserire eventi prefissati, che si attivano al verificarsi di determinate condizioni (eroe X assunto nella gilda, raggiungimento di un livello, ritrovamento di una certa arma ecc.).

# Formule utilizzate
## Vittoria della spedizione
$$\frac{\text{Party Power}}{\text{Dungeon Difficulty}} \geq \text{randNum}(0, 1)$$
## Party Power
$$\sum{\text{Hero Power}}$$
## Hero Power
### Mage
$$\text{Weapon Set Power} + \text{Hero Level} * 1.1$$
### Knight
$$\text{Weapon Set Power} + \text{Hero Level} * 1.2$$
## Weapon Set Power
$$\sum{\text{Weapon Power}}$$
### Weapons' power list
- Sword: 10
- Wand: 10
