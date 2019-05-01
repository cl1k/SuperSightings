package com.sg.supersightings.dao;

import com.sg.supersightings.dao.LocationDaoDB.LocationMapper;
import com.sg.supersightings.dao.SuperPersonDaoDB.SuperMapper;
import com.sg.supersightings.models.Location;
import com.sg.supersightings.models.Sighting;
import com.sg.supersightings.models.SuperPerson;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setSuperPersonList(getSupersForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l" +
                " JOIN sighting s ON s.locationId = l.locationId WHERE s.sightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private List<SuperPerson> getSupersForSighting(int id) {
        final String SELECT_SUPERS_FOR_SIGHTING = "SELECT s.* FROM superPerson s " +
                "JOIN superPerson_sighting si ON si.superPersonId = s.superPersonId WHERE si.sightingId = ?";
        return jdbc.query(SELECT_SUPERS_FOR_SIGHTING, new SuperMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    private void associateLocationAndSupers(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSuperPersonList(getSupersForSighting(sighting.getId()));
        }
    }

    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION,
                new SightingMapper(), location.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    public List<Sighting> getSightingsForSupers(SuperPerson superPerson) {
        final String SELECT_SIGHTINGS_FOR_SUPERS = "SELECT i.* FROM sighting i JOIN superPerson_sighting si " +
                "ON si.sightingId = i.sightingId WHERE si.superPersonId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERS,
                new SightingMapper(), superPerson.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, locationId) VALUES(?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertSightingSuper(sighting);
        return sighting;
    }

    private void insertSightingSuper(Sighting sighting) {
        final String INSERT_SIGHTING_SUPER = "INSERT INTO superPerson_sighting(sightingId, superPersonId) VALUES(?,?)";
        for (SuperPerson superPerson : sighting.getSuperPersonList()) {
            jdbc.update(INSERT_SIGHTING_SUPER,
                    sighting.getId(),
                    superPerson.getId());
        }
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?, locationId = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getId());

        final String DELETE_SIGHTING_SUPER = "DELETE FROM superPerson_sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING_SUPER, sighting.getId());
        insertSightingSuper(sighting);
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING_SUPER = "DELETE FROM superPerson_sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING_SUPER, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }

    }

}
