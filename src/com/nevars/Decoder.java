package com.nevars;

/**
 * Created by erafiil on 25.03.15.
 */

import com.nevars.converter.AbstractConverterImage;
import com.nevars.convolutions.ZigZag;
import com.nevars.images.OutputImage;
import com.nevars.quant.Quant;
import com.nevars.wavelets.Haar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by erafiil on 25.03.15.
 */
public class Decoder extends AbstractConverterImage {

    public Decoder() throws IOException {
        quant       = new Quant();
        zigZag      = new ZigZag();
        haar        = new Haar();
        huffman     = new com.nevars.huffmanZiP.Huffman();
        dct         = new DCT();
        huffman.decompress();
        inputStream = huffman.getArray();
        try (FileInputStream fis = new FileInputStream("imageProperties.attrib");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            compressedImage = (CompressedImage)ois.readObject();

        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HEIGHT = compressedImage.getHeight();
        WIDTH  = compressedImage.getWidth();
        matrix = new int[HEIGHT][WIDTH][COUNT_COLOR_LAYS];
    }

    public void decompress() {
        System.out.println("START DECODER");
        System.out.println("\n        START DECOMPRESSING");
        for (int lay = 0; lay < COUNT_COLOR_LAYS; lay++) {
            for (int row = 0; row < HEIGHT; row += STEP) {
                for (int column = 0; column < WIDTH; column += STEP) {
                    block = zigZag.getExpandArray(inputStream, ind);
                    ind += 64;
                    block = quant.converseQuant(block);
                    matrix = haar.inverseTransformation(matrix, block, row, column, lay);
                    //matrix = dct.idct(matrix, block, row, column, lay);
                }
            }
        }
        image = new OutputImage(HEIGHT, WIDTH, matrix);
        image.saveImage();
        System.out.println("        END DECOMPRESSING\n\n------------END WORKING OF ALGORITHMS------------\n");
    }

    private CompressedImage compressedImage;
    private int ind = 0;
    private int inputStream[];
    private int block[][];
    private OutputImage image;
}
