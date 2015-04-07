package com.nevars.splinewavelet;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;


public class IrregularNet {

    public IrregularNet(double c[], int N) {
        this.N = N;
        this.c = c;
        x      = generateSourceNet();
        C      = getC();
        xx     = new int[N];
        generateIndexesIrregularNet();
    }

    // массив нулей и единиц
    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    public int[] getIrregularNet() {
        return xx;
    }

    public ArrayList<Integer> getSourceNet() {
        return x;
    }

    private void generateIndexesIrregularNet() {
        indexes = new ArrayList<Integer>();
        Arrays.fill(xx, 1);
        int S = 3;
        int previousS;
        int currentS;
        while (S < N) { // или S < N - 3
            currentS = getNewIndexS(S);
            //System.out.println("previousStepS = " + S + " || currentStepS = " + currentS);
            for (int i = S; i < (S + currentS - 1) && (i < N - 3); i++) {
                xx[i] = 0;
                indexes.add(i);
            }
            S += currentS;
        }
        System.out.println();
    }

    private ArrayList<Integer> generateSourceNet() {
        x = new ArrayList<Integer>(N);
        for (int i = 0; i < N; i++) {
            x.add(i + 1);
        }
        return x;
    }

    private int getNewIndexS(int i) {
        //q = getQ(i);
        q = 10;
        int g = 10;
        double P = 0.6;
        double summ = 0;
        /*for (int j = i; j < i + q - 1 && (i + q - 1) < N; j++) {
            summ += abs((c.get(j) - c.get(j - 1)) / (float) (x.get(j) - x.get(j - 1)));
        }*/
        for (int j = i; j < min(i + q - 1, N); j++) {
            summ += abs((c[j] - c[j-1]) / (float) (x.get(j) - x.get(j - 1)));
        }
        int S = (int) round(C / (P * (summ / q + C / (double) g)));
        return max(1, S);
    }

    private double getC() {
        double C = 0;
        for (int i = 0; i < N - 1; i++) {
            //C += abs((c[i] - c[i - 1]) / (float) (x[i] - x[i - 1])); // if index i start from 1 to N
            C += abs((c[i + 1] - c[i]) / (double) (x.get(i + 1) - x.get(i)));
        }
        return C / N;
    }

    private int getQ(int i) {
        return min(10, N - i);
    }

    private ArrayList<Integer> indexes;
    private int xx[];
    private double C;
    private int q = 10;
    private double[] c;
    private ArrayList<Integer> x;
    private final int N;
}
