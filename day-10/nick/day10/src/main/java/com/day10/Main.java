package com.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static int nodeCount = 0;

    public static void main(String[] args) {

        String filePath = "C:\\Users\\nickh\\OneDrive\\Desktop\\Code\\AdventOfCode\\Advent-of-Code-2023\\day-10\\nick\\day10\\src\\main\\resources\\input.txt";
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
        traverse(array2D, startingPoint);
        // System.out.println(startingPoint);
        // Point farthestPoint = findFurthestPoint(array2D, startingPoint);
        // System.out.println(nodeCount / 2);
    }

    private static void traverse(char[][] array2D, Point startingPoint) {
        // send each node out once
        // search S's node for starting point
        ArrayList<int[]> validStartDirections = searchAdjacentSpots(array2D, startingPoint.x, startingPoint.y);
        Point node1 = new Point(startingPoint.x + validStartDirections.get(0)[0],
                startingPoint.y + validStartDirections.get(0)[1]);
         Point node2 = new Point(startingPoint.x + validStartDirections.get(1)[0],
         startingPoint.y + validStartDirections.get(1)[1]);

        // ======NODE 1 ===========
        int lastX1 = startingPoint.x;
        int lastY1 = startingPoint.y;

        int currX1 = node1.x;
        int currY1 = node1.y;
        
        // ========NODE 2 =========
        int lastX2 = startingPoint.x;
        int lastY2 = startingPoint.y;

        int currX2 = node2.x;
        int currY2 = node2.y;


        int numSteps = 0;
        while (currX1 != currX2 || currY1 != currY2) {
            numSteps++;
            // while node1.x != node2.x || node2.1 != node2.y, traverse one more point

            // ======NODE 1 ===========
            System.out.println("Current Node1: " + currX1 + "," + currY1);

            ArrayList<int[]> validDirections1 = searchAdjacentSpots(array2D, currX1, currY1);

            for (int[] arr : validDirections1) {
                System.out.println(arr[0] + ":::" + arr[1]);
                int newX1 = currX1 + arr[0];
                int newY1 = currY1 + arr[1];
                if(newX1 != lastX1 || newY1 != lastY1){
                    System.out.println("oldX:" + currX1 + " , oldY:" + currY1 +" , newX:" + newX1 + " , newY:" + newY1);
                    lastX1 = currX1;
                    currX1 = newX1;
                    lastY1 = currY1;
                    currY1 = newY1;
                    break;
                } else {
                    System.out.println("Cannot traverse to " + newX1 + ","+newY1);
                }
            }
            System.out.println("================");
            System.out.println();

            // =========== NODE2 ============

            System.out.println("Current Node2: " + currX2 + "," + currY2);

            ArrayList<int[]> validDirections2 = searchAdjacentSpots(array2D, currX2, currY2);

            for (int[] arr : validDirections2) {
                System.out.println(arr[0] + ":::" + arr[1]);
                int newX2 = currX2 + arr[0];
                int newY2 = currY2 + arr[1];
                if(newX2 != lastX2 || newY2 != lastY2){
                    System.out.println("oldX:" + currX2 + " , oldY:" + currY2 +" , newX:" + newX2 + " , newY:" + newY2);
                    lastX2 = currX2;
                    currX2 = newX2;
                    lastY2 = currY2;
                    currY2 = newY2;
                    break;
                } else {
                    System.out.println("Cannot traverse to " + newX2 + ","+newY2);
                }
            }
            System.out.println("================");
            System.out.println();

            // Point nextNode1 = new Point(startingPoint.x + validDirections.get(0)[0],
            // startingPoint.y + validDirections.get(0)[1]);
            // Point nextNode2 = new Point(startingPoint.x + validDirections.get(1)[0],
            // startingPoint.y + validDirections.get(1)[1]);

        }
        System.out.println(numSteps + 1);
    }

    // private static Point findFurthestPoint(char[][] array, Point referencePoint)
    // {
    // boolean[][] visited = new boolean[array.length][array[0].length];
    // Point[] farthestPoint = { null };

    // dfs(array, referencePoint, referencePoint, visited);

    // return farthestPoint[0];
    // }

    // private static void dfs(char[][] grid, Point start, Point current,
    // boolean[][] visited) {
    // int rows = grid.length;
    // int cols = grid[0].length;

    // if (current.x < 0 || current.x >= rows || current.y < 0 || current.y >= cols
    // || visited[current.x][current.y]) {
    // System.out.println("=======DONE" + current.x + "," + current.y);
    // // also may have to check barriers here
    // return;
    // }

    // visited[current.x][current.y] = true;
    // nodeCount++;

    // ArrayList<int[]> possibleDirections = new ArrayList<int[]>();
    // possibleDirections = searchAdjacentSpots(grid, current.x, current.y,
    // visited);

    // for (int[] direction : possibleDirections) {
    // int newRow = current.x + direction[0];
    // int newCol = current.y + direction[1];
    // dfs(grid, start, new Point(newRow, newCol), visited);
    // }
    // }

    private static ArrayList<int[]> searchAdjacentSpots(char[][] array, int row, int col) {
        System.out.println("Searching: " + row + ", " + col + "  Char:  " + array[row][col]);
        ArrayList<int[]> possibleDirections = new ArrayList<int[]>();
        switch (array[row][col]) {
            case '|': {
                System.out.println("searching top and bottom");
                int[] topCheck = checkTopSpot(array, row, col);
                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }

                int[] bottomCheck = checkBottomSpot(array, row, col);
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);

                }
            }

                break;

            case '-': {
                System.out.println("searching left and right");
                int[] leftCheck = checkLeftSpot(array, row, col);
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }

                int[] rightCheck = checkRightSpot(array, row, col);
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }

            }

                break;

            case 'F': {
                System.out.println("searching bottom and right");

                int[] bottomCheck = checkBottomSpot(array, row, col);
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }

                int[] rightCheck = checkRightSpot(array, row, col);
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }

            }

                break;

            case '7': {
                System.out.println("searching left and bottom");

                int[] bottomCheck = checkBottomSpot(array, row, col);
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }

                int[] leftCheck = checkLeftSpot(array, row, col);
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }
            }

                break;

            case 'J': {
                System.out.println("searching left and top");

                int[] topCheck = checkTopSpot(array, row, col);
                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }

                int[] leftCheck = checkLeftSpot(array, row, col);
                if (leftCheck != null) {
                    possibleDirections.add(leftCheck);
                }
            }

                break;

            case 'L': {
                System.out.println("searching top and right");

                int[] topCheck = checkTopSpot(array, row, col);
                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }

                int[] rightCheck = checkRightSpot(array, row, col);
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }
            }

                break;
            case 'S': {
                System.out.println("searching ALL ");

                int[] topCheck = checkTopSpot(array, row, col);
                if (topCheck != null) {
                    possibleDirections.add(topCheck);
                }

                int[] rightCheck = checkRightSpot(array, row, col);
                if (rightCheck != null) {
                    possibleDirections.add(rightCheck);
                }

                int[] bottomCheck = checkBottomSpot(array, row, col);
                if (bottomCheck != null) {
                    possibleDirections.add(bottomCheck);
                }

                int[] leftCheck = checkLeftSpot(array, row, col);
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

    public static int[] checkTopSpot(char[][] array, int row, int col) {
        // Check the top spot
        if (row - 1 >= 0) {
            char topChar = array[row - 1][col];
            int[] topDir = new int[] { -1, 0 };
            switch (topChar) {
                case '|': {
                    System.out.println("Top available to traverse to");
                    return topDir;
                }

                case 'F': {
                    System.out.println("Top available to traverse to");
                    return topDir;
                }

                case '7': {
                    System.out.println("Top available to traverse to");
                    return topDir;
                }
                default:
                    break;
            }

        }
        return null;
    }

    public static int[] checkBottomSpot(char[][] array, int row, int col) {

        // Check the bottom spot
        if (row + 1 < array.length) {

            char bottomChar = array[row + 1][col];
            int[] bottomDir = new int[] { 1, 0 };
            switch (bottomChar) {
                case '|': {
                    System.out.println("Bottom available to traverse to");
                    return bottomDir;
                }
                case 'L': {
                    System.out.println("Bottom available to traverse to");
                    return bottomDir;
                }
                case 'J': {
                    System.out.println("Bottom available to traverse to");
                    return bottomDir;
                }
                default:
                    break;
            }

        }
        return null;
    }

    public static int[] checkLeftSpot(char[][] array, int row, int col) {

        // Check the left spot
        if (col - 1 >= 0) {
            char leftChar = array[row][col - 1];
            int[] leftDir = new int[] { 0, -1 };
            switch (leftChar) {
                case '-': {
                    System.out.println("Left available to traverse to");
                    return leftDir;
                }

                case 'L': {
                    System.out.println("Left available to traverse to");
                    return leftDir;
                }
                case 'F': {
                    System.out.println("Left available to traverse to");
                    return leftDir;
                }
                default:
                    break;

            }
        }
        return null;
    }

    public static int[] checkRightSpot(char[][] array, int row, int col) {

        // Check the right spot
        if (col + 1 < array[row].length) {
            char rightChar = array[row][col + 1];
            int[] rightDir = new int[] { 0, 1 };
            switch (rightChar) {

                case '-': {
                    System.out.println("Right available to traverse to");
                    return rightDir;
                }

                case 'J': {
                    System.out.println("Right available to traverse to");
                    return rightDir;
                }

                case '7': {
                    System.out.println("Right available to traverse to");
                    return rightDir;
                }
                default:
                    break;
            }
        }
        return null;
    }
}