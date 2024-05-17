package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;

public class HandEvaluator {
    public int evaluateCombinationStrength(Hand hand) {
        if (isRoyalFlush(hand)) {
            return 10;
        }
        if (isStraightFlush(hand)) {
            return 9;
        }
        if (isFourOfAKind(hand)) {
            return 8;
        }
        if (isFullHouse(hand)) {
            return 7;
        }
        if (isFlush(hand)) {
            return 6;
        }
        if (isStraight(hand)) {
            return 5;
        }
        if (isThreeOfAKind(hand)) {
            return 4;
        }
        if (isTwoPair(hand)) {
            return 3;
        }
        if (isOnePair(hand)) {
            return 2;
        }
        return 1;
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
}
