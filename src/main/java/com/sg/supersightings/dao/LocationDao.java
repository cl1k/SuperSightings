package com.sg.supersightings.dao;

import com.sg.supersightings.models.Location;

import java.util.List;

public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation (Location location);
    void updateLocation (Location location);
    void deleteLocationById (int id);

}
