package com.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Math;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Map<String, String[]> inputMap = new HashMap<>();

    public static void main(String[] args) {

        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-8\\nick\\day8\\src\\main\\resources\\input.txt";
        Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
        ArrayList<Character> instructions = new ArrayList<Character>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the first line and store it in a char array
            String instructionsLine = reader.readLine();
            char[] instructionsArr = instructionsLine.toCharArray();
            for (char c : instructionsArr) {
                instructions.add(c);
            }

            // Assuming you want to iterate through the next lines after the instructions
            String line;
            while ((line = reader.readLine()) != null) {
                // Match the pattern against the input string
                Matcher matcher = pattern.matcher(line);

                // Check if the pattern is found
                if (matcher.matches()) {
                    String currentStep = matcher.group(1);
                    String leftChoice = matcher.group(2);
                    String rightChoice = matcher.group(3);

                    // populate inputmap
                    inputMap.put(currentStep, new String[] { leftChoice, rightChoice });

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String[]> entry : inputMap.entrySet()) {
            String key = entry.getKey();
            String value1 = entry.getValue()[0];
            String value2 = entry.getValue()[1];
            //System.out.println("Key: " + key + ", Value: " + value1 + ", " + value2);
        }

        String nextStep = "KNA";
        int instructionIdx = 0;
        int numberOfSteps = 0;
        while (!nextStep.endsWith("Z")) {
            if (instructionIdx > instructions.size() - 1) {
                instructionIdx = 0;
            }
            char currentInstruction = instructions.get(instructionIdx);
            if (currentInstruction == 'R') {
                // get right choice
                nextStep = inputMap.get(nextStep)[1];
                instructionIdx++;
            } else if (currentInstruction == 'L') {
                // get left choice
                nextStep = inputMap.get(nextStep)[0];
                instructionIdx++;
            }
            numberOfSteps++;
        }
        System.out.println(numberOfSteps);
        System.out.println(LCM(284793035507L, 13207));
    }

    /**
     * Calculate Lowest Common Multiplier
     */
    public static long LCM(long a, long b) {
        return (a * b) / GCF(a, b);
    }

    /**
     * Calculate Greatest Common Factor
     */
    public static long GCF(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (GCF(b, a % b));
        }
    }

}