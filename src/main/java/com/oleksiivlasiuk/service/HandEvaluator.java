package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEvaluator {
    public int evaluateCombinationStrength(Hand hand) {
        Hand sortedCardsHand = hand.getSortedCopyDesc();

        if (isRoyalFlush(sortedCardsHand)) {
            return 10;
        }
        if (isStraightFlush(sortedCardsHand)) {
            return 9;
        }
        if (isFourOfAKind(sortedCardsHand)) {
            return 8;
        }
        if (isFullHouse(sortedCardsHand)) {
            return 7;
        }
        if (isFlush(sortedCardsHand)) {
            return 6;
        }
        if (isStraight(sortedCardsHand)) {
            return 5;
        }
        if (isThreeOfAKind(sortedCardsHand)) {
            return 4;
        }
        if (isTwoPair(sortedCardsHand)) {
            return 3;
        }
        if (isOnePair(sortedCardsHand)) {
            return 2;
        }
        return 1;
    }

    public int compareHandsOfSameType(Hand hand1, Hand hand2, int combinationStrength) {
        Hand sortedCardsHand1 = hand1.getSortedCopyDesc();
        Hand sortedCardsHand2 = hand2.getSortedCopyDesc();

        return switch (combinationStrength) {
            case 2 -> comparePair(sortedCardsHand1, sortedCardsHand2);
            case 3 -> compareTwoPair(sortedCardsHand1, sortedCardsHand2);
            case 4 -> compareThreeOfAKind(sortedCardsHand1, sortedCardsHand2);
            case 7 -> compareFullHouse(sortedCardsHand1, sortedCardsHand2);
            case 8 -> compareFourOfAKind(sortedCardsHand1, sortedCardsHand2);
            case 10 -> 0;
            default -> compareHighCards(sortedCardsHand1, sortedCardsHand2);
        };
    }

    private boolean isRoyalFlush(Hand hand) {
        return isFlush(hand) && hand.getHighestCard().getRank().equals("A");
    }

    private boolean isStraightFlush(Hand hand) {
        return isFlush(hand) && isStraight(hand);
    }

    private boolean isFourOfAKind(Hand hand) {
        return hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(3).getRank()) ||
                hand.getCardByIndex(1).getRank().equals(hand.getCardByIndex(4).getRank()) ;
    }

    private boolean isFullHouse(Hand hand) {
        return  (hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(1).getRank()) && hand.getCardByIndex(2).getRank().equals(hand.getCardByIndex(4).getRank())) ||
                (hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(2).getRank()) && hand.getCardByIndex(3).getRank().equals(hand.getCardByIndex(4).getRank()));
    }

    private boolean isFlush(Hand hand) {
        String suit = hand.getCardByIndex(0).getSuit();
        for (Card card : hand.getCards()) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    private boolean isStraight(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (Card.getCardRankValue(hand.getCardByIndex(i)) - Card.getCardRankValue(hand.getCardByIndex(i + 1)) != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeOfAKind(Hand hand) {
        return  hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(2).getRank()) ||
                hand.getCardByIndex(1).getRank().equals(hand.getCardByIndex(3).getRank()) ||
                hand.getCardByIndex(2).getRank().equals(hand.getCardByIndex(4).getRank());
    }

    private boolean isTwoPair(Hand hand) {
        return  (hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(1).getRank()) && hand.getCardByIndex(2).getRank().equals(hand.getCardByIndex(3).getRank())) ||
                (hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(1).getRank()) && hand.getCardByIndex(3).getRank().equals(hand.getCardByIndex(4).getRank())) ||
                (hand.getCardByIndex(1).getRank().equals(hand.getCardByIndex(2).getRank()) && hand.getCardByIndex(3).getRank().equals(hand.getCardByIndex(4).getRank()));
    }

    private boolean isOnePair(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                return true;
            }
        }
        return false;
    }

    private int comparePair(Hand hand1, Hand hand2) {
        int pairValue1 = findPairValue(hand1);
        int pairValue2 = findPairValue(hand2);

        if (pairValue1 != pairValue2) {
            return Integer.compare(pairValue1, pairValue2);
        }

        return compareKickers(hand1, hand2, pairValue1);
    }

    private int compareTwoPair(Hand hand1, Hand hand2) {
        List<Integer> pairsInHand1 = findFwoPairValues(hand1);
        List<Integer> pairsInHand2 = findFwoPairValues(hand2);

        for (int i = 0; i < 2; i++) {
            if (!pairsInHand1.get(i).equals(pairsInHand2.get(i))) {
                return Integer.compare(pairsInHand1.get(i), pairsInHand2.get(i));
            }
        }

        List<Integer> kickersInHand1 = getKickers(hand1, pairsInHand1);
        List<Integer> kickersInHand2 = getKickers(hand2, pairsInHand2);

        for (int i = 0; i < kickersInHand1.size(); i++) {
            if (!kickersInHand1.get(i).equals(kickersInHand2.get(i))) {
                return Integer.compare(kickersInHand1.get(i), kickersInHand2.get(i));
            }
        }
        return 0;
    }

    private int compareThreeOfAKind(Hand hand1, Hand hand2) {
        int threeOfAKindValue1 = findThreeOfAKindValue(hand1);
        int threeOfAKindValue2 = findThreeOfAKindValue(hand2);

        if (threeOfAKindValue1 != threeOfAKindValue2) {
            return Integer.compare(threeOfAKindValue1, threeOfAKindValue2);
        }

        return compareKickers(hand1, hand2, threeOfAKindValue1);
    }

    private int compareFullHouse(Hand hand1, Hand hand2) {
        int threeOfAKindValueHand1 = findThreeOfAKindValue(hand1);
        int threeOfAKindValueHand2 = findThreeOfAKindValue(hand2);

        if (threeOfAKindValueHand1 != threeOfAKindValueHand2) {
            return Integer.compare(threeOfAKindValueHand1, threeOfAKindValueHand2);
        }

        int pairValueHand1 = findPairValueForFullHouse(hand1, threeOfAKindValueHand1);
        int pairValueHand2 = findPairValueForFullHouse(hand2, threeOfAKindValueHand2);

        return Integer.compare(pairValueHand1, pairValueHand2);
    }

    private int compareFourOfAKind(Hand hand1, Hand hand2) {
        int fourOfAKindValueHand1 = findFourOfAKindValue(hand1);
        int fourOfAKindValueHand2 = findFourOfAKindValue(hand2);

        if (fourOfAKindValueHand1 != fourOfAKindValueHand2) {
            return Integer.compare(fourOfAKindValueHand1, fourOfAKindValueHand2);
        }
        return compareHighCards(hand1, hand2);
    }

    private int findFourOfAKindValue(Hand hand) {
        return hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(1).getRank()) ?
                Card.getCardRankValue(hand.getCardByIndex(0)) :
                Card.getCardRankValue(hand.getCardByIndex(2));
    }

    private int compareHighCards(Hand hand1, Hand hand2) {
        for (int i = 0; i < 5; i++) {
            if (!hand1.getCardByIndex(i).getRank().equals(hand2.getCardByIndex(i).getRank())) {
                return Integer.compare(
                        Card.getCardRankValue(hand1.getCardByIndex(i)),
                        Card.getCardRankValue(hand2.getCardByIndex(i))
                );
            }
        }
        return 0;
    }

    public int findThreeOfAKindValue(Hand hand) {
        for (int i = 0; i < 3; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 2).getRank())) {
                return Card.getCardRankValue(hand.getCardByIndex(i));
            }
        }
        return -1;
    }

    private int findPairValue(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                return Card.getCardRankValue(hand.getCardByIndex(i));
            }
        }
        return -1;
    }

    private int findPairValueForFullHouse(Hand hand, int threeOfAKindValue) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                int cardRankValue = Card.getCardRankValue(hand.getCardByIndex(i));
                if (cardRankValue != threeOfAKindValue) {
                    return Card.getCardRankValue(hand.getCardByIndex(i));
                }
            }
        }
        return -1;
    }

    private List<Integer> findFwoPairValues(Hand hand) {
        List<Integer> twoPairValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                twoPairValues.add(Card.getCardRankValue(hand.getCardByIndex(i)));
                i++;
            }
        }
        twoPairValues.sort(Collections.reverseOrder());
        return twoPairValues;
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

    private List<Integer> getKickers(Hand hand, List<Integer> combinationValues) {
        List<Integer> kickers = new ArrayList<>();
        for (Card card : hand.getCards()) {
            if (!combinationValues.contains(Card.getCardRankValue(card))) {
                kickers.add(Card.getCardRankValue(card));
            }
        }
        kickers.sort(Collections.reverseOrder());
        return kickers;
    }

    private int compareKickers(Hand hand1, Hand hand2, int combinationValue) {
        List<Integer> kickers1 = getKickers(hand1, combinationValue);
        List<Integer> kickers2 = getKickers(hand2, combinationValue);
        for (int i = 0; i < kickers1.size(); i++) {
            if (!kickers1.get(i).equals(kickers2.get(i))) {
                return Integer.compare(kickers1.get(i), kickers2.get(i));
            }
        }
        return 0;
    }
}
