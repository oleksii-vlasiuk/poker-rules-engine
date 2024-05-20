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
        int fourOfAKindValue = findFourOfAKindValue(hand);
        int kicker;
        if (hand.getCardByIndex(0).getRank() != fourOfAKindValue) {
            kicker = hand.getCardByIndex(0).getRank();
        } else {
            kicker = hand.getCardByIndex(4).getRank();
        }
        StringBuffer hexCode = new StringBuffer();
        hexCode.append(Integer.toHexString(fourOfAKindValue));
        hexCode.append(Integer.toHexString(kicker));
        return Integer.parseInt(hexCode.toString(), 16);
    }

    private int encodeFullHouse(Hand hand) {
        int threeOfAKindValue = findThreeOfAKindValue(hand);
        int pairValue = findPairValueForFullHouse(hand, threeOfAKindValue);
        StringBuffer hexCode = new StringBuffer();
        hexCode.append(Integer.toHexString(threeOfAKindValue));
        hexCode.append(Integer.toHexString(pairValue));
        return Integer.parseInt(hexCode.toString(), 16);
    }

    private int encodeStraight(Hand hand) {
        return encodeFlashOrHighCard(hand);
    }

    private int encodeThreeOfAKind(Hand hand) {
        int threeOfAKindValue = findThreeOfAKindValue(hand);
        int[] parts = new int[2];
        for (int i = 0, j = 0; i < 5; i++) {
            int currentCardRankValue = hand.getCardByIndex(i).getRank();
            if (currentCardRankValue != threeOfAKindValue) {
                parts[j++] = currentCardRankValue;
            }
        }
        StringBuffer hexCode = new StringBuffer();
        hexCode.append(Integer.toHexString(threeOfAKindValue));
        hexCode.append(Integer.toHexString(parts[0]));
        hexCode.append(Integer.toHexString(parts[1]));
        return Integer.parseInt(hexCode.toString(), 16);
    }

    private int encodeTwoPair(Hand hand) {
        List<Integer> pairHexStringValues = findTwoPairValues(hand);
        for (int i = 0; i < 5; i++) {
            int currentCardRankValue = hand.getCardByIndex(i).getRank();
            if (!pairHexStringValues.contains(currentCardRankValue)) {
                StringBuffer hexCode = new StringBuffer();
                hexCode.append(Integer.toHexString(pairHexStringValues.get(0)));
                hexCode.append(Integer.toHexString(pairHexStringValues.get(1)));
                hexCode.append(Integer.toHexString(currentCardRankValue));
                return Integer.parseInt(hexCode.toString(), 16);
            }
        }
        return 0x0;
    }

    private int encodePair(Hand hand) {
        String pairHexValue = Integer.toHexString(findPairValue(hand));
        String[] parts = new String[3];
        for (int i = 0, j = 0; i < 5; i++) {
            int currentCardRankValue = hand.getCardByIndex(i).getRank();
            int pairValue = findPairValue(hand);
            if (currentCardRankValue != pairValue) {
                parts[j] = Integer.toHexString(currentCardRankValue);
                j++;
            }
        }
        StringBuffer hexCode = new StringBuffer();
        hexCode.append(pairHexValue);
        hexCode.append(parts[0]);
        hexCode.append(parts[1]);
        hexCode.append(parts[2]);
        return Integer.parseInt(hexCode.toString(), 16);
    }

    private int encodeFlashOrHighCard(Hand hand) {
        StringBuffer hexCode = new StringBuffer();
        hexCode.append(Integer.toHexString(hand.getCardByIndex(0).getRank()));
        hexCode.append(Integer.toHexString(hand.getCardByIndex(1).getRank()));
        hexCode.append(Integer.toHexString(hand.getCardByIndex(2).getRank()));
        hexCode.append(Integer.toHexString(hand.getCardByIndex(3).getRank()));
        hexCode.append(Integer.toHexString(hand.getCardByIndex(4).getRank()));
        return Integer.parseInt(hexCode.toString(), 16);
    }

    private boolean isRoyalFlush(Hand hand) {;
        return isFlush(hand) && hand.getHighestCard().getRank() == 0xE;
    }

    private boolean isStraightFlush(Hand hand) {
        return isFlush(hand) && isStraight(hand);
    }

    private boolean isFourOfAKind(Hand hand) {
        return hand.getCardByIndex(0).getRank() == hand.getCardByIndex(3).getRank() ||
                hand.getCardByIndex(1).getRank() == hand.getCardByIndex(4).getRank() ;
    }

    private boolean isFullHouse(Hand hand) {
        return  (hand.getCardByIndex(0).getRank() == hand.getCardByIndex(1).getRank() && hand.getCardByIndex(2).getRank() == hand.getCardByIndex(4).getRank()) ||
                (hand.getCardByIndex(0).getRank() == hand.getCardByIndex(2).getRank() && hand.getCardByIndex(3).getRank() == hand.getCardByIndex(4).getRank());
    }

    private boolean isFlush(Hand hand) {
        int suit = hand.getCardByIndex(0).getSuit();
        for (Card card : hand.getCards()) {
            if (card.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }

    private boolean isStraight(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank() - hand.getCardByIndex(i + 1).getRank() != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isThreeOfAKind(Hand hand) {
        return  hand.getCardByIndex(0).getRank() == hand.getCardByIndex(2).getRank() ||
                hand.getCardByIndex(1).getRank() == hand.getCardByIndex(3).getRank() ||
                hand.getCardByIndex(2).getRank() == hand.getCardByIndex(4).getRank();
    }

    private boolean isTwoPair(Hand hand) {
        return  (hand.getCardByIndex(0).getRank() == hand.getCardByIndex(1).getRank() && hand.getCardByIndex(2).getRank() == hand.getCardByIndex(3).getRank()) ||
                (hand.getCardByIndex(0).getRank() == hand.getCardByIndex(1).getRank() && hand.getCardByIndex(3).getRank() == hand.getCardByIndex(4).getRank()) ||
                (hand.getCardByIndex(1).getRank() == hand.getCardByIndex(2).getRank() && hand.getCardByIndex(3).getRank() == hand.getCardByIndex(4).getRank());
    }

    private boolean isPair(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank() == hand.getCardByIndex(i + 1).getRank()) {
                return true;
            }
        }
        return false;
    }

    private int findFourOfAKindValue(Hand hand) {
        return hand.getCardByIndex(0).getRank() == hand.getCardByIndex(1).getRank() ?
                hand.getCardByIndex(0).getRank() :
                hand.getCardByIndex(2).getRank();
    }

    public int findThreeOfAKindValue(Hand hand) {
        for (int i = 0; i < 3; i++) {
            if (hand.getCardByIndex(i).getRank() == hand.getCardByIndex(i + 2).getRank()) {
                return hand.getCardByIndex(i).getRank();
            }
        }
        return 0x0;
    }

    private int findPairValue(Hand hand) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank() == hand.getCardByIndex(i + 1).getRank()) {
                return hand.getCardByIndex(i).getRank();
            }
        }
        return 0x0;
    }

    private int findPairValueForFullHouse(Hand hand, int threeOfAKindValue) {
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank() == hand.getCardByIndex(i + 1).getRank() &&
                    hand.getCardByIndex(i).getRank() != threeOfAKindValue) {
                return hand.getCardByIndex(i).getRank();
            }
        }
        return 0x0;
    }

    private List<Integer> findTwoPairValues(Hand hand) {
        List<Integer> twoPairValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (hand.getCardByIndex(i).getRank() == hand.getCardByIndex(i + 1).getRank()) {
                twoPairValues.add(hand.getCardByIndex(i).getRank());
                i++;
            }
        }
        return twoPairValues;
    }
}
