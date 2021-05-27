package gps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gps.service.GpsService;
import gps.service.GpsServiceInterface;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GpsController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private GpsServiceInterface gpsServiceInterface;

    public GpsController() {
        logger.info("GpsController()");

        gpsServiceInterface = new GpsService();
    }

    public GpsController(GpsServiceInterface gpsServiceInterface) {
        logger.info("GpsController(" + gpsServiceInterface + ")");

        this.gpsServiceInterface = gpsServiceInterface;
    }

    @GetMapping("/getLocation")
    public String getLocation(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getLocation(" + userName + ")");

        return objectMapper.writeValueAsString(gpsServiceInterface.getUserLocation(userName));
    }

    @GetMapping("/getAttraction")
    public String getAttraction(@RequestParam String attractionName) throws JsonProcessingException {
        logger.info("getAttraction(" + attractionName + ")");

        return objectMapper.writeValueAsString(gpsServiceInterface.getAttraction(attractionName));
    }

    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() throws JsonProcessingException {
        logger.info("getAllCurrentLocations()");

        return objectMapper.writeValueAsString(gpsServiceInterface.getAllCurrentLocations());
    }

    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getNearbyAttractions(" + userName + ")");

        VisitedLocation visitedLocation = gpsServiceInterface.getUserLocation(userName);

        return objectMapper.writeValueAsString(gpsServiceInterface.getNearByAttractions(userName, visitedLocation));
    }
}