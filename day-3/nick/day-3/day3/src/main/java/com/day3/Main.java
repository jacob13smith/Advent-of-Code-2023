package com.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-3\\nick\\day-3\\day3\\src\\main\\resources\\input.txt";
        int total = 0;
        try {
            Scanner scanner = new Scanner(new File(filePath));
            StringBuilder inputBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                inputBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            String[] inputLines = inputBuilder.toString().trim().split("\n");

            int numRows = inputLines.length;
            int numCols = inputLines[0].length();

            char[][] array2D = new char[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    array2D[i][j] = inputLines[i].charAt(j);
                }
            }

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (!Character.isDigit(array2D[i][j]) && array2D[i][j] != '.') {
                        int tempTotal = searchSurroundings(array2D, i, j);
                        // System.out.println("Temp total for " + i + "," + j + ": " + tempTotal);
                        total += tempTotal;
                    }
                }
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        System.out.println(total);
    }

    public static Integer searchSurroundings(char[][] array2D, int row, int col) {
        System.out.println("Symbol at: " + row + ", " + col);
        int symTotal = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip out-of-bounds positions
                if (i >= 0 && i < array2D.length && j >= 0 && j < array2D[0].length) {
                    // Skip the center point itself
                    if (!(i == row && j == col)) {
                        char value = array2D[i][j];
                        if (Character.isDigit(value)) {
                            String number = extractNumberAtPosition2D(array2D, i, j);
                            System.out.println(number);
                            symTotal += Integer.valueOf(number);
                            // set number to not exist in the array anymore

                        }
                    }
                }
            }
        }
        return symTotal;
    }

    public static String extractNumberAtPosition2D(char[][] input, int row, int col) {
        // Initialize indices for iterating in both directions
        int startIndex = col;
        int endIndex = col;

        // Iterate to the left to find the start of the number
        while (startIndex >= 0 && Character.isDigit(input[row][startIndex])) {
            startIndex--;
        }

        // Iterate to the right to find the end of the number
        while (endIndex < input[row].length && Character.isDigit(input[row][endIndex])) {
            endIndex++;
        }

        // Extract the number
        String number = new String(input[row], startIndex + 1, endIndex - startIndex - 1);

        for (int i = startIndex+1; i < endIndex; i++) {
            if(Character.isDigit(input[row][i])){
                input[row][i] = '.';
            }
        }

        return number;
    }

}