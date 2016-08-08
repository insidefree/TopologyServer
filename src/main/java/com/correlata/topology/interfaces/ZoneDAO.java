package com.correlata.topology.interfaces;

import com.correlata.topology.dao.Edge;
import com.correlata.topology.dao.Zone;
import javax.sql.DataSource;

import java.util.List;

/**
 * Created by Denis on 15/12/13.
 */
public interface ZoneDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    public void setDataSource(DataSource ds);
    /**
     * This is the method to be used to create
     * a record in the Student table.
     */
    //public void create(String name, Integer age);
    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    //public Zone getZone(Integer id);
    /**
     * This is the method to be used to list down
     * all the records from the Zone table.
     */
    //public List<Zone> listZones();
    /**
     * This is the method to be used to list down
     * all the records from the Zone table by max date.
     */
    public List<Zone> listZonesByMaxDate();
    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    //public void delete(Integer id);
    /**
     * This is the method to be used to update
     * a record into the Student table.
     */
    //public void update(Integer id, Integer age);
    /**
     * This is the method to be used to list down
     * all EDGES from the table.
     */
//    public List<Edge> listEdges();
}
