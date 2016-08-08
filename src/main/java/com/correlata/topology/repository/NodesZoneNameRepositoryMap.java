package com.correlata.topology.repository;

import com.correlata.topology.dao.*;

import java.util.*;

/**
 * Created by Denis on 15/12/24.
 */
public class NodesZoneNameRepositoryMap {
    public static Map<String,Set> nodesZoneName = new TreeMap<String, Set>();
    public static Map<String,Set> edgesZoneName = new TreeMap<String, Set>();
    public static Smooth smoothDefault = new Smooth("cubicBezier","horizontal", (float) 0.8);
    public static Smooth smoothSwitch = new Smooth("curvedCCW","horizontal", (float) 0.5);
    //public  static Color colorDefault = new Color("black");
    public  static Color colorSwitch = new Color("rgba(67,191,236,0.3)");

    public static void addNode(List<Zone> zoneList){
        for (Zone zone : zoneList) {
            if (nodesZoneName.containsKey(zone.getZoneName())) {
                Set<Node> nodeSet = nodesZoneName.get(zone.getZoneName());
                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "black"));

                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "black"));

                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "rgba(67,191,236,0.3)"));

                nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA(), "rgba(67,191,236,0.3)"));

                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "black"));

                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "black"));

                //nodesHostName.put(zone.getHostName(), nodeSet);
            } else {
                Set<Node> nodeSet = new LinkedHashSet<Node>();
                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "black"));

                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "black"));
                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "rgba(67,191,236,0.3)"));
                nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA(), "rgba(67,191,236,0.3)"));

                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "black"));

                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "black"));

                nodesZoneName.put(zone.getZoneName(), nodeSet);
            }
        }
    }
    public static void addEdge(List<Zone> zoneList){
        for(Zone zone : zoneList) {
            if (edgesZoneName.containsKey(zone.getZoneName())) {
                Set<Edge> edgesList = edgesZoneName.get(zone.getZoneName());
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHostName(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getSwitchPORTH() + "::" + zone.getSwitchH(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchH(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getArrayID(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getSwitchPORTA() + "::" + zone.getSwitchA(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchA(), smoothDefault, 1, new Color("from")));

                if (!zone.getSwitchH().equalsIgnoreCase(zone.getSwitchA())) {
                    edgesList.add((new Edge(zone.getSwitchH(), zone.getSwitchA(), smoothSwitch, 10, new Color("from"))));

                }
            } else {
                Set<Edge> edgesList = new LinkedHashSet<Edge>();
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHostName(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getSwitchPORTH() + "::" + zone.getSwitchH(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchH(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getArrayID(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getSwitchPORTA() + "::" + zone.getSwitchA(), smoothDefault, 1, new Color("from")));

                edgesList.add(new Edge(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchA(), smoothDefault, 1, new Color("from")));
                if (!zone.getSwitchH().equalsIgnoreCase(zone.getSwitchA())) {
                    edgesList.add((new Edge(zone.getSwitchH(), zone.getSwitchA(), smoothSwitch, 10, new Color("from"))));
                }
                edgesZoneName.put(zone.getZoneName(), edgesList);
            }
        }
    }
    @Override
    public String toString() {
        for (String key: nodesZoneName.keySet()) {
            System.out.println("key:" + key + " - " + nodesZoneName.get(key));
        }
        return "NodesHostNameRepositoryMap{}";
    }
}
