package com.pingao.assignment.week5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


/**
 * Created by pingao on 2018/8/27.
 */
public class KdTree {
    private static final int K = 2;
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!contains(p)) {
            root = insert(root, p, 0);
            size++;
        }
    }

    private Node insert(Node n, Point2D p, int depth) {
        if (n == null) {
            return new Node(p);
        }

        double cp = coordinate(p, depth);
        double cn = coordinate(n, depth);
        if (cp < cn) {
            n.left = insert(n.left, p, depth + 1);
        } else {
            if (p.equals(n.location)) {
                n.location = p;
            } else {
                n.right = insert(n.right, p, depth + 1);
            }
        }

        return n;
    }

    private double coordinate(Point2D p, int depth) {
        return depth % K == 0 ? p.x() : p.y();
    }

    private double coordinate(Node n, int depth) {
        return depth % K == 0 ? n.location.x() : n.location.y();
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return get(root, p, 0) != null;
    }

    private Node get(Node n, Point2D p, int depth) {
        if (n == null) {
            return null;
        }

        double cp = coordinate(p, depth);
        double cn = coordinate(n, depth);
        if (cp < cn) {
            return get(n.left, p, depth + 1);
        } else {
            if (p.equals(n.location)) {
                return n;
            } else {
                return get(n.right, p, depth + 1);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0);
    }

    private void draw(Node n, int depth) {
        if (n == null) {
            return;
        }

        StdDraw.point(n.location.x(), n.location.y());
        draw(n.left, depth + 1);
        draw(n.right, depth + 1);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> points = new Queue<>();
        range(root, rect, points, 0);
        return points;
    }

    private void range(Node n, RectHV rect, Queue<Point2D> points, int depth) {
        if (n == null) {
            return;
        }

        if (rect.contains(n.location)) {
            points.enqueue(n.location);
        }

        if (isLeft(rect, n.location, depth)) {
            range(n.left, rect, points, depth + 1);
        } else if (isRight(rect, n.location, depth)) {
            range(n.right, rect, points, depth + 1);
        } else {
            range(n.left, rect, points, depth + 1);
            range(n.right, rect, points, depth + 1);
        }
    }

    // left or bottom
    private boolean isLeft(RectHV rect, Point2D p, int depth) {
        return depth % K == 0 ? rect.xmax() < p.x() : rect.ymax() < p.y();
    }

    // right or top
    private boolean isRight(RectHV rect, Point2D p, int depth) {
        return depth % K == 0 ? rect.xmin() > p.x() : rect.ymin() > p.y();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        NearestCache nearest = new NearestCache(Double.POSITIVE_INFINITY, null);
        nearest(root, p, 0, nearest);
        return nearest.point;
    }

    private void nearest(Node n, Point2D p, int depth, NearestCache cache) {
        if (n == null) {
            return;
        }

        double distance = p.distanceSquaredTo(n.location);
        if (cache.distance > distance) {
            cache.distance = distance;
            cache.point = n.location;
        }

        if (isLeft(n, p, depth)) {
            nearest(n.left, p, depth + 1, cache);
            if (cache.distance > distanceSquared(n, p, depth)) {
                nearest(n.right, p, depth + 1, cache);
            }
        } else {
            nearest(n.right, p, depth + 1, cache);
            if (cache.distance > distanceSquared(n, p, depth)) {
                nearest(n.left, p, depth + 1, cache);
            }
        }
    }

    private double distanceSquared(Node n, Point2D p, int depth) {
        return Math.pow(coordinate(n, depth) - coordinate(p, depth), 2);
    }

    // left or bottom
    private boolean isLeft(Node n, Point2D p, int depth) {
        return coordinate(p, depth) < coordinate(n, depth);
    }

    public static void main(String[] args) {
        In in = new In(System.getProperty("user.dir") + "/src/test/resources/week5-test.txt");
        KdTree kdtree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        System.out.println(kdtree.root.location);
        System.out.println(kdtree.size());
        System.out.println(kdtree.range(new RectHV(0.8, 0.16, 0.96, 0.45)));
    }

    private static class Node {
        private Node left;
        private Node right;
        private Point2D location;

        Node(Point2D location) {
            this.location = location;
        }
    }


    private static class NearestCache {
        private double distance;
        private Point2D point;

        NearestCache(double distance, Point2D point) {
            this.distance = distance;
            this.point = point;
        }
    }
}
