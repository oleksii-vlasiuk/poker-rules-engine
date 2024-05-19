package com.oleksiivlasiuk.model;

public class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public static int getCardRankValue(Card card) {
        return switch (card.getRank()) {
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10" -> 10;
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            default -> 0;
        };
    }

    public static String getCardRankHexStringValue(Card card) {
        return switch (card.getRank()) {
            case "2" -> "2";
            case "3" -> "3";
            case "4" -> "4";
            case "5" -> "5";
            case "6" -> "6";
            case "7" -> "7";
            case "8" -> "8";
            case "9" -> "9";
            case "10" -> "A";
            case "J" -> "B";
            case "Q" -> "C";
            case "K" -> "D";
            case "A" -> "E";
            default -> "0";
        };
    }

    @Override
    public String toString() {
        return rank + suit;
    }
}
