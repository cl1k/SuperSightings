package com.sg.supersightings.models;

import java.util.List;
import java.util.Objects;

public class Organization {
    private int id;
    private String name;
    private String description;
    private String address;
    private String email;
    private String phone;
    private List<SuperPerson> superPersonList;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Organization that = (Organization) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                address.equals(that.address) &&
                email.equals(that.email) &&
                phone.equals(that.phone) &&
                Objects.equals(superPersonList, that.superPersonList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, email, phone, superPersonList);
    }
}
