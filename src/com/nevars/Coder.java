package com.nevars;

import com.nevars.converter.AbstractConverterImage;
import com.nevars.convolutions.ZigZag;
import com.nevars.huffman.Huffman;
import com.nevars.images.InputImage;
import com.nevars.quant.Quant;
import com.nevars.wavelets.Haar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by erafiil on 25.03.15.
 */
public class Coder extends AbstractConverterImage {

    public Coder(String nameFile) throws IOException {
        image   = new InputImage(nameFile);
        HEIGHT  = image.getHeight();
        WIDTH   = image.getWidth();
        zigZag  = new ZigZag();
        quant   = new Quant();
        huffman = new Huffman();
        haar    = new Haar();
        dct     = new DCT();
    }

    public void compress() throws IOException {
        System.out.println("START CODER");
        System.out.println("\n        START COMPRESSING");
        matrix = image.getYCbCrMatrix();
        outputStream = new int[HEIGHT * WIDTH * 3];
        int resul[][];
        for (int lay = 0; lay < COUNT_COLOR_LAYS; lay++) {
            for (int row = 0; row < HEIGHT; row += STEP) {
                for (int column = 0; column < WIDTH; column += STEP) {
                    result     = haar.directTransformation(matrix, row, column, lay);
                    /*resul    = dct.dct(matrix, row, column, lay);
                    quantArray = quant.directQuant(resul);*/
                    quantArray = quant.directQuant(result);
                    fillOutputStream(zigZag.getHideZigZagArray(quantArray));
                }
            }
        }
        // ходит наша бабушка палочкой стуча
        /*float rowMatrix[];
        ArrayList<Float> res = new ArrayList<>();
        for (int lay = 0; lay < COUNT_COLOR_LAYS; lay++) {
            for (int row = 0; row < HEIGHT; row++) {
                rowMatrix = haar.directHaar(matrix, row, 0, WIDTH, lay);
                for (float e : rowMatrix) {
                    res.add(e);
                }
            }
        }
        float min = Collections.min(res);
        float max = Collections.max(res);
        for (int i = 0; i < res.size(); i++) {
            res.set(i, round(res.get(i) + min) / max);
        }*/
        huffman.compress(outputStream);
        saveCompressedImage();
        System.out.println("        END COMPRESSING\n\n------------------------------------------------\n");
    }

    private void fillOutputStream(int block[]) {
        for (int elem : block) {
            outputStream[ind++] = elem;
        }
    }

    private void saveCompressedImage() throws IOException {
        String codes = huffman.getCodes();
        String table = huffman.getTable();
        compressedImage = new CompressedImage();
        compressedImage.setHeight(HEIGHT);
        compressedImage.setWidth(WIDTH);
        compressedImage.setTree(codes);
        compressedImage.setTable(table);
        FileOutputStream fos = new FileOutputStream(new File("RESULT.nev"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(compressedImage);
        oos.flush();
        oos.close();
        fos.flush();
        fos.close();
    }

    private CompressedImage compressedImage;
    private int ind = 0;
    private int outputStream[];
    private float result[][];
    private int quantArray[][];
    private InputImage image;
}
