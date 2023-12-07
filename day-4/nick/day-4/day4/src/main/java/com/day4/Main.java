package com.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int res = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-4\\n" + //
                "ick\\day-4\\day4\\src\\main\\resources\\input.txt"))) {
            String line;
            Map<Integer, List<String[]>> cardMap = new HashMap<>();
            Map<Integer, Integer> cardCopies = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] firstSplit = line.split(":");
                String[] cardSplit = firstSplit[0].split("\\s+");
                String[] secondSplit = firstSplit[1].split("\\|");
                String[] winningNums = secondSplit[0].trim().split("\\s+");
                String[] cardNums = secondSplit[1].trim().split("\\s+");
                int currCardNum = Integer.parseInt(cardSplit[1]);
                cardCopies.put(currCardNum, 1);
                List<String[]> cardNumList = List.of(winningNums, cardNums);
                cardMap.put(currCardNum, cardNumList);
            }

            for (Map.Entry<Integer, List<String[]>> entry : cardMap.entrySet()) {
                List<String[]> cardList = entry.getValue();
                copiesWon(cardList.get(0), cardList.get(1), entry.getKey(), cardCopies);
            }

            for (Map.Entry<Integer, Integer> entry : cardCopies.entrySet()) {
                res += entry.getValue();
            }

            System.out.println("Answer: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void copiesWon(String[] winningNums, String[] cardNums, int currCardNum, Map<Integer, Integer> cardCopies) {
        int count = 0;
        Map<String, Integer> freq = new HashMap<>();
        for (String s : winningNums) {
            freq.put(s, freq.getOrDefault(s, 0) + 1);
        }

        for (String s : cardNums) {
            if (freq.containsKey(s)) {
                freq.put(s, freq.get(s) - 1);
            }
        }

        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 0) {
                count++;
            }
        }

        for (int i = 1; i <= count; i++) {
            int currCardCopies = cardCopies.get(currCardNum);
            cardCopies.put(currCardNum + i, cardCopies.get(currCardNum + i) + currCardCopies);
        }
    }
}