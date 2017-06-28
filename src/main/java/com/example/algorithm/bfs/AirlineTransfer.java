package com.example.algorithm.bfs;

import com.example.algorithm.GraphicEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding least transfer time
 */
public class AirlineTransfer {

    static class TravelPoint {
        final int current;
        final int prevIndex;

        public TravelPoint(int current, int prevIndex) {
            this.current = current;
            this.prevIndex = prevIndex;
        }

        public int getCurrent() {
            return current;
        }

        public int getPrevIndex() {
            return prevIndex;
        }
    }

    private final int pointNum;
    private final int[][] adjacentMatrix;
    private final boolean[] travelled;
    private final ArrayList<TravelPoint> travelPoints;
    private final int startPoint;
    private final int endPoint;

    private int head = -1;
    private int tail = -1;
    private int step = 0;

    public AirlineTransfer(int pointNum, List<GraphicEdge> edges, int startPoint, int endPoint) {
        this.pointNum = pointNum;
        this.adjacentMatrix = new int[pointNum][pointNum];
        this.travelled = new boolean[pointNum];
        this.travelPoints = new ArrayList<TravelPoint>(pointNum);
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
            // undirected graphic
            adjacentMatrix[e.getPoint1() - 1][e.getPoint2() - 1] = e.getDistance();
        }
        for (int i = 0; i < travelled.length; i++) {
            travelled[i] = false;
        }
    }

    public void dsf() {

        while (head <= tail) {
            // printTraveled();

            if (!travelPoints.isEmpty() && travelPoints.get(travelPoints.size() - 1).getCurrent() == endPoint) {
                System.out.print("got end: ");
                int prev = travelPoints.size() - 1;
                TravelPoint travelPoint = null;
                while (prev != -1){
                    travelPoint = travelPoints.get(prev);
                    System.out.print((travelPoint.getCurrent() + 1) + " ");
                    prev = travelPoint.getPrevIndex();
                }
                return;
            }

            int point = travelPoints.get(head).getCurrent();
            for (int i = 0; i < pointNum; i++) {
                if (travelled[i] || adjacentMatrix[point][i] == 0 || adjacentMatrix[point][i] == -1) {
                    continue;
                }
                // System.out.println("travel: " + (point + 1) + ", next:" + (i + 1));

                travelled[i] = true;
                travelPoints.add(new TravelPoint(i, head));
                tail++;
            }
            head++;
        }
    }

    private void printTraveled() {
        System.out.print("travelled: ");
        for (int i = 0; i < travelled.length; i++) {
            System.out.print(travelled[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<GraphicEdge> edges = new ArrayList<GraphicEdge>(5);
        edges.add(new GraphicEdge(1,2,1));
        edges.add(new GraphicEdge(1,3,1));
        edges.add(new GraphicEdge(2,3,1));
        edges.add(new GraphicEdge(2,4,1));
        edges.add(new GraphicEdge(3,4,1));
        edges.add(new GraphicEdge(3,5,1));
        edges.add(new GraphicEdge(4,5,1));

        AirlineTransfer graphicSearch = new AirlineTransfer(5, edges, 1, 5);
        graphicSearch.beginSearch();
    }

    private void beginSearch() {
        travelPoints.add(new TravelPoint(0, -1));
        travelled[startPoint] = true;
        head++;
        tail++;
        dsf();
    }
}
