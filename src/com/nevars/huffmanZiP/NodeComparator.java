package com.nevars.huffmanZiP;

import java.util.Comparator;

/**
 * Created by erafiil on 10.04.15.
 */
class NodeComparator implements Comparator<Node> {


    @Override
    public int compare(Node o1, Node o2) {
        return o1.getFrequency() > o2.getFrequency() ? 1 :
                o1.getFrequency() < o2.getFrequency() ? -1 : 0;
    }
}
