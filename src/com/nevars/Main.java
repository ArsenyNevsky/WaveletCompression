package com.nevars;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static int N = 100;

    private double initC(double f[], int x[]) {
        double c = 0;
        for (int i = 0; i < N - 2; i++) {
            c += (f[x[i + 1]] - f[x[i]]) / (x[i + 1] - x[i]);
        }
        c = Math.abs(c);
        return c / (double)N;
    }

    private double initS(double f[], int x[]) {
        int ind[] = new int[N];
        int S = 0;
        double P = 0.5;
        int h = 1;
        double C = initC(f, x);
        int g = 10;
        return Math.max(1, S);
    }

    private int[] initNet(int x[], double f[]) {
        int ind[] = new int[N];
        int index = 0;
        Arrays.fill(ind, 0);
        ind[0] = 1;
        ind[1] = 1;
        ind[2] = 1;
        int S = 3;
        double P = 0.5;
        int h = 1;
        double C;
        int g = 7;
        //int g = 12;
        while (S < N) {
            //S = initS(f, x);
        }
        return new int[9];
    }
    
    public static void main(String[] args) throws IOException {
        int x[] = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = i + 1;    
        }
        
        double c[] = new double[N];
        for (int i = 0; i < N; i++) {
            c[i] = Math.cos(i);
        }


    }
}
