package com.sg.supersightings.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    private int id;
    private String name;
    private String description;
    private String address;
    private BigDecimal lat;
    private BigDecimal lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                name.equals(location.name) &&
                Objects.equals(description, location.description) &&
                address.equals(location.address) &&
                lat.equals(location.lat) &&
                lng.equals(location.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, lat, lng);
    }
}
