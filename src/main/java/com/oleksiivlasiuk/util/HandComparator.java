package com.oleksiivlasiuk.util;

import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;
import com.oleksiivlasiuk.service.HandEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HandComparator implements Comparator<Hand> {
    @Override
    public int compare(Hand hand1, Hand hand2) {
        HandEvaluator handEvaluator = new HandEvaluator();

        int combinationStrengthHand1 = handEvaluator.evaluateCombinationStrength(hand1);
        int combinationStrengthHand2 = handEvaluator.evaluateCombinationStrength(hand2);

        if (combinationStrengthHand1 != combinationStrengthHand2) {
            return Integer.compare(combinationStrengthHand1, combinationStrengthHand2);
        }

        switch (combinationStrengthHand1) {
            case 2:
                return comparePair(hand1, hand2);
            case 3:
                return 0;
            case 4:
                return 0;
            case 5:
                return 0;
            case 6:
                return 0;
            case 7:
                return 0;
            case 8:
                return 0;
            case 9:
                return 0;
            case 10:
                return 0;
            default:
                return 0;
        }
    }

    @Override
    public Comparator<Hand> reversed() {
        return Comparator.super.reversed();
    }

    private int comparePair(Hand hand1, Hand hand2) {
        int pairValue1 = findPairValue(hand1);
        int pairValue2 = findPairValue(hand2);

        if (pairValue1 != pairValue2) {
            return Integer.compare(pairValue1, pairValue2);
        }

        return compareKickers(hand1, hand2, pairValue1);
    }

    private int findPairValue(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                return Card.getCardRankValue(hand.getCardByIndex(i));
            }
        }
        return -1;
    }

    private int compareKickers(Hand hand1, Hand hand2, int pairValue) {
        List<Integer> kickers1 = getKickers(hand1, pairValue);
        List<Integer> kickers2 = getKickers(hand2, pairValue);

        for (int i = 0; i < kickers1.size(); i++) {
            if (!kickers1.get(i).equals(kickers2.get(i))) {
                return Integer.compare(kickers1.get(i), kickers2.get(i));
            }
        }

        return 0;
    }

    private List<Integer> getKickers(Hand hand, int combinationValue) {
        List<Integer> kickers = new ArrayList<>();
        for (Card card : hand.getCards()) {
            if (Card.getCardRankValue(card) != combinationValue) {
                kickers.add(Card.getCardRankValue(card));
            }
        }
        kickers.sort(Collections.reverseOrder());
        return kickers;
    }


}
