package com.oleksiivlasiuk.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private List<Card> cards;

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
        return Collections.max(cards, Comparator.comparingInt(Card::getCardRankValue));
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
