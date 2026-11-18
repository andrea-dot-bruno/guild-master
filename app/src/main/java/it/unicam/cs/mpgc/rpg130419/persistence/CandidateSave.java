package it.unicam.cs.mpgc.rpg130419.persistence;

import it.unicam.cs.mpgc.rpg130419.model.heroes.RecruitmentCandidate;

record CandidateSave(HeroSave hero, int hireFee) {
    static CandidateSave from(RecruitmentCandidate candidate) {
        return new CandidateSave(HeroSave.from(candidate.hero()), candidate.hireFee());
    }

    RecruitmentCandidate restore() {
        return new RecruitmentCandidate(hero.restore(), hireFee);
    }
}
