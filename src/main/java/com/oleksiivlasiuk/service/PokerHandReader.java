package com.oleksiivlasiuk.service;

import com.oleksiivlasiuk.exception.InvalidPokerHandException;
import com.oleksiivlasiuk.model.Card;
import com.oleksiivlasiuk.model.Hand;
import com.oleksiivlasiuk.util.SourceFileValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PokerHandReader {

    public List<Hand> readHandsFromFile(String path) throws IOException, InvalidPokerHandException {
        SourceFileValidator sourceFileValidator = new SourceFileValidator();

        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        sourceFileValidator.validateSourceFile(lines);

        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            hands.add(parseHand(line));
        }
        return hands;
    }

    public Card parseCard(String card) {
        String rank = card.substring(0, card.length() - 1);
        String suit = card.substring(card.length() - 1);
        return new Card(rank, suit);
    }

    public Hand parseHand(String hand) {
        List<String> cardsNames = List.of(hand.split(" "));
        List<Card> cards = new ArrayList<>();
        for (String cardName : cardsNames) {
            cards.add(parseCard(cardName));
        }
        return new Hand(cards);
    }
}
