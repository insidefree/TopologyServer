package com.correlata.topology.repository;

import com.correlata.topology.dao.Zone;
import com.correlata.topology.interfaces.TopologyConstants;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Denis on 15/12/13.
 */
public class ZoneMapper implements RowMapper<Zone> {
    public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zone zone = new Zone();

        zone.setFubricWWN(rs.getString(TopologyConstants.FABRICWWN));
        zone.setZoneName(rs.getString(TopologyConstants.ZONENAME));
        zone.setHostName(rs.getString(TopologyConstants.HOSTNAME));
        zone.setHbaWWN(rs.getString(TopologyConstants.HBAWWN));
        zone.setArrayPort(rs.getString(TopologyConstants.ARRAYPORT));
        zone.setArrayID(rs.getString(TopologyConstants.ARRAYID));
        zone.setArrayPortWWN(rs.getString(TopologyConstants.ARRAYPORTWWN));
        zone.setZoneSet(rs.getString(TopologyConstants.ZONESET));
        zone.setVsan(rs.getString(TopologyConstants.VSAN));
        zone.setSwitchH(rs.getString(TopologyConstants.SWITCH_H));
        zone.setSwitchA(rs.getString(TopologyConstants.SWITCH_A));
        zone.setSwitchPORTH(rs.getString(TopologyConstants.SWITCHPORT_H));
        zone.setSwitchPORTA(rs.getString(TopologyConstants.SWITCHPORT_A));

        return zone;
    }
}
