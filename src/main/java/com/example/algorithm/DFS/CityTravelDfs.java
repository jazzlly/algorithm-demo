package com.example.algorithm.dfs;

import com.example.algorithm.GraphicEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding a shortest path in a city represented by an adjacent matrix
 */
public class CityTravelDfs {

    private final int pointNum;
    private final int[][] adjacentMatrix;
    private final boolean[] travelled;
    private final int startPoint;
    private final int endPoint;


    private final ArrayList<Integer> travelPoints;
    public CityTravelDfs(int pointNum, List<GraphicEdge> edges,
                         int startPoint, int endPoint) {
        this.pointNum = pointNum;
        this.adjacentMatrix = new int[pointNum][pointNum];
        this.travelled = new boolean[pointNum];
        this.travelPoints = new ArrayList<Integer>(pointNum);
        this.startPoint = startPoint - 1;
        this.endPoint = endPoint - 1;

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
            adjacentMatrix[e.getPoint1() - 1][e.getPoint2() - 1] = e.getDistance();
        }
        for (int i = 0; i < travelled.length; i++) {
            travelled[i] = false;
        }
    }

    public void dsf(int point) {
        if (point == endPoint) {
            travelPoints.add(point);

            System.out.print("got end: ");
            Integer prev = null;
            int sum = 0;
            for (Integer p : travelPoints) {
                System.out.print((p + 1) + " ");
                if (prev != null) {
                    sum += adjacentMatrix[prev][p];
                }
                prev = p;
            }
            System.out.println(", distance: " + sum);
            travelPoints.remove(travelPoints.size() - 1);
            return;
        }

        for (int i = 0; i < pointNum; i++) {
            if (travelled[i] || adjacentMatrix[point][i] == 0 || adjacentMatrix[point][i] == -1) {
                continue;
            }
            // System.out.println("travel: " + point + ", next:" + i + ", step: " + step);

            travelled[point] = true;
            travelPoints.add(point);
            dsf(i);
            travelled[point] = false;
            travelPoints.remove(travelPoints.size() - 1);
        }
    }

    private void search() {
        dsf(startPoint);
    }

    public static void main(String[] args) {
        List<GraphicEdge> edges = new ArrayList<GraphicEdge>(5);
        edges.add(new GraphicEdge(1, 2, 2));
        edges.add(new GraphicEdge(1, 5, 10));
        edges.add(new GraphicEdge(2, 3, 3));
        edges.add(new GraphicEdge(2, 5, 7));
        edges.add(new GraphicEdge(3, 1, 4));
        edges.add(new GraphicEdge(3, 4, 4));
        edges.add(new GraphicEdge(4, 5, 5));
        edges.add(new GraphicEdge(5, 3, 3));

        CityTravelDfs graphicSearch = new CityTravelDfs(5, edges, 1, 5);
        graphicSearch.search();
    }

}
