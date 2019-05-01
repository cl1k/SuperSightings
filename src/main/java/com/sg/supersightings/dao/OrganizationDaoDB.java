package com.sg.supersightings.dao;

import com.sg.supersightings.dao.SuperPersonDaoDB.SuperMapper;
import com.sg.supersightings.models.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrgById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            org.setSuperPersonList(getSupersForOrg(id));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<SuperPerson> getSupersForOrg(int id) {
        final String GET_SUPERS_FOR_ORG = "SELECT s.* FROM superPerson s " +
                "JOIN superPerson_organization so ON so.superPersonId = s.superPersonId " +
                "WHERE so.organizationId = ?";
        return jdbc.query(GET_SUPERS_FOR_ORG, new SuperMapper(), id);
    }

    @Override
    public List<Organization> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
        associateSupersWithOrg(organizations);
        return organizations;
    }

    private void associateSupersWithOrg(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setSuperPersonList(getSupersForOrg(organization.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrg(Organization org) {
        final String INSERT_ORG = "INSERT INTO organization(name, description, address, email, phone) " +
                "VALUES (?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getEmail(),
                org.getPhone());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertOrganizationSuper(org);
        return org;
    }

    private void insertOrganizationSuper(Organization org) {
        final String INSERT_ORGANIZATION_SUPER = "INSERT INTO superperson_organization(organizationId, superPersonId) " +
                "VALUES(?,?)";
        for (SuperPerson superPerson : org.getSuperPersonList()) {
            jdbc.update(INSERT_ORGANIZATION_SUPER,
                    org.getId(),
                    superPerson.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrg(Organization org) {
        final String UPDATE_ORG = "UPDATE organization SET name = ?, description = ?, address = ?, email = ?, phone = ?";
        jdbc.update(UPDATE_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getEmail(),
                org.getPhone());

        final String DELETE_ORGANIZATION_SUPER = "DELETE FROM superPerson_organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPER, org.getId());
        insertOrganizationSuper(org);
    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_ORGANIZATION_SUPER = "DELETE FROM superPerson_organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPER, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE organizationId = ?";
        jdbc.update(DELETE_ORG, id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("organizationId"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setEmail(rs.getString("email"));
            organization.setPhone(rs.getString("phone"));
            return organization;
        }

    }

}
