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
public class SightingDaoDBTest {

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
    public void getSightingsForLocal() {
        Location location = new Location();
        location.setName("Testly");
        location.setDescription("Testman");
        location.setAddress("Around");
        location.setLat(new BigDecimal("11.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test-o");
        location2.setDescription("Test Master");
        location2.setAddress("The Happy Path");
        location2.setLat(new BigDecimal("33.111111"));
        location2.setLng(new BigDecimal("44.111111"));
        location2 = locationDao.addLocation(location2);

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

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setSuperPersonList(supers);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDate.now());
        sighting3.setSuperPersonList(supers);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);

        List<Sighting> sightings = sightingDao.getSightingsForLocation(location);
        assertEquals(2,sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));

    }

    @Test
    public void addSightingsToSupers() {
        Location location = new Location();
        location.setName("Test Local");
        location.setDescription("A Place For Tests");
        location.setAddress("Sesame Street");
        location.setLat(new BigDecimal("11.111111"));
        location.setLng(new BigDecimal("22.111111"));
        location = locationDao.addLocation(location);

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Bad Dude");
        superPerson.setDescription("Evil");
        superPerson.setSuperPower("THE STRONGEST POWER");
        superPerson.setVillain(true);
        superPerson = superPersonDao.addSuperPerson(superPerson);

        SuperPerson superPerson2 = new SuperPerson();
        superPerson2.setName("Nice Hero");
        superPerson2.setDescription("Good");
        superPerson2.setSuperPower("Weak Power");
        superPerson2.setVillain(false);
        superPerson2 = superPersonDao.addSuperPerson(superPerson2);

        List<SuperPerson> supers = new ArrayList<>();
        supers.add(superPerson);
        supers.add(superPerson2);

        List<SuperPerson> supers2 = new ArrayList<>();
        supers2.add(superPerson2);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setSuperPersonList(supers);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now());
        sighting2.setSuperPersonList(supers2);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);

        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDate.now());
        sighting3.setSuperPersonList(supers);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);

        List<Sighting> sightings = sightingDao.getSightingsForSupers(superPerson);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
    }

    @Test
    public void addAndGetSighting() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void updateSighting() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.of(1985,7,3));
        SuperPerson super2 = new SuperPerson();
        super2.setName("Test2");
        super2.setDescription("Neat Also");
        super2.setSuperPower("A Pretty Bad Power");
        super2.setVillain(false);
//        super2 = superPersonDao.addSuperPerson(super2);
//        supers.add(super2);
//        sighting.setSuperPersonList(supers);

        sightingDao.updateSighting(sighting);
        assertNotEquals(fromDao, sighting);

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

    }

    @Test
    public void deleteSightingById() {
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

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
}