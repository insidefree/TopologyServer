package com.correlata.topology.repository;

import com.correlata.topology.dao.Node;
import com.correlata.topology.dao.Zone;
import com.correlata.topology.interfaces.NodesRepository;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by Denis on 15/12/24.
 */
public class NodesRepositoryMaps implements NodesRepository {
    TreeMap<String,List<Node>> nodesHostName = new TreeMap<String, List<Node>>();
    TreeMap<String,List<Node>> nodesSwitch = new TreeMap<String, List<Node>>();
    TreeMap<String,List<Node>> nodesZoneName = new TreeMap<String, List<Node>>();

    public boolean addZone(Zone zone) {
        return false;
    }
}
