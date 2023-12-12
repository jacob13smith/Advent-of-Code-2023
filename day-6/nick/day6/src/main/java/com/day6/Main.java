package com.day6;

public class Main {
     public static void main(String[] args) {
        long time = 48876981;
        long distance = 255128811171623L;
        long result = 1;
        
        long numWays = 0;
        for(long j = 0; j <= time; j++){
            long timeHeld = j;
            long timeRemaining = time - j;
            long distCovered = timeHeld * timeRemaining;
            if(distCovered >= distance){
                numWays += 1;
            }
        }
        result *= numWays;
        System.out.println(result);
    }
}