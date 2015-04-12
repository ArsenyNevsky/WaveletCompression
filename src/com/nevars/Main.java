package com.nevars;

import java.io.IOException;
public class Main {

    public static String byteToString(int b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static void main(String[] args) throws IOException {
        Coder coder = new Coder("image0.bmp");
        coder.compress();

        //Decoder decoder = new Decoder();
        //decoder.decompress();
    }
}
