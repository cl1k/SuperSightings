package com.sg.supersightings.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Sighting {
    private int id;
    private LocalDate date;
    private Location location;
    private List<SuperPerson> superPersonList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<SuperPerson> getSuperPersonList() {
        return superPersonList;
    }

    public void setSuperPersonList(List<SuperPerson> superPersonList) {
        this.superPersonList = superPersonList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id &&
                date.equals(sighting.date) &&
                location.equals(sighting.location) &&
                superPersonList.equals(sighting.superPersonList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, location, superPersonList);
    }
}
