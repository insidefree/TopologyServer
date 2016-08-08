package com.correlata.topology.repository;

import com.correlata.topology.dao.*;

import java.util.*;

/**
 * Created by Denis on 15/12/24.
 */
public class NodesSwitchNameRepositoryMap {
    public static Map<String,Set> nodesSwitchName = new TreeMap<String, Set>();
    public static Map<String,Set> edgesSwitchName = new TreeMap<String, Set>();
    public static Smooth smoothDefault = new Smooth("cubicBezier","horizontal", (float) 0.8);
    public static Smooth smoothSwitch = new Smooth("curvedCCW","horizontal", (float) 0.5);
    //public  static Color colorDefault = new Color("black");
    public  static Color colorSwitch = new Color("rgba(67,191,236,0.3)");

    public static void addNode(List<Zone> zoneList){
        for (Zone zone : zoneList) {
            if (nodesSwitchName.containsKey(zone.getSwitchH())) {
                Set<Node> nodeSet = nodesSwitchName.get(zone.getSwitchH());

                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "black"));

                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "black"));

                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "black"));

                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "rgba(67,191,236,0.3)"));

                nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA(), "rgba(67,191,236,0.3)"));

                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "black"));

                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "black"));

                //nodesSwitchName.put(zone.getSwitchH(), nodeSet);
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

                nodesSwitchName.put(zone.getSwitchH(), nodeSet);
            }
        }
    }
    public static void addEdge(List<Zone> zoneList){
        for(Zone zone : zoneList) {
            if (edgesSwitchName.containsKey(zone.getSwitchH())) {
                Set<Edge> edgesList = edgesSwitchName.get(zone.getSwitchH());
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
                edgesSwitchName.put(zone.getSwitchH(), edgesList);
            }
        }
    }
    @Override
    public String toString() {
        for (String key: nodesSwitchName.keySet()) {
            System.out.println("key:" + key + " - " + nodesSwitchName.get(key));
        }
        return "NodesHostNameRepositoryMap{}";
    }
}
