package com.pingao.assignment.week7;

import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;


/**
 * Created by pingao on 2018/11/8.
 */
public class SeamCarver {
    // private final Picture picture;

    private int[][] rgb;
    // private double[][] energy;
    // private int width;
    // private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture can't be null");
        }
        rgb = new int[picture.width()][picture.height()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                rgb[x][y] = picture.getRGB(x, y);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(width(), height());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                picture.setRGB(x, y, rgb[x][y]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return rgb.length;
    }

    // height of current picture
    public int height() {
        if (width() == 0) {
            return 0;
        }
        return rgb[0].length;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1) {
            throw new IllegalArgumentException(String.format("x is out of range %d~%d", 0, width() - 1));
        }
        if (y < 0 || y > height() - 1) {
            throw new IllegalArgumentException(String.format("y is out of range %d~%d", 0, height() - 1));
        }

        return compute(x, y);
    }

    private double compute(int x, int y) {
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }

        int xp = rgb[x - 1][y];
        int xn = rgb[x + 1][y];
        int yp = rgb[x][y - 1];
        int yn = rgb[x][y + 1];

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

    private static int[][] transpose(int[][] a) {
        if (a.length == 0) {
            return new int[0][0];
        }
        int[][] t = new int[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                t[j][i] = a[i][j];
            }
        }
        return t;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam can't be null");
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam can't be null");
        }
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {3, 2, 1}};
        int[][] t = transpose(a);
        for (int[] aT : t) {
            System.out.println(Arrays.toString(aT));
        }
    }
}
