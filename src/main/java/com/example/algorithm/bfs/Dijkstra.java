package com.example.algorithm.bfs;

import com.example.algorithm.GraphicEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding least transfer time
 */
public class Dijkstra {

    private final int pointNum;
    private final int[][] adjacentMatrix;
    private final boolean[] travelled;
    private final int[] directDistance2Start;


    public Dijkstra(int pointNum, List<GraphicEdge> edges, int start) {
        this.pointNum = pointNum;

        this.adjacentMatrix = new int[pointNum][pointNum];
        initAdjacentMatrix(pointNum, edges);

        this.travelled = new boolean[pointNum];
        directDistance2Start = new int[pointNum];
        initMisc(pointNum, start - 1);
    }

    private void initMisc(int pointNum, int start) {
        for (int i = 0; i < pointNum; i++) {
            travelled[i] = false;
            directDistance2Start[i] = Integer.MAX_VALUE;
        }
        directDistance2Start[start] = 0;
    }

    private void initAdjacentMatrix(int pointNum, List<GraphicEdge> edges) {
        for (int i = 0; i < pointNum; i++) {
            for (int j = 0; j < pointNum; j++) {
                if (i == j) {
                    adjacentMatrix[i][j] = 0;
                } else {
                    adjacentMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            GraphicEdge e = edges.get(i);
            adjacentMatrix[e.getPoint1() - 1][e.getPoint2() - 1] = e.getDistance();
        }
    }

    public void dijkstra() {
        int traveledPoint = 0;

        while (traveledPoint < pointNum) {
            int selectIndex = getUntraveledPointMinDistance2Start();
            int selectDis2root = directDistance2Start[selectIndex];

            travelled[selectIndex] = true;
            traveledPoint++;

            for (int i = 0; i < pointNum; i++) {
                if (travelled[i]) continue;
                if (adjacentMatrix[selectIndex][i] == Integer.MAX_VALUE) continue;

                if (directDistance2Start[i] > selectDis2root + adjacentMatrix[selectIndex][i]) {
                    directDistance2Start[i] = selectDis2root + adjacentMatrix[selectIndex][i];
                }
            }
            printDis();
        }
    }

    private void printDis() {
        System.out.print("directDistance2Start: ");

        for (int i = 0; i < pointNum; i++) {
            System.out.print(directDistance2Start[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<GraphicEdge> edges = new ArrayList<GraphicEdge>();
        edges.add(new GraphicEdge(1,2,1));
        edges.add(new GraphicEdge(1,3,12));
        edges.add(new GraphicEdge(2,3,9));
        edges.add(new GraphicEdge(2,4,3));
        edges.add(new GraphicEdge(3,5,5));
        edges.add(new GraphicEdge(4,3,4));
        edges.add(new GraphicEdge(4,5,13));
        edges.add(new GraphicEdge(4,6,15));
        edges.add(new GraphicEdge(5,6,4));

        Dijkstra graphicSearch = new Dijkstra(6, edges, 1);
        graphicSearch.dijkstra();
    }


    private int getUntraveledPointMinDistance2Start() {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < pointNum; i++) {
            if (travelled[i]) continue;

            if (min > directDistance2Start[i]) {
                min = directDistance2Start[i];
                index = i;
            }
        }

        if (index == -1) {
            throw new IllegalStateException("can not get min value from directDistance2Start array");
        }
        return index;
    }
}
