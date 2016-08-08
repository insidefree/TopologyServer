package com.correlata.topology.repository;

import com.correlata.topology.dao.*;
import com.correlata.topology.interfaces.TopologyConstants;
import com.correlata.topology.utils.EdgeComparator;
import com.correlata.topology.utils.NodeIDComparator;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class ZoneService {
    //List<Zone> zoneList = new ArrayList<Zone>();
    public List<Zone> zoneList = new LinkedList<Zone>();
    public Map<String,Set<Node>> nodesZoneName = new TreeMap<String, Set<Node>>();
    public Map<String,Set<Edge>> edgesZoneName = new TreeMap<String, Set<Edge>>();
    public NodesHostNameRepositoryMap  nodesHostNameRepositoryMap = new NodesHostNameRepositoryMap();
    public NodesSwitchNameRepositoryMap  nodesSwitchNameRepositoryMap = new NodesSwitchNameRepositoryMap();
    public NodesArrayIDNameRepositoryMap nodesArrayIDNameRepositoryMap = new NodesArrayIDNameRepositoryMap();
    public NodesZoneNameRepositoryMap nodesZoneNameRepositoryMap = new NodesZoneNameRepositoryMap();
    /*public Map<String,Set<String>> hostNameZoneNameMap = new TreeMap<String, Set<String>>();
    public Map<String,Set<String>> arrayIDZoneNameMap = new TreeMap<String, Set<String>>();
    public Map<String,Set<String>> switchZoneNameMap = new TreeMap<String, Set<String>>();
    public Map<String,Set<String>> zoneNameMap = new TreeMap<String, Set<String>>();*/
    public Set<String> hostNameSet = new TreeSet<String>();
    public Set<String> arrayIDNameSet = new TreeSet<String>();
    public Set<String> switchNameSet = new TreeSet<String>();
    public Set<String> zoneNameSet = new TreeSet<String>();
    public static Smooth smoothDefault = new Smooth("cubicBezier","horizontal", (float) 0.8);
    public static Smooth smoothSwitch = new Smooth("curvedCCW","horizontal", (float) 0.5);
    public  static Color colorDefault = new Color("black");
    public  static Color colorSwitch = new Color("rgba(67,191,236,0.3)");

    ZoneService(){
        try {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException" + e.getMessage());
            }
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@10.0.1.230:1521:itazdb", "itsmappl",
                        "itsmappl");
//                                "jdbc:oracle:thin:@bench:1521:orcl", "system",
//                                "4Insideit");
            } catch (SQLException e) {
                System.out.println("CONNECTION HAVE NOT CREATED" + e.getMessage());
            }
            if (connection != null) {
                //PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM ITAZHOSTSUMMARY");

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select ZONENAME,SERVER,HBAWWN," +
                    "ARRAYPORT,ARRAYID,PORTWWN,ZONESET,SWITCHH,SWITCH_A,SWITCHPORTH,SWITCHPORTA " +
                    "from ITAZHOSTSUMMARY WHERE CURRENTDATE = (SELECT MAX(CURRENTDATE) from ITAZHOSTSUMMARY)");

            /*ResultSet rs = stmt.executeQuery("select FABRICWWN,ZONENAME,HOSTNAME,HBAWWN," +
                    "ARRAYPORT,ARRAYID,ARRAYPORTWWN,ZONESET,VSAN,SWITCH_H,SWITCH_A,SWITCHPORT_H,SWITCHPORT_A " +
                    "from ITSMACTIVEZONE WHERE CURRENTDATE = (SELECT MAX(CURRENTDATE) from ITSMACTIVEZONE)");*/

            while (rs.next()) {
                /*Zone zone = new Zone(rs.getString(TopologyConstants.FABRICWWN),rs.getString(TopologyConstants.ZONENAME),
                        rs.getString(TopologyConstants.HOSTNAME),rs.getString(TopologyConstants.HBAWWN),
                        rs.getString(TopologyConstants.ARRAYPORT),rs.getString(TopologyConstants.ARRAYID),
                        rs.getString(TopologyConstants.ARRAYPORTWWN),rs.getString(TopologyConstants.ZONESET),
                        rs.getString(TopologyConstants.VSAN),rs.getString(TopologyConstants.SWITCH_H),
                        rs.getString(TopologyConstants.SWITCH_A),rs.getString(TopologyConstants.SWITCHPORT_H),
                        rs.getString(TopologyConstants.SWITCHPORT_A));*/
                Zone zone = new Zone(rs.getString(TopologyConstants.ZONENAME),
                        rs.getString("SERVER"),rs.getString(TopologyConstants.HBAWWN),
                        rs.getString(TopologyConstants.ARRAYPORT),rs.getString(TopologyConstants.ARRAYID),
                        rs.getString("PORTWWN"),rs.getString(TopologyConstants.ZONESET),
                        rs.getString("SWITCHH"),
                        rs.getString(TopologyConstants.SWITCH_A),rs.getString("SWITCHPORTH"),
                        rs.getString("SWITCHPORTA"));

//
                if(zone.getZoneName() == null)
                    zone.setZoneName("unknownZoneName");
                if(zone.getHostName() == null)
                    zone.setHostName("unknownHostName");
                if(zone.getHbaWWN() == null)
                    zone.setHbaWWN("unknownHbaWWN");
                if(zone.getArrayPort() == null)
                    zone.setArrayPort("unknownArrayPort");
                if(zone.getArrayID() == null)
                    zone.setArrayID("unknownArrayID");
                if(zone.getArrayPortWWN() == null)
                    zone.setArrayPortWWN("unknownArrayPortWWN");
                if(zone.getZoneSet() == null)
                    zone.setZoneSet("unknownZoneSet");
                if(zone.getSwitchH() == null)
                    zone.setSwitchH("unknownSwitchH");
                if(zone.getSwitchA() == null)
                    zone.setSwitchA("unknownSwitchA");
                if(zone.getSwitchPORTH() == null)
                    zone.setSwitchPORTH("unknownSwitchPortH");
                if(zone.getSwitchPORTA() == null)
                    zone.setSwitchPORTA("unknownSwitchPortA");

                zoneList.add(zone);


            }

            System.out.println("statement");
            System.out.println("statementAFTER");
            System.out.println("close");
            rs.close();
            stmt.close();
            connection.close();

            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.toString());
        }
        createMenuData(zoneList);
        nodesHostNameRepositoryMap.addNode(zoneList);
        nodesHostNameRepositoryMap.addEdge(zoneList);
        nodesSwitchNameRepositoryMap.addNode(zoneList);
        nodesSwitchNameRepositoryMap.addEdge(zoneList);
        nodesArrayIDNameRepositoryMap.addNode(zoneList);
        nodesArrayIDNameRepositoryMap.addEdge(zoneList);
        nodesZoneNameRepositoryMap.addNode(zoneList);
        nodesZoneNameRepositoryMap.addEdge(zoneList);
        System.out.println("END");
        //System.out.println(nodesHostName.keySet().toString());
    }

    public List<Zone> showAll(){
        return zoneList;
    }

    public void createMenuData(List<Zone> zoneList){
        for(Zone zone : zoneList) {
            hostNameSet.add(zone.getHostName());
            arrayIDNameSet.add(zone.getArrayID());
            switchNameSet.add(zone.getSwitchH());
            zoneNameSet.add(zone.getZoneName());
        /*    if (nodesZoneName.containsKey(zone.getZoneName())) {
                Set<Node> nodeSet = nodesZoneName.get(zone.getZoneName());
                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "red"));
                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "red"));
                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),"red"));
                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "red"));

                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "red"));
                //nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA()));
                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(),"red"));
                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "red"));
                nodesZoneName.put(zone.getZoneName(), nodeSet);
            } else {
                Set<Node> nodeSet = new LinkedHashSet<Node>();
                nodeSet.add(new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "red"));
                nodeSet.add(new Node(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "red"));
                nodeSet.add(new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                        zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "red"));
                nodeSet.add(new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "red"));
                nodeSet.add(new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCHH", zone.getSwitchH(), "red"));
                //nodeSet.add(new Node(zone.getSwitchA(), zone.getSwitchA(), "SWITCHA", zone.getSwitchA()));
                nodeSet.add(new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "PORTH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "red"));
                nodeSet.add(new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "PORTA", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "red"));
                nodesZoneName.put(zone.getZoneName(), nodeSet);
            }


            if (edgesZoneName.containsKey(zone.getZoneName())) {
                Set<Edge> edgesList = edgesZoneName.get(zone.getZoneName());
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHostName(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getSwitchPORTH() + "::" + zone.getSwitchH(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchH(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getArrayID(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getSwitchPORTA() + "::" + zone.getSwitchA(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchA(), smoothDefault, 1, colorDefault, new Inherit("from")));

                if (!zone.getSwitchH().equalsIgnoreCase(zone.getSwitchA())) {
                    edgesList.add((new Edge(zone.getSwitchH(), zone.getSwitchA(), smoothSwitch, 10, colorSwitch, new Inherit("from"))));
                }
            } else {
                Set<Edge> edgesList = new LinkedHashSet<Edge>();
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getHostName(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getHbaWWN() + "::" + zone.getHostName(), zone.getSwitchPORTH() + "::" + zone.getSwitchH(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchH(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getArrayID(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getSwitchPORTA() + "::" + zone.getSwitchA(), smoothDefault, 1, colorDefault, new Inherit("from")));
                edgesList.add(new Edge(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchA(), smoothDefault, 1, colorDefault, new Inherit("from")));
                if (!zone.getSwitchH().equalsIgnoreCase(zone.getSwitchA())) {
                    edgesList.add((new Edge(zone.getSwitchH(), zone.getSwitchA(), smoothSwitch, 10, colorSwitch, new Inherit("from"))));
                }
                edgesZoneName.put(zone.getZoneName(), edgesList);
            }*/
            /*if (zoneNameMap.containsKey(zone.getZoneName())) {
                Set<String> zoneSet = zoneNameMap.get(zone.getZoneName());
                zoneSet.add(zone.getZoneName());
            } else {
                Set<String> zoneSet = new LinkedHashSet<String>();
                zoneSet.add(zone.getZoneName());
                //zoneNameMap.put(zone.getZoneName(), zoneSet);
            }

            if (hostNameZoneNameMap.containsKey(zone.getHostName())) {
                Set<String> zoneSet = hostNameZoneNameMap.get(zone.getHostName());
                zoneSet.add(zone.getZoneName());
            } else {
                Set<String> zoneSet = new LinkedHashSet<String>();
                zoneSet.add(zone.getZoneName());
                //hostNameZoneNameMap.put(zone.getHostName(), zoneSet);
            }

            if (arrayIDZoneNameMap.containsKey(zone.getArrayID())) {
                Set<String> zoneSet = arrayIDZoneNameMap.get(zone.getArrayID());
                zoneSet.add(zone.getZoneName());
            } else {
                Set<String> zoneSet = new LinkedHashSet<String>();
                zoneSet.add(zone.getZoneName());
                //arrayIDZoneNameMap.put(zone.getArrayID(), zoneSet);
            }

            if (switchZoneNameMap.containsKey(zone.getSwitchH())) {
                Set<String> zoneSet = switchZoneNameMap.get(zone.getSwitchH());
                zoneSet.add(zone.getZoneName());
            } else {
                Set<String> zoneSet = new LinkedHashSet<String>();
                zoneSet.add(zone.getZoneName());
                //switchZoneNameMap.put(zone.getSwitchH(), zoneSet);
            }*/
        }
    }
    public Map<String,Set<Node>> showNodesZoneNameMap(){
        return nodesZoneName;
    }

   /* public Set<String> showSetHostName(){
        return hostNameZoneNameMap.keySet();
    }

    public Set<String> showSetArrayID(){
        return arrayIDZoneNameMap.keySet();
    }

    public Set<String> showSetSwitch(){
        return switchZoneNameMap.keySet();
    }

    public Map<String,Set<Edge>> showEdgesZoneNameMap(){
        return  edgesZoneName;
    }

    public Map<String,Set<String>> hostNameZoneNameMap(){
        return hostNameZoneNameMap;
    }

    public Map<String,Set<String>> arrayIDZoneNameMap(){
        return arrayIDZoneNameMap;
    }

    public Map<String,Set<String>> switchZoneNameMap(){
        return switchZoneNameMap;
    }*/

    public Set<Node> calcCoordinates(Set<Node> allSelectedNodes){
        Map<String,Set<Node>> typeNodesMap = new LinkedHashMap<String, Set<Node>>();
        for(Node node : allSelectedNodes){
            if(typeNodesMap.containsKey(node.getType())){
                typeNodesMap.get(node.getType()).add(node);
            }else{
                Set<Node> nodesSet = new LinkedHashSet<Node>();
                nodesSet.add(node);
                typeNodesMap.put(node.getType(), nodesSet);
            }
        }
        //int yPortTotal = 0;
        int yPortTotalSwitchH = 0;
        int yPortTotalSwitchA = 0;

        int yPortHTotalSwitchH = 0;
        int yPortATotalSwitchH = 0;

        int yPortHTotalSwitchA = 0;
        int yPortATotalSwitchA = 0;


        Fixed fixed = new Fixed(true, true);




        int xSwitchH = -100;
        int ySwitchH = 0;
        if(typeNodesMap.get("SWITCHH") != null) {
            for (Node node : typeNodesMap.get("SWITCHH")) {
                node.setFixed(fixed);
                node.setShape("image");
                node.setX(xSwitchH);
                node.setY(ySwitchH);
                node.setSize(60);
                //node.setColor(new Color("#F2CF66")/*new Color(new Inherit("from"))*/);
                node.setImage("/TopologyServer/resources/img/switch_denchik.png");

                int xPortH = -180;
                int yPortHSwitchH = node.getY();
                int countPortHSwitchH = 0;
                for (Node portNode : typeNodesMap.get("PORTH")) {
                    String[] arr;
                    arr = portNode.getId().split("::");
                    if (arr[1].equalsIgnoreCase(node.getId())) {
                        portNode.setFixed(new Fixed(true, true));
                        portNode.setShape("image");
                        portNode.setX(xPortH);
                        portNode.setY(yPortHSwitchH);
                        portNode.setSize(15);
                        //portNode.setColor(new Color("#1D2956")/*new Color(new Inherit("from"))*/);
                        portNode.setImage("/TopologyServer/resources/img/fbch_black_denchik.png");
                        yPortHSwitchH += 100;
                        countPortHSwitchH++;
                    }
                }

                int xPortAswitchH = -20;
                int yPortAswitchH = node.getY();
                int countPortASwitchH = 0;
                for (Node portNode : typeNodesMap.get("PORTA")) {
                    String[] arr;
                    arr = portNode.getId().split("::");
                    if (arr[1].equalsIgnoreCase(node.getId())) {
                        portNode.setFixed(new Fixed(true, true));
                        portNode.setShape("image");
                        portNode.setX(xPortAswitchH);
                        portNode.setY(yPortAswitchH);
                        portNode.setSize(15);
                        //portNode.setColor(new Color("#1D2956")/*new Color(new Inherit("from"))*/);
                        portNode.setImage("/TopologyServer/resources/img/fbch_black_denchik.png");
                        yPortAswitchH += 100;
                        countPortASwitchH++;
                    }
                }

                if (yPortHSwitchH >= yPortAswitchH && countPortASwitchH != 0) {
                    int yPortAStep = (yPortHSwitchH - ySwitchH - 100)/(countPortASwitchH);
                    int yPortA = ySwitchH + yPortAStep/2;
                    for (Node portNode : typeNodesMap.get("PORTA")) {
                        String[] arr;
                        arr = portNode.getId().split("::");
                        if (arr[1].equalsIgnoreCase(node.getId())) {
                            portNode.setX(xPortAswitchH);
                            portNode.setY(yPortA);
                            yPortA += yPortAStep;
                        }
                    }
                    node.setY((ySwitchH + yPortHSwitchH - 100) / 2);
                    yPortTotalSwitchH = yPortHSwitchH;

                } else {
                    if(countPortHSwitchH != 0) {
                        int yPortHStep = (yPortAswitchH - ySwitchH - 100) / (countPortHSwitchH);
                        int yPortH = ySwitchH + yPortHStep / 2;
                        for (Node portNode : typeNodesMap.get("PORTH")) {
                            String[] arr;
                            arr = portNode.getId().split("::");
                            if (arr[1].equalsIgnoreCase(node.getId())) {
                                portNode.setX(xPortH);
                                portNode.setY(yPortH);
                                yPortH += yPortHStep;
                            }
                        }
                    }
                    node.setY((ySwitchH + yPortAswitchH - 100) / 2);
                    yPortTotalSwitchH = yPortAswitchH;
                }

                ySwitchH = (yPortHSwitchH >= yPortAswitchH) ? (yPortHSwitchH + 150) : (yPortAswitchH + 150);
            }
        }
        int xSwitchA = 100;
        int ySwitchA = 0;
        if(typeNodesMap.get("SWITCHA") != null) {
            for (Node node : typeNodesMap.get("SWITCHA")) {
                node.setFixed(fixed);
                node.setShape("image");
                node.setX(xSwitchA);
                node.setY(ySwitchA);
                node.setSize(60);
                //node.setColor(new Color("#F2CF66")/*new Color(new Inherit("from"))*/);
                node.setImage("/TopologyServer/resources/img/switch_denchik.png");

                int xPortH = 45;
                int yPortHSwitchA = node.getY();
                int countPortHSwitchA = 0;
                for (Node portNode : typeNodesMap.get("PORTH")) {
                    String[] arr;
                    arr = portNode.getId().split("::");
                    if (arr[1].equalsIgnoreCase(node.getId())) {
                        portNode.setFixed(new Fixed(true, true));
                        portNode.setShape("image");
                        portNode.setX(xPortH);
                        portNode.setY(yPortHSwitchA);
                        portNode.setSize(15);
                        //portNode.setColor(new Color("#1D2956")/*new Color(new Inherit("from"))*/);
                        portNode.setImage("/TopologyServer/resources/img/fbch_black_denchik.png");
                        yPortHSwitchA += 100;
                        countPortHSwitchA++;
                    }
                }

                int xPortA = 175;
                int yPortAswitchA = node.getY();
                int countPortASwitchA = 0;
                for (Node portNode : typeNodesMap.get("PORTA")) {
                    String[] arr;
                    arr = portNode.getId().split("::");
                    if (arr[1].equalsIgnoreCase(node.getId())) {
                        portNode.setFixed(new Fixed(true, true));
                        portNode.setShape("image");
                        portNode.setX(xPortA);
                        portNode.setY(yPortAswitchA);
                        portNode.setSize(15);
                        //portNode.setColor(new Color("#1D2956")/*new Color(new Inherit("from"))*/);
                        portNode.setImage("/TopologyServer/resources/img/fbch_black_denchik.png");
                        yPortAswitchA += 100;
                        countPortASwitchA++;
                    }
                }
                ///////////////
                /*node.setY((ySwitchA + yPortAswitchA - 100) / 2);
                ySwitchA = yPortAswitchA + 150;
                yPortATotalSwitchA = yPortAswitchA;*/
                ///////////

                if (yPortHSwitchA >= yPortAswitchA && countPortASwitchA != 0) {
                    int yPortAStep = (yPortHSwitchA - ySwitchA - 100)/(countPortASwitchA);
                    int yPortA = ySwitchA + yPortAStep/2;
                    for (Node portNode : typeNodesMap.get("PORTA")) {
                        String[] arr;
                        arr = portNode.getId().split("::");
                        if (arr[1].equalsIgnoreCase(node.getId())) {
                            portNode.setX(xPortA);
                            portNode.setY(yPortA);
                            yPortA += yPortAStep;
                        }
                    }

                    node.setY((ySwitchA + yPortHSwitchA - 100) / 2);
                    yPortTotalSwitchA = yPortHSwitchA;
                } else {
                    if (countPortHSwitchA != 0) {
                        int yPortHStep = (yPortAswitchA - ySwitchA - 100) / (countPortHSwitchA);
                        int yPortH = ySwitchA + yPortHStep / 2;
                        for (Node portNode : typeNodesMap.get("PORTH")) {
                            String[] arr;
                            arr = portNode.getId().split("::");
                            if (arr[1].equalsIgnoreCase(node.getId())) {
                                portNode.setX(xPortH);
                                portNode.setY(yPortH);
                                yPortH += yPortHStep;
                            }
                        }
                    }

                    node.setY((ySwitchA + yPortAswitchA - 100) / 2);
                    yPortTotalSwitchA = yPortAswitchA;
                }

                ySwitchA = (yPortHSwitchA >= yPortAswitchA) ? (yPortHSwitchA + 150) : (yPortAswitchA + 150);
            }
        }
        int xHost = -500;
        int xHBAWWN = -330;
        int yHBAWWNStep = (yPortTotalSwitchH - 100)/(typeNodesMap.get("HBAWWN").size() + 1);
        int yHBAWWN = yHBAWWNStep;
        for(Node node : typeNodesMap.get("HOST")){
            node.setFixed(fixed);
            node.setShape("image");
            node.setX(xHost);
            node.setY(yHBAWWN);
            node.setSize(40);
            //node.setColor(new Color("#F24B0F")/*new Color(new Inherit("from"))*/);
            node.setImage("/TopologyServer/resources/img/server_d.png");


            for(Node hbawwnNode : typeNodesMap.get("HBAWWN")){
                String[] arr;
                arr = hbawwnNode.getId().split("::");
                if(arr[1].equalsIgnoreCase(node.getId())) {
                    hbawwnNode.setShape("image");
                    hbawwnNode.setX(xHBAWWN);
                    hbawwnNode.setY(yHBAWWN);
                    hbawwnNode.setSize(20);
                    //hbawwnNode.setColor(new Color("#F2994B")/*new Color(new Inherit("from"))*/);
                    hbawwnNode.setImage("/TopologyServer/resources/img/fibercard.png");
                    yHBAWWN += yHBAWWNStep;
                }
            }
            node.setY((node.getY() + yHBAWWN - yHBAWWNStep)/2);
        }
        int yTotalArrayPort = (yPortTotalSwitchA >= yPortTotalSwitchH) ? yPortTotalSwitchA : yPortTotalSwitchH;

        int xArrayID = 500;
        int xArrayPort = 300;
        int yArrayPortStep = (yTotalArrayPort - 100)/(typeNodesMap.get("ARRAYPORT").size() + 1);
        int yArrayPort = yArrayPortStep;
        for(Node node : typeNodesMap.get("ARRAYID")){
            node.setFixed(fixed);
            node.setShape("image");
            node.setX(xArrayID);
            node.setY(yArrayPort);
            node.setSize(60);
            //node.setColor(new Color("#0387B0")/*new Color(new Inherit("from"))*/);
            node.setImage("/TopologyServer/resources/img/database_denchik.png");


            for(Node arrayPortNode : typeNodesMap.get("ARRAYPORT")){
                String[] arr;
                arr = arrayPortNode.getId().split("::");

                if(arr[1].equalsIgnoreCase(node.getId())) {
                    arrayPortNode.setFixed(new Fixed(true, true));
                    arrayPortNode.setShape("image");
                    arrayPortNode.setX(xArrayPort);
                    arrayPortNode.setY(yArrayPort);
                    arrayPortNode.setSize(25);
                    //arrayPortNode.setColor(new Color("#0494D9")/*new Color(new Inherit("from"))*/);
                    arrayPortNode.setImage("/TopologyServer/resources/img/fbch_blue_denchik.png");
                    yArrayPort += yArrayPortStep;
                }
            }
            node.setY((node.getY() + yArrayPort - yArrayPortStep)/2);
        }
        /*for (String key: typeNodesMap.keySet()){
            System.out.println("key:" + key + " - " + typeNodesMap.get(key));
        }*/
        /*for (String key: nodesHostName.keySet()){
            System.out.println("key:" + key + " " + nodesHostName.get(key));
        }*/


        return allSelectedNodes;
    }
}
