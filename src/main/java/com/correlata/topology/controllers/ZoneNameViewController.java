package com.correlata.topology.controllers;

import com.correlata.topology.dao.Edge;
import com.correlata.topology.dao.Node;
import com.correlata.topology.dao.Zone;
import com.correlata.topology.interfaces.TopologyConstants;
import com.correlata.topology.repository.*;
import com.correlata.topology.utils.EdgeComparator;
import com.correlata.topology.utils.NodeIDComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.sql.*;
import java.util.*;


/**
 * Created by Denis on 15/12/13.
 */
@Controller
@RequestMapping({"/"})
public class ZoneNameViewController{
    @Autowired
    private ZoneService zoneService;

    Set<Node> nodesSet = new HashSet<Node>();
//    List nodesArr = new ArrayList();
    List edgesArr = new ArrayList();
    Map<String,Set<String>> nodesByZoneNameMap = new HashMap<String, Set<String>>();

    @RequestMapping(value = "/zoneList")
    @ResponseBody
    public String zoneList(Model model) {
        List<Zone> zoneList = zoneService.showAll();
        Map<String, Set<Node>> nodesZoneNameMap = zoneService.showNodesZoneNameMap();
        Gson gson = new Gson();
        try {
            File file = new File("c:/data/itaz/bin/objects.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int i = 1;
            for(Zone zone : zoneList){
                bw.write(i + " ");
                bw.write(zone.toString());
                bw.newLine();
                i++;
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File("c:/data/itaz/bin/objectsJson.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(gson.toJson(zoneList));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*model.addAttribute("zoneList", gson.toJson(zoneList));*/
        return gson.toJson(nodesZoneNameMap);
    }

    @RequestMapping(value = "/zoneNameList", method = RequestMethod.GET)
    @ResponseBody
    public String zoneNameList (){
        Gson gson = new Gson();
        return gson.toJson(nodesByZoneNameMap);
    }
    @RequestMapping(value = "/zoneNameView", method = RequestMethod.GET)
    @ResponseBody
    public String zoneNameView(Model model) {
        Gson gson = new Gson();

        List<Zone> zoneList = new ArrayList<Zone>();
        try {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                return "1 " + e.getMessage();
            }
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@10.0.1.27:1521:itazdb", "itsmappl",
                        "itsmappl");
//                                "jdbc:oracle:thin:@bench:1521:orcl", "system",
//                                "4Insideit");
            } catch (SQLException e) {
                return "2 " + e.getMessage();
            }
            if (connection != null) {
            } else {
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select FABRICWWN,ZONENAME,HOSTNAME,HBAWWN," +
                    "ARRAYPORT,ARRAYID,ARRAYPORTWWN,ZONESET,VSAN,SWITCH_H,SWITCH_A,SWITCHPORT_H,SWITCHPORT_A " +
                    "from ITSMACTIVEZONE WHERE CURRENTDATE = (SELECT MAX(CURRENTDATE) from ITSMACTIVEZONE)");

            while (rs.next()) {
                Zone zone = new Zone(rs.getString(TopologyConstants.FABRICWWN),rs.getString(TopologyConstants.ZONENAME),
                        rs.getString(TopologyConstants.HOSTNAME),rs.getString(TopologyConstants.HBAWWN),
                        rs.getString(TopologyConstants.ARRAYPORT),rs.getString(TopologyConstants.ARRAYID),
                        rs.getString(TopologyConstants.ARRAYPORTWWN),rs.getString(TopologyConstants.ZONESET),
                        rs.getString(TopologyConstants.VSAN),rs.getString(TopologyConstants.SWITCH_H),
                        rs.getString(TopologyConstants.SWITCH_A),rs.getString(TopologyConstants.SWITCHPORT_H),
                        rs.getString(TopologyConstants.SWITCHPORT_A));

                /*zoneList.add(zone);*/
                zone.disassembleZone(zone, nodesByZoneNameMap, nodesSet, edgesArr);
                System.out.println("!!!!!!!!!!!!!!!!!!!!");
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException ex) {return "3 " + ex.getMessage();}

       return gson.toJson(nodesSet);
//        return canvas;
    }

//    @RequestMapping(value = "/edges")
//    @ResponseBody
//    public String edges(){
//        Gson gson = new Gson();
//        return gson.toJson(edgesArr);
//    }

//    @RequestMapping(value = "/nodes")
//    public @ResponseBody String nodes(){
//        Gson gson = new Gson();
//        return gson.toJson(nodesArr);
//    }

    @RequestMapping(value = "/canvas", method = RequestMethod.GET)
    public String canvas(/*ModelMap model*/){
        /*Gson gson = new Gson();
        model.addAttribute("nodeList", gson.toJson(nodesSet));
        model.addAttribute("edgeList", gson.toJson(edgesArr));*/
        return "canvas";
    }

    @RequestMapping(value = "/canvas02", method = RequestMethod.GET)
    public String canvas02(/*ModelMap model*/){
        /*Gson gson = new Gson();
        model.addAttribute("nodeList", gson.toJson(nodesSet));
        model.addAttribute("edgeList", gson.toJson(edgesArr));*/
        return "canvas02";
    }
    @RequestMapping(value = "/canvasModelMap", method = RequestMethod.GET)
    public String canvasModelMap(/*ModelMap model*/){
        /*Gson gson = new Gson();
        model.addAttribute("nodeList", gson.toJson(nodesSet));
        model.addAttribute("edgeList", gson.toJson(edgesArr));*/
        return "canvasModelMap";
    }

    @RequestMapping(value = "/showNodesByZoneNameMap")
    @ResponseBody
    public String showNodesByZoneNameMap(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.showNodesZoneNameMap());
    }

    @RequestMapping(value = "/showKeySetNodesByZoneNameMap")
    @ResponseBody
    public String showKeySetNodesByZoneNameMap(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.zoneNameSet);
    }

    @RequestMapping(value = "/showSetHostName")
    @ResponseBody
    public String showKeySetHostName(){
        Gson gson = new Gson();
        //return gson.toJson(zoneService.nodesHostName.keySet());
        return gson.toJson(zoneService.hostNameSet);
    }

    @RequestMapping(value = "/showSetArrayID")
    @ResponseBody
    public String showKeySetArrayID(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.arrayIDNameSet);
    }

    @RequestMapping(value = "/showSetSwitch")
    @ResponseBody
    public String showKeySetSwitch(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.switchNameSet);
    }

    @RequestMapping(value = "/idArr", method = RequestMethod.POST)
    @ResponseBody
    public Map myRequest(@RequestBody String str){
        Set<Node> allSelectedNodes = new LinkedHashSet<Node>();
        Set<Edge> allSelectedEdges = new LinkedHashSet<Edge>();
        Map<String,Collection> resultMap = new LinkedHashMap<String,Collection>();
        Gson gson = new Gson();
        String[] idArr = gson.fromJson(str, String[].class);
        for(int i = 0; i < idArr.length; i++){
            System.out.println(idArr[i]);
        }

        for(int i = 0; i < idArr.length; i++){
            /*if(zoneService.nodesZoneName.get(idArr[i]) != null) {
                allSelectedNodes.addAll(zoneService.nodesZoneName.get(idArr[i]));
                allSelectedEdges.addAll(zoneService.edgesZoneName.get(idArr[i]));
            }*/
            if(NodesZoneNameRepositoryMap.nodesZoneName.get(idArr[i]) != null) {
                allSelectedNodes.addAll(NodesZoneNameRepositoryMap.nodesZoneName.get(idArr[i]));
                allSelectedEdges.addAll(NodesZoneNameRepositoryMap.edgesZoneName.get(idArr[i]));
            }
            /*if(zoneService.hostNameZoneNameMap.get(idArr[i]) != null) {
                Set<String> listZones = zoneService.hostNameZoneNameMap.get(idArr[i]);
                for(String zone : listZones){
                    allSelectedNodes.addAll(zoneService.nodesZoneName.get(zone));
                    allSelectedEdges.addAll(zoneService.edgesZoneName.get(zone));
                }
            }*/
            if(NodesHostNameRepositoryMap.nodesHostName.get(idArr[i]) != null) {
                allSelectedNodes.addAll(NodesHostNameRepositoryMap.nodesHostName.get(idArr[i]));
                allSelectedEdges.addAll(NodesHostNameRepositoryMap.edgesHostName.get(idArr[i]));
            }

            /*if(zoneService.arrayIDZoneNameMap.get(idArr[i]) != null) {
                Set<String> listZones = zoneService.arrayIDZoneNameMap.get(idArr[i]);
                for(String zone : listZones){
                    allSelectedNodes.addAll(zoneService.nodesZoneName.get(zone));
                    allSelectedEdges.addAll(zoneService.edgesZoneName.get(zone));
                }
            }*/
            if(NodesArrayIDNameRepositoryMap.nodesArrayIDName.get(idArr[i]) != null) {
                allSelectedNodes.addAll(NodesArrayIDNameRepositoryMap.nodesArrayIDName.get(idArr[i]));
                allSelectedEdges.addAll(NodesArrayIDNameRepositoryMap.edgesArrayIDName.get(idArr[i]));
            }
            /*if(zoneService.switchZoneNameMap.get(idArr[i]) != null) {
                Set<String> listZones = zoneService.switchZoneNameMap.get(idArr[i]);
                for(String zone : listZones){
                    allSelectedNodes.addAll(zoneService.nodesZoneName.get(zone));
                    allSelectedEdges.addAll(zoneService.edgesZoneName.get(zone));
                }
            }*/
            if(NodesSwitchNameRepositoryMap.nodesSwitchName.get(idArr[i]) != null) {
                allSelectedNodes.addAll(NodesSwitchNameRepositoryMap.nodesSwitchName.get(idArr[i]));
                allSelectedEdges.addAll(NodesSwitchNameRepositoryMap.edgesSwitchName.get(idArr[i]));
            }

        }
        System.out.println("BEFORE CALC COORDINATES");
        for(Node nd : allSelectedNodes){
            System.out.println(nd.toString());
        }
        /*
        System.out.println("AFTER CALC COORDINATES");
        zoneService.calcCoordinates(allSelectedNodes);
        for(Node nd : allSelectedNodes){
            System.out.println(nd.toString());
        }*/
        zoneService.calcCoordinates(allSelectedNodes);
        resultMap.put("nodes", allSelectedNodes);
//        resultMap.put("nodes", allSelectedNodes);
        resultMap.put("edges", allSelectedEdges);

        return resultMap;
    }

    @RequestMapping(value = "/edges", method = RequestMethod.GET)
    @ResponseBody
    public String showEdges(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.edgesZoneName);
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    @ResponseBody
    public String showNodes(){
        Gson gson = new Gson();
        return gson.toJson(zoneService.nodesZoneName);
    }

}