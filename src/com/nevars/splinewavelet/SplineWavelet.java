package com.nevars.splinewavelet;

import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.pow;

/**
 * Created by erafiil on 05.04.15.
 */
public class SplineWavelet {

    public SplineWavelet(double[] c) {
        net                = new IrregularNet(c, c.length);
        this.irregularNet  = net.getIrregularNet(); // net contains 1 and 0
        this.x             = net.getSourceNet(); // исходная сетка
        this.c             = c;
        SIZE_SOURCE_NET    = x.size();
    }

    public void compress() {
        int xi;
        int xiInd;
        ArrayList<Integer> indexes = net.getIndexes();
        b = new Stack<Double>();
        int increaseStep = 0;
        int SIZE = x.size();
        System.out.println("Deleted nodes:\n" + indexes);
        for (int index : indexes) {
            --SIZE;
            a = new double[SIZE];
            xiInd = index - increaseStep;
            xi = x.get(xiInd);

            for (int i = 0; i <= xiInd - 3; i++) {
                a[i] = c[i];
            }
            a[xiInd - 2] = -(x.get(xiInd) - xi) * pow(xi - x.get(xiInd - 2), -1) * c[xiInd - 3] +
                    c[xiInd - 2] * (x.get(xiInd) - x.get(xiInd - 2)) * pow(xi - x.get(xiInd - 2), -1);
            for (int i = xiInd - 1; i < SIZE; i++) {
                a[i] = c[i + 1];
            }
            double n1 = ( (x.get(xiInd + 1) - xi) * (x.get(xiInd) - xi) * c[xiInd - 3] );
            double n2 = -( (x.get(xiInd + 1) - xi) * (x.get(xiInd) - x.get(xiInd - 2)) * c[xiInd - 2] );
            double n3 = ( (x.get(xiInd + 1) - x.get(xiInd - 1)) * (xi - x.get(xiInd - 2) ) * c[xiInd - 1] );
            double n4 = -( (xi - x.get(xiInd - 1)) * (xi - x.get(xiInd - 2)) * c[xiInd] );
            double n = n1 + n2 + n3 + n4;

            double m = pow(x.get(xiInd + 1) - x.get(xiInd - 1), -1) * pow(xi - x.get(xiInd - 2), -1);  // item 1.17
            b.push(n * m);
            x.remove(xiInd);
            c = a;
            increaseStep++;
        }
    }

    public void decompress(int irregularIndexes[], double a[], Stack<Double> b, double[] source) {
        x.clear();
        System.out.println("Non-deleted nodes:");
        for (int i = 0; i < irregularIndexes.length; i++) { // воспроизводим узлы, которые не удаляли
            if (irregularIndexes[i] == 1) {
                x.add(i + 1);
                System.out.print((i) + " ");
            }
        }
        int size = irregularIndexes.length;
        int decreaseIndex = 0;
        int ind;
        double c[];
        int SIZE = x.size();
        for (int i = size - 1; i > 0; i--) {
            if (irregularIndexes[i] == 0) {
                ind = x.size() - decreaseIndex;
                int ksi = i + 1;
                x.add(ind, ksi);
                ++SIZE;

                c = new double[SIZE];
                for (int j = 0; j <= ind - 3; j++) {
                    c[j] = a[j];
                }

                c[ind - 2] = a[ind - 3] * (x.get(ind) - ksi) * pow(x.get(ind) - x.get(ind - 2), -1) +
                        a[ind - 2] * (ksi - x.get(ind - 2)) * pow(x.get(ind) - x.get(ind - 2), -1);

                c[ind - 1] = a[ind - 2] * (x.get(ind + 1) - ksi) * pow(x.get(ind + 1) - x.get(ind - 1), -1) +
                        a[ind - 1] * (ksi - x.get(ind - 1)) * pow(x.get(ind + 1) - x.get(ind - 1), -1) + b.pop();
                for (int j = ind; j < SIZE; j++) {
                    c[j] = a[j - 1];
                }
                a = c;
            }
            decreaseIndex++;
        }
        System.out.println("\n\nRecovered net: ");
        System.out.println(x);
        System.out.println("Size net = " + x.size());
        System.out.println("\n--------RESULT OF DECOMPRESSING--------");
        PrintStream.print(a, source);
        System.out.println("-----------END DECOMPRESSING-----------");
    }

    public ArrayList<Integer> getNet() {
        return x;
    }

    public double[] getMainStream() {
        return a;
    }

    public Stack<Double> getWaveletStream() {
        return b;
    }

    private Stack<Double> b;
    private double[] a;
    private double[] c;
    private int SIZE_SOURCE_NET;
    private ArrayList<Integer> x;
    private int irregularNet[];
    private IrregularNet net;
}
