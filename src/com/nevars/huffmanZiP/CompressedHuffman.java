package com.nevars.huffmanZiP;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by erafiil on 10.04.15.
 */
public class CompressedHuffman implements Serializable {

    public Node getTree() {
        return tree;
    }

    public void setTree(Node tree) {
        this.tree = tree;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(StringBuilder sequence) throws UnsupportedEncodingException {
        this.sequence = new String(sequence.toString().getBytes(), 0, sequence.length(), "UTF-8");
    }

    public void setHasTail(boolean hasTail) {
        this.hasTail = hasTail;
    }

    public void setSymbols(char symbols[]) {
        this.symbols = symbols;
    }

    public char[] getSymbols() {
        return symbols;
    }

    public boolean getHasTail() {
        return hasTail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getTail() {
        return tail;
    }

    public void setHEIGHT(short HEIGHT, short WIDTH) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
    }

    public short getHEIGHT() {
        return HEIGHT;
    }

    public short getWIDTH() {
        return WIDTH;
    }

    private Node tree;
    private String tail;
    private String sequence;
    private char[] symbols;
    private boolean hasTail;

    private short HEIGHT;
    private short WIDTH;
}
