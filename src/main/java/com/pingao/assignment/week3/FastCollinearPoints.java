package com.pingao.assignment.week3;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;


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

        ResizingArrayQueue<Double> slopes = new ResizingArrayQueue<>();
        ResizingArrayQueue<Point> ends = new ResizingArrayQueue<>();

        for (Point p : points) {
            Arrays.sort(pointsCopy, p.slopeOrder().thenComparing(Point::compareTo));
            int start = 0;
            int end = start + 2;
            while (end < len) {
                double s1 = p.slopeTo(pointsCopy[start]);
                double s3 = p.slopeTo(pointsCopy[end]);

                if (Double.compare(s1, s3) == 0) {
                    end++;
                    if (end == len && end - start > 2) {
                        addSegIfNotPresent(slopes, s1, ends, min(p, pointsCopy[start]), max(p, pointsCopy[end - 1]));
                    }
                } else {
                    if (end - start > 2) {
                        addSegIfNotPresent(slopes, s1, ends, min(p, pointsCopy[start]), max(p, pointsCopy[end - 1]));
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

    private void addSegIfNotPresent(ResizingArrayQueue<Double> slopes, double slope, ResizingArrayQueue<Point> ends, Point start, Point end) {
        if (!isDuplicate(slopes, slope, ends, end)) {
            slopes.enqueue(slope);
            ends.enqueue(end);
            segments.enqueue(new LineSegment(start, end));
        }
    }

    private Point min(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p2 : p1;
    }

    private Point max(Point p1, Point p2) {
        return p1.compareTo(p2) > 0 ? p1 : p2;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
