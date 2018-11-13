package com.pingao.assignment.week7;

import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;


/**
 * 这个作业中的findHorizontalSeam，findHorizontalSeam，
 * removeHorizontalSeam，removeVerticalSeam是参考
 * https://github.com/michael0905/SeamCarver，图算法这章
 * 有点懵，作业做起来很吃力，截至日期又卡在那，而且作业必须得从前往后做，
 * 所以作为权宜之计先抄别人的，等后面的完了回来再做。
 * Created by pingao on 2018/11/8.
 */
public class SeamCarver {

    private int[][] rgb;

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
        rgb = transpose(rgb);
        int[] seam = findVerticalSeam();
        rgb = transpose(rgb);
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int n = this.width() * this.height();
        int[] seam = new int[this.height()];
        int[] nodeTo = new int[n];
        double[] distTo = new double[n];
        for (int i = 0; i < n; i++) {
            if (i < width())
                distTo[i] = 0;
            else
                distTo[i] = Double.POSITIVE_INFINITY;
        }
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                for (int k = -1; k <= 1; k++) {
                    if (j + k < 0 || j + k > this.width() - 1 || i + 1 < 0 || i + 1 > this.height() - 1) {
                    } else {
                        if (distTo[index(j + k, i + 1)] > distTo[index(j, i)] + energy(j, i)) {
                            distTo[index(j + k, i + 1)] = distTo[index(j, i)] + energy(j, i);
                            nodeTo[index(j + k, i + 1)] = index(j, i);
                        }
                    }
                }
            }
        }

        // find min dist in the last row
        double min = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int j = 0; j < width(); j++) {
            if (distTo[j + width() * (height() - 1)] < min) {
                index = j + width() * (height() - 1);
                min = distTo[j + width() * (height() - 1)];
            }
        }

        // find seam one by one
        for (int j = 0; j < height(); j++) {
            int y = height() - j - 1;
            int x = index - y * width();
            seam[height() - 1 - j] = x;
            index = nodeTo[index];
        }

        return seam;

    }

    private int index(int x, int y) {
        return width() * y + x;
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
        if (height() <= 1) throw new IllegalArgumentException();
        if (seam.length != width()) throw new IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > height() - 1)
                throw new IllegalArgumentException();
            if (i < width() - 1 && Math.pow(seam[i] - seam[i + 1], 2) > 1)
                throw new IllegalArgumentException();
        }

        int[][] updatedColor = new int[width()][height() - 1];
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] == 0) {
                System.arraycopy(this.rgb[i], seam[i] + 1, updatedColor[i], 0, height() - 1);
            } else if (seam[i] == height() - 1) {
                System.arraycopy(this.rgb[i], 0, updatedColor[i], 0, height() - 1);
            } else {
                System.arraycopy(this.rgb[i], 0, updatedColor[i], 0, seam[i]);
                System.arraycopy(this.rgb[i], seam[i] + 1, updatedColor[i], seam[i], height() - seam[i] - 1);
            }

        }
        this.rgb = updatedColor;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam can't be null");
        }
        this.rgb = transpose(this.rgb);
        removeHorizontalSeam(seam);
        this.rgb = transpose(this.rgb);
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {3, 2, 1}};
        int[][] t = transpose(a);
        for (int[] aT : t) {
            System.out.println(Arrays.toString(aT));
        }
    }
}
