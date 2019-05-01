package com.sg.supersightings.dao;

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
public class SuperPersonDaoDB implements SuperPersonDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperPerson getSuperById(int id) {
        try {
            final String GET_SUPERPERSON_BY_ID = "SELECT * FROM superperson WHERE superPersonid = ?";
            return jdbc.queryForObject(GET_SUPERPERSON_BY_ID, new SuperMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPerson> getAllSupers() {
        final String GET_ALL_SUPERS = "SELECT * FROM superperson";
        return jdbc.query(GET_ALL_SUPERS, new SuperMapper());
    }

    @Override
    @Transactional
    public SuperPerson addSuperPerson(SuperPerson superPerson) {
        final String INSERT_SUPERPERSON = "INSERT INTO superperson(name, description, superPower, isVillain) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_SUPERPERSON,
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getSuperPower(),
                superPerson.getVillain());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPerson.setId(newId);
        return superPerson;
    }

    @Override
    public void updateSuper(SuperPerson superPerson) {
        final String UPDATE_SUPERPERSON = "UPDATE superperson SET name = ?," +
                "description = ?," +
                "superPower = ?," +
                "isVillain = ?";

        jdbc.update(UPDATE_SUPERPERSON,
                superPerson.getName(),
                superPerson.getDescription(),
                superPerson.getSuperPower(),
                superPerson.getVillain());

    }

    @Override
    @Transactional
    public void deleteSuperById(int id) {
        final String DELETE_ORGANIZATION_SUPERPERSON = "DELETE FROM " +
                "superperson_organization WHERE superpersonId = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERPERSON, id);

        final String DELETE_SIGHTING_SUPERPERSON = "DELETE FROM " +
                "superperson_sighting WHERE superpersonId = ?";
        jdbc.update(DELETE_SIGHTING_SUPERPERSON, id);

        final String DELETE_SUPERPERSON = "DELETE FROM superperson WHERE superpersonId = ?";
        jdbc.update(DELETE_SUPERPERSON, id);
    }

    public static final class SuperMapper implements RowMapper<SuperPerson> {

        @Override
        public SuperPerson mapRow(ResultSet rs, int index) throws SQLException {
            SuperPerson superPerson = new SuperPerson();
            superPerson.setId(rs.getInt("superPersonId"));
            superPerson.setName(rs.getString("name"));
            superPerson.setDescription(rs.getString("description"));
            superPerson.setSuperPower(rs.getString("superPower"));
            superPerson.setVillain(rs.getBoolean("isVillain"));

            return superPerson;
        }

    }

}
