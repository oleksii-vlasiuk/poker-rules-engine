package com.oleksiivlasiuk;

import com.oleksiivlasiuk.exception.InvalidPokerHandException;
import com.oleksiivlasiuk.service.PokerHandPrinter;
import com.oleksiivlasiuk.service.PokerHandRanker;
import com.oleksiivlasiuk.service.PokerHandReader;

import java.io.IOException;

public class Main {

    private static final String INVALID_SOURCE_FILE = "Source file cannot be read. Check file availability.";
    private static final String MISSING_ARGUMENT = "Missing command line argument. Please provide a path to a source file and restart.";


    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(MISSING_ARGUMENT);
            return;
        }

        String sourceFilePath = args[0];

        PokerHandReader pokerHandReader = new PokerHandReader();
        PokerHandRanker pokerHandRanker = new PokerHandRanker();
        PokerHandPrinter pokerHandPrinter = new PokerHandPrinter();

        try {
            pokerHandPrinter.print(
                pokerHandRanker.rankHands(
                        pokerHandReader.readHandsFromFile(sourceFilePath)));

        } catch (IOException e) {
            System.err.println(INVALID_SOURCE_FILE);
            e.printStackTrace();
        } catch (InvalidPokerHandException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}