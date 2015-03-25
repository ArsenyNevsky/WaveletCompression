package com.nevars.wavelets;

import static java.lang.Math.round;
/**
 * Created by erafiil on 25.03.15.
 */
public class Haar {

    public float[][] directTransformation(int matrix[][][], int row, int column, int colour) {
        int ind = 0;
        for (int r = row; r < row + BLOCK; r++) { // копируем в одномерный массив
            for (int c = column; c < column + BLOCK; c++) {
                coeff[ind++] = matrix[r][c][colour];
            }
        }
        for (int i = 0; i < (BLOCK * BLOCK) / 2; i++) { // вычисления
            c[2 * i] = (float)((coeff[2 * i] + coeff[2 * i + 1]) / 2.0);
            c[2 * i + 1] = (float)((coeff[2 * i] - coeff[2 * i + 1]) / 2.0);
        }
        ind = 0;
        for (int i = 0; i < BLOCK; i++) { // обратно заносим значения в матрицу
            for (int j = 0; j < BLOCK; j++) {
                result[i][j] = c[ind++];
            }
        }
        return result;
    }


    public int[][][] inverseTransformation(int matrix[][][], int block[][], int row, int column, int colour) {
        int ind = 0;
        for (int i = 0; i < BLOCK; i++) { // копируем в одномерный массив
            for (int j = 0; j < BLOCK; j++) {
                coeff[ind++] = block[i][j];
            }
        }
        for (int i = 0; i < (BLOCK * BLOCK) / 2; i++) { // вычисления
            a[2 * i] = round((coeff[2 * i] + coeff[2 * i + 1]));
            a[2 * i + 1] = round((coeff[2 * i] - coeff[2 * i + 1]));
        }
        ind = 0;
        for (int r = row; r < row + BLOCK; r++) { // обратно заносим значения в матрицу
            for (int c = column; c < column + BLOCK; c++) {
                matrix[r][c][colour] = a[ind++];
            }
        }
        return matrix;
    }

    private final int BLOCK = 8;
    private float coeff[] = new float[BLOCK * BLOCK];
    private float c[] = new float[BLOCK * BLOCK];
    private int a[] = new int[BLOCK * BLOCK];
    private float result[][] = new float[BLOCK][BLOCK];
}
