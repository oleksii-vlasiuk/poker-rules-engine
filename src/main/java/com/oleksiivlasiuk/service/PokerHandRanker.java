package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.model.Hand;
import com.oleksiivlasiuk.util.HandComparator;

import java.util.List;

public class PokerHandRanker {
    public List<Hand> rankHands(List<Hand> hands) {
        hands.sort(new HandComparator().reversed());
        return hands;
    }
}