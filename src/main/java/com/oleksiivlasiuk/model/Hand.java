package com.oleksiivlasiuk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCardByIndex(int index) {
        return cards.get(index);
    }

    public Card getHighestCard() {
        return Collections.max(cards, Comparator.comparingInt(Card::getRank));
    }

    public Hand getSortedCopyDesc() {
        List<Card> handCards = new ArrayList<>(cards);
        handCards.sort(Comparator.comparingInt(Card::getRank).reversed());
        return new Hand(handCards);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(cards.get(i));
            if (i != 4) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
