package com.nevars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.round;

public class Main {

    public static String byteToString(int b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static void main(String[] args) throws IOException {
        Coder coder = new Coder("image6.bmp");
        coder.compress();
        //coder.compressing();

        Decoder decoder = new Decoder();
        decoder.decompress();
        //decoder.decompressing();

        List<Integer> l = new ArrayList<>();
        l.add(100);
        l.add(254);
        l.add(-12);
        l.add(4);
        l.add(5);
        l.add(0);

        int min = Collections.min(l);
        int max = Collections.max(l);
        System.out.println("Max = " + max + "\nMin = " + min);
        float array[] = new float[l.size()];
        for (int i = 0; i < l.size(); i++) {
            array[i] = round(((l.get(i) - min) / (float) (max - min))*255);
            System.out.print(array[i] + " ");

        }
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(round(array[i] * (max - min) / 255 + min)+ " ");
        }
    }
}
