package com.nevars.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Huffman {

    public void compress(ArrayList<Integer> array) {
        char symbol;
        StringBuilder str = new StringBuilder();
        mydata_str = new StringBuilder("");
        for (int i : array) {
            int item = i + 150;
            symbol = (char)item;
            str.append(item);
            if (mydata_str == null) {
                mydata_str.append(Character.toString(symbol));
            } else
                mydata_str.append(Character.toString(symbol));
            add_to_list(Character.toString(symbol));
        }

        sort_my_data(mylist);
        evaluate_code();
        write_To_file();
        code_data_content();
        double resultBefore = getBits(str.toString());
        double resultAfter = mydata_coded.length();
        System.out.printf("            Length of initial string = %.0f b", resultBefore);
        System.out.printf("\n            Length of compressed string = %.0f b", resultAfter / 8);
        System.out.println("\n            How much we have compressed = " + Math.round(resultBefore / resultAfter));
        System.out.println("            Koeff of compression = " + Math.round((resultAfter / resultBefore) * 100) + "%");
        write_Compressed();
    }

    public String getCodes() {
        return mydata_coded.toString();
    }

    public String getTable() {
        return table.toString();
    }

    public int[] decompressedStream() {
        decompress();
        String resultDecompression = getResultDecompress();
        char array[] = resultDecompression.toCharArray();
        int arrayForHuffman[] = new int[array.length];
        int i = 0;
        int temp = 0;
        for (char symbol : array) {
            temp = (int)symbol - 150;
            arrayForHuffman[i++] = temp;
        }
        return arrayForHuffman;
    }

    private void decompress() {
        try {
            ReadHand1 = new BufferedReader(new FileReader("table.txt"));
            ReadHand2 = new BufferedReader(new FileReader("comp.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        String total;
        String data_from_file = null;

        try {
            while (true) {
                total = ReadHand1.readLine();
                if (total == null) break;
                if (total != null)
                    list_From_file.add(new data(total.substring(0, 1), total.substring(2, total.length())));
            }

            while (true) {
                total = ReadHand2.readLine();
                if (total == null) break;
                if (data_from_file == null) data_from_file = total;
                else
                    data_from_file += total;
            }


            int j = 0;
            int index;
            StringBuilder data_to_file = new StringBuilder();
            for (int i = 1; i <= data_from_file.length(); i++) {
                index = isEqualSomething(data_from_file.substring(j, i));
                if (index != -1) {
                    if (data_to_file == null)
                        data_to_file = new StringBuilder(((data) list_From_file.get(index)).getChar());
                    else
                        data_to_file.append(((data) list_From_file.get(index)).getChar());

                    j = i;
                }
            }
            resultDecompress = data_to_file;
            WriteHand = new BufferedWriter(new FileWriter("decompress.txt"));
            WriteHand.write(data_to_file.toString());
            WriteHand.flush();
            WriteHand.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getResultDecompress() {
        return resultDecompress.toString();
    }

    public int getBits(String string) {
        byte[] bytes = string.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.length();
    }

    private void add_to_list(String ch) {
        for (int i = 0; i < mylist.size(); i++) {
            if (ch.equals(((data) mylist.get(i)).getChar())) {
                ((data) mylist.get(i)).increment();
                return;
            }
        }
        mylist.add(new data(ch));
    }

    private void sort_my_data(myDataList myLinklist) {
        Collections.sort(myLinklist, my_comparator);
    }

    private void evaluate_code() {
        myDataList linklist = (myDataList) mylist.clone();
        myDataList temp;
        int size = linklist.size();

        arrList.add(linklist.clone());
        while (size > 2) {
            ((data) linklist.get(size - 2)).append_data(((data) linklist.get(size - 1)));
            linklist.removeLast();
            temp = ((myDataList) linklist.clone());
            ((data) linklist.get(size - 2)).setFlag(true);
            setToflagOthers(temp);
            sort_my_data(linklist);
            sort_my_data(temp);
            arrList.add(temp);
            size--;
        }
        doCoding_to_tree();
    }

    private void doCoding_to_tree() {
        myDataList temp1 = ((myDataList) arrList.get(arrList.size() - 1));
        myDataList temp2;
        ((data) temp1.get(0)).setBinary_Code(ONE);
        ((data) temp1.get(1)).setBinary_Code(ZERO);
        for (int i = arrList.size() - 1; i > 0; i--) {
            temp1 = ((myDataList) arrList.get(i));
            temp2 = ((myDataList) arrList.get(i - 1));
            copmare_and_code(temp1, temp2);
        }
    }

    private void setToflagOthers(myDataList mylist) {
        for (int i = 0; i < mylist.size() - 1; i++) {
            ((data) mylist.get(i)).setFlag(false);
        }
    }

    private int get_Where_the_true_code(myDataList mylist) {
        for (int i = 0; i < mylist.size(); i++) {
            if (((data) mylist.get(i)).isFlag())
                return i;
        }
        return -1;
    }

    private void copmare_and_code(myDataList temp1, myDataList temp2) {
        for (int j = 0; j < temp2.size(); j++)
            for (int i = 0; i < temp1.size(); i++) {
                if ((((data) temp2.get(j)).getChar()).equals(((data) temp1.get(i)).getChar())) {
                    ((data) temp2.get(j)).setBinary_Code(((data) temp1.get(i)).getBinary_Code());
                }
            }
        int postion = get_Where_the_true_code(temp1);
        ((data) temp2.get(temp2.size() - 2)).setBinary_Code(((data) temp1.get(postion)).getBinary_Code() + ONE);
        ((data) temp2.get(temp2.size() - 1)).setBinary_Code(((data) temp1.get(postion)).getBinary_Code() + ZERO);
    }

    private void write_To_file() {

        try {
            WriteHand = new BufferedWriter(new FileWriter("table.txt"));
            myDataList mytable = (myDataList) arrList.get(0);

            for (int i = 0; i < mytable.size(); i++) {
                WriteHand.write(((data) mytable.get(i)).getChar());
                table.append(((data) mytable.get(i)).getChar());//
                WriteHand.write(" ");
                table.append(" ");
                WriteHand.write(((data) mytable.get(i)).getBinary_Code());
                table.append((((data) mytable.get(i)).getBinary_Code()));
                WriteHand.newLine();
                table.append("\n");
            }
            WriteHand.flush();
            WriteHand.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void code_data_content() {
        String mySub;
        mydata_coded = new StringBuilder("");
        for (int i = 0; i < mydata_str.length(); i++) {
            mySub = mydata_str.substring(i, i + 1);
            mySub = getChar_code(mySub);
            if (mydata_coded == null) {
                mydata_coded.append(mySub);
            } else {
                mydata_coded.append(mySub);
            }
        }
    }

    private String getChar_code(String str) {
        myDataList mytable = (myDataList) arrList.get(0);
        for (int i = 0; i < mytable.size(); i++) {
            if (((data) mytable.get(i)).getChar().equals(str)) {
                return (((data) mytable.get(i)).getBinary_Code());
            }
        }
        return "??";
    }

    private void write_Compressed() {
        try {
            WriteHand = new BufferedWriter(new FileWriter("comp.txt"));
            WriteHand.write(mydata_coded.toString());
            WriteHand.flush();
            WriteHand.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private int isEqualSomething(String str) {
        for (int i = 0; i < list_From_file.size(); i++) {
            if (((data) list_From_file.get(i)).getBinary_Code().equals(str))
                return i;
        }
        return -1;
    }


    private final String ONE  = "1";
    private final String ZERO = "0";
    private StringBuilder table = new StringBuilder();
    private data_Comparator my_comparator = new data_Comparator();
    private myDataList mylist = new myDataList();
    private myDataList arrList = new myDataList();
    private myDataList list_From_file = new myDataList();
    private StringBuilder mydata_str;
    private BufferedWriter WriteHand;
    private BufferedReader ReadHand1;
    private BufferedReader ReadHand2;
    private StringBuilder mydata_coded;
    private StringBuilder resultDecompress = new StringBuilder();
}
