package com.sg.supersightings.dao;

import com.sg.supersightings.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE locationId = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, address, description, lat, lng) VALUES (?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getAddress(),
                location.getDescription(),
                location.getLat(),
                location.getLng());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, address = ?, description = ?," +
                "lat = ?, lng = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getAddress(),
                location.getDescription(),
                location.getLat(),
                location.getLng());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SUPERPERSON_SIGHTING = "DELETE si.* FROM superPerson_sighting si "
                + "JOIN sighting s ON si.sightingId = s.sightingId WHERE s.locationId = ?";
        jdbc.update(DELETE_SUPERPERSON_SIGHTING, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_LOCATION = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("locationId"));
            location.setName(rs.getString("name"));
            location.setAddress(rs.getString("address"));
            location.setDescription(rs.getString("description"));
            location.setLat(rs.getBigDecimal("lat"));
            location.setLng(rs.getBigDecimal("lng"));

            return location;
        }
    }

}
