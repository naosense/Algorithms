package com.pingao.assignment.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


/**
 * Created by pingao on 2018/6/3.
 */
public class FastCollinearPoints {
    private final ResizingArrayQueue<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validate(points);
        int len = points.length;
        segments = new ResizingArrayQueue<>();
        Point[] pointsCopy = points.clone();

        for (int i = 0; i < len; i++) {
            Point p = pointsCopy[i];
            Arrays.sort(pointsCopy, i + 1, len, p.slopeOrder().thenComparing(Point::compareTo));
            int index1 = i + 1;
            int index2 = index1 + 2;
            while (index2 < len) {
                Point p1 = pointsCopy[index1];
                Point p2 = pointsCopy[index2];
                double s1 = p.slopeTo(p1);
                double s2 = p.slopeTo(p2);

                if (Double.compare(s1, s2) == 0) {
                    index2++;
                    if (index2 == len && index2 - index1 > 2) {
                        addSeg(min(p, p1), max(p, pointsCopy[index2 - 1]));
                    }
                } else {
                    if (index2 - index1 > 2) {
                        addSeg(min(p, p1), max(p, pointsCopy[index2 - 1]));
                        index1 = index2;
                        index2 = index1 + 2;
                    } else {
                        index1++;
                        index2 = index1 + 2;
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

    private void addSeg(Point start, Point end) {
        segments.enqueue(new LineSegment(start, end));
    }

    private Point min(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p2 : p1;
    }

    private Point max(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p1 : p2;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment segment : segments) {
            copy[i++] = segment;
        }
        return copy;
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

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
