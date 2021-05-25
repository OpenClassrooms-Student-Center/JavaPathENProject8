package gps.controller;

import com.jsoniter.output.JsonStream;
import gps.model.User;
import gps.proxy.UserProxy;
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

    private UserProxy userProxy;

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

        User user = userProxy.getUser(userName);

        return JsonStream.serialize(gpsServiceInterface.getUserLocation(user));
    }

    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
        logger.info("getAllCurrentLocations()");

        return JsonStream.serialize(gpsServiceInterface.getAllCurrentLocations());
    }

    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) {
        logger.info("getNearbyAttractions(" + userName + ")");

        User user = userProxy.getUser(userName);

        VisitedLocation visitedLocation = gpsServiceInterface.getUserLocation(user);

        return JsonStream.serialize(gpsServiceInterface.getNearByAttractions(visitedLocation));
    }
}