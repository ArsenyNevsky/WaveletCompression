package com.nevars.images;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by erafiil on 25.03.15.
 */
public class InputImage extends AbstractImage {

    public InputImage(String nameImage) throws IOException {
        img = ImageIO.read(new File(nameImage));
        HEIGHT = img.getHeight();
        //HEIGHT = 4;
        WIDTH  = img.getWidth();
        //WIDTH  = 4;
        convertPixelsToYCbCr();
    }

    public int[][][] getYCbCrMatrix() {
        return matrixImage;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }

    private void convertPixelsToYCbCr() {
        matrixImage = new int[HEIGHT][WIDTH][NUMBER_COLOR_CANALS];
        int rgb;
        float R;
        float G;
        float B;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                rgb = img.getRGB(col, row);
                matrixImage[row][col][0] = (rgb >> 16) & 0xff; //red
                matrixImage[row][col][1] = (rgb >> 8) & 0xff; // green
                matrixImage[row][col][2] = rgb & 0xff; // blue

                R = matrixImage[row][col][0];
                G = matrixImage[row][col][1];
                B = matrixImage[row][col][2];

                matrixImage[row][col][0] = (int)(0.299 * R + 0.587 * G + 0.114 * B); // Y channel
                matrixImage[row][col][1] = (int)(128 - (0.1687 * R) - (0.3313 * G) + 0.5 * B); // Cb channel
                matrixImage[row][col][2] = (int)(128 + (0.5 * R) - (0.4187 * G) - 0.0813 * B); // Cr channel
            }
        }
    }

    private int matrixImage[][][];
    private final int HEIGHT;
    private final int WIDTH;
}
