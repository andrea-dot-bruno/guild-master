package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.Expedition;
import it.unicam.cs.mpgc.rpg130419.model.heroes.Hero;

import java.util.List;
import java.util.Map;
import java.util.UUID;

record ExpeditionSave(UUID id, DungeonSave dungeon, int remainingTime, List<UUID> partyIds) {
    static ExpeditionSave from(Expedition expedition) {
        return new ExpeditionSave(expedition.getId(), DungeonSave.from(expedition.getDungeon()),
                expedition.getRemainingTime(), expedition.getInvolvedHeroes().stream().map(Hero::getId).toList());
    }

    Expedition restore(Map<UUID, Hero> heroesById) {
        List<Hero> party = partyIds.stream().map(id -> {
            Hero hero = heroesById.get(id);
            if (hero == null) throw new IllegalArgumentException("Unknown expedition hero: " + id);
            return hero;
        }).toList();
        return Expedition.restore(id, dungeon.restore(), party, remainingTime);
    }
}
