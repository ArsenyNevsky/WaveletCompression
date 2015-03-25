package com.nevars.converter;

import com.nevars.DCT;
import com.nevars.convolutions.ZigZag;
import com.nevars.huffman.Huffman;
import com.nevars.quant.Quant;
import com.nevars.wavelets.Haar;

/**
 * Created by erafiil on 25.03.15.
 */
public class AbstractConverterImage {

    protected final short STEP             = 8;
    protected final short BLOCK_STEP       = 64;
    protected final short COUNT_COLOR_LAYS = 3;
    protected int HEIGHT;
    protected int WIDTH;
    protected Huffman huffman;
    protected ZigZag zigZag;
    protected Quant quant;
    protected int matrix[][][];
    protected Haar haar;
    protected DCT dct;
}
