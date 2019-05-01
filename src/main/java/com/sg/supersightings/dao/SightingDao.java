package com.sg.supersightings.dao;

import com.sg.supersightings.models.Location;
import com.sg.supersightings.models.Sighting;
import com.sg.supersightings.models.SuperPerson;

import java.util.List;

public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    List<Sighting> getSightingsForLocation(Location location);
    List<Sighting> getSightingsForSupers(SuperPerson superPerson);

}
