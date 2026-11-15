package it.unicam.cs.mpgc.rpg130419.model;

import java.util.List;

public record DayResult(List<Expedition> completedExpeditions, boolean gameOver) {
    public DayResult {
        completedExpeditions = List.copyOf(completedExpeditions);
    }
}
