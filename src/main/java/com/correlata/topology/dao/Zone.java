package com.correlata.topology.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Denis on 15/12/13.
 */
public class Zone {
    private String fubricWWN;
    private String zoneName;
    private String hostName;
    private String hbaWWN;
    private String arrayPort;
    private String arrayID;
    private String arrayPortWWN;
    private String zoneSet;
    private String vsan;
    private String switchH;
    private String switchA;
    private String switchPORTH;
    private String switchPORTA;

    public Zone() {
    }

    public Zone(String fubricWWN, String zoneName, String hostName, String hbaWWN, String arrayPort, String arrayID, String arrayPortWWN, String zoneSet, String vsan, String switchH, String switchA, String switchPORTH, String switchPORTA) {
        this.fubricWWN = fubricWWN;
        this.zoneName = zoneName;
        this.hostName = hostName;
        this.hbaWWN = hbaWWN;
        this.arrayPort = arrayPort;
        this.arrayID = arrayID;
        this.arrayPortWWN = arrayPortWWN;
        this.zoneSet = zoneSet;
        this.vsan = vsan;
        this.switchH = switchH;
        this.switchA = switchA;
        this.switchPORTH = switchPORTH;
        this.switchPORTA = switchPORTA;
    }

    public Zone(String zoneName, String hostName, String hbaWWN, String arrayPort, String arrayID, String arrayPortWWN, String zoneSet, String switchH, String switchA, String switchPORTH, String switchPORTA) {
        this.zoneName = zoneName;
        this.hostName = hostName;
        this.hbaWWN = hbaWWN;
        this.arrayPort = arrayPort;
        this.arrayID = arrayID;
        this.arrayPortWWN = arrayPortWWN;
        this.zoneSet = zoneSet;
        this.switchH = switchH;
        this.switchA = switchA;
        this.switchPORTH = switchPORTH;
        this.switchPORTA = switchPORTA;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "fubricWWN='" + fubricWWN + '\'' +
                ", zoneName='" + zoneName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", hbaWWN='" + hbaWWN + '\'' +
                ", arrayPort='" + arrayPort + '\'' +
                ", arrayID='" + arrayID + '\'' +
                ", arrayPortWWN='" + arrayPortWWN + '\'' +
                ", zoneSet='" + zoneSet + '\'' +
                ", vsan='" + vsan + '\'' +
                ", switchH='" + switchH + '\'' +
                ", switchA='" + switchA + '\'' +
                ", switchPORTH='" + switchPORTH + '\'' +
                ", switchPORTA='" + switchPORTA + '\'' +
                '}';
    }

    public String getFubricWWN() {
        return fubricWWN;
    }

    public void setFubricWWN(String fubricWWN) {
        this.fubricWWN = fubricWWN;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHbaWWN() {
        return hbaWWN;
    }

    public void setHbaWWN(String hbaWWN) {
        this.hbaWWN = hbaWWN;
    }

    public String getArrayPort() {
        return arrayPort;
    }

    public void setArrayPort(String arrayPort) {
        this.arrayPort = arrayPort;
    }

    public String getArrayID() {
        return arrayID;
    }

    public void setArrayID(String arrayID) {
        this.arrayID = arrayID;
    }

    public String getArrayPortWWN() {
        return arrayPortWWN;
    }

    public void setArrayPortWWN(String arrayPortWWN) {
        this.arrayPortWWN = arrayPortWWN;
    }

    public String getZoneSet() {
        return zoneSet;
    }

    public void setZoneSet(String zoneSet) {
        this.zoneSet = zoneSet;
    }

    public String getVsan() {
        return vsan;
    }

    public void setVsan(String vsan) {
        this.vsan = vsan;
    }

    public String getSwitchH() {
        return switchH;
    }

    public void setSwitchH(String switchH) {
        this.switchH = switchH;
    }

    public String getSwitchA() {
        return switchA;
    }

    public void setSwitchA(String switchA) {
        this.switchA = switchA;
    }

    public String getSwitchPORTH() {
        return switchPORTH;
    }

    public void setSwitchPORTH(String switchPORTH) {
        this.switchPORTH = switchPORTH;
    }

    public String getSwitchPORTA() {
        return switchPORTA;
    }

    public void setSwitchPORTA(String switchPORTA) {
        this.switchPORTA = switchPORTA;
    }

    public void disassembleZone(Zone zone, Map<String,Set<String>> nodesByZoneNameMap, Set<Node> nodesSet, List edgesArr){

        if(zone.getHostName() == null)
            zone.setHostName("unknown");
        else {
            Node nodeHostName = new Node(zone.getHostName(), zone.getHostName(), "HOST", zone.getHostName(), "red");
            nodesSet.add(nodeHostName);
        }

        if(zone.getHbaWWN() == null)
            zone.setHbaWWN("unknown");
        else {
            Node nodeHbaWWN = new Node(zone.getHbaWWN(), zone.getHbaWWN(), "HBAWWN", zone.getHbaWWN(), "red");
            nodesSet.add(nodeHbaWWN);
        }

        if(zone.getArrayPort() == null)
            zone.setArrayPort("unknown");
        else {
            Node nodeArrayPort = new Node(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(),
                    zone.getArrayPort(), "ARRAYPORT", zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), "red");
            nodesSet.add(nodeArrayPort);
        }

        if(zone.getArrayID() == null)
            zone.setArrayID("unknown");
        else {
            Node nodeArrayID = new Node(zone.getArrayID(), zone.getArrayID(), "ARRAYID", zone.getArrayID(), "red");
            nodesSet.add(nodeArrayID);
        }

        if(zone.getSwitchH() == null)
            zone.setSwitchH("unknown");
        else {
            Node nodeSwitch = new Node(zone.getSwitchH(), zone.getSwitchH(), "SWITCH", zone.getSwitchH(), "red");
            nodesSet.add(nodeSwitch);
        }

        if(zone.getSwitchPORTH() == null)
            zone.setSwitchPORTH("unknown");
        else {
            Node nodeSwitchPortH = new Node(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchPORTH(), "SWITCH", zone.getSwitchPORTH() + "::" + zone.getSwitchH(), "red");
            nodesSet.add(nodeSwitchPortH);
        }

        if(zone.getSwitchPORTA() == null)
            zone.setSwitchPORTA("unknown");
        else {
            Node nodeSwitchPortA = new Node(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchPORTA(), "SWITCH", zone.getSwitchPORTA() + "::" + zone.getSwitchA(), "red");
            nodesSet.add(nodeSwitchPortA);
        }

        edgesArr.add(new Edge(zone.getHbaWWN(), zone.getHostName()));
        edgesArr.add(new Edge(zone.getHbaWWN(), zone.getSwitchPORTH() + "::" + zone.getSwitchH()));
        edgesArr.add(new Edge(zone.getSwitchPORTH() + "::" + zone.getSwitchH(), zone.getSwitchH()));
        edgesArr.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getArrayID()));
        edgesArr.add(new Edge(zone.getArrayPort() + "::" + zone.getArrayID() + "::" + zone.getArrayPortWWN(), zone.getSwitchPORTA() + "::" + zone.getSwitchA()));
        edgesArr.add(new Edge(zone.getSwitchPORTA() + "::" + zone.getSwitchA(), zone.getSwitchH()));
//        nodesByZoneNameMap.put(zoneName,)
    };
}
