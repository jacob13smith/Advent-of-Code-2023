package com.day7;

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

public class Main {
    private static final Map<Character, Integer> cardValues = new HashMap<>();

    static {
        cardValues.put('2', 2);
        cardValues.put('3', 3);
        cardValues.put('4', 4);
        cardValues.put('5', 5);
        cardValues.put('6', 6);
        cardValues.put('7', 7);
        cardValues.put('8', 8);
        cardValues.put('9', 9);
        cardValues.put('T', 10);
        cardValues.put('J', 1);
        cardValues.put('Q', 11);
        cardValues.put('K', 12);
        cardValues.put('A', 13);
    }

    public static void main(String[] args) {

        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-7\\nick\\day7\\src\\main\\resources\\input.txt";
        ArrayList<String> hands = new ArrayList<String>();
        Map<String, Integer> handsBetsMap = new HashMap<>();
        
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                String[] values = line.split(" ");
                hands.add(values[0]); 
                handsBetsMap.put(values[0], Integer.parseInt(values[1]));

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int winnings = 0;
        Collections.sort(hands, handComparator);
        for(int i = 0; i < hands.size(); i++){
            long rank = i + 1;
            String hand = hands.get(i);
            winnings += (handsBetsMap.get(hand) * rank);
            System.out.println("Hand: " + hand + " Rank: " + rank + " winnings: " + handsBetsMap.get(hand) * rank);
        }
        System.out.println(winnings);
    }

    // Create a custom comparator for sorting hands
    public static Comparator<String> handComparator = new Comparator<String>() {
        @Override
        public int compare(String hand1, String hand2) {

            // Count occurrences of each digit in both hands
            int[] count1 = countDigits(hand1);
            int[] count2 = countDigits(hand2);

            // count highest appearance of digits in each
            int highest1 = 0;
            int highest2 = 0;
            int numPairs1 = 0;
            int numPairs2 = 0;
            int numTriples1 = 0;
            int numTriples2 = 0;
            int numJokers1 = count1[0];
            int numJokers2 = count2[0];
            int idxHighest1 = 0;
            int idxHighest2 = 0;


            //count highest multiple
            for (int i = 1; i < count1.length; i++) {
                if (count1[i] > highest1) {
                    //more multiples at count1[i]
                    highest1 = count1[i];
                    idxHighest1 = i;
                } else if(count1[i] == highest1){
                    //same number of multiples, eg: 2 4s and 2 5s
                    if(i > idxHighest1){
                        idxHighest1 = i;
                    }
                }

                if (count2[i] > highest2) {
                    highest2 = count2[i];
                    idxHighest2 = i;
                } else if(count2[i] == highest2){
                    //same number of multiples, eg: 2 4s and 2 5s
                    if(i > idxHighest2){
                        idxHighest2 = i;
                    }
                } 
            }
            //add jokers
            count1[idxHighest1] += numJokers1;
            count2[idxHighest2] += numJokers2;

            //recount multiples
            //count highest multiple
            for (int i = 1; i < count1.length; i++) {
                if (count1[i] > highest1) {
                    //more multiples at count1[i]
                    highest1 = count1[i];
                }

                if (count2[i] > highest2) {
                    highest2 = count2[i];
                }
            }

            //count number of pairs and triples
            for (int i = 1; i < count1.length; i++) {
                //pairs
                if (count1[i] == 2) {
                    numPairs1++;
                }
                if (count2[i] == 2) {
                    numPairs2++;
                }
                //triples
                if (count1[i] == 3) {
                    numTriples1++;
                }
                if (count2[i] == 3) {
                    numTriples2++;
                }
            }


            if (highest1 > highest2) {
                //1 has higher multiples than 2
                return 1;
            } else if (highest2 > highest1) {
                //2 has higher multiples than 1
                return -1;
            } else {
                // hands have same numbers of digits, compare each letter in the hands

                //compare number of triples
                if(numTriples1 > numTriples2){
                    return 1;
                } else if(numTriples2 > numTriples1){
                    return -1;
                } else {
                    //compare number of pairs
                    if(numPairs1 > numPairs2){
                        return 1;
                    } else if(numPairs2 > numPairs1){
                        return -1;
                    } else {
                        for (int i = 0; i < hand1.length(); i++) {
                            int value1 = cardValues.get(hand1.charAt(i));
                            int value2 = cardValues.get(hand2.charAt(i));
        
                            // Compare the values of the cards
                            if (value1 > value2) {
                                return 1;
                            } else if (value1 < value2) {
                                return -1;
                            }
                        }
                    }
                }
                return 1;
            }

        }

    };

    public static int[] countDigits(String hand) {
        int[] count = new int[13]; // Assuming digits 2-9, T, J, Q, K, A

        for (char digit : hand.toCharArray()) {
            if (digit >= '2' && digit <= '9') {
                count[Character.getNumericValue(digit) - 1]++;
                // System.out.println("digit: "+ digit + ": " +
                // count[Character.getNumericValue(digit) - 2]);
            } else {
                // Handle T, J, Q, K, A
                switch (digit) {
                    case 'T':
                        count[9]++;
                        break;
                    case 'J':
                        count[0]++;
                        break;
                    case 'Q':
                        count[10]++;
                        break;
                    case 'K':
                        count[11]++;
                        break;
                    case 'A':
                        count[12]++;
                        break;
                }
            }
        }

        return count;
    }
}