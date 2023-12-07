package com.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Converter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import main.java.com.Converter;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-5\\nick\\day5\\src\\main\\resources\\input.txt";
        int val = 0;

        Map<String, Converter> converters = new HashMap<String, Converter>();
        List<Long> seeds = new ArrayList<Long>();
        Converter currentConverter = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                if(line.startsWith("seeds:")){
                    
                    for(String seed : line.replace("seeds:", "").trim().split(" ")){
                        seeds.add(Long.parseLong(seed));
                    }
                } else{
                    if(line.matches("^\\d.*")){
                        currentConverter.addRange(line);
                    } else {
                        String[] parts = line.split("-");
                        currentConverter = new Converter(parts[0], parts[2].replace("map:", "").trim());
                        converters.put(parts[0], currentConverter);
                        System.out.println("Desc: " + line);
                    }
                }

                long smallestValue = Long.MAX_VALUE;

                for(long seed : seeds){
                    String destination = "seed";
                    System.out.println("seed:" + seed);
                    long value = seed;
                    // while(!destination.equals("location")){
                    //     Converter currentConvert = converters.get(destination);
                    //     //destination = currentConvert.getDestination();
                    //     //value = currentConvert.getDestinationValue(value);
                    // }
                    if(value < smallestValue){
                        smallestValue = value;
                    }
                }

                System.out.println("Value: " + smallestValue);
                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}