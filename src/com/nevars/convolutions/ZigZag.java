package com.nevars.convolutions;

/**
 * Created by erafiil on 25.03.15.
 */
public class ZigZag {

    public int[] getHideZigZagArray(int[][] f) {
        int i = 1;
        int j = 1;
        int k = 0;
        int arrayCoeff[] = new int[SIZE * SIZE];
        for (int element = 0; element < SIZE * SIZE; element++) {
            arrayCoeff[k] = f[i - 1][j - 1];
            k++;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < SIZE) {
                    j++;
                } else {
                    i += 2;
                }
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < SIZE)
                    i++;
                else
                    j += 2;
                if (j > 1)
                    j--;
            }
        }
        arrayCoeff[SIZE * SIZE - 1] = f[SIZE - 1][SIZE - 1];
        return arrayCoeff;
    }

    public int[][] getExpandArray(int[] arrayHuffman, int ind) {
        int i = 1;
        int j = 1;
        int k = ind;
        int arrayCoeff[][] = new int[SIZE][SIZE];
        for (int element = 0; element < SIZE * SIZE; element++) {
            arrayCoeff[i - 1][j - 1] = arrayHuffman[k];
            k++;
            if ((i + j) % 2 == 0) {
                // Even stripes
                if (j < SIZE) {
                    j++;
                } else {
                    i += 2;
                }
                if (i > 1)
                    i--;
            } else {
                // Odd stripes
                if (i < SIZE)
                    i++;
                else
                    j += 2;
                if (j > 1)
                    j--;
            }
        }
        return arrayCoeff;
    }

    private final int SIZE = 8;
}