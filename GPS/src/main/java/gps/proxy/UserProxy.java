package gps.proxy;

import gps.model.User;
import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * This interface allows to send requests to the user api
 */
@Component
@FeignClient(name = "user-api", url = "localhost:8080")
public interface UserProxy {

    /**
     * Send the user getting request
     * @param userName : Name of the User to found
     * @return The User found
     */
    @GetMapping(value = "/getUser", produces = "application/json")
    public User getUser(@RequestParam String userName);

    /**
     * Send the user list getting request
     * @return The User list
     */
    @GetMapping(value = "/getAllUser", produces = "application/json")
    public List<User> getAllUser();

    /**
     * Send the user visited location adding request
     * @param userName : Name of the User to add
     * @param longitude : Longitude of the visited location
     * @param latitude : Latitude of the visited location
     * @param timeVisited : Time of the visited location
     */
    @PostMapping(value = "/addToVisitedLocations", produces = "application/json")
    public void addToVisitedLocations(@RequestParam String userName, @RequestParam double longitude,
                                      @RequestParam double latitude, @RequestParam String timeVisited);
}
