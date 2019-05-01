package com.sg.supersightings.dao;

import com.sg.supersightings.models.SuperPerson;

import java.util.List;

public interface SuperPersonDao {
    SuperPerson getSuperById(int id);
    List<SuperPerson> getAllSupers();
    SuperPerson addSuperPerson(SuperPerson superPerson);
    void updateSuper(SuperPerson superPerson);
    void deleteSuperById(int id);

}
