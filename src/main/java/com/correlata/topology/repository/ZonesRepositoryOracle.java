package com.correlata.topology.repository;

import com.correlata.topology.dao.Zone;
import com.correlata.topology.interfaces.ZoneDAO;
//import org.apache.tomcat.jdbc.pool.DataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.util.List;

/**
 * Created by Denis on 15/12/13.
 */
public class ZonesRepositoryOracle implements ZoneDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource ds) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public List<Zone> listZonesByMaxDate() {
        String SQL = "select * from ITSMACTIVEZONE WHERE CURRENTDATE = (SELECT MAX(CURRENTDATE) from ITSMACTIVEZONE)";
        List<Zone> zones = jdbcTemplateObject.query(SQL, new ZoneMapper());
        return zones;
    }
}
