package com.nevars;

/**
 * Created by erafiil on 25.03.15.
 */

import com.nevars.converter.AbstractConverterImage;
import com.nevars.convolutions.ZigZag;
import com.nevars.huffman.Huffman;
import com.nevars.images.OutputImage;
import com.nevars.quant.Quant;
import com.nevars.wavelets.Haar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by erafiil on 25.03.15.
 */
public class Decoder extends AbstractConverterImage {

    public Decoder() {
        quant       = new Quant();
        zigZag      = new ZigZag();
        haar        = new Haar();
        huffman     = new Huffman();
        dct         = new DCT();
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
        max = compressedImage.getMax();
        min = compressedImage.getMin();
        matrix = new int[HEIGHT][WIDTH][COUNT_COLOR_LAYS];
    }

    public void decompressing() {
        System.out.println("START DECODER\n" +
                "        START DECOMPRESSING");
        inputStream = huffman.decompressedStream();
        ArrayList<Integer> subStream = new ArrayList<>(COUNT_COLOR_LAYS * HEIGHT * WIDTH);
        int start = 0;
        int end;
        for (int color = 0; color < COUNT_COLOR_LAYS; color++) {
            end = (color + 1) * HEIGHT * WIDTH;
            for (int i = start; i < end; i++) {
                subStream.add(inputStream[i] * (max[color] - min[color]) / 255 + min[color]);

            }
            start = end;
            subStream = haar.converseHaar(subStream);
            int ind = 0;
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    matrix[i][j][color] = subStream.get(ind++);
                }
            }
            subStream.clear();
        }
        image = new OutputImage(HEIGHT, WIDTH, matrix);
        image.saveImage();
        System.out.println("        END DECOMPRESSING\n\n------------END WORKING OF ALGORITHMS------------\n");
    }

    public void decompress() {
        System.out.println("START DECODER");
        System.out.println("\n        START DECOMPRESSING");
        inputStream = new int[HEIGHT * WIDTH * COUNT_COLOR_LAYS];
        int in[] = huffman.decompressedStream();
        int start = 0;
        int end;
        for (int i = 0; i < 3; i++) {
            end = (i + 1) * HEIGHT * WIDTH;
            for (int j = start; j < end; j++) {
                inputStream[j] = (in[j] * (max[i] - min[i]) / 255 + min[i]);
            }
            start = end;
        }
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
        System.out.println("        END DECOMPRESSING\n\n------------END WORKING OF ALGORITHMS------------\n");
    }

    private int min[];
    private int max[];
    private CompressedImage compressedImage;
    private int ind = 0;
    private int inputStream[];
    private int block[][];
    private OutputImage image;
}
