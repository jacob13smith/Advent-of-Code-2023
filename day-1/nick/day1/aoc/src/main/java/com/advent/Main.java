package com.advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        String filePath = "D:/Coding/AdventOfCode/day1/aoc/src/main/resources/input.txt";
        Integer result = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String lineResult = translateNumber(line);
                // char[] chars = line.toCharArray();
                // for (char c : chars) {
                // if (Character.isDigit(c)) {
                // lineResult += c;
                // }
                // }
                String firstChar = String.valueOf(lineResult.charAt(0));
                String lastChar = "";
                if(lineResult.length() >= 11){
                    lastChar = String.valueOf(Long.valueOf(lineResult.substring(lineResult.length() - 10)) % 10);
                } else {
                    lastChar = String.valueOf(Long.valueOf(lineResult) % 10);
                }
                String tempAnswer = firstChar + lastChar;
                System.out.println(lineResult);
                System.out.println(tempAnswer);
                result += Integer.valueOf(tempAnswer);

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
    }

    public static String translateNumber(String input) {
        Map<String, Integer> numberMap = new HashMap<>();
        numberMap.put("one", 1);
        numberMap.put("two", 2);
        numberMap.put("three", 3);
        numberMap.put("four", 4);
        numberMap.put("five", 5);
        numberMap.put("six", 6);
        numberMap.put("seven", 7);
        numberMap.put("eight", 8);
        numberMap.put("nine", 9);
        // Create a regex pattern for matching both spelled-out numbers and integers
        String regex = "(?:" + String.join("|", numberMap.keySet()) + "|\\d+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(input);
        StringBuilder translatedStringBuilder = new StringBuilder();

        while (matcher.find()) {
            // Get the matched value
            String matchedValue = matcher.group().toLowerCase();

            // If it's a spelled-out number, translate to its integer equivalent
            if (numberMap.containsKey(matchedValue)) {
                int translatedNumber = numberMap.get(matchedValue);
                translatedStringBuilder.append(translatedNumber);
            }
            // If it's an integer, append it as is
            else if (matchedValue.matches("\\d+")) {
                translatedStringBuilder.append(matchedValue);
            }

            // Move the matcher to the next position for overlapping words
            matcher.region(matcher.start() + 1, input.length());
        }

        // Return the translated string
        return translatedStringBuilder.toString();
    }
}