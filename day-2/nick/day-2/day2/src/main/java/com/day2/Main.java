package com.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-2\\n" + //
                "ick\\day-2\\day2\\src\\main\\resources\\input.txt";
        int numReds = 12;
        int numGreens = 13;
        int numBlues = 14;
        int total = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                Pattern pattern = Pattern.compile("Game (\\d+):");
                Matcher matcher = pattern.matcher(line);

                int gameNumber = 0;
                if (matcher.find()) {
                    gameNumber = Integer.parseInt(matcher.group(1));
                }

                int colonIndex = line.indexOf(": ");
                String modifiedString = line.substring(colonIndex + 2);
                String[] games = modifiedString.split("; ");
                int highestRed = 0;
                int highestGreen = 0;
                int highestBlue = 0;
                for (String game : games) {
                    String[] counts = game.split(", ");

                    // Variables to store counts for red, green, and blue
                    int redCount = 0;
                    int greenCount = 0;
                    int blueCount = 0;

                    // Parse the counts for red, green, and blue
                    for (String count : counts) {
                        String[] parts = count.split(" ");
                        int value = Integer.parseInt(parts[0]);

                        if (count.contains("red")) {
                            redCount = value;
                        } else if (count.contains("green")) {
                            greenCount = value;
                        } else if (count.contains("blue")) {
                            blueCount = value;
                        }
                    }

                    // Update the highest counts
                    if (redCount > highestRed) {
                        highestRed = redCount;
                    }
                    if (greenCount > highestGreen) {
                        highestGreen = greenCount;
                    }
                    if (blueCount > highestBlue) {
                        highestBlue = blueCount;
                    }
                }

                System.out.println(line);
                System.out.println("Game Number: " + gameNumber);
                System.out.println("Red: " + highestRed + " | Blue: " + highestBlue + " | Green: " + highestGreen);

                if(highestBlue <= numBlues && highestGreen <= numGreens && highestRed <= numReds){
                    total += gameNumber;
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total: " + total);
    }
}