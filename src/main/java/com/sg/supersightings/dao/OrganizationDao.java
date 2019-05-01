package com.sg.supersightings.dao;

import com.sg.supersightings.models.Organization;
import com.sg.supersightings.models.SuperPerson;

import java.util.List;

public interface OrganizationDao {
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    Organization addOrg(Organization org);
    void updateOrg(Organization org);
    void deleteOrgById(int id);

}
