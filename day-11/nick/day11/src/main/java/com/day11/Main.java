package com.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "C:\\Users\\nickh\\OneDrive\\Desktop\\Code\\AdventOfCode\\Advent-of-Code-2023\\day-11\\nick\\day11\\src\\main\\resources\\input.txt";
        // Define the size of the 2D array based on the number of rows and columns in
        // the input
        int numRows = 10;
        int numCols = 10;

        // Create a 2D array
        char[][] array2D = new char[numRows][numCols];

        // Read the input file and populate the array
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                // Populate the array with characters from the line
                for (int col = 0; col < numCols; col++) {
                    array2D[row][col] = line.charAt(col);
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // expand array
        expandArray(array2D);

    }

    public static void expandArray(char[][] grid) {
        int gridRows = grid.length;
        ArrayList<Integer> rowsToAdd = new ArrayList<Integer>();

        for (int i = 0; i < gridRows; i++) {
            ArrayList<Character> rowList = new ArrayList<Character>();
            for (char c : grid[i]) {
                rowList.add(c);
            }
            if (!rowList.contains('#')) {
                System.out.println("Line on row " + i);
                rowsToAdd.add(i);
            }
        }

        int gridCols = grid[0].length;
        ArrayList<Integer> colsToAdd = new ArrayList<Integer>();

        for (int col = 0; col < gridCols; col++) {
            ArrayList<Character> colList = new ArrayList<Character>();
            for (int row = 0; row < gridRows; row++) {
                colList.add(grid[row][col]);
            }
            if (!colList.contains('#')) {
                System.out.println("Line on col " + col);
                colsToAdd.add(col);
            }
        }

        int newRowLen = gridRows + rowsToAdd.size();
        int newColLen = gridCols + colsToAdd.size();
        char[][] newGrid = duplicateRowsAndColumns(grid, rowsToAdd, colsToAdd);

        for(int i = 0; i < newGrid.length; i++){
            for(int j = 0; j < newGrid[0].length; j++){
                //System.out.print(newGrid[i][j]);
            }
        }

    }

    public static char[][] duplicateRowsAndColumns(char[][] inputArray, List<Integer> rowsToAdd, List<Integer> colsToAdd) {
        int numRows = inputArray.length;
        int numCols = inputArray[0].length;

        List<char[]> resultRows = new ArrayList<>();

        // Duplicate rows
        for (int row = 0; row < numRows; row++) {
            char[] currentRow = inputArray[row];

            if (rowsToAdd.contains(row)) {
                System.out.println("Adding Dupe Row: " + row);
                resultRows.add(currentRow.clone());  // Clone the row if it needs to be duplicated
            }

            resultRows.add(currentRow);
        }
        List<char[]> newRows = new ArrayList<>();

        for(int i = 0; i < resultRows.size(); i++){
            char[] currentRow = resultRows.get(i);
            
            for(int j = 0; j < resultRows.get(0).length + colsToAdd.size(); j++){
            }
            for(char c : rowWithNewCols){
                System.out.print(c);
            }
            System.out.println();
        }



        //char[][] resultArray = new char[resultRows.size()][newNumCols];
        return null;
    }
}