package user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.*;
import user.model.User;
import user.model.UserPreferences;
import user.model.UserReward;
import user.service.UserService;
import user.service.UserServiceInterface;

import javax.money.Monetary;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserServiceInterface userServiceInterface;

    public UserController() {
        logger.info("UserController()");

        userServiceInterface = new UserService();
    }

    public UserController(UserServiceInterface userServiceInterface) {
        logger.info("UserController(" + userServiceInterface + ")");

        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping(value = "/addUser")
    public void addUser(@RequestParam String userName, @RequestParam String phoneNumber,
                        @RequestParam String emailAddress) {

        logger.info("addUser(" + userName + "," + phoneNumber + "," + emailAddress + ")");

        userServiceInterface.addUser(new User(UUID.randomUUID(), userName, phoneNumber, emailAddress));
    }

    @PostMapping(value = "/addToVisitedLocations")
    public void addToVisitedLocations(@RequestParam String userName, @RequestParam double longitude,
                                      @RequestParam double latitude, @RequestParam String timeVisited) {

        logger.info("addToVisitedLocations(" + userName + "," + longitude + "," + latitude + "," + timeVisited + ")");

        User user = userServiceInterface.getUser(userName);

        Date date = null;

        try {

            date = new SimpleDateFormat("dd/MM/yyyy").parse(timeVisited);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(longitude,latitude), date);

        userServiceInterface.addToVisitedLocations(userName, visitedLocation);
    }

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

    @PostMapping(value = "/setUserPreferences")
    public void setUserPreferences(@RequestParam String userName, @RequestParam int tripDuration,
                                   @RequestParam int ticketQuantity, @RequestParam int numberOfAdults,
                                   @RequestParam int numberOfChildren, @RequestParam int attractionProximity,
                                   @RequestParam int highPricePoint, @RequestParam int lowerPricePoint) {

        logger.info("addUserReward(" + userName + "," + tripDuration + "," + ticketQuantity + "," + numberOfAdults + ","
                + numberOfChildren + "," + attractionProximity + "," + highPricePoint + "," + lowerPricePoint + ")");

        UserPreferences userPreferences = new UserPreferences(tripDuration, ticketQuantity, numberOfAdults,
                numberOfChildren, attractionProximity, Money.of(highPricePoint, Monetary.getCurrency("USD")),
                Money.of(lowerPricePoint, Monetary.getCurrency("USD")));

        userServiceInterface.setUserPreferences(userName, userPreferences);
    }

    @GetMapping(value = "/getUser")
    public String getUser(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getUser(" + userName + ")");

        return objectMapper.writeValueAsString(userServiceInterface.getUser(userName));
    }

    @GetMapping(value = "/getAllUser")
    public String getAllUser() throws JsonProcessingException {
        logger.info("getAllUser()");

        return objectMapper.writeValueAsString(userServiceInterface.getAllUser());
    }
}