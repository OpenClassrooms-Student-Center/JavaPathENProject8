package user.controller;

import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import user.model.User;
import user.model.UserReward;
import user.service.UserService;
import user.service.UserServiceInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

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
    public void addUser(@RequestParam String userName, @RequestParam String phoneNumber, @RequestParam String emailAddress) {
        logger.info("addUser(" + userName + ","+ phoneNumber + ","+ emailAddress + ")");

        userServiceInterface.addUser(new User(UUID.randomUUID(), userName, phoneNumber, emailAddress));
    }

    @PostMapping(value = "/addToVisitedLocations")
    public void addToVisitedLocations(@RequestParam String userName, @RequestParam double longitude, @RequestParam double latitude, @RequestParam String timeVisited) throws ParseException {
        logger.info("addToVisitedLocations(" + userName + ","+ longitude + ","+ latitude + ","+ timeVisited + ")");

        User user = userServiceInterface.getUser(userName);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(timeVisited);
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(longitude, latitude), date);

        userServiceInterface.addToVisitedLocations(userName, visitedLocation);
    }

    @PostMapping(value = "/addUserReward")
    public void addUserReward(@RequestParam String userName, @RequestParam double longitude, @RequestParam double latitude, @RequestParam String timeVisited, @RequestParam String attractionName, @RequestParam String attractionCity, @RequestParam String attractionState) throws ParseException {
        logger.info("addUserReward(" + userName + ","+ longitude + ","+ latitude + ","+ timeVisited + ","+ attractionName + ","+ attractionCity + ","+ attractionState + ")");

        User user = userServiceInterface.getUser(userName);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(timeVisited);
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(longitude, latitude), date);
        Attraction attraction = new Attraction(attractionName, attractionCity, attractionState, latitude, longitude);
        UserReward userReward = new UserReward(visitedLocation, attraction, 0);

        userServiceInterface.addUserReward(userName, userReward);
    }

    @GetMapping(value = "/getUser")
    public String getUser(@RequestParam String userName) {
        logger.info("getUser(" + userName + ")");

        return JsonStream.serialize(userServiceInterface.getUser(userName));
    }

    @GetMapping(value = "/getAllUser")
    public String getAllUser() {
        logger.info("getAllUser()");

        return JsonStream.serialize(userServiceInterface.getAllUser());
    }
}