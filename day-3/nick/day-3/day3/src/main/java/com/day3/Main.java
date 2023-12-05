package com.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-3\\nick\\day-3\\day3\\src\\main\\resources\\input.txt";
        // iterate and populate a map containing symbol positions
        HashMap<Integer, List<Integer>> symbolPositions = new HashMap<Integer, List<Integer>>();
        // <row number, list<positionsOfSymbols>>
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int rowNumber = 0;
            while (line != null) {

                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    if (!Character.isDigit(currentChar) && currentChar != '.') {
                        // char is symbol, put into hashmap for the next line
                        addValue(symbolPositions, rowNumber, i);
                    }
                }
                rowNumber++;
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);
                    List<Integer> thisRowSymbols = symbolPositions.get(1);
                    for (Integer val : thisRowSymbols) {
                        System.out.println(val);
                    }
                    if (Character.isDigit(currentChar)) {
                        // char is number
                        int endIndex = findEndIndex(line, i);
                        String number = line.substring(i, endIndex + 1);
                        int startPosition = i;
                        int endPosition = i + number.length() - 1;

                        // System.out.println(
                        // "Number: " + number + ", Position: " + startPosition + ", EndPosition: " +
                        // endPosition);

                        if (thisRowSymbols != null) {
                            if (thisRowSymbols.contains(startPosition - 1)
                                    || (thisRowSymbols.contains(endPosition + 1))) {
                                // System.out.println(number + " is adjacent to a symbol");
                            }
                        }

                        i = endIndex;
                    }

                    // check if adjacent to a symbol

                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findEndIndex(String line, int startIndex) {
        int endIndex = startIndex;

        while (endIndex + 1 < line.length() && Character.isDigit(line.charAt(endIndex + 1))) {
            endIndex++;
        }

        return endIndex;
    }

    private static void addValue(Map<Integer, List<Integer>> map, int key, int value) {
        // If the key is not present, create a new ArrayList for it
        map.putIfAbsent(key, new ArrayList<>());

        // Add the value to the list associated with the key
        map.get(key).add(value);
    }
}