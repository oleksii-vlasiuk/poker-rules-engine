package com.oleksiivlasiuk;

import com.oleksiivlasiuk.exception.InvalidPokerHandException;
import com.oleksiivlasiuk.service.PokerHandPrinter;
import com.oleksiivlasiuk.service.PokerHandRanker;
import com.oleksiivlasiuk.service.PokerHandReader;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String INVALID_SOURCE_FILE = "Source file cannot be read. Check file availability.";
    private static final String MISSING_ARGUMENT = "Missing command line argument. Please provide a path to a source file.";
    private static final String UNEXPECTED_ERROR = "An unexpected error occurred: ";
    private static final String HAND_RANKING_ERROR = "A concurrency error occurred during hand ranking: ";


    public static void main(String[] args) {

        if (args.length == 0) {
            LOGGER.log(Level.SEVERE, MISSING_ARGUMENT);
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
            LOGGER.log(Level.SEVERE, INVALID_SOURCE_FILE);
        } catch (InvalidPokerHandException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, HAND_RANKING_ERROR + e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, UNEXPECTED_ERROR, e.getMessage());
        }
    }
}