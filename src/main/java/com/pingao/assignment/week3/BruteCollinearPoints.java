package com.pingao.assignment.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
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

        segments = new LineSegment[points.length];
        int[] ends = new int[points.length];
        double[] slopes = new double[points.length];
        for (int i = 0; i < pointsCopy.length; i++) {
            int end = 0;
            for (int j = i + 1; j < pointsCopy.length; j++) {
                for (int m = j + 1; m < pointsCopy.length; m++) {
                    for (int n = m + 1; n < pointsCopy.length; n++) {
                        Point p1 = pointsCopy[i];
                        Point p2 = pointsCopy[j];
                        Point p3 = pointsCopy[m];
                        Point p4 = pointsCopy[n];
                        double s12 = p1.slopeTo(p2);
                        double s13 = p1.slopeTo(p3);
                        double s14 = p1.slopeTo(p4);
                        if (Double.compare(s12, s13) == 0 && Double.compare(s12, s14) == 0) {
                            end = n;
                        }
                    }
                }
            }

            Point p1 = pointsCopy[i];
            Point p2 = pointsCopy[end];
            double slope = p1.slopeTo(p2);
            if (end != 0 && !isDuplicate(ends, slopes, end, slope)) {
                ends[size] = end;
                slopes[size] = slope;
                segments[size] = new LineSegment(p1, p2);
                size++;
            }
        }
    }

    private boolean isDuplicate(int[] ends, double[] slopes, int end, double slope) {
        for (int i = 0; i < size; i++) {
            if (Double.compare(slopes[i], slope) == 0 && ends[i] == end) {
                return true;
            }
        }
        return false;
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
        In in = new In(new File("D:\\project\\IdeaProjects\\Algorithms\\src\\test\\resources\\input8.txt"));
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
        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
