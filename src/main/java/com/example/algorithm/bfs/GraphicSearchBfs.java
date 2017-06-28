package com.example.algorithm.bfs;

import com.example.algorithm.GraphicEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Searching a graphic represented by an adjacent matrix
 */
public class GraphicSearchBfs {

    private final int pointNum;
    private final int[][] adjacentMatrix;
    private final boolean[] travelled;
    private final ArrayList<Integer> travelPoints;

    private int head = -1;
    private int tail = -1;

    public GraphicSearchBfs(int pointNum, List<GraphicEdge> edges) {
        this.pointNum = pointNum;
        this.adjacentMatrix = new int[pointNum][pointNum];
        this.travelled = new boolean[pointNum];
        this.travelPoints = new ArrayList<Integer>(pointNum);

        for (int i = 0; i < pointNum; i++) {
            for (int j = 0; j < pointNum; j++) {
                if (i == j) {
                    adjacentMatrix[i][j] = 0;
                } else {
                    adjacentMatrix[i][j] = -1;
                }
            }
        }
        for (int i = 0; i < edges.size(); i++) {
            GraphicEdge e = edges.get(i);
            // undirected graphic
            adjacentMatrix[e.getPoint1()][e.getPoint2()] = e.getDistance();
            adjacentMatrix[e.getPoint2()][e.getPoint1()] = e.getDistance();
        }
        for (int i = 0; i < travelled.length; i++) {
            travelled[i] = false;
        }
    }

    public void dsf() {

        while (head <= tail) {
            // System.out.println("head: " + head + ", tail: " + tail);

            if (travelPoints.size() == pointNum) {
                for (Integer travelPoint : travelPoints) {
                    System.out.print(travelPoint + " ");
                }
                System.out.println();
                return;
            }

            int point = travelPoints.get(head);
            for (int i = 0; i < pointNum; i++) {
                if (travelled[i] || adjacentMatrix[point][i] == 0 || adjacentMatrix[point][i] == -1) {
                    continue;
                }
                // System.out.println("travel: " + point + ", next:" + i);

                travelled[i] = true;
                travelPoints.add(i);
                tail++;
            }
            head++;
        }
    }

    private void printTraveled() {
        for (int i = 0; i < travelled.length; i++) {
            System.out.print(travelled[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<GraphicEdge> edges = new ArrayList<GraphicEdge>(5);
        edges.add(new GraphicEdge(0,1,1));
        edges.add(new GraphicEdge(0,2,1));
        edges.add(new GraphicEdge(0,4,1));
        edges.add(new GraphicEdge(1,3,1));
        edges.add(new GraphicEdge(2,4,1));

        GraphicSearchBfs graphicSearch = new GraphicSearchBfs(5, edges);
        graphicSearch.beginSearch();
    }

    private void beginSearch() {
        travelPoints.add(0);
        travelled[0] = true;
        head++;
        tail++;
        dsf();
    }

}
