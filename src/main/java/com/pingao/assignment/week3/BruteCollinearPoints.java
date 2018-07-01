package com.pingao.assignment.week3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;


/**
 * Created by pingao on 2018/5/31.
 */
public class BruteCollinearPoints {
    private final ResizingArrayQueue<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        int len = points.length;

        segments = new ResizingArrayQueue<>();
        ResizingArrayQueue<Double> slopes = new ResizingArrayQueue<>();
        ResizingArrayQueue<Point> ends = new ResizingArrayQueue<>();
        for (int i = 0; i < len; i++) {
            int p = 0;
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point p1 = pointsCopy[i];
                        Point p2 = pointsCopy[j];
                        Point p3 = pointsCopy[k];
                        Point p4 = pointsCopy[m];
                        double s12 = p1.slopeTo(p2);
                        double s13 = p1.slopeTo(p3);
                        double s14 = p1.slopeTo(p4);
                        if (Double.compare(s12, s14) == 0 && Double.compare(s12, s13) == 0) {
                            p = m;
                        }

                        if (m == len - 1) {
                            Point start = pointsCopy[i];
                            Point end = pointsCopy[p];
                            double slope = start.slopeTo(end);
                            if (p != 0 && !isDuplicate(slopes, slope, ends, end)) {
                                segments.enqueue(new LineSegment(start, end));
                                slopes.enqueue(slope);
                                ends.enqueue(end);
                            }
                        }
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
            Point p1 = points[i];
            if (p1 == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                Point p2 = points[j];
                if (p2 == null || p1.compareTo(p2) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private boolean isDuplicate(ResizingArrayQueue<Double> slopes, double slope, ResizingArrayQueue<Point> ends, Point end) {
        Iterator<Double> it1 = slopes.iterator();
        Iterator<Point> it2 = ends.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            double s = it1.next();
            Point e = it2.next();
            if (Double.compare(s, slope) == 0 && e.compareTo(end) == 0) {
                return true;
            }
        }
        return false;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
