package com.nevars.quant;

import static java.lang.Math.round;

/**
 * Created by erafiil on 25.03.15.
 */
public class Quant {

    public Quant() {
        block8x8 = new int[BLOCK][BLOCK];
    }



    public int[][] directQuant(float matrix[][]) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < BLOCK; i++) {
            for (int j = 0; j < BLOCK; j++) {
                block8x8[i][j] = round(matrix[i][j] / tableQuant[i][j]);
            }
        }
        return block8x8;
    }

    public int[][] directQuant(int matrix[][]) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < BLOCK; i++) {
            for (int j = 0; j < BLOCK; j++) {
                block8x8[i][j] = round(matrix[i][j] / tableQuant[i][j]);
            }
        }
        return block8x8;
    }

    public int[][] converseQuant(int matrix[][]) {
        for (int i = 0; i < BLOCK; i++) {
            for (int j = 0; j < BLOCK; j++) {
                block8x8[i][j] = matrix[i][j] * tableQuant[i][j];
            }
        }
        return block8x8;
    }


    private final int BLOCK = 8;
    private int block8x8[][];
    private final double[][] tableQuantY = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}
    };
    private final int[][] tableQuant = {
            {3, 5, 7, 9, 11, 13, 15, 17},
            {5, 7, 9, 11, 13, 15, 17, 19},
            {7, 9, 11, 13, 15, 17, 19, 21},
            {9, 11, 13, 15, 17, 19, 21, 23},
            {11, 13, 15, 17, 19, 21, 23, 25},
            {13, 15, 17, 19, 21, 23, 25, 27},
            {15, 17, 19, 21, 23, 25, 27, 29},
            {17, 19, 21, 23, 25, 27, 29, 31}
    };

    private final double[][] tableQuantC = {
            {17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 13, 16, 24, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}
    };
}
