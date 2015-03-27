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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    private String nameImage;

    private int height;

    private int width;

    private String table;

    private String tree;

    public int[] getMax() {
        return max;
    }

    public void setMax(int max[]) {
        this.max = max;
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int min[]) {
        this.min = min;
    }

    private int max[];
    private int min[];
}
