package com.nevars.huffmanZiP;

import java.io.Serializable;

/**
 * Created by erafiil on 10.04.15.
 */
class Node implements Serializable {

    Node() {

    }

    Node(char symbol, int frequency) {
        this.symbol    = symbol;
        this.frequency = frequency;
    }

    Node(Node node1, Node node2) {
        frequency = node1.getFrequency() + node2.getFrequency();
        if (node1.getFrequency() > node2.getFrequency()) {
            right = node1;
            left  = node2;
        } else {
            right = node2;
            left  = node1;
        }
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }


    private Node left;

    private Node right;

    private char symbol;

    private int frequency;
}
