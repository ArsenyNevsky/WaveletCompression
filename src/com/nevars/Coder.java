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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import static java.lang.Math.round;

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
        outputStream = new ArrayList<>();

        for (int lay = 0; lay < COUNT_COLOR_LAYS; lay++) {
            for (int row = 0; row < HEIGHT; row += STEP) {
                for (int column = 0; column < WIDTH; column += STEP) {
                    result     = haar.directTransformation(matrix, row, column, lay);
                    quantArray = quant.directQuant(result);
                    fillOutputStream(zigZag.getHideZigZagArray(quantArray));
                }
            }
        }
        List<Integer> subList = outputStream.subList(0, HEIGHT * WIDTH);
        int max[] = new int[3];
        int min[] = new int[3];
        max[0] = Collections.max(subList);
        min[0] = Collections.min(subList);

        subList = outputStream.subList(HEIGHT * WIDTH, 2 * HEIGHT * WIDTH);
        max[1] = Collections.max(subList);
        min[1] = Collections.min(subList);

        subList = outputStream.subList(2 * HEIGHT * WIDTH, 3 * HEIGHT * WIDTH);
        max[2] = Collections.max(subList);
        min[2] = Collections.min(subList);

        int start = 0;
        int end;
        for (int i = 0; i < 3; i++) {
            end = (i + 1) * HEIGHT * WIDTH;
            for (int j = start; j < end; j++) {
                outputStream.set(j, round((outputStream.get(j) - min[i]) / (float) (max[i] - min[i]) * 255));
            }
            start = end;
        }
        // ходит наша бабушка палочкой стуча
        huffman.compress(outputStream);
        saveCompressedImage(max, min);
        System.out.println("        END COMPRESSING\n\n" +
                "------------------------------------------------\n");
    }

    public void compressing() {
        System.out.println("START CODER");
        System.out.println("\n        START COMPRESSING");
        matrix = image.getYCbCrMatrix();
        outputStream = new ArrayList<>(HEIGHT * WIDTH * 3);
        ArrayList<Integer> subStream = new ArrayList<>();
        int min[] = new int[COUNT_COLOR_LAYS];
        int max[] = new int[COUNT_COLOR_LAYS];
        int start = 0;
        int end;
        for (int color = 0; color < COUNT_COLOR_LAYS; color++) {
            for (int row = 0; row < HEIGHT; row++) {
                for (int column = 0; column < WIDTH; column++) {
                subStream.add(matrix[row][column][color]);
                }
            }
            outputStream.addAll(haar.directHaar(subStream));
            min[color] = Collections.min(subStream);
            max[color] = Collections.max(subStream);
            subStream.clear();
            end = (color + 1) * HEIGHT * WIDTH;
            for (int j = start; j < end; j++) {
                outputStream.set(j, round((outputStream.get(j) - min[color]) / (float) (max[color] - min[color]) * 255));
            }
            start = end;
        }
        huffman.compress(outputStream);
        try {
            saveCompressedImage(max, min);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("        END COMPRESSING\n\n" +
                "------------------------------------------------\n");
    }

    public int[] getOutputStream() {
        return out;
    }

    private void fillOutputStream(int block[]) {
        for (int elem : block) {
            outputStream.add(elem);
        }
    }

    private void saveCompressedImage(int max[], int min[]) throws IOException {
        String codes = "";
        //String codes = huffman.getCodes();
        String table = "";
       // String table = huffman.getTable();
        compressedImage = new CompressedImage();
        compressedImage.setHeight(HEIGHT);
        compressedImage.setWidth(WIDTH);

        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(new File("codes.nevbin")));
        gzipOutputStream.write(codes.getBytes());
        gzipOutputStream.flush();
        gzipOutputStream.close();
        compressedImage.setTree(codes);
        compressedImage.setTable(table);
        compressedImage.setMax(max);
        compressedImage.setMin(min);
        FileOutputStream fos = new FileOutputStream(new File("RESULT.nev"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(compressedImage);
        oos.flush();
        oos.close();
        fos.flush();
        fos.close();
    }

    private int out[];
    private CompressedImage compressedImage;
    private ArrayList<Integer> outputStream;
    private float result[][];
    private int quantArray[][];
    private InputImage image;
}
