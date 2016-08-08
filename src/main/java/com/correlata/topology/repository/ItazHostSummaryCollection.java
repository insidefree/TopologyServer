package com.correlata.topology.repository;

import com.correlata.topology.dao.Device;
import com.sun.org.apache.xpath.internal.operations.String;

import java.util.*;

/**
 * Created by bench on 08/03/16.
 */
public class ItazHostSummaryCollection {
    Map<String,Set> host = new LinkedHashMap<String, Set>();

    public boolean addDevice(Device device){

        return true;
    }
}