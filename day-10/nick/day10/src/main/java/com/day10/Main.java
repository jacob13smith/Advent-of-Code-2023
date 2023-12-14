package com.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int nodeCount = 0;

    public static void main(String[] args) {

        String filePath = "D:\\Coding\\AdventOfCode\\Advent-of-Code-2023\\day-10\\nick\\day10\\src\\main\\resources\\input.txt";
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

        int startingRow = 0;
        int startingCol = 0;
        // Print the populated 2D array
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char currentChar = array2D[i][j];
                if (currentChar == 'S') {
                    startingRow = i;
                    startingCol = j;
                }
            }
        }
        Point startingPoint = new Point(startingRow, startingCol);
        System.out.println(startingPoint);
        Point farthestPoint = findFurthestPoint(array2D, startingPoint);
        System.out.println(nodeCount / 2);
    }
    
    private static Point findFurthestPoint(char[][] array, Point referencePoint) {
        boolean[][] visited = new boolean[array.length][array[0].length];
        Point[] farthestPoint = { null };
        
        dfs(array, referencePoint, referencePoint, visited);
        
        return farthestPoint[0];
    }
    
    private static void dfs(char[][] grid, Point start, Point current, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        if (current.x < 0 || current.x >= rows || current.y < 0 || current.y >= cols || visited[current.x][current.y]) {
            // also may have to check barriers here
            return;
        }
        
        visited[current.x][current.y] = true;
        System.out.println();
        System.out.println("Visiting: " + current.x + ", " + current.y);
        nodeCount++;
        
        ArrayList<int[]> possibleDirections = new ArrayList<int[]>();
        possibleDirections = searchAdjacentSpots(grid, current.x, current.y);

        for (int[] direction : possibleDirections) {
            int newRow = current.x + direction[0];
            int newCol = current.y + direction[1];
            dfs(grid, start, new Point(newRow, newCol), visited);
        }
    }

    private static ArrayList<int[]> searchAdjacentSpots(char[][] array, int row, int col) {
        ArrayList<int[]> possibleDirections = new ArrayList<int[]>();
        System.out.println("Searching Adjacent Spots of " + array[row][col]);
        switch (array[row][col]) {
            case '|': {
                int[] topCheck = checkTopSpot(array, row, col, possibleDirections);
                int[] bottomCheck = checkBottomSpot(array, row, col, possibleDirections);

                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }
            }

                break;

            case '-': {
                int[] leftCheck = checkLeftSpot(array, row, col, possibleDirections);
                int[] rightCheck = checkRightSpot(array, row, col, possibleDirections);

                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }
            }

                break;

            case 'F': {
                int[] bottomCheck = checkBottomSpot(array, row, col, possibleDirections);
                int[] rightCheck = checkRightSpot(array, row, col, possibleDirections);

                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }

            }

                break;

            case '7': {
                int[] leftCheck = checkLeftSpot(array, row, col, possibleDirections);

                int[] bottomCheck = checkBottomSpot(array, row, col, possibleDirections);

                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }

            }

                break;

            case 'J': {
                int[] topCheck = checkTopSpot(array, row, col, possibleDirections);
                int[] leftCheck = checkLeftSpot(array, row, col, possibleDirections);

                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }
            }

                break;

            case 'L': {
                int[] topCheck = checkTopSpot(array, row, col, possibleDirections);
                int[] rightCheck = checkRightSpot(array, row, col, possibleDirections);

                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }
            }

                break;
            case 'S': {
                int[] topCheck = checkTopSpot(array, row, col, possibleDirections);
                int[] bottomCheck = checkBottomSpot(array, row, col, possibleDirections);
                int[] leftCheck = checkLeftSpot(array, row, col, possibleDirections);
                int[] rightCheck = checkRightSpot(array, row, col, possibleDirections);

                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }
            }
                break;

            default:
                break;

        }
        return possibleDirections;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point(Point other) {
            this.x = other.x;
            this.y = other.y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static int[] checkTopSpot(char[][] array, int row, int col, ArrayList<int[]> possibleDirections) {
        // Check the top spot
        if (row - 1 >= 0) {
            char topChar = array[row - 1][col];
            int[] topDir = new int[] { -1, 0 };
            System.out.println("Checking TOP of " + row + ", " + col + ":" + topChar);

            if (!possibleDirections.contains(topDir)) {
                switch (topChar) {
                    case '|':
                        return topDir;

                    case 'F':
                        return topDir;

                    case '7':
                        return topDir;
                    default:
                        break;
                }
            }
        }
        return null;
    }

    public static int[] checkBottomSpot(char[][] array, int row, int col, ArrayList<int[]> possibleDirections) {
        
        // Check the bottom spot
        if (row + 1 < array.length) {
            char bottomChar = array[row + 1][col];
            System.out.println("Checking BOT of " + row + ", " + col + ":" + bottomChar);
            int[] bottomDir = new int[] { 1, 0 };
            if (!possibleDirections.contains(bottomDir)) {
                switch (bottomChar) {
                    case '|':
                            return bottomDir;

                    case 'L':
                            return bottomDir;

                    case 'J':
                            return bottomDir;
                    default:
                        break;
                }
            }
        }
        return null;
    }

    public static int[] checkLeftSpot(char[][] array, int row, int col, ArrayList<int[]> possibleDirections) {
        
        // Check the left spot
        if (col - 1 >= 0) {
            char leftChar = array[row][col - 1];
            int[] leftDir = new int[] { 0, -1 };
            System.out.println("Checking L of " + row + ", " + col+ ":" + leftChar);
            if (!possibleDirections.contains(leftDir)) {
                switch (leftChar) {
                    case '-':
                        return (leftDir);

                    case 'L':
                        return (leftDir);

                    case 'F':
                        return (leftDir);
                    default:
                        break;
                }
            }

        }
        return null;
    }

    public static int[] checkRightSpot(char[][] array, int row, int col, ArrayList<int[]> possibleDirections) {
        
        // Check the right spot
        if (col + 1 < array[row].length) {
            char rightChar = array[row][col + 1];
            System.out.println("Checking R of " + row + ", " + col + ":" + rightChar);
            int[] rightDir = new int[] { 0, 1 };
            if (!possibleDirections.contains(rightDir)) {
                switch (rightChar) {

                    case '-':
                        return (rightDir);

                    case 'J':
                        return (rightDir);

                    case '7':
                        return (rightDir);
                    default:
                        break;
                }
            }
        }
        return null;
    }
}