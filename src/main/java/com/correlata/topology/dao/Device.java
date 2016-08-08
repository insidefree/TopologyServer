package com.correlata.topology.dao;

/**
 * Created by Denis on 15/12/13.
 */
public class Device {
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
    private String server;
    private String portWWN;
    private String dev;

    public Device() {
    }

    public Device(String fubricWWN, String zoneName, String hbaWWN, String hostName, String SwitchPORTH,
                  String SwitchH, String SwitchPORTA, String SwitchA, String arrayPort,
                  String arrayPortWWN, String arrayID, String zoneSet, String vsan) {
        this.fubricWWN = fubricWWN;
        this.zoneName = zoneName;
        this.hostName = hostName;
        this.hbaWWN = hbaWWN;
        this.arrayPort = arrayPort;
        this.arrayID = arrayID;
        this.arrayPortWWN = arrayPortWWN;
        this.zoneSet = zoneSet;
        this.vsan = vsan;
        this.switchH = SwitchH;
        this.switchA = SwitchA;
        this.switchPORTH = SwitchPORTH;
        this.switchPORTA = SwitchPORTA;
    }

    public Device(String zoneName, String hbaWWN, String hostName, String SwitchPORTH,
                  String SwitchH, String SwitchPORTA, String SwitchA, String arrayPort,
                  String arrayID) {
        this.zoneName = zoneName;
        this.hostName = hostName;
        this.hbaWWN = hbaWWN;
        this.arrayPort = arrayPort;
        this.arrayID = arrayID;
        this.switchH = SwitchH;
        this.switchA = SwitchA;
        this.switchPORTH = SwitchPORTH;
        this.switchPORTA = SwitchPORTA;
    }

    public Device(String server, String hbaWWN, String portWWN, String arrayID, String dev, String arrayPort,String zoneName,String zoneSet,
                  String switchH, String switchPORTH, String switchA, String switchPORTA) {
        this.zoneName = zoneName;
        this.hbaWWN = hbaWWN;
        this.arrayPort = arrayPort;
        this.arrayID = arrayID;
        this.zoneSet = zoneSet;
        this.switchH = switchH;
        this.switchA = switchA;
        this.switchPORTH = switchPORTH;
        this.switchPORTA = switchPORTA;
        this.server = server;
        this.portWWN = portWWN;
        this.dev = dev;
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

    public void setSwitchH(String SwitchH) {
        this.switchH = SwitchH;
    }

    public String getSwitchA() {
        return switchA;
    }

    public void setSwitchA(String SwitchA) {
        this.switchA = SwitchA;
    }

    public String getSwitchPORTH() {
        return switchPORTH;
    }

    public void setSwitchPORTH(String SwitchPORTH) {
        this.switchPORTH = SwitchPORTH;
    }

    public String getSwitchPORTA() {
        return switchPORTA;
    }

    public void setSwitchPORTA(String SwitchPORTA) {
        this.switchPORTA = SwitchPORTA;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPortWWN() {
        return portWWN;
    }

    public void setPortWWN(String portWWN) {
        this.portWWN = portWWN;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    @Override
    public String toString() {
        return "Device{" + "fubricWWN=" + fubricWWN + ", zoneName=" + zoneName + ", hostName=" + hostName + ", hbaWWN=" + hbaWWN + ", arrayPort=" + arrayPort + ", arrayID=" + arrayID + ", arrayPortWWN=" + arrayPortWWN + ", zoneSet=" + zoneSet + ", vsan=" + vsan + ", SwitchH=" + switchH + ", SwitchA=" + switchA + ", SwitchPORTH=" + switchPORTH + ", SwitchPORTA=" + switchPORTA + '}';
    }
}
