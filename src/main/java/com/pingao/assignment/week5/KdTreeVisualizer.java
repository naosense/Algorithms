package com.pingao.assignment.week5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by pingao on 2018/8/28.
 */
public class KdTreeVisualizer {
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        rect.draw();
        StdDraw.enableDoubleBuffering();
        //KdTree kdtree = new KdTree();
        PointSET pointset = new PointSET();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    pointset.insert(p);
                    StdDraw.clear();
                    pointset.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }

    }
}
