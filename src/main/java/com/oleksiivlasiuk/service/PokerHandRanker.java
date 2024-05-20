package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Hand;

import java.util.ArrayList;
import java.util.List;

public class PokerHandRanker {

    private final HandEvaluator handEvaluator;

    public PokerHandRanker() {
        this.handEvaluator = new HandEvaluator();
    }

    public List<Hand> rankHands(List<Hand> hands) {
        List<HandEvaluationResult> handEvaluationResults = new ArrayList<>();

        for (Hand hand : hands) {
            // this evaluation will use threads in future
            int strength = handEvaluator.evaluateCombinationStrength(hand);
            handEvaluationResults.add(new HandEvaluationResult(hand, strength));
        }

        handEvaluationResults.sort((result1, result2) -> {
            if (result1.strength() != result2.strength()) {
                return Integer.compare(result1.strength(), result2.strength());
            } else {
                return handEvaluator.compareHandsOfSameType(result1.hand(), result2.hand(), result1.strength());
            }
        });

        List<Hand> sortedHands = new ArrayList<>();
        for (HandEvaluationResult result : handEvaluationResults) {
            sortedHands.add(result.hand());
        }

        return sortedHands.reversed();
    }

    private record HandEvaluationResult(Hand hand, int strength) {}
}