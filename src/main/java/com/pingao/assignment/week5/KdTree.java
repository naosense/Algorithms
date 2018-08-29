package com.pingao.assignment.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Set;
import java.util.TreeSet;


/**
 * Created by pingao on 2018/8/27.
 */
public class KdTree {
    private static final int K = 2;
    private Node<Double, Point2D> root;
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

        root = insert(root, p, 0);
        size++;
    }

    private Node<Double, Point2D> insert(Node<Double, Point2D> n, Point2D p, int depth) {
        if (n == null) {
            return new Node<>(coordinate(p, depth % K), p);
        }

        double co = coordinate(p, depth % K);
        if (co < n.key) {
            n.left = insert(n.left, p, depth + 1);
        } else if (co > n.key) {
            n.right = insert(n.right, p, depth + 1);
        } else {
            n.value = p;
        }

        return n;
    }

    private double coordinate(Point2D p, int axis) {
        return axis == 0 ? p.x() : p.y();
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return get(root, p, 0) != null;
    }

    private Node<Double, Point2D> get(Node<Double, Point2D> root, Point2D p, int depth) {
        if (root == null) {
            return null;
        }

        double co = coordinate(p, depth % K);
        if (co < root.key) {
            return get(root.left, p, depth + 1);
        } else if (co > root.key) {
            return get(root.right, p, depth + 1);
        } else {
            return root;
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node<Double, Point2D> n) {
        if (n == null) {
            return;
        }

        StdDraw.point(n.value.x(), n.value.y());
        draw(n.left);
        draw(n.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Set<Point2D> points = new TreeSet<>();
        range(root, rect, points, 0);
        return points;
    }

    private void range(Node<Double, Point2D> n, RectHV rect, Set<Point2D> points, int depth) {
        if (n == null) {
            return;
        }

        if (isLeft(rect, n.value, depth)) {
            range(n.left, rect, points, depth + 1);
        } else if (isRight(rect, n.value, depth)) {
            range(n.right, rect, points, depth + 1);
        } else {
            if (rect.contains(n.value)) {
                points.add(n.value);
            }
            range(n.left, rect, points, depth + 1);
            range(n.right, rect, points, depth + 1);
        }
    }

    private boolean isLeft(RectHV rect, Point2D p, int depth) {
        return depth % K == 0 ? rect.xmax() < p.x() : rect.ymax() < p.y();
    }

    private boolean isRight(RectHV rect, Point2D p, int depth) {
        return depth % K == 0 ? rect.xmin() > p.x() : rect.ymin() > p.y();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Nearest nearest = new Nearest(Double.POSITIVE_INFINITY, null);
        nearest(root, p, 0, nearest);
        return nearest.point;
    }

    private void nearest(Node<Double, Point2D> n, Point2D p, int depth, Nearest nearest) {
        if (n == null) {
            return;
        }

        double distance = p.distanceSquaredTo(n.value);
        if (nearest.distance > distance) {
            nearest.distance = distance;
            nearest.point = n.value;
        }

        if (isLeft(n, p, depth)) {
            nearest(n.left, p, depth + 1, nearest);
        } else if (isRight(n, p, depth)) {
            nearest(n.right, p, depth + 1, nearest);
        } else {
            nearest(n.left, p, depth + 1, nearest);
            nearest(n.right, p, depth + 1, nearest);
        }
    }

    private boolean isLeft(Node<Double, Point2D> n, Point2D p, int depth) {
        return depth % K == 0 ? p.x() < n.value.x() : p.y() < n.value.y();
    }

    private boolean isRight(Node<Double, Point2D> n, Point2D p, int depth) {
        return depth % K == 0 ? p.x() > n.value.x() : p.y() > n.value.y();
    }

    private static class Node<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;
        private K key;
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    private static class Nearest {
        private double distance;
        private Point2D point;

        Nearest(double distance, Point2D point) {
            this.distance = distance;
            this.point = point;
        }
    }
}
