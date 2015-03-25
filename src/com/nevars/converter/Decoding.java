package com.nevars.converter;

/**
 * Created by erafiil on 25.03.15.
 */

import com.nevars.CompressedImage;
import com.nevars.convolutions.ZigZag;
import com.nevars.huffman.Huffman;
import com.nevars.images.OutputImage;
import com.nevars.quant.Quant;
import com.nevars.wavelets.Haar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by erafiil on 25.03.15.
 */
public class Decoding extends AbstractConverterImage {

    public Decoding() {
        quant       = new Quant();
        zigZag      = new ZigZag();
        haar        = new Haar();
        huffman     = new Huffman();
        inputStream = huffman.decompressedStream();
        try (FileInputStream fis = new FileInputStream("RESULT.nev");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            compressedImage = (CompressedImage)ois.readObject();

        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HEIGHT = compressedImage.getHeight();
        WIDTH  = compressedImage.getWidth();
        System.out.println("HEIGHT = " + HEIGHT+ "\nWIDTH = " + WIDTH);
        matrix = new int[HEIGHT][WIDTH][COUNT_COLOR_LAYS];
    }

    public void decompress() {
        System.out.println("\n\n----------START DECOMPRESSING--------");
        for (int lay = 0; lay < COUNT_COLOR_LAYS; lay++) {
            for (int row = 0; row < HEIGHT; row += STEP) {
                for (int column = 0; column < WIDTH; column += STEP) {
                    block = zigZag.getExpandArray(inputStream, ind);
                    ind += 64;
                    block = quant.converseQuant(block);
                    matrix = haar.inverseTransformation(matrix, block, row, column, lay);
                }
            }
        }
        image = new OutputImage(HEIGHT, WIDTH, matrix);
        image.saveImage();
        System.out.println("-------END DECOMPRESSING--------");
    }

    private CompressedImage compressedImage;
    private int ind = 0;
    private int inputStream[];
    private int block[][];
    private OutputImage image;
}
