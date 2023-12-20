package com.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Main {
    public static List<Integer> duplicatedColumns = new ArrayList<Integer>();
    public static List<Integer> duplicatedRows = new ArrayList<Integer>();

    public static void main(String[] args) {

        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-11\\n" + //
                "ick\\day11\\src\\main\\resources\\input.txt";
        // Define the size of the 2D array based on the number of rows and columns in
        // the input
        int numRows = 140;
        int numCols = 140;

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
        char[][] expandedArr = expandArray(array2D);
        List<Point> points = findPoints(expandedArr);

        long totalDist = 0;

        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j > i && j < points.size(); j++) {
                if (i != j) {
                    long dist = shortestDistance(expandedArr, points.get(i), points.get(j));
                    totalDist += dist;
                }
            }
        }

        System.out.println("Dist: " + totalDist);
    }

    public static char[][] expandArray(char[][] grid) {
        int gridRows = grid.length;
        ArrayList<Integer> rowsToAdd = new ArrayList<Integer>();

        for (int i = 0; i < gridRows; i++) {
            ArrayList<Character> rowList = new ArrayList<Character>();
            for (char c : grid[i]) {
                rowList.add(c);
            }
            if (!rowList.contains('#')) {
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
                colsToAdd.add(col);
            }
        }

        return duplicateRowsAndColumns(grid, rowsToAdd, colsToAdd);
    }

    public static char[][] duplicateRowsAndColumns(char[][] inputArray, List<Integer> rowsToAdd,
            List<Integer> colsToAdd) {
        int numRows = inputArray.length;
        int numCols = inputArray[0].length;

        List<char[]> resultRows = new ArrayList<>();

        // Duplicate rows
        int rowCount = 0;
        for (int row = 0; row < numRows; row++) {
            char[] currentRow = inputArray[row];
            if (rowsToAdd.contains(row)) {
                resultRows.add(currentRow.clone()); // Clone the row if it needs to be duplicated
                if (!duplicatedRows.contains(row)) {
                    duplicatedRows.add(row + rowCount);
                    rowCount++;
                }
            }

            resultRows.add(currentRow);
        }

        List<char[]> resultList = duplicateColumnsInPlace(resultRows, colsToAdd);
        char[][] result = convertToListToArray(resultList);

        return result;
    }

    public static List<char[]> duplicateColumnsInPlace(List<char[]> matrix, List<Integer> columnsToDuplicate) {
        // Validate input
        if (matrix == null || matrix.isEmpty() || matrix.get(0).length == 0 || columnsToDuplicate == null
                || columnsToDuplicate.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }

        // Iterate through each row in the matrix
        for (int rowIndex = 0; rowIndex < matrix.size(); rowIndex++) {
            char[] originalRow = matrix.get(rowIndex);
            int originalColumns = originalRow.length;
            int additionalColumns = columnsToDuplicate.size();

            // Create a new row with expanded size
            char[] newRow = new char[originalColumns + additionalColumns];

            // Copy the original columns, inserting duplicates in place
            for (int colIndex = 0, newColIndex = 0; colIndex < originalColumns; colIndex++, newColIndex++) {
                if (columnsToDuplicate.contains(colIndex)) {
                    // Duplicate the column and insert it before the current column
                    newRow[newColIndex] = originalRow[colIndex];
                    if (!duplicatedColumns.contains(newColIndex)) {
                        duplicatedColumns.add(newColIndex);
                    }
                    newColIndex++;
                }
                newRow[newColIndex] = originalRow[colIndex];
            }

            // Update the matrix with the new row
            matrix.set(rowIndex, newRow);
        }

        return matrix;
    }

    public static char[][] convertToListToArray(List<char[]> list) {
        char[][] array = new char[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static long shortestDistance(char[][] grid, Point start, Point end) {
        int xDiff = Math.abs(end.x - start.x);
        int yDiff = Math.abs(end.y - start.y);
        // return xDiff + yDiff;

        // System.out.println("X Dist========");
        long xDist = calculateDistanceWithList(start.x, end.x, duplicatedRows);
        // System.out.println("Y Dist=======");
        long yDist = calculateDistanceWithList(start.y, end.y, duplicatedColumns);
        long returnDist = xDist + yDist;
        //  System.out.println("TOTALDIST: " + returnDist);
        //  System.out.println();
        //  System.out.println();
        return returnDist;
    }

    public static List<Point> findPoints(char[][] grid) {
        List<Point> points = new ArrayList<>();

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] != '.') {
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static long calculateDistanceWithList(int startNumber, int endNumber, List<Integer> numberList) {
        long distance = 0;
        //System.out.println("Start: " + startNumber + ", End: " + endNumber);
        // Iterate from startNumber to endNumber
        if (startNumber < endNumber) {
            for (int i = startNumber; i < endNumber; i++) {
                // Check if the current number is in the list
                if (numberList.contains(i)) {
                    //System.out.println("MULTIPLIED i AT: " + i);
                    distance += 999998;
                }

                //System.out.println("STEP AT: " + i);
                distance++; // Increment the distance for each step
            }
        } else if (startNumber > endNumber) {
            for (int i = endNumber; i < startNumber; i++) {
                // Check if the current number is in the list

                if (numberList.contains(i)) {
                    //System.out.println("---MULTIPLIED i AT: " + i);

                    //System.out.println(i);
                    distance += 999998;
                }
                //System.out.println("---STEP AT: " + i);

                distance++; // Increment the distance for each step
            }
        }
        //System.out.println("Dist: " + distance);
        return distance;
    }

}