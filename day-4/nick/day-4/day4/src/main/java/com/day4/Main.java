package com.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-4\\nick\\day-4\\day4\\src\\main\\resources\\input.txt";
        int result = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                int total = 0;
                // Separate the game number, numbers on the left, and numbers on the right
                String[] separatedValues = separateGameNumbers(line);

                // Print the results
                System.out.println("Game Number: " + separatedValues[0]);
                String[] winningNumbers = separatedValues[1].split("\\s+");
                String[] ownedNumbers = separatedValues[2].split("\\s+");

                // Convert arrays to sets for efficient comparison
                Set<String> winningSet = new HashSet<>(Set.of(winningNumbers));
                Set<String> ownedSet = new HashSet<>(Set.of(ownedNumbers));

                // Find common elements (intersection)
                Set<String> commonElements = new HashSet<>(winningSet);
                commonElements.retainAll(ownedSet);

                if (commonElements.size() > 0) {
                    total = 1;
                    for (int i = 1; i < commonElements.size(); i++) {
                        total *= 2; // Double for each additional common element
                    }
                }

                // Print the common elements
                System.out.println("Common Elements: " + commonElements);
                System.out.println(total);
                result += total;

                // Check if there are any common elements
                if (commonElements.isEmpty()) {
                    System.out.println("There are no common elements between numbers on the left and on the right.");
                }
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    // Method to separate game number, numbers on the left, and numbers on the right
    public static String[] separateGameNumbers(String inputLine) {
        String[] result = new String[3];

        // Split the input line based on the colon (:) to get the game number
        String[] parts = inputLine.split(":");

        // Extract and store the game number in the result array
        result[0] = parts[0].trim().replaceAll("\\D", "");

        // Split the second part (after the colon) based on the pipe character (|) to
        // get numbers on the left and right
        String[] numbers = parts[1].split("\\|");

        // Trim leading and trailing whitespaces from each part
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = numbers[i].trim();
        }

        // Extract and store the values in the result array
        result[1] = numbers[0]; // Numbers on the left
        result[2] = numbers[1]; // Numbers on the right

        return result;
    }

}