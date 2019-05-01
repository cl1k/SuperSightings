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
public class LocationDaoDBTest {

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
    public void addAndGetLocation() {
        Location location = new Location();
        location.setName("Test One");
        location.setDescription("Test Desc");
        location.setAddress("Test Addr");
        location.setLat(new BigDecimal("22.220000"));
        location.setLng(new BigDecimal("11.110000"));
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void getAllLocations() {
        Location location = new Location();
        location.setName("Test First");
        location.setDescription("A Nice Place");
        location.setAddress("Over There");
        location.setLat(new BigDecimal("33.333333"));
        location.setLng(new BigDecimal("44.444444"));
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Second");
        location2.setDescription("A Less Nice Place");
        location2.setAddress("Over Here");
        location2.setLat(new BigDecimal("55.333333"));
        location2.setLng(new BigDecimal("66.444444"));
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void updateLocation() {
        Location location = new Location();
        location.setName("Tester");
        location.setDescription("A Test");
        location.setAddress("Test Place");
        location.setLat(new BigDecimal("11.111111"));
        location.setLng(new BigDecimal("22.222222"));
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("A New Name");
        locationDao.updateLocation(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    public void deleteLocationById() {

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
        sighting = sightingDao.addSighting(sighting);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getId());

        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }
}