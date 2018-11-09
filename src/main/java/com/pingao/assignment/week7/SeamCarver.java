package com.pingao.assignment.week7;

import edu.princeton.cs.algs4.Picture;


/**
 * Created by pingao on 2018/11/8.
 */
public class SeamCarver {
    private final Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture can't be null");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1) {
            throw new IllegalArgumentException(String.format("x is out of range %d~%d", 0, width() - 1));
        }
        if (y < 0 || y > height() - 1) {
            throw new IllegalArgumentException(String.format("y is out of range %d~%d", 0, height() - 1));
        }

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }

        int xp = picture.getRGB(x - 1, y);
        int xn = picture.getRGB(x + 1, y);
        int yp = picture.getRGB(x, y - 1);
        int yn = picture.getRGB(x, y + 1);

        return Math.sqrt(deltaSquare(xp, xn) + deltaSquare(yp, yn));
    }

    private double deltaSquare(int rgb1, int rgb2) {
        double rp = Math.pow(red(rgb1) - red(rgb2), 2);
        double gp = Math.pow(green(rgb1) - green(rgb2), 2);
        double bp = Math.pow(blue(rgb1) - blue(rgb2), 2);
        return rp + gp + bp;
    }

    private int red(int rgb) {
        return (rgb & 0xFF0000) >> 16;
    }

    private int green(int rgb) {
        return (rgb & 0xFF00) >> 8;
    }

    private int blue(int rgb) {
        return rgb & 0xFF;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }
}
