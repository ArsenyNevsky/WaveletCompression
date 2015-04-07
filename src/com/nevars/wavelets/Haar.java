package com.nevars.wavelets;

import java.util.ArrayList;

import static java.lang.Math.round;
/**
 * Created by erafiil on 25.03.15.
 */
public class Haar {

    public ArrayList<Integer> directHaar(ArrayList<Integer> inputStream) {
        ArrayList<Integer> outputStream = new ArrayList<>(inputStream.size());
        final int SIZE = inputStream.size() / 2;
        for (int i = 0; i < SIZE; i++) {
            outputStream.add(2 * i, (int) round((inputStream.get(2 * i) + inputStream.get(2 * i + 1)) / 2.0));
            outputStream.add(2 * i, (int) round((inputStream.get(2 * i) - inputStream.get(2 * i + 1)) / 2.0));
        }
        return outputStream;
    }

    public ArrayList<Integer> converseHaar(ArrayList<Integer> inputStream) {
        ArrayList<Integer> outputStream = new ArrayList<>(inputStream.size());
        final int SIZE = inputStream.size() / 2;
        for (int i = 0; i < SIZE; i++) {
            outputStream.add(2 * i, round(inputStream.get(2 * i) + inputStream.get(2 * i + 1)));
            outputStream.add(2 * i, round(inputStream.get(2 * i) - inputStream.get(2 * i + 1)));
        }
        return outputStream;
    }

    public float[] directHaar(int matrix[][][], int row, int begin, int end, int colour) {
        int ind = 0;
        for (int i = begin; i < end; i++) {
            coeff[i] = matrix[row][i][colour];
        }
        for (int i = 0; i < end / 2; i++) {
            c[2 * i] = (float)((coeff[2 * i] + coeff[2 * i + 1]) / 2.0);
            c[2 * i + 1] = (float)((coeff[2 * i] - coeff[2 * i + 1]) / 2.0);
        }
        return c;
    }

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

    public int[][] doQuantization(int R, int[][] block) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                block[i][j] /= (1 + (i + j) * R);
            }
        }
        return block;
    }

    private final int BLOCK = 8;
    private int coeff[] = new int[BLOCK * BLOCK];
    private float c[] = new float[BLOCK * BLOCK];
    private int a[] = new int[BLOCK * BLOCK];
    private float result[][] = new float[BLOCK][BLOCK];

}
