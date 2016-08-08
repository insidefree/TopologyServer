package com.correlata.topology.repository;

import com.correlata.topology.dao.*;

import java.util.*;
import java.lang.String;

/**
 * Created by Denis on 15/12/24.
 */
public class NodesHostNameRepositoryMap {
    public static Map<String,Set> nodesHostName = new TreeMap<String, Set>();
    public static Map<String,Set> edgesHostName = new TreeMap<String, Set>();
    public static Smooth smoothDefault = new Smooth("cubicBezier","horizontal", (float) 0.8);
    public static Smooth smoothSwitch = new Smooth("curvedCCW","horizontal", (float) 0.5);
    //public  static Color colorDefault = new Color("black");
    //public  static Color colorSwitch = new Color("rgba(67,191,236,0.3)");

    public static void addNode(List<Zone> zoneList){
        for (Zone zone : zoneList) {
            if (nodesHostName.containsKey(zone.getHostName())) {
                Set<Node> nodeSet = nodesHostName.get(zone.getHostName());


                //nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "black", zone.getHbaWWN()));
                Set<String> hbaHiddenGroup = new TreeSet<String>();
                hbaHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "black", hbaHiddenGroup));

                Set<String> arrayPortHiddenGroup = new TreeSet<String>();
                arrayPortHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "black", arrayPortHiddenGroup));


                Set<String> arrayIDHiddenGroup = new TreeSet<String>();
                arrayIDHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "black",arrayIDHiddenGroup));

                Set<String> switchHHiddenGroup = new TreeSet<String>();
                switchHHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "rgba(67,191,236,0.3)",switchHHiddenGroup));

                Set<String> switchAHiddenGroup = new TreeSet<String>();
                switchAHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA(), "rgba(67,191,236,0.3)",switchAHiddenGroup));

                Set<String> portHHiddenGroup = new TreeSet<String>();
                portHHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "black",portHHiddenGroup));

                Set<String> portAHiddenGroup = new TreeSet<String>();
                portAHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "black", portAHiddenGroup));

                //nodesHostName.put(zone.getHostName(), nodeSet);

                for (String key: nodesHostName.keySet()) {
                    Set<Node> nodes = nodesHostName.get(key);
                    Set<String> hiddenGroupPortA = new TreeSet<String>();
                    for(Node node : nodes){
                        /*nodesHostName.get(key);*/
                        //System.out.println("key:" + key + " - " + typeNodesMap.get(key));

                        if (/*node.getType().equalsIgnoreCase("HOST")*/zone.getHostName().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }
                        /*if (zone.getSwitchH().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }
                        if (zone.getSwitchA().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }*/
                        if (/*node.getType().equalsIgnoreCase("ARRAYID")*/zone.getArrayID().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }
                        if (/*node.getType().equalsIgnoreCase("ARRAYPORT")*/zone.getArrayPort().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                            /*for(Node nodeIn : nodes){
                                if (zone.getSwitchPORTA().equalsIgnoreCase(nodeIn.getLabel())) {
                                    //nodeIn.getHiddenGroup().add(zone.getHbaWWN());
                                    nodeIn.setHiddenGroup(node.getHiddenGroup());
                                }
                            }*/
                        }

                        if ((zone.getSwitchPORTA() + "::" + zone.getSwitchA()).equalsIgnoreCase(node.getId())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }
                    }
                    /*for(Node node : nodes){
                        if (zone.getSwitchPORTA().equalsIgnoreCase(node.getLabel())) {
                            node.getHiddenGroup().add(zone.getHbaWWN());
                        }
                    }*/
                }
            } else {
                Set<Node> nodeSet = new LinkedHashSet<Node>();
                Set<String> hostHiddenGroup = new TreeSet<String>();
                hostHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "black", hostHiddenGroup));

                Set<String> hbaHiddenGroup = new TreeSet<String>();
                hbaHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "black", hbaHiddenGroup));

                Set<String> arrayPortHiddenGroup = new TreeSet<String>();
                arrayPortHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "black", arrayPortHiddenGroup));

                Set<String> arrayIDHiddenGroup = new TreeSet<String>();
                arrayIDHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "black", arrayIDHiddenGroup));

                Set<String> switchHHiddenGroup = new TreeSet<String>();
                switchHHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "rgba(67,191,236,0.3)", switchHHiddenGroup));

                Set<String> switchAHiddenGroup = new TreeSet<String>();
                switchAHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA(), "rgba(67,191,236,0.3)", switchAHiddenGroup));

                Set<String> portHHiddenGroup = new TreeSet<String>();
                portHHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "black", portHHiddenGroup));

                Set<String> portAHiddenGroup = new TreeSet<String>();
                portAHiddenGroup.add(zone.getHbaWWN());
                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "black", portAHiddenGroup));

                nodesHostName.put(zone.getHostName(), nodeSet);
            }
        }
    }
    public static void addEdge(List<Zone> zoneList){
        for(Zone zone : zoneList) {
            if (edgesHostName.containsKey(zone.getHostName())) {
                Set<Edge> edgesList = edgesHostName.get(zone.getHostName());
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
                edgesHostName.put(zone.getHostName(), edgesList);
            }
        }
    }

    public void addHiddenGroup(){

    }
    @Override
    public String toString() {
        for (String key: nodesHostName.keySet()) {
            System.out.println("key:" + key + " - " + nodesHostName.get(key));
        }
        return "NodesHostNameRepositoryMap{}";
    }
}
