package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HandEvaluator {
    public int evaluateCombinationStrength(Hand hand) {
        List<Card> handCards = new ArrayList<>(hand.getCards());
        handCards.sort(Comparator.comparingInt(Card::getCardRankValue));
        Hand sortedCardsHand = new Hand(handCards);

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
        Hand reversedHand = new Hand(hand.getCards().reversed());
        for (int i = 0; i < 4; i++) {
            if (Card.getCardRankValue(reversedHand.getCardByIndex(i)) - Card.getCardRankValue(reversedHand.getCardByIndex(i + 1)) != 1) {
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
