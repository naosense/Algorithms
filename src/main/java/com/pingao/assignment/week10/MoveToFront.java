package com.pingao.assignment.week10;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


/**
 * Created by pingao on 2018/12/10.
 */
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] extendAscii = initExtendAscii();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (int i = 0; i < 256; i++) {
                if (c == extendAscii[i]) {
                    BinaryStdOut.write(i, 8);
                    moveToFront(extendAscii, i);
                    break;
                }
            }
        }
        BinaryStdOut.close();
    }

    private static void moveToFront(char[] chars, int index) {
        char c = chars[index];
        System.arraycopy(chars, 0, chars, 1, index);
        chars[0] = c;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] extendAscii = initExtendAscii();
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readInt(8);
            BinaryStdOut.write(extendAscii[i]);
            moveToFront(extendAscii, i);
        }
        BinaryStdOut.close();
    }

    private static char[] initExtendAscii() {
        char[] extendAscii = new char[256];
        for (int i = 0; i < 256; i++) {
            extendAscii[i] = (char) i;
        }
        return extendAscii;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                encode();
                break;
            case "+":
                decode();
                break;
            default:
                throw new IllegalArgumentException("args[0] must be '-' or '+'");
        }
    }
}
