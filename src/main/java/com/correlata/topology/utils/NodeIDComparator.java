package com.correlata.topology.utils;

import com.correlata.topology.dao.Node;

import java.util.Comparator;

/**
 * Created by Denis on 15/12/15.
 */
public class NodeIDComparator implements Comparator<Node>{

    public int compare(Node node0, Node node1) {
        return node0.getId().compareTo(node1.getId());
    }
}
