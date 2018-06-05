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

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            Arrays.sort(pointsCopy, p.slopeOrder());

            int start = 1;
            int end = start + 2;
            while (end < N) {
                double s1 = p.slopeTo(pointsCopy[end - 2]);
                double s2 = p.slopeTo(pointsCopy[end - 1]);
                double s3 = p.slopeTo(pointsCopy[end]);

                if (Double.compare(s1, s2) == 0 && Double.compare(s1, s3) == 0) {
                    end++;
                    //System.out.println("==: " + start + ", " + end);
                } else {
                    //System.out.println("!=: " + start + ", " + end);
                    start++;
                    end = start + 2;
                }


            }

            if (end - start > 2) {
                segments[size++] = new LineSegment(pointsCopy[start] , pointsCopy[end - 1]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
