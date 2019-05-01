package com.sg.supersightings.controllers;

import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.dao.SuperPersonDao;
import com.sg.supersightings.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class LocationController {

    @Autowired
    SuperPersonDao superPersonDao;
    @Autowired
    SightingDao sightingDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    LocationDao locationDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locationsList = locationDao.getAllLocations();
        model.addAttribute("locations", locationsList);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest req) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String address = req.getParameter("address");
        String lat = req.getParameter("latitude");
        String lng = req.getParameter("longitude");

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLat(new BigDecimal(lat));
        location.setLng(new BigDecimal(lng));

        locationDao.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest req, Model model) {
        int id = Integer.parseInt(req.getParameter("id"));
        Location location = locationDao.getLocationById(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        Location location = locationDao.getLocationById(id);

        location.setName(req.getParameter("name"));
        location.setDescription(req.getParameter("description"));
        location.setAddress(req.getParameter("address"));
        location.setLat(new BigDecimal(req.getParameter("lat")));
        location.setLat(new BigDecimal(req.getParameter("lng")));

        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

}
