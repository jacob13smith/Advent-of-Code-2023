package com.day6;

import main.java.com.Converter;

public class Main {
     public static void main(String[] args) {
        int[] times = {48, 87, 69, 81};
        int[] distances = {255, 1288, 1117, 1623};
        int result = 1;
        for(int i = 0; i < times.length; i++){
            int numWays = 0;
            int time = times[i];
            int dist = distances[i];
            for(int j = 0; j <= time; j++){
                int timeHeld = j;
                int timeRemaining = time - j;
                int distCovered = timeHeld * timeRemaining;
                if(distCovered >= dist){
                    numWays += 1;
                }
            }
            System.out.println("NumWays for time " + i + ": " + numWays);
            result *= numWays;
        }
        System.out.println(result);
    }
}