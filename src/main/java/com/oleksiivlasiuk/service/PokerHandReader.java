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
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        SourceFileValidator.validateSourceFile(lines);

        List<Hand> hands = new ArrayList<>();
        for (String line : lines) {
            hands.add(parseHandFromString(line));
        }
        return hands;
    }

    public Card parseCardFromString(String cardString) {
        String rank = cardString.substring(0, cardString.length() - 1);
        String suit = cardString.substring(cardString.length() - 1);
        int rankValue = Card.getRankHexValue(rank);
        int suitValue = Card.getSuitHexValue(suit);
        return new Card(rankValue, suitValue);
    }

    public Hand parseHandFromString(String handString) {
        List<String> cardStrings = List.of(handString.split(" "));
        List<Card> cards = new ArrayList<>();
        for (String cardString : cardStrings) {
            cards.add(parseCardFromString(cardString));
        }
        return new Hand(cards);
    }
}
