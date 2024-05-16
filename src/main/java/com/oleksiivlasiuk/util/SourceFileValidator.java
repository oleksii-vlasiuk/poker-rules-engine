package com.oleksiivlasiuk.util;


import com.oleksiivlasiuk.exception.InvalidPokerHandException;

import java.util.List;
import java.util.regex.Pattern;

public class SourceFileValidator {

    private static final String HAND_REGEX = "^((10|[2-9JQKA])[♥♦♠♣]\\s){4}(10|[2-9JQKA])[♥♦♠♣]$";
    private static final Pattern PATTERN = Pattern.compile(HAND_REGEX);
    private static final String EMPTY_FILE_ERROR = "File is empty.";
    private static final String INVALID_FORMAT_ERROR = "File doesn't match the requirements.";

    public void validateSourceFile(List<String> lines) throws InvalidPokerHandException {
        if (lines == null || lines.isEmpty()) {
            throw new InvalidPokerHandException(EMPTY_FILE_ERROR);
        }
        for (String line : lines) {
            if (!PATTERN.matcher(line).matches()) {
                throw new InvalidPokerHandException(INVALID_FORMAT_ERROR);
            }
        }
    }
}
