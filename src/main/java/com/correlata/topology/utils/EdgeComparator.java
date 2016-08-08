package com.correlata.topology.utils;

import com.correlata.topology.dao.Edge;

import java.util.Comparator;

/**
 * Created by bench on 04/01/16.
 */
public class EdgeComparator implements Comparator<Edge> {
    public int compare(Edge edge1, Edge edge2) {
        if (edge1.getFrom().equalsIgnoreCase(edge2.getFrom()) && edge1.getTo().equalsIgnoreCase(edge2.getTo()))
            return 0;
        return edge1.getFrom().compareTo(edge2.getFrom());
    }
}
