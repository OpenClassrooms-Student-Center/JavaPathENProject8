package TripPricer.proxy;

import TripPricer.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tripPricer.Provider;

import java.util.List;

/**
 * This interface allows to send requests to the user api
 */
@FeignClient(name = "user-api", url = "http://user-api:8080")
public interface UserProxy {

    /**
     * Send the user getting request
     * @param userName : Name of the User to found
     * @return The User found
     */
    @GetMapping(value = "/getUser", produces = "application/json")
    public User getUser(@RequestParam String userName);

    /**
     * Send the user trip deals setting request
     * @param userName : Name of the User to found
     * @return The User found
     */
    @PostMapping(value = "/setTripDeals", produces = "application/json")
    public void setTripDeals(@RequestParam String userName, @RequestBody List<Provider> tripDeals);
}
