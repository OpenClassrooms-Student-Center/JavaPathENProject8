package user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.model.User;
import user.model.UserPreferences;
import user.model.UserReward;
import user.service.UserService;
import user.service.UserServiceInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * This class allows to intercept user requests
 */
@RestController
public class UserController {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserService userService;

    /**
     * Creates a new UserController
     */
    public UserController() {
        logger.info("UserController()");

        userServiceInterface = userService;
    }

    /**
     * Creates a new UserController with the specified UserServiceInterface
     * @param userServiceInterface : service that this controller will use
     */
    public UserController(UserServiceInterface userServiceInterface) {
        logger.info("UserController(" + userServiceInterface + ")");

        this.userServiceInterface = userServiceInterface;
    }

    /**
     * Intercepts the user adding request
     * @param userName : Name of the User to add
     * @param phoneNumber : Phone number of the User to add
     * @param emailAddress : Email address of the User to add
     */
    @PostMapping(value = "/addUser")
    public void addUser(@RequestParam String userName, @RequestParam String phoneNumber,
                        @RequestParam String emailAddress) {

        logger.info("addUser(" + userName + "," + phoneNumber + "," + emailAddress + ")");

        userServiceInterface.addUser(new User(UUID.randomUUID(), userName, phoneNumber, emailAddress));
    }

    /**
     * Intercepts the user visited location adding request
     * @param userName : Name of the User to add
     * @param longitude : Longitude of the visited location
     * @param latitude : Latitude of the visited location
     * @param timeVisited : Time of the visited location
     */
    @PostMapping(value = "/addToVisitedLocations")
    public void addToVisitedLocations(@RequestParam String userName, @RequestParam double longitude,
                                      @RequestParam double latitude, @RequestParam String timeVisited) {

        logger.info("addToVisitedLocations(" + userName + "," + longitude + "," + latitude + "," + timeVisited + ")");

        User user = userServiceInterface.getUser(userName);

        Date date = null;

        try {

            date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(timeVisited);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(longitude,latitude), date);

        userServiceInterface.addToVisitedLocations(userName, visitedLocation);
    }

    /**
     * Intercepts the user reward adding request
     * @param userName : Name of the User to add
     * @param longitude : Longitude of the visited location
     * @param latitude : Latitude of the visited location
     * @param timeVisited : Time of the visited location
     * @param attractionName : Name of the attraction
     * @param attractionCity : Name of the city of the attraction
     * @param attractionState : Name of the state of the city
     */
    @PostMapping(value = "/addUserReward")
    public void addUserReward(@RequestParam String userName, @RequestParam double longitude,
                              @RequestParam double latitude, @RequestParam String timeVisited,
                              @RequestParam String attractionName, @RequestParam String attractionCity,
                              @RequestParam String attractionState) throws ParseException {

        logger.info("addUserReward(" + userName + "," + longitude + "," + latitude + "," + timeVisited + ","
                + attractionName + "," + attractionCity + "," + attractionState + ")");

        User user = userServiceInterface.getUser(userName);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(timeVisited);
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(longitude, latitude), date);
        Attraction attraction = new Attraction(attractionName, attractionCity, attractionState, latitude, longitude);
        UserReward userReward = new UserReward(visitedLocation, attraction, 0);

        userServiceInterface.addUserReward(userName, userReward);
    }

    /**
     * Intercepts the user preferences setting request
     * @param userName : Name of the User to add
     * @param tripDuration : Duration of the trip
     * @param ticketQuantity : Quantity of ticket
     * @param numberOfAdults : Number of adults
     * @param numberOfChildren : Number of children
     * @param attractionProximity : Proximity of attractions
     * @param highPricePoint : Higher price
     * @param lowerPricePoint : Lower price
     */
    @PostMapping(value = "/setUserPreferences")
    public void setUserPreferences(@RequestParam String userName, @RequestParam int tripDuration,
                                   @RequestParam int ticketQuantity, @RequestParam int numberOfAdults,
                                   @RequestParam int numberOfChildren, @RequestParam int attractionProximity,
                                   @RequestParam int highPricePoint, @RequestParam int lowerPricePoint) {

        logger.info("setUserPreferences(" + userName + "," + tripDuration + "," + ticketQuantity + ","
                + numberOfAdults + "," + numberOfChildren + "," + attractionProximity + "," + highPricePoint
                + "," + lowerPricePoint + ")");

        UserPreferences userPreferences = new UserPreferences(tripDuration, ticketQuantity, numberOfAdults,
                numberOfChildren, attractionProximity);

        userServiceInterface.setUserPreferences(userName, userPreferences);
    }

    /**
     * Intercepts the user getting request
     * @param userName : Name of the User to found
     * @return The User found (JSon)
     */
    @GetMapping(value = "/getUser")
    public String getUser(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getUser(" + userName + ")");

        return objectMapper.writeValueAsString(userServiceInterface.getUser(userName));
    }

    /**
     * Intercepts the user list getting request
     * @return The User list (JSon)
     */
    @GetMapping(value = "/getAllUser")
    public String getAllUser() throws JsonProcessingException {
        logger.info("getAllUser()");

        return objectMapper.writeValueAsString(userServiceInterface.getAllUser());
    }
}