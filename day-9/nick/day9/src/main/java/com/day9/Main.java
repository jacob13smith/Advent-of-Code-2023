package com.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-9\\nick\\day9\\src\\main\\resources\\input.txt";
        int result = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                ArrayList<Integer> values = new ArrayList<Integer>();
                ArrayList<Integer> differences = new ArrayList<>();
                ArrayList<Integer> endValues = new ArrayList<>();
                String[] stringVals = line.split(" ");

                // populate values from string input
                for (String val : stringVals) {
                    int intVal = Integer.parseInt(val);
                    values.add(intVal);
                }

                endValues.add(values.get(values.size() - 1));
                
                differences = calculateDifferences(values);

                boolean loopCanTerminate = false;

                while(!loopCanTerminate){
                    endValues.add(differences.get(differences.size() - 1));
                    differences = calculateDifferences(differences);
                    if(!checkForNonZeroes(differences)){
                        loopCanTerminate = true;
                    }
                }

                int numToAdd = 0;
                for(int i = endValues.size() - 1; i >= 0; i--){
                    numToAdd += endValues.get(i);
                }
                result += numToAdd;
                line = reader.readLine();
            }

            System.out.println(result);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkForNonZeroes(ArrayList<Integer> differences) {
        boolean containsNonZero = false;
        for (int value : differences) {
            if (value != 0) {
                containsNonZero = true;
                break;
            }
        }
        return containsNonZero;
    }

    // populate differences arraylist
    public static ArrayList<Integer> calculateDifferences(ArrayList<Integer> input) {
        ArrayList<Integer> tempDiffs = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            // for each value in values, calculate
            int diff = input.get(i + 1) - input.get(i);
            tempDiffs.add(diff);
        }
        return tempDiffs;
    }
}