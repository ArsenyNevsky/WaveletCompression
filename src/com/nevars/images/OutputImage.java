package com.nevars.images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by erafiil on 25.03.15.
 */
public class OutputImage extends AbstractImage {

    public OutputImage(int height, int width, int matrix[][][]) {
        HEIGHT = height;
        WIDTH  = width;
        this.matrix = matrix;
        pixelImageArray = new int[HEIGHT * WIDTH];
    }

    public void saveImage() {
        System.out.println("            START SAVING");
        int ind = 0;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                convertYCBCRtoRGB(matrix[row][col][0], matrix[row][col][1], matrix[row][col][2]);
                pixelImageArray[ind++] = getPixelValue(R, G, B);
            }
        }
        try {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, WIDTH, HEIGHT, pixelImageArray, 0, WIDTH);
            ImageIO.write(image, "bmp", new FileOutputStream("DECOMPRESSED_RESULT.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("            END SAVING");
    }

    private void convertYCBCRtoRGB(double Y, double Cb, double Cr) {
        R = (int)(Y + (Cr - 128) * 1.402);
        G = (int)(Y - (Cb - 128) * 0.34414 - (Cr - 128) * 0.71414);
        B = (int)(Y + 1.772 * (Cb - 128));
        R = R > 255 ? 255 : (R < 0 ? 0 : R);
        G = G > 255 ? 255 : (G < 0 ? 0 : G);
        B = B > 255 ? 255 : (B < 0 ? 0 : B);

    }

    private int getPixelValue(int r, int g, int b) {
        return (r << 16 | g << 8 | b);
    }


    private int R;
    private int G;
    private int B;
    private final int HEIGHT;
    private final int WIDTH;
    private int matrix[][][];
    private BufferedImage image;
    private int pixelImageArray[];
}
