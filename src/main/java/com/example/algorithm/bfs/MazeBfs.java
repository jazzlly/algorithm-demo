package com.example.algorithm.bfs;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Search a maze with Breadth First Search
 */
public class MazeBfs {
    class PathPoint {
        final int row;
        final int column;
        final int step;
        final int parentIndex;

        public PathPoint(int row, int column, int step, int parentIndex) {
            this.row = row;
            this.column = column;
            this.step = step;
            this.parentIndex = parentIndex;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int getStep() {
            return step;
        }

        public int getParent() {
            return parentIndex;
        }

        @Override
        public String toString() {
            return "PathPoint{" +
                    "row=" + row +
                    ", column=" + column +
                    ", step=" + step +
                    ", parentIndex=" + parentIndex +
                    '}';
        }
    }

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

    private final ArrayList<PathPoint> pathPoints = new ArrayList<PathPoint>();
    private int head = -1;
    private int tail = -1;

    public MazeBfs(int[][] mazeMatrix, int startRow, int startColumn, int targetRow, int targetColumn) {
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
        pathPoints.add(new PathPoint(startRow, startColumn, 1, -1));
        head++;
        tail++;
        bfs();
    }

    private void bfs() {
        while (head <= tail) {
            PathPoint point = pathPoints.get(head);
            if (isTargetPoint(point.getRow(), point.getColumn())) {
                printPath();
                System.out.println("");

                cleanPath();
                PathPoint tmp = new PathPoint(-1, -1, -1, head);
                do {
                    tmp = pathPoints.get(tmp.getParent());
                    passed[tmp.getRow()][tmp.getColumn()] = tmp.getStep();
                } while (tmp.getParent() != -1);
                printPath();

                return;
            }
            for (int i = 0; i < nextPoints.length; i++) {
                int nextRow = point.getRow() + nextPoints[i][0];
                int nextColumn = point.getColumn() + nextPoints[i][1];
                if (!pointInMaze(nextRow, nextColumn) || isWall(nextRow, nextColumn) ||
                        passed[nextRow][nextColumn] > 0) {
                    continue;
                }

                pathPoints.add(new PathPoint(nextRow, nextColumn, point.getStep() + 1, head));
                passed[nextRow][nextColumn] = point.getStep() + 1;
                tail++;
            }
            head++;
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
    private void cleanPath() {
        for (int i = 0; i < mazeHeight; i++) {
            for (int j = 0; j < mazeWidth; j++) {
                passed[i][j] = 0;
            }
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
        MazeBfs maze = new MazeBfs(matrix, 0, 0, 3, 2);
        maze.beginSearch();
    }
}
