package gps.controller;

import com.jsoniter.output.JsonStream;
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
    public String getLocation(@RequestParam String userName) {
        logger.info("getLocation(" + userName + ")");

        return JsonStream.serialize(gpsServiceInterface.getUserLocation(userName));
    }

    @GetMapping("/getAttraction")
    public String getAttraction(@RequestParam String attractionName) {
        logger.info("getAttraction(" + attractionName + ")");

        return JsonStream.serialize(gpsServiceInterface.getAttraction(attractionName));
    }

    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        logger.info("getAllCurrentLocations()");

        return JsonStream.serialize(gpsServiceInterface.getAllCurrentLocations());
    }

    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) {
        logger.info("getNearbyAttractions(" + userName + ")");

        VisitedLocation visitedLocation = gpsServiceInterface.getUserLocation(userName);

        return JsonStream.serialize(gpsServiceInterface.getNearByAttractions(userName, visitedLocation));
    }
}