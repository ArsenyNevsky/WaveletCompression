package com.nevars.huffmanZiP;


import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.zip.GZIPOutputStream;

/**
 * Created by erafiil on 10.04.15.
 */
public class Huffman {

    public Huffman() {
        tree = new HashMap<>();
        nodes = new PriorityQueue<>(new NodeComparator());
    }

    public Huffman(String inputStream) {
        this.inputStream = inputStream;
        nodes = new PriorityQueue<>(new NodeComparator());
        initFrequency(inputStream);
        tree = new HashMap<>();
    }

    public Huffman(int[] stream) {
        int item;
        char symbol;
        StringBuilder inputStream = new StringBuilder(stream.length);
        for (int elem : stream) {
            item = elem + 250;
            symbol = (char)item;
            inputStream.append(symbol);
        }
        this.inputStream = inputStream.toString();
        nodes = new PriorityQueue<>(new NodeComparator());
        initFrequency(this.inputStream);
        tree = new HashMap<>();
    }

    public int[] getArray() {
        return array;
    }

    public void compress() throws IOException {
        for (int i = 0; i < SIZE_ARRAY; i++) {
            if (frequency[i] > 0) {
                //System.out.println("Symbol = " + (char)i + "\t frequency = " + frequency[i]);
                nodes.add(new Node((char)i, frequency[i]));
            }
        }
        Node node1;
        Node node2;
        Node newNode;
        while (nodes.size() > 1) {
            node1 = nodes.poll();
            node2 = nodes.poll();
            newNode = new Node(node1, node2);
            nodes.add(newNode);
        }
        outputTree = nodes.peek();
        System.out.println();
        doMark(outputTree, ""); // помечаем узлы 1
        generateOutputString(); // генерируем поток из 0 и 1
        generateIntegerOutputString(); // из потока 0 и 1 генерируем байтовый поток
        writeCompressedResults(); // записываем дерево и байтовы поток в файл
    }

    public void decompress() throws IOException {
        readCompressedResults();
        generateByteInputString();
        StringBuilder accum = new StringBuilder();
        Node head = inputTree;
        Node temp = inputTree;
        for (int i = 0; i < inputString.length(); i++) {
            switch (inputString.charAt(i)) {
                case '0':
                    temp = temp.getLeft();
                    break;
                case '1':
                    temp = temp.getRight();
                    break;
            }
            if (temp.isLeaf()) {
                ///System.out.print(temp.getSymbol());
                accum.append(temp.getSymbol());
                temp = head;
            }
        }
        recoverArray(accum);
    }

    private void recoverArray(StringBuilder accum) {
        char numbers[] = accum.toString().toCharArray();
        int ind = 0;
        array = new int[numbers.length];
        for (char c : numbers) {
            array[ind++] = (int)c - 250;
        }
    }

    private void initFrequency(String inputStream) {
        frequency = new int[SIZE_ARRAY];
        for (int i = 0; i < inputStream.length(); i++) {
            frequency[inputStream.charAt(i)]++;
        }
    }

    private void doMark(Node root, String marker) {
        if (!root.isLeaf()) {
            doMark(root.getLeft(), marker + "0");
            doMark(root.getRight(), marker + "1");
        } else {
            //System.out.println("Symbol = " + root.getSymbol() + "\t code = " + marker);
            tree.put(root.getSymbol(), marker);
        }
    }

    /**
     * Генерируется выходной поток. Для каждого символа мы берем получившееся значение с помощью сжатия Хаффмана
     * byteOutputString
     */
    private void generateOutputString() {
        char symbols[] = inputStream.toCharArray();
        byteOutputString = new StringBuilder(symbols.length * 8);
        for (int i = 0; i < symbols.length; i++) {
            byteOutputString.append(tree.get(symbols[i]));
        }
    }

    /**
     * Из выходного потока 0 и 1 генерируется байтовый поток
     */
    private void generateIntegerOutputString() throws IOException {
        integerOutputString = new StringBuilder();
        final int SIZE = byteOutputString.length();
        int threshold = byteOutputString.length() / 8;
        int marker = byteOutputString.length() % 8;
        symbols = new char[threshold];
        int ind = 0;
        hasTail = marker > 0 ? false : true;
        for (int i = 0; i < SIZE - marker; i += 8) {
            integerOutputString.append((char)Integer.parseInt(byteOutputString.substring(i, i + 8), 2));
            symbols[ind++] = (char)Integer.parseInt(byteOutputString.substring(i, i + 8), 2);
        }
        tail = byteOutputString.substring(8 * threshold, 8 * threshold + marker);
    }

    /**
     * Из входного потока целых чисел генерируется битовый поток
     */
    private void generateByteInputString() {
        /*char symbol;
        inputString = new StringBuilder();
        if (hasTail) {
            for (int i = 0; i < symbols.length; i++) {
                symbol = symbols[i];
                inputString.append(byteToString(Integer.parseInt((int)symbol + "")));
            }
            inputString.append(tail);
        } else {
            for (int i = 0; i < symbols.length; i++) {
                symbol = symbols[i];
                inputString.append(byteToString(Integer.parseInt((int)symbol + "")));
            }
        }*/
        inputString = new StringBuilder();
        char symbols[] = integerInputString.toString().toCharArray();
        char symbol;
        if (hasTail) {
            for (int i = 0; i < symbols.length; i++) {
                symbol = symbols[i];
                inputString.append(byteToString(Integer.parseInt((int)symbol + "")));
            }
            inputString.append(tail);
        } else {
            for (int i = 0; i < symbols.length; i++) {
                symbol = symbols[i];
                inputString.append(byteToString(Integer.parseInt((int)symbol + "")));
            }
            inputString.append(tail);
        }
    }

    private String byteToString(int b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    private void writeCompressedResults() throws IOException {
        CompressedHuffman c = new CompressedHuffman();
        System.out.println();
        System.out.println("Length of compressed stream: " + integerOutputString.length());
        c.setSequence(integerOutputString);
        c.setTree(outputTree);
        c.setHasTail(hasTail);
        c.setTail(tail);

        FileOutputStream fos = new FileOutputStream("compress.nev");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(c);
        oos.close();
        fos.close();

        GZIPOutputStream g = new GZIPOutputStream(new FileOutputStream("doublecompress.nev"));
        g.write(integerOutputString.toString().getBytes("UTF-8"));
        g.flush();
        g.close();
    }

    private void readCompressedResults() throws IOException{
        FileInputStream fis = new FileInputStream("compress.nev");
        ObjectInputStream ois = new ObjectInputStream(fis);
        CompressedHuffman c = null;
        try {
            c = (CompressedHuffman)ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        integerInputString = new StringBuilder(c.getSequence());
        inputTree = c.getTree();
        symbols = c.getSymbols();
        hasTail = c.getHasTail();
        tail    = c.getTail();
    }

    private String tail;
    private char[] symbols;
    private boolean hasTail;
    private int[] array;
    private Node inputTree;
    private Node outputTree;
    private StringBuilder integerOutputString;
    private StringBuilder integerInputString;
    private StringBuilder byteOutputString;
    private StringBuilder inputString;
    private String inputStream;
    private HashMap<Character, String> tree;
    private final int SIZE_ARRAY = 1024;
    private PriorityQueue<Node> nodes;
    private int[] frequency;
}