package com.nevars;

import com.nevars.converter.Decoding;
import com.nevars.converter.coderWavelet.Coder;

import java.io.IOException;

public class Main {

    public static String byteToString(int b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static String[] toByte(String s) {
        String a = "000000000000000100000010000000110000010000000101000001100000011100001000000010010000101011111111";
        String res[] = new String[11];
        int ind = 0;
        for (int i = 0; i < s.length(); i += 8) {
            System.out.println(s.substring(i, i+8));
        }
        System.out.println();
        for (String ss : res) {
            System.out.println(ss);
        }
        return res;
    }

    public static void toInt(String b[]) {
        System.out.println("Length");
        int c[] = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = Integer.parseInt(b[i], 2);
            System.out.println(c[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        Coder coder = new Coder("image3.bmp");
        coder.compress();

        Decoding decoder = new Decoding();
        decoder.decompress();
    }
}
