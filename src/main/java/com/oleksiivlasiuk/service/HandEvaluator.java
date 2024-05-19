package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;
import com.oleksiivlasiuk.model.HandEvaluationResult;

import java.util.ArrayList;
import java.util.List;

public class HandEvaluator {

    public HandEvaluationResult evaluateHandStrength(Hand hand) {
        int combinationStrength;
        Hand sortedCardsHand = hand.getSortedCopyDesc();

        if (isRoyalFlush(sortedCardsHand)) {
            combinationStrength = 10;
            return new HandEvaluationResult(hand, combinationStrength, encodeFlashOrHighCard(sortedCardsHand));
        }
        if (isStraightFlush(sortedCardsHand)) {
            combinationStrength = 9;
            return new HandEvaluationResult(hand, combinationStrength, encodeStraight(sortedCardsHand));
        }
        if (isFourOfAKind(sortedCardsHand)) {
            combinationStrength = 8;
            return new HandEvaluationResult(hand, combinationStrength, encodeFourOfAKind(sortedCardsHand));
        }
        if (isFullHouse(sortedCardsHand)) {
            combinationStrength = 7;
            return new HandEvaluationResult(hand, combinationStrength, encodeFullHouse(sortedCardsHand));
        }
        if (isFlush(sortedCardsHand)) {
            combinationStrength = 6;
            return new HandEvaluationResult(hand, combinationStrength, encodeFlashOrHighCard(sortedCardsHand));
        }
        if (isStraight(sortedCardsHand)) {
            combinationStrength = 5;
            return new HandEvaluationResult(hand, combinationStrength, encodeStraight(sortedCardsHand));
        }
        if (isThreeOfAKind(sortedCardsHand)) {
            combinationStrength = 4;
            return new HandEvaluationResult(hand, combinationStrength, encodeThreeOfAKind(sortedCardsHand));
        }
        if (isTwoPair(sortedCardsHand)) {
            combinationStrength = 3;
            return new HandEvaluationResult(hand, combinationStrength, encodeTwoPair(sortedCardsHand));
        }
        if (isPair(sortedCardsHand)) {
            combinationStrength = 2;
            return new HandEvaluationResult(hand, combinationStrength, encodePair(sortedCardsHand));
        }
        return new HandEvaluationResult(hand, 1, encodeFlashOrHighCard(sortedCardsHand));
    }

    private int encodeFourOfAKind(Hand hand) {
        String fourOfAKindHexStringValue = findFourOfAKindHexStringValue(hand);
        String kicker;
        if (!Card.getCardRankHexStringValue(hand.getCardByIndex(0)).equals(fourOfAKindHexStringValue)) {
            kicker = Card.getCardRankHexStringValue(hand.getCardByIndex(0));
        } else kicker = Card.getCardRankHexStringValue(hand.getCardByIndex(4));
        return Integer.parseInt(fourOfAKindHexStringValue + kicker, 16);
    }

    private int encodeFullHouse(Hand hand) {
        String threeOfAKindHexStringValue = findThreeOfAKindHexStringValue(hand);
        String pairHexValue = findPairHexStringValueForFullHouse(hand, threeOfAKindHexStringValue);
        return Integer.parseInt(threeOfAKindHexStringValue + pairHexValue, 16);
    }

    private int encodeStraight(Hand hand) {
        return encodeFlashOrHighCard(hand);
    }

    private int encodeThreeOfAKind(Hand hand) {
        String threeOfAKindHexStringValue = findThreeOfAKindHexStringValue(hand);
        String[] parts = new String[2];
        for (int i = 0, j = 0; i < 5; i++) {
            String currentCardRankHexStringValue = Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            if (!currentCardRankHexStringValue.equals(threeOfAKindHexStringValue)) {
                parts[j++] = currentCardRankHexStringValue;
            }
        }
        return Integer.parseInt(threeOfAKindHexStringValue + parts[0] + parts[1], 16);
    }

    private int encodeTwoPair(Hand hand) {
        List<String> pairHexStringValues = findTwoPairHexStringValues(hand);
        for (int i = 0; i < 5; i++) {
            String currentCardRankHexStringValue = Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            if (!pairHexStringValues.contains(currentCardRankHexStringValue)) {
                return Integer.parseInt(pairHexStringValues.get(0) + pairHexStringValues.get(1) + currentCardRankHexStringValue, 16);
            }
        }
        return 0;
    }

    private int encodePair(Hand hand) {
        String pairHexValue = findPairHexStringValue(hand);
        String[] parts = new String[3];
        for (int i = 0, j = 0; i < 5; i++) {
            String currentCardRankHexStringValue = Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            if (!currentCardRankHexStringValue.equals(pairHexValue)) {
                parts[j] = currentCardRankHexStringValue;
                j++;
            }
        }
        return Integer.parseInt(pairHexValue + parts[0] + parts[1] + parts[2], 16);
    }

    private int encodeFlashOrHighCard(Hand hand) {
        String hexCodeStr;
        hexCodeStr = Card.getCardRankHexStringValue(hand.getCardByIndex(0)) +
                Card.getCardRankHexStringValue(hand.getCardByIndex(1)) +
                Card.getCardRankHexStringValue(hand.getCardByIndex(2)) +
                Card.getCardRankHexStringValue(hand.getCardByIndex(3)) +
                Card.getCardRankHexStringValue(hand.getCardByIndex(4));
        return Integer.parseInt(hexCodeStr, 16);
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

    private boolean isPair(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                return true;
            }
        }
        return false;
    }

    private String findFourOfAKindHexStringValue(Hand hand) {
        return hand.getCardByIndex(0).getRank().equals(hand.getCardByIndex(1).getRank()) ?
                Card.getCardRankHexStringValue(hand.getCardByIndex(0)) :
                Card.getCardRankHexStringValue(hand.getCardByIndex(2));
    }

    public String findThreeOfAKindHexStringValue(Hand hand) {
        for (int i = 0; i < 3; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 2).getRank())) {
                return Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            }
        }
        return "0x0";
    }

    private String findPairHexStringValue(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                return Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            }
        }
        return "0x0";
    }

    private String findPairHexStringValueForFullHouse(Hand hand, String threeOfAKindValue) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank()) &&
                    !Card.getCardRankHexStringValue(hand.getCardByIndex(i)).equals(threeOfAKindValue)) {
                return Card.getCardRankHexStringValue(hand.getCardByIndex(i));
            }
        }
        return "0x0";
    }

    private List<String> findTwoPairHexStringValues(Hand hand) {
        List<String> twoPairValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank().equals(hand.getCardByIndex(i + 1).getRank())) {
                twoPairValues.add(Card.getCardRankHexStringValue(hand.getCardByIndex(i)));
                i++;
            }
        }
        return twoPairValues;
    }
}
