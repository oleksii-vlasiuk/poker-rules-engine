package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Hand;
import com.oleksiivlasiuk.model.HandEvaluationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PokerHandRanker {

    private final HandEvaluator handEvaluator;
    private final ExecutorService executor;

    public PokerHandRanker() {
        this.handEvaluator = new HandEvaluator();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public List<Hand> rankHands(List<Hand> hands) throws ExecutionException, InterruptedException {
        List<Future<HandEvaluationResult>> futures = new ArrayList<>();
        List<HandEvaluationResult> handEvaluationResults = new ArrayList<>();

        for (Hand hand : hands) {
            futures.add(executor.submit(() -> handEvaluator.evaluateHandStrength(hand)));
        }

        for (Future<HandEvaluationResult> future : futures) {
            handEvaluationResults.add(future.get());
        }

        handEvaluationResults.sort((result1, result2) -> {
            if (result1.combinationStrength() != result2.combinationStrength()) {
                return Integer.compare(result1.combinationStrength(), result2.combinationStrength());
            } else {
                return Integer.compare(result1.encoding(), result2.encoding());
            }
        });

        List<Hand> sortedHands = new ArrayList<>();
        for (HandEvaluationResult result : handEvaluationResults) {
            sortedHands.add(result.hand());
        }

        executor.shutdown();
        return sortedHands.reversed();
    }
}