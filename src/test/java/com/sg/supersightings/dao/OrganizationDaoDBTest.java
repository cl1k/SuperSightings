package com.sg.supersightings.dao;

import com.sg.supersightings.models.Location;
import com.sg.supersightings.models.Organization;
import com.sg.supersightings.models.Sighting;
import com.sg.supersightings.models.SuperPerson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    LocationDao locationDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SuperPersonDao superPersonDao;

    @Before
    public void setUp() throws Exception {
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();
        for (SuperPerson superPerson : superPersonList) {
            superPersonDao.deleteSuperById(superPerson.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrgs();
        for (Organization organization : organizations) {
            organizationDao.deleteOrgById(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getOrgById() {
    }

    @Test
    public void addGetOrg() {
        Location location = new Location();
        location.setName("Testly");
        location.setDescription("Testman");
        location.setAddress("Around");
        location.setLat(new BigDecimal("00.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Test Hero");
        superPerson.setDescription("Neat");
        superPerson.setSuperPower("A Good Power");
        superPerson.setVillain(false);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperPersonList(supers);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Organization org = new Organization();
        org.setName("Bad Dudes");
        org.setDescription("So Bad.");
        org.setAddress("Addy");
        org.setEmail("aValidEmail@a.com");
        org.setPhone("6664257896");
        org.setSuperPersonList(supers);
        org = organizationDao.addOrg(org);

        Organization fromDao =  organizationDao.getOrgById(org.getId());
        assertEquals(fromDao,org);
    }

    @Test
    public void getAllOrgs() {
        Location location = new Location();
        location.setName("Testly");
        location.setDescription("Testman");
        location.setAddress("Around");
        location.setLat(new BigDecimal("00.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Test Hero");
        superPerson.setDescription("Neat");
        superPerson.setSuperPower("A Good Power");
        superPerson.setVillain(false);
        superPerson = superPersonDao.addSuperPerson(superPerson);
        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperPersonList(supers);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Organization org = new Organization();
        org.setName("Bad Dudes");
        org.setDescription("So Bad.");
        org.setAddress("Addy");
        org.setEmail("aValidEmail@a.com");
        org.setPhone("6664257896");
        org.setSuperPersonList(supers);
        org = organizationDao.addOrg(org);

        Organization org2 = new Organization();
        org2.setName("Good Guys");
        org2.setDescription("VERY GOOD!");
        org2.setAddress("Space");
        org2.setEmail("helpfulheroes@aol.com");
        org2.setPhone("7775556666");
        org2.setSuperPersonList(supers);
        org2 = organizationDao.addOrg(org2);

        List<Organization> orgList = organizationDao.getAllOrgs();
        assertEquals(2, orgList.size());
        assertTrue(orgList.contains(org));
        assertTrue(orgList.contains(org2));
    }

    @Test
    public void updateOrg() {
        Location location = new Location();
        location.setName("Testly");
        location.setDescription("Testman");
        location.setAddress("Around");
        location.setLat(new BigDecimal("00.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Test Hero");
        superPerson.setDescription("Neat");
        superPerson.setSuperPower("A Good Power");
        superPerson.setVillain(false);
        superPerson = superPersonDao.addSuperPerson(superPerson);

        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperPersonList(supers);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Organization org = new Organization();
        org.setName("Bad Dudes");
        org.setDescription("So Bad.");
        org.setAddress("Addy");
        org.setEmail("aValidEmail@a.com");
        org.setPhone("6664257896");
        org.setSuperPersonList(supers);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org, fromDao);

        org.setName("Good Guys");
        org.setDescription("VERY GOOD!");
        org.setAddress("Space");
        org.setEmail("helpfulheroes@aol.com");
        org.setPhone("7775556666");
        org.setSuperPersonList(supers);

        organizationDao.updateOrg(org);

        assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(org, fromDao);
    }

    @Test
    public void deleteOrgById() {
        Location location = new Location();
        location.setName("Testly");
        location.setDescription("Testman");
        location.setAddress("Around");
        location.setLat(new BigDecimal("00.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Test Hero");
        superPerson.setDescription("Neat");
        superPerson.setSuperPower("A Good Power");
        superPerson.setVillain(false);
        superPerson = superPersonDao.addSuperPerson(superPerson);

        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperPersonList(supers);
        sighting.setLocation(location);
        sightingDao.addSighting(sighting);

        Organization org = new Organization();
        org.setName("Bad Dudes");
        org.setDescription("So Bad.");
        org.setAddress("Addy");
        org.setEmail("aValidEmail@a.com");
        org.setPhone("6664257896");
        org.setSuperPersonList(supers);
        org = organizationDao.addOrg(org);

        Organization fromDao = organizationDao.getOrgById(org.getId());
        assertEquals(fromDao, org);

        organizationDao.deleteOrgById(org.getId());

        fromDao = organizationDao.getOrgById(org.getId());
        assertNull(fromDao);

    }
}