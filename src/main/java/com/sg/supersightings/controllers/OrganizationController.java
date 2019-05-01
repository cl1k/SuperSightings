package com.sg.supersightings.controllers;

import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.dao.SuperPersonDao;
import com.sg.supersightings.models.Organization;
import com.sg.supersightings.models.SuperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    SuperPersonDao superPersonDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    LocationDao locationDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizationsList = organizationDao.getAllOrgs();
        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();
        model.addAttribute("organizations", organizationsList);
        model.addAttribute("superPersonList", superPersonList);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest req) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        Organization org = new Organization();
        org.setName(name);
        org.setDescription(description);
        org.setAddress(address);
        org.setEmail(email);
        org.setPhone(phone);

        String[] superPersonIds = req.getParameterValues("superPersonId");

        if (superPersonIds != null) {
            List<SuperPerson> superPersonList = new ArrayList<>();
            for (String superPersonId : superPersonIds) {
                superPersonList.add(superPersonDao.getSuperById(Integer.parseInt(superPersonId)));
            }

            org.setSuperPersonList(superPersonList);
            organizationDao.addOrg(org);

            return "redirect:/organizations";
        }
        return null;
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        organizationDao.deleteOrgById(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization org = organizationDao.getOrgById(id);

        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();

        model.addAttribute("organization", org);
        model.addAttribute("superPersonList", superPersonList);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization org, HttpServletRequest req) {
        String[] superPersonIds = req.getParameterValues("superPersonId");

        List<SuperPerson> superPersonList = new ArrayList<>();
        for (String superPersonId : superPersonIds) {
            superPersonList.add(superPersonDao.getSuperById(Integer.parseInt(superPersonId)));
        }
        org.setSuperPersonList(superPersonList);
        organizationDao.updateOrg(org);

        return "redirect:/organizations";
    }

}