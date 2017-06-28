package com.example.algorithm;

/**
 * Created by jiangrui on 5/6/15.
 */
public class GraphicEdge {
    private final int point1;
    private final int point2;
    private final int distance;

    public GraphicEdge(int point1, int point2, int distance) {
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
    }

    public int getPoint1() {
        return point1;
    }

    public int getPoint2() {
        return point2;
    }

    public int getDistance() {
        return distance;
    }

}
