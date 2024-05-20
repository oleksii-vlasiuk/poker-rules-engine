package com.oleksiivlasiuk.model;

public class Card {
    private final int rank;
    private final int suit;

//    public Card(String rank, String suit) {
//        this.rank = rank;
//        this.suit = suit;
//    }

    public Card(int rankHexValue, int suitHexValue) {
        this.rank = rankHexValue;
        this.suit = suitHexValue;
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

    public static int getRankHexValue(String rank) {
        return switch (rank) {
            case "2" -> 0x2;
            case "3" -> 0x3;
            case "4" -> 0x4;
            case "5" -> 0x5;
            case "6" -> 0x6;
            case "7" -> 0x7;
            case "8" -> 0x8;
            case "9" -> 0x9;
            case "10" -> 0xA;
            case "J" -> 0xB;
            case "Q" -> 0xC;
            case "K" -> 0xD;
            //case "A" -> 0xE;
            default -> 0xE;
        };
    }

    public static String getRankStringValue(int rank) {
        return switch (rank) {
            case 0x2 -> "2";
            case 0x3 -> "3";
            case 0x4 -> "4";
            case 0x5 -> "5";
            case 0x6 -> "6";
            case 0x7 -> "7";
            case 0x8 -> "8";
            case 0x9 -> "9";
            case 0xA -> "10";
            case 0xB -> "J";
            case 0xC -> "Q";
            case 0xD -> "K";
            //case "A" -> 0xE;
            default -> "A";
        };
    }

    public static int getSuitHexValue(String suit) {
        return switch (suit) {
            case "♠" -> 0x1;
            case "♥" -> 0x2;
            case "♦" -> 0x3;
            //case "♣" -> 0x4;
            default -> 0x4;
        };
    }

    public static String getSuitStringValue(int suit) {
        return switch (suit) {
            case 0x1 -> "♠";
            case 0x2 -> "♥";
            case 0x3 -> "♦";
            //case "♣" -> 0x4;
            default -> "♣";
        };
    }

//    public static int getCardRankValue(Card card) {
//        return switch (card.getRank()) {
//            case "2" -> 2;
//            case "3" -> 3;
//            case "4" -> 4;
//            case "5" -> 5;
//            case "6" -> 6;
//            case "7" -> 7;
//            case "8" -> 8;
//            case "9" -> 9;
//            case "10" -> 10;
//            case "J" -> 11;
//            case "Q" -> 12;
//            case "K" -> 13;
//            //case "A" -> 14;
//            default -> 14;
//        };
//    }

//    public static String getCardRankHexStringValue(Card card) {
//        return switch (card.getRank()) {
//            case "2" -> "2";
//            case "3" -> "3";
//            case "4" -> "4";
//            case "5" -> "5";
//            case "6" -> "6";
//            case "7" -> "7";
//            case "8" -> "8";
//            case "9" -> "9";
//            case "10" -> "A";
//            case "J" -> "B";
//            case "Q" -> "C";
//            case "K" -> "D";
//            //case "A" -> "E";
//            default -> "E";
//        };
//    }

//    public static int getCardRankHexValue(Card card) {
//        return getRankHexValue(card.getRank());
//    }

    @Override
    public String toString() {
        return getRankStringValue(rank) + getSuitStringValue(suit);
    }
}
