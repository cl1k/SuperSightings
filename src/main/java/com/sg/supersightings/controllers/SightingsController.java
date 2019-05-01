package com.sg.supersightings.controllers;

import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.dao.SuperPersonDao;
import com.sg.supersightings.models.Location;
import com.sg.supersightings.models.Sighting;
import com.sg.supersightings.models.SuperPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class SightingsController {

    @Autowired
    SuperPersonDao superPersonDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    LocationDao locationDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superPersonList", superPersonList);
        model.addAttribute("locations", locations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest req) {
        LocalDate date = LocalDate.parse(req.getParameter("date"));
        Location location = locationDao.getLocationById(Integer.parseInt(req.getParameter("locationId")));

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setLocation(location);

        String[] superPersonIds = req.getParameterValues("superPersonId");

        if (superPersonIds != null) {
            List<SuperPerson> superPersonList = new ArrayList<>();
            for (String superPersonId : superPersonIds) {
                superPersonList.add(superPersonDao.getSuperById(Integer.parseInt(superPersonId)));
            }

            sighting.setSuperPersonList(superPersonList);
            sightingDao.addSighting(sighting);

            return "redirect:/sightings";
        }
        return null;
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);

        List<SuperPerson> superPersonList = superPersonDao.getAllSupers();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("superPersonList", superPersonList);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

//    @PostMapping("editSighting")


}
