package com.pingao.assignment.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.Arrays;


/**
 * Created by pingao on 2018/6/3.
 */
public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int size;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validate(points);
        int N = points.length;
        segments = new LineSegment[N];
        Point[] pointsCopy = points.clone();

        double[] slopes = new double[N];
        initSlopes(slopes);

        for (Point p : points) {
            Arrays.sort(pointsCopy, p.slopeOrder().thenComparing(Point::compareTo));
            int start = 0;
            int end = start + 2;
            while (end < N) {
                double s1 = p.slopeTo(pointsCopy[start]);
                double s3 = p.slopeTo(pointsCopy[end]);

                if (Double.compare(s1, s3) == 0) {
                    end++;
                    if (end == N && end - start > 2) {
                        addSegIfNotPresent(slopes, s1, min(p, pointsCopy[start]), max(p, pointsCopy[end - 1]));
                    }
                } else {
                    if (end - start > 2) {
                        addSegIfNotPresent(slopes, s1, min(p, pointsCopy[start]), max(p, pointsCopy[end - 1]));
                        start = end;
                        end = start + 2;
                    } else {
                        start++;
                        end = start + 2;
                    }
                }
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

    private void addSegIfNotPresent(double[] slopes, double slope, Point start, Point end) {
        if (!isDuplicate(slopes, slope)) {
            segments[size] = new LineSegment(start, end);
            slopes[size] = slope;
            size++;
        }
    }

    private Point min(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p2 : p1;
    }

    private Point max(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p1 : p2;
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
        In in = new In(new File("D:\\project\\Algorithms\\src\\test\\resources\\week3-input8.txt"));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
