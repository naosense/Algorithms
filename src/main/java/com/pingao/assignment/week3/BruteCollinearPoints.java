package com.pingao.assignment.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


/**
 * Created by pingao on 2018/5/31.
 */
public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private int size;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        int N = points.length;

        segments = new LineSegment[N];
        double[] slopes = new double[N];
        initSlopes(slopes);
        for (int start = 0; start < N; start++) {
            int end = 0;
            for (int i = start + 1; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    for (int k = j + 1; k < N; k++) {
                        Point p1 = pointsCopy[start];
                        Point p2 = pointsCopy[i];
                        Point p4 = pointsCopy[k];
                        double s12 = p1.slopeTo(p2);
                        double s14 = p1.slopeTo(p4);
                        if (Double.compare(s12, s14) == 0) {
                            end = k;
                        }
                    }
                }
            }

            Point p1 = pointsCopy[start];
            Point p2 = pointsCopy[end];
            double slope = p1.slopeTo(p2);
            if (end != 0 && !isDuplicate(slopes, slope)) {
                segments[size] = new LineSegment(p1, p2);
                slopes[size] = slope;
                size++;
            }
        }
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // make sure not null and not equal
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point p1 = points[i];
                Point p2 = points[j];
                if (p1 == null || p2 == null || p1.compareTo(p2) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void initSlopes(double[] slopes) {
        for (int i = 0; i < slopes.length; i++) {
            slopes[i] = Double.NEGATIVE_INFINITY;
        }
    }

    private boolean isDuplicate(double[] slopes, double slope) {
        return Arrays.stream(slopes).anyMatch(s -> Double.compare(s, slope) == 0);
    }

    // the number of line segments
    public int numberOfSegments() {
        return size;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, size);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(System.getProperty("user.dir") + "/src/test/resources/week3-input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        //print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
