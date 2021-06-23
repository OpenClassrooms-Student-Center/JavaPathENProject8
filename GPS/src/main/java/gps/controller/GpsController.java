package gps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gps.service.GpsService;
import gps.service.GpsServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class allows to intercept gps requests
 */
@RestController
public class GpsController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    @Autowired
    private GpsServiceInterface gpsServiceInterface;

    @Autowired
    private GpsService gpsService;

    /**
     * Creates a new GpsController
     */
    public GpsController() {
        logger.info("GpsController()");

        gpsServiceInterface = gpsService;
    }

    /**
     * Creates a new GpsController with the specified GpsServiceInterface
     * @param gpsServiceInterface : service that this controller will use
     */
    public GpsController(GpsServiceInterface gpsServiceInterface) {
        logger.info("GpsController(" + gpsServiceInterface + ")");

        this.gpsServiceInterface = gpsServiceInterface;
    }

    /**
     * Intercepts the user location getting request
     * @param userName : Name of the User to use for find his location
     * @return The VisitedLocation of the User (JSon)
     */
    @GetMapping("/getLocation")
    public String getLocation(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getLocation(" + userName + ")");

        return objectMapper.writeValueAsString(gpsServiceInterface.getUserLocation(userName));
    }

    /**
     * Intercepts the attraction getting request
     * @param attractionName : Name of the Attraction to found
     * @return The Attraction found (JSon)
     */
    @GetMapping("/getAttraction")
    public String getAttraction(@RequestParam String attractionName) throws JsonProcessingException {
        logger.info("getAttraction(" + attractionName + ")");

        return objectMapper.writeValueAsString(gpsServiceInterface.getAttraction(attractionName));
    }

    /**
     * Intercepts the attraction list getting request
     * @return The all Attraction (JSon)
     */
    @GetMapping("/getAllAttraction")
    public String getAllAttraction() throws JsonProcessingException {
        logger.info("getAllAttraction()");

        return objectMapper.writeValueAsString(gpsServiceInterface.getAllAttraction());
    }

    /**
     * Intercepts the user location list getting request
     * @return The locations of all users (JSon)
     */
    @GetMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() throws JsonProcessingException {
        logger.info("getAllCurrentLocations()");

        return objectMapper.writeValueAsString(gpsServiceInterface.getAllCurrentLocations());
    }

    /**
     * Intercepts the nearest attraction getting request
     * @param userName : Name of the User to use for find his location
     * @return The nearest attraction list (JSon)
     */
    @GetMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getNearbyAttractions(" + userName + ")");

        gpsServiceInterface.getUserLocation(userName);

        return objectMapper.writeValueAsString(gpsServiceInterface.getNearByAttractions(userName));
    }
}