package com.sg.supersightings.controllers;


import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.dao.SuperPersonDao;
import com.sg.supersightings.models.SuperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SupersController {

    @Autowired
    SuperPersonDao superPersonDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    LocationDao locationDao;

    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();
        model.addAttribute("superPersonList", superPersonList);
        return "supers";
    }

    @PostMapping("addSuperPerson")
    public String addSuperPerson(HttpServletRequest req) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String superPower = req.getParameter("superPower");
        Boolean isVillain = Boolean.valueOf(req.getParameter("isVillain"));

        SuperPerson superPerson = new SuperPerson();
        superPerson.setName(name);
        superPerson.setDescription(description);
        superPerson.setSuperPower(superPower);
        superPerson.setVillain(isVillain);

        superPersonDao.addSuperPerson(superPerson);
        return "redirect:/supers";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        superPersonDao.deleteSuperById(id);

        return "redirect:/supers";
    }

    @GetMapping("editSuper")
    public String editSuper(HttpServletRequest req, Model model) {
        int id = Integer.parseInt(req.getParameter("id"));

        SuperPerson superPerson = superPersonDao.getSuperById(id);

        model.addAttribute("superPerson", superPerson);

        return "editSuperPerson";
    }

    @PostMapping("editSuperPerson")
    public String performEditSuperPerson(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));

        SuperPerson superPerson = superPersonDao.getSuperById(id);

        superPerson.setName(req.getParameter("name"));
        superPerson.setDescription(req.getParameter("description"));
        superPerson.setSuperPower(req.getParameter("superPower"));
        superPerson.setVillain(Boolean.valueOf("isVillain"));

        superPersonDao.updateSuper(superPerson);
        return "redirect:/supers";
    }

}
