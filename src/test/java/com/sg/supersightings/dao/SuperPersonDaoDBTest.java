package com.sg.supersightings.dao;

import com.sg.supersightings.models.Location;
import com.sg.supersightings.models.Organization;
import com.sg.supersightings.models.Sighting;
import com.sg.supersightings.models.SuperPerson;
import net.bytebuddy.implementation.bind.annotation.Super;
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
public class SuperPersonDaoDBTest {


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
    public void addAndGetSuperPerson() {
        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Test One");
        superPerson.setDescription("Test Desc");
        superPerson.setSuperPower("Cool Power");
        superPerson.setVillain(false);
        superPerson = superPersonDao.addSuperPerson(superPerson);

        SuperPerson fromDao = superPersonDao.getSuperById(superPerson.getId());

        assertEquals(superPerson, fromDao);
    }

    @Test
    public void updateSuperPerson() {
        SuperPerson superPerson = new SuperPerson();
        superPerson.setName("Tester");
        superPerson.setDescription("A Test");
        superPerson.setSuperPower("A Less Cool Power");
        superPerson.setVillain(true);
        superPerson = superPersonDao.addSuperPerson(superPerson);

        SuperPerson fromDao = superPersonDao.getSuperById(superPerson.getId());
        assertEquals(superPerson, fromDao);

        superPerson.setName("A New Name");
        superPersonDao.updateSuper(superPerson);

        assertNotEquals(superPerson, fromDao);

        fromDao = superPersonDao.getSuperById(superPerson.getId());

        assertEquals(superPerson, fromDao);
    }

    @Test
    public void deleteSuperById() {

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

        SuperPerson fromDao = superPersonDao.getSuperById(superPerson.getId());
        assertEquals(superPerson, fromDao);

        superPersonDao.deleteSuperById(superPerson.getId());

        fromDao = superPersonDao.getSuperById(superPerson.getId());
        assertNull(fromDao);
    }
}