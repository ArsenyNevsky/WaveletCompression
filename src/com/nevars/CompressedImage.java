package com.nevars;

import java.io.Serializable;

/**
 * Created by erafiil on 25.03.15.
 */
public class CompressedImage implements Serializable {

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private String nameImage;

    private int height;

    private int width;

}
