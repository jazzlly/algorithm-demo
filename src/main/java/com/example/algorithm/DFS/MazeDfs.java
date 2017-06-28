package com.example.algorithm.dfs;

/**
 * Search a maze with Depth First Search
 */
public class MazeDfs {
    /** 迷宫路径，可以通行 */
    public final static int MAZE_PATH = 0;
    /** 迷宫墙壁，不可通行 */
    public final static int MAZE_WALL = 1;

    private final int mazeHeight;
    private final int mazeWidth;

    private final int startRow;
    private final int startColumn;
    private final int targetRow;
    private final int targetColumn;

    private final int[][] mazeMatrix;
    private final int[][] passed;

    private final int[][] nextPoints = {
            {0, 1},
            {1, 0},
            {-1, 0},
            {0, -1}
    };

    public MazeDfs(int[][] mazeMatrix, int startRow, int startColumn, int targetRow, int targetColumn) {
        if (mazeMatrix.length <= 0 || mazeMatrix[0].length <=0) {
            throw new IllegalArgumentException("Maze matrix should not be empty!");
        }

        this.mazeMatrix = mazeMatrix;
        this.mazeHeight = mazeMatrix.length;
        this.mazeWidth = mazeMatrix[0].length;

        if (!pointInMaze(startRow, startColumn)) {
            throw new IllegalArgumentException("start point not in maze!");
        }
        if (!pointInMaze(targetRow, targetColumn)) {
            throw new IllegalArgumentException("target point not in maze!");
        }

        this.startRow = startRow;
        this.startColumn = startColumn;
        this.targetRow = targetRow;
        this.targetColumn = targetColumn;

        this.passed = new int[mazeHeight][mazeWidth];
        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                passed[i][j] = 0;
            }
        }
    }

    public void beginSearch() {
        passed[startRow][startColumn] = 1;
        dfs(startRow, startColumn, 1);
    }

    private void dfs(int r, int c, int step) {
        // 1. boundary
        if (isTargetPoint(r, c)) {
            // Target found, print the path
            printPath();
            System.out.println(step-1);
            return;
        }

        // 2. search frame
        for (int i = 0; i < nextPoints.length; i++) {
            int nextRow = r + nextPoints[i][0];
            int nextColumn = c + nextPoints[i][1];
            if (!pointInMaze(nextRow, nextColumn) || isWall(nextRow, nextColumn) ||
                    passed[nextRow][nextColumn] > 0) {
                continue;
            }

            passed[nextRow][nextColumn] = step + 1;
            dfs(nextRow, nextColumn, step+1);
            passed[nextRow][nextColumn] = 0;
        }
    }

    private boolean isWall(int row, int column) {
        return (mazeMatrix[row][column] == MAZE_WALL);
    }

    private void printPath() {
        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                if (isWall(i,j) && passed[i][j] > 0) {
                    throw new IllegalStateException("The pass path across the wall!");
                }
                if (isWall(i,j)) {
                    System.out.print("x ");
                    continue;
                }
                System.out.print(passed[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private boolean pointInMaze(int row, int column) {
        if (row < 0 || row >= this.mazeHeight || column < 0 || column >= this.mazeWidth) {
            return false;
        }
        return true;
    }

    private boolean isTargetPoint(int r, int c) {
        if (r == targetRow && c == targetColumn) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 1, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 1}
        };
        MazeDfs maze = new MazeDfs(matrix, 0, 0, 3, 2);
        maze.beginSearch();
    }
}
