package com.nevars;


import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

/**
 * Created by erafiil on 25.03.15.
 */
public class DCT {

    private final int N = 8;
    private final double PI = Math.PI;

    public int[][][] idct(int matrix[][][], int F[][], int row, int column, int color) {
        double tempX[][] = new double[N][N];
        double tempY[][] = new double[N][N];
        int f[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempY[i][j] = (double)F[i][j];
            }
        }

        idct_direct(tempY, tempX);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                f[i][j] = (int)(floor(tempX[i][j] + 0.5));
            }
        }
        return fillMatrix(matrix, f, row, column, color);
    }

    private void idct_direct(double F[][], double f[][]) {

        double a[] = new double[N];
        a[0] = sqrt(1. / N);
        for (int i = 1; i < N; i++) {
            a[i] = sqrt(2. / N);
        }

        double summ = 0;
        double koeff = 0;
        for (short i = 0; i < N; i++) {
            for (short j = 0; j < N; j++) {
                summ = 0.;
                for (short u = 0; u < N; u++) {
                    for (short v = 0; v < N; v++) {
                        koeff = cos((2 * j + 1) * v * PI / (2 * N)) *
                                cos((2 * i + 1) * u * PI / (2 * N));
                        summ += a[u] * a[v] * F[u][v] * koeff;
                    }
                    f[i][j] = summ;
                }
            }
        }
    }


    public int[][] dct(int matrix[][][], int row, int column, int colour) {
        int result[][] = new int[N][N];
        int f[][] = getBlock(matrix, row,column, colour);

        double a[] = new double[N];
        a[0] = sqrt(1. / N);
        for (int i = 1; i < N; i++) {
            a[i] = sqrt(2. / N);
        }

        double summ = 0;
        double koeff = 0;
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                summ = 0.;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        koeff = cos((2 * i + 1) * u * PI / (2 * N)) *
                                cos((2 * j + 1) * v * PI / (2 * N));
                        summ += f[i][j] * koeff;
                    }
                    result[u][v] = (int)floor(a[u] * a[v] * summ + 0.5);
                }
            }
        }
        return f;
    }

    private void dct_direct(double f[][], double F[][]) {
        double a[] = new double[N];
        a[0] = sqrt(1. / N);
        for (int i = 1; i < N; i++) {
            a[i] = sqrt(2. / N);
        }

        double summ = 0;
        double koeff = 0;
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                summ = 0.;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        koeff = cos((2 * i + 1) * u * PI / (2 * N)) *
                                cos((2 * j + 1) * v * PI / (2 * N));
                        summ += f[i][j] * koeff;
                    }
                    F[u][v] = a[u] * a[v] * summ;
                }
            }
        }
    }

    private int[][][] fillMatrix(int matrix[][][], int block[][], int row, int column, int color) {
        int x = 0;
        int y = 0;
        for (int i = row; i < row + N; i++) {
            for (int j = column; j < column + N; j++) {
                matrix[i][j][color] = block[x][y++];
            }
            y = 0;
            x++;
        }
        return matrix;
    }

    private int[][] getBlock(int matrix[][][], int row, int column, int color) {
        int block[][] = new int[N][N];
        int x = 0;
        int y = 0;
        for (int i = row; i < row + N; i++) {
            for (int j = column; j < column + N; j++) {
                block[x][y++] = matrix[i][j][color];
            }
            y = 0;
            x++;
        }
        return block;
    }
}