package com.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Main {
    static UniqueElementList possiblePositionLists = new UniqueElementList();

    public static void main(String[] args) {

        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-12\\n" + //
                "ick\\day12\\src\\main\\resources\\input.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Operational'.' Damaged'#' unknown'?'

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String springRow = parts[0];
                String orders = parts[1];
                String[] orderNumberStrings = orders.split(",");
                ArrayList<Integer> orderNumbers = new ArrayList<Integer>();
                for (String ch : orderNumberStrings) {
                    orderNumbers.add(Integer.parseInt(ch));
                }

                int result = countCombinations(springRow, orderNumbers);

                System.out.println("Result: " + result);

                // System.out.println(possiblePositionLists.getMainList());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countCombinations(String input, ArrayList<Integer> arrangementsToMake) {
        // Create a list to store the positions of '?'
        List<Integer> openPositions = new ArrayList<>();
        List<Integer> allDamagedPositions = new ArrayList<>();

        // Find the positions of '?'
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '?') {
                openPositions.add(i);
                allDamagedPositions.add(i);
                System.out.println("? at " + i);
            } else if (input.charAt(i) == '#') {
                allDamagedPositions.add(i);
                System.out.println("# at " + i);
            }
        }

        for (int i = 0; i < allDamagedPositions.size(); i++) {
            int damagedPosition = allDamagedPositions.get(i);
            System.out.println("Damaged or potential damaged at: " + damagedPosition);
            List<Integer> result = getContiguousSetIndices(allDamagedPositions, i);
            possiblePositionLists.add(result);
        }

        // Create a list to store the generated combinations
        List<String> combinations = new ArrayList<>();

        for (List<Integer> list : possiblePositionLists.getMainList()) {
            if (list.size() > 1) {
                System.out.println("==========");
                System.out.println(list);

                // Iterate over all possible positions for the first 'O'
                for (int i = 0; i < list.size() - 1; i++) {
                    // Iterate over all possible positions for the second 'O'
                    for (int j = i + 1; j < list.size(); j++) {
                        System.out.println(i + " : " + j);
                        // Check if the 'O's are not next to each other
                        if (list.get(j) != list.get(i) + 1) {
                            System.out.println("AA");
                            // Create a combination with 'O' at the specified positions and '.' elsewhere
                            StringBuilder combination = new StringBuilder(input);
                            combination.setCharAt(list.get(i), 'O');
                            combination.setCharAt(list.get(j), 'O');
                            combinations.add(combination.toString());
                        }
                    }
                }

                System.out.println("==========");
            } else {
                // //only one contiguous open position

                StringBuilder combination = new StringBuilder(input);
                combination.setCharAt(openPositions.get(0), 'O');
                combinations.add(combination.toString());
            }
        }

        // // Iterate over all possible positions for the first 'O'
        // for (int i = 0; i < openPositions.size() - 1; i++) {
        // // Iterate over all possible positions for the second 'O'
        // for (int j = i + 1; j < openPositions.size(); j++) {
        // // Check if the 'O's are not next to each other
        // if (openPositions.get(j) != openPositions.get(i) + 1) {
        // // Create a combination with 'O' at the specified positions and '.' elsewhere
        // StringBuilder combination = new StringBuilder(input);
        // combination.setCharAt(openPositions.get(i), 'O');
        // combination.setCharAt(openPositions.get(j), 'O');
        // combinations.add(combination.toString());
        // }
        // }
        // }

        // Print the generated combinations (optional)
        System.out.println("Generated combinations: " + combinations);

        // Return the number of generated combinations
        return combinations.size();
    }

    public static List<Integer> getContiguousSetIndices(List<Integer> numbers, int index) {
        List<Integer> contiguousIndices = new ArrayList<>();

        // Check if the index is valid
        if (index < 0 || index >= numbers.size()) {
            System.out.println("Invalid index.");
            return contiguousIndices;
        }

        // Determine the starting index of the contiguous set
        int startIndex = index;

        // Check for contiguous integers and store their indices
        while (startIndex < numbers.size() - 1 && numbers.get(startIndex) + 1 == numbers.get(startIndex + 1)) {
            contiguousIndices.add(startIndex);
            startIndex++;
        }

        // Add the last index of the contiguous set
        contiguousIndices.add(startIndex);

        return contiguousIndices;
    }
}